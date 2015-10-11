/*
 * blanco Framework
 * Copyright (C) 2004-2006 IGA Tosiki
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.batchprocess;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;

/**
 * blancoCgのための典型的なユーティリティ。
 * 
 * TODO blancoCgに併合できないかどうか検討すること。
 */
class BlancoCgUtil {
    /**
     * 例外クラスのための典型的なコンストラクタ4つを追加します。
     * 
     * @param argCgFactory
     *            blancoCgオブジェクトファクトリのインスタンス。
     * @param argCgClass
     *            クラス情報。
     */
    public static void addConstructorForException(
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgClass argCgClass) {
        {
            final BlancoCgMethod method = argCgFactory.createMethod(argCgClass
                    .getName(), "詳細メッセージを持たない例外を構築します。");
            argCgClass.getMethodList().add(method);

            method.setConstructor(true);
            method.setSuperclassInvocation("super()");
        }

        {
            final BlancoCgMethod method = argCgFactory.createMethod(argCgClass
                    .getName(), "指定された詳細メッセージを持つ例外を構築します。");
            argCgClass.getMethodList().add(method);

            method.setConstructor(true);
            method.setSuperclassInvocation("super(message)");

            method.getParameterList().add(
                    argCgFactory.createParameter("message", "java.lang.String",
                            "詳細メッセージ。"));
        }

        {
            final BlancoCgMethod method = argCgFactory.createMethod(argCgClass
                    .getName(), "指定された詳細メッセージおよび原因を指定して例外を構築します。");
            argCgClass.getMethodList().add(method);

            method.setConstructor(true);
            method.setSuperclassInvocation("super(message, cause)");

            method.getParameterList().add(
                    argCgFactory.createParameter("message", "java.lang.String",
                            "詳細メッセージ。"));
            method.getParameterList().add(
                    argCgFactory.createParameter("cause",
                            "java.lang.Throwable", "原因。"));

        }

        {
            final BlancoCgMethod method = argCgFactory.createMethod(argCgClass
                    .getName(), "原因を指定して例外を構築します。");
            argCgClass.getMethodList().add(method);

            method.setConstructor(true);
            method.setSuperclassInvocation("super(cause)");

            method.getParameterList().add(
                    argCgFactory.createParameter("cause",
                            "java.lang.Throwable", "原因。"));

        }
    }
}
