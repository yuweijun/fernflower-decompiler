package com.example.test.javap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectInnerClass {
   public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
      Class innerClass = Class.forName("com.example.test.javap.OuterClass$InnerClass");
      Constructor[] declaredConstructors = innerClass.getDeclaredConstructors();
      Constructor[] var3 = declaredConstructors;
      int var4 = declaredConstructors.length;

      int var5;
      Constructor syntheticConstructor;
      for(var5 = 0; var5 < var4; ++var5) {
         syntheticConstructor = var3[var5];
         System.out.printf("%14s\t%s\n", "constructor : ", syntheticConstructor);
      }

      Method[] declaredMethods = innerClass.getDeclaredMethods();
      Method[] var13 = declaredMethods;
      var5 = declaredMethods.length;

      int var16;
      for(var16 = 0; var16 < var5; ++var16) {
         Method declaredMethod = var13[var16];
         System.out.printf("%14s\t%s\n", "method : ", declaredMethod);
      }

      Field[] declaredFields = innerClass.getDeclaredFields();
      Field[] var15 = declaredFields;
      var16 = declaredFields.length;

      Field field_1;
      for(int var18 = 0; var18 < var16; ++var18) {
         field_1 = var15[var18];
         System.out.printf("%14s\t%s\n", "field : ", field_1);
      }

      System.out.println("\n");
      System.out.println("get OuterClass$InnerClass instance using reflect:");
      OuterClass outerClassInstance = new OuterClass();
      syntheticConstructor = declaredConstructors[0];
      Object innerClassInstance = syntheticConstructor.newInstance(outerClassInstance);
      System.out.println("innerClassInstance class is : " + innerClassInstance.getClass());
      System.out.println("\n");
      System.out.println("get field OuterClass$InnerClass.this$0 of OuterClass$InnerClass:");
      field_1 = declaredFields[1];
      Object this_0 = field_1.get(innerClassInstance);
      System.out.println("this$0 is instance of OuterClass : " + (this_0 == outerClassInstance));
      System.out.println("\n");
      System.out.println("run method static int com.example.test.javap.OuterClass$InnerClass.access$000 :");
      Method access_000 = declaredMethods[1];
      access_000.setAccessible(true);
      Object invoked = access_000.invoke(innerClassInstance);
      System.out.println(invoked);
   }
}
