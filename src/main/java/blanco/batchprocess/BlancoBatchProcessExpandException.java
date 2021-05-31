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

import java.io.File;

import blanco.batchprocess.stringgroup.BlancoBatchProcessSupportedLangStringGroup;
import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * Outputs the exception class for blancoBatchProcess.
 */
class BlancoBatchProcessExpandException {
    /**
     * A programming language to be output.
     */
    private int fTargetLang = BlancoBatchProcessSupportedLangStringGroup.NOT_DEFINED;

    /**
     * A factory for blancoCg to be used internally.
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * Source file information for blancoCg to be used internally.
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * Class information for blancoCg to be used internally.
     */
    private BlancoCgClass fCgClass = null;

    /**
     * Auto-generates source code based on the collected information.
     * 
     * @param argRuntimePackage
     *            Runtime package.
     * @param argTargetLang
     *            Target programming language.
     * @param argDirectoryTarget
     *            Output directory for the source code.
     */
    public void expandSourceFile(final String argRuntimePackage,
            final int argTargetLang, final File argDirectoryTarget) {
        fTargetLang = argTargetLang;

       // 従来と互換性を持たせるため、/mainサブフォルダに出力します。
        final File fileBlancoMain = new File(argDirectoryTarget
                .getAbsolutePath()
                + "/main");

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(argRuntimePackage, null);
        fCgSourceFile.setEncoding("UTF-8"); // FIXME set encoding from input info.
        fCgClass = fCgFactory.createClass("BlancoBatchProcessException",
                "Used if an exception occurs in batch process. blancoBatchProcess exception.");
        fCgSourceFile.getClassList().add(fCgClass);

        fCgClass.getExtendClassList().add(
                fCgFactory.createType("java.lang.RuntimeException"));

        // Suppresses serial ID warning.
        fCgClass.getAnnotationList().add("SuppressWarnings(\"serial\")");

        // Adds a constructor.
        BlancoCgUtil.addConstructorForException(fCgFactory, fCgClass);

        switch (fTargetLang) {
        case BlancoBatchProcessSupportedLangStringGroup.JAVA:
            BlancoCgTransformerFactory.getJavaSourceTransformer().transform(
                    fCgSourceFile, fileBlancoMain);
            break;
        default:
            break;
        }
    }
}
