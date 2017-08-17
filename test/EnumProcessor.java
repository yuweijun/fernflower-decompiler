package org.jetbrains.java.decompiler.main;

import java.util.Iterator;
import org.jetbrains.java.decompiler.main.rels.ClassWrapper;
import org.jetbrains.java.decompiler.main.rels.MethodWrapper;
import org.jetbrains.java.decompiler.modules.decompiler.exps.Exprent;
import org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent;
import org.jetbrains.java.decompiler.modules.decompiler.stats.Statement;
import org.jetbrains.java.decompiler.modules.decompiler.stats.Statements;
import org.jetbrains.java.decompiler.struct.StructClass;
import org.jetbrains.java.decompiler.struct.StructField;
import org.jetbrains.java.decompiler.struct.StructMethod;
import org.jetbrains.java.decompiler.util.InterpreterUtil;

public class EnumProcessor {
   public static void clearEnum(ClassWrapper wrapper) {
      StructClass cl = wrapper.getClassStruct();
      Iterator var2 = wrapper.getMethods().iterator();

      while(var2.hasNext()) {
         MethodWrapper method = (MethodWrapper)var2.next();
         StructMethod mt = method.methodStruct;
         String name = mt.getName();
         String descriptor = mt.getDescriptor();
         if ("values".equals(name)) {
            if (descriptor.equals("()[L" + cl.qualifiedName + ";")) {
               wrapper.getHiddenMembers().add(InterpreterUtil.makeUniqueKey(name, descriptor));
            }
         } else if ("valueOf".equals(name)) {
            if (descriptor.equals("(Ljava/lang/String;)L" + cl.qualifiedName + ";")) {
               wrapper.getHiddenMembers().add(InterpreterUtil.makeUniqueKey(name, descriptor));
            }
         } else if ("<init>".equals(name)) {
            Statement firstData = Statements.findFirstData(method.root);
            if (firstData != null && !firstData.getExprents().isEmpty()) {
               Exprent exprent = (Exprent)firstData.getExprents().get(0);
               if (exprent.type == 8) {
                  InvocationExprent invExpr = (InvocationExprent)exprent;
                  if (Statements.isInvocationInitConstructor(invExpr, method, wrapper, false)) {
                     firstData.getExprents().remove(0);
                  }
               }
            }
         }
      }

      var2 = cl.getFields().iterator();

      while(var2.hasNext()) {
         StructField fd = (StructField)var2.next();
         String descriptor = fd.getDescriptor();
         if (fd.isSynthetic() && descriptor.equals("[L" + cl.qualifiedName + ";")) {
            wrapper.getHiddenMembers().add(InterpreterUtil.makeUniqueKey(fd.getName(), descriptor));
         }
      }

   }
}
