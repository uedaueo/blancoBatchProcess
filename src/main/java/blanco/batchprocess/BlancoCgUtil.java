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
 * A typical utility for blancoCg.
 * 
 * TODO Consider whether it could be annexed to blancoCg.
 */
class BlancoCgUtil {
    /**
     * Adds four typical constructors for the exception class.
     * 
     * @param argCgFactory
     *            An instance of the blancoCg object factory.
     * @param argCgClass
     *            Class information.
     */
    public static void addConstructorForException(
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgClass argCgClass) {
        {
            final BlancoCgMethod method = argCgFactory.createMethod(argCgClass
                    .getName(), "Constructs an exception without a detailed message.");
            argCgClass.getMethodList().add(method);

            method.setConstructor(true);
            method.setSuperclassInvocation("super()");
        }

        {
            final BlancoCgMethod method = argCgFactory.createMethod(argCgClass
                    .getName(), "Constructs an exception with a specified detailed message.");
            argCgClass.getMethodList().add(method);

            method.setConstructor(true);
            method.setSuperclassInvocation("super(message)");

            method.getParameterList().add(
                    argCgFactory.createParameter("message", "java.lang.String",
                            "The detailed message."));
        }

        {
            final BlancoCgMethod method = argCgFactory.createMethod(argCgClass
                    .getName(), "Constructs an exception with a specified detailed message and cause.");
            argCgClass.getMethodList().add(method);

            method.setConstructor(true);
            method.setSuperclassInvocation("super(message, cause)");

            method.getParameterList().add(
                    argCgFactory.createParameter("message", "java.lang.String",
                            "The detailed message."));
            method.getParameterList().add(
                    argCgFactory.createParameter("cause",
                            "java.lang.Throwable", "The cause."));

        }

        {
            final BlancoCgMethod method = argCgFactory.createMethod(argCgClass
                    .getName(), "Constructs an exception with a specified cause.");
            argCgClass.getMethodList().add(method);

            method.setConstructor(true);
            method.setSuperclassInvocation("super(cause)");

            method.getParameterList().add(
                    argCgFactory.createParameter("cause",
                            "java.lang.Throwable", "The cause."));

        }
    }
}
