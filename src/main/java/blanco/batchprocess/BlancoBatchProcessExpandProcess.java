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
import blanco.batchprocess.valueobject.BlancoBatchProcessStructure;
import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.BlancoCgInterface;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;

/**
 * Main class for processing (blancoProcess).
 * 
 * Expands the processing interface.
 */
class BlancoBatchProcessExpandProcess {
    /**
     * Target programming language.
     */
    private int fTargetLang = BlancoBatchProcessSupportedLangStringGroup.NOT_DEFINED;

    /**
     * Runtime package.
     */
    private String fRuntimePackage = null;

    /**
     * A factory for blancoCg to be used internally.
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * Source file information for the blancoCg to be used internally.
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * Class information for the blancoCg to be used internally.
     */
    private BlancoCgInterface fCgInterface = null;

    /**
     * Character encoding of the auto-generated source files.
     */
    private String fEncoding = null;

    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * Auto-generates source code based on the collected information.
     * 
     * @param argProcessStructure
     *            Process structure collected from metafiles.
     * @param argRuntimePackage
     *            Runtime package.
     *            For null and zero-length strings, generates a runtime class for each definition document.
     * @param argTargetLang
     *            Target programming language.
     * @param argDirectoryTarget
     *            Output directory of the generated source code.
     */
    public void expandSourceFile(
            final BlancoBatchProcessStructure argProcessStructure,
            final String argRuntimePackage, final int argTargetLang,
            final File argDirectoryTarget) {
        fRuntimePackage = argRuntimePackage;
        fTargetLang = argTargetLang;

        // To make it compatible with the previous version, output to the /main subfolder.
        final File fileBlancoMain = new File(argDirectoryTarget
                .getAbsolutePath()
                + "/main");

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(argProcessStructure
                .getPackage(), null);
        fCgSourceFile.setEncoding(fEncoding);
        fCgInterface = fCgFactory.createInterface(
                getProcessInterfaceName(argProcessStructure), BlancoStringUtil
                        .null2Blank(argProcessStructure.getDescription()));
        fCgSourceFile.getInterfaceList().add(fCgInterface);

        fCgInterface.setAccess("");

        fCgInterface.setDescription("Process ["
                + getProcessInterfaceName(argProcessStructure) + "] interface.");
        fCgInterface.getLangDoc().getDescriptionList().add(
                "Inherit this interface and create a [" + getProcessInterfaceName(argProcessStructure) 
                        + "class in the ["
                        + argProcessStructure.getPackage()
                        + "] package to implement the actual batch processing.<br>");
        fCgInterface.getLangDoc().getDescriptionList().add("");

        expandMethodExecute(argProcessStructure);

        expandMethodProgress();

        {
            final BlancoBatchProcessExpandProcessInput expandProcessInput = new BlancoBatchProcessExpandProcessInput();
            expandProcessInput.setEncoding(fEncoding);
            expandProcessInput.expandSourceFile(argProcessStructure,
                    argDirectoryTarget);
        }

        if (BlancoStringUtil.null2Blank(
                argProcessStructure.getOutput().getEndBatchProcessException())
                .length() > 0) {
            // Generates only if the value for batch processing exception termination is set.

            fCgSourceFile.getImportList().add(
                    getBatchProcessExceptionClassName(argProcessStructure));

            if (BlancoStringUtil.null2Blank(fRuntimePackage).length() == 0) {
                // Generates runtime classes in the same package for each definition document.
                new BlancoBatchProcessExpandException().expandSourceFile(
                        argProcessStructure.getPackage(), fTargetLang,
                        argDirectoryTarget);
            } else {
                // Generates a class in the specified runtime package since there is a runtime package specification.
                new BlancoBatchProcessExpandException().expandSourceFile(
                        fRuntimePackage, fTargetLang, argDirectoryTarget);
            }
        }

        switch (fTargetLang) {
        case BlancoBatchProcessSupportedLangStringGroup.JAVA:
            BlancoCgTransformerFactory.getJavaSourceTransformer().transform(
                    fCgSourceFile, fileBlancoMain);
            break;
        default:
            break;
        }
    }

    /**
     * Expands the execute method.
     * 
     * @param argProcessStructure
     *            Process structure data collected from metafiles.
     */
    private void expandMethodExecute(
            final BlancoBatchProcessStructure argProcessStructure) {
        final BlancoCgMethod method = fCgFactory.createMethod("execute",
                "The entry point for intastantiating the class and executing the process.");
        fCgInterface.getMethodList().add(method);
        method.setFinal(true);

        method
                .getParameterList()
                .add(
                        fCgFactory
                                .createParameter(
                                        "input",
                                        argProcessStructure.getPackage()
                                                + ".valueobject."
                                                + BlancoBatchProcessExpandProcessInput
                                                        .getBatchProcessValueObjectInputClassName(argProcessStructure),
                                        "Innput parameters for a process."));
        method.setReturn(fCgFactory.createReturn("int", "Result of the process."));
        method.getThrowList().add(
                fCgFactory.createException("java.io.IOException",
                        "If an I/O exception occurs."));
        method.getThrowList().add(
                fCgFactory
                        .createException("java.lang.IllegalArgumentException",
                                "If an invalid input value is found."));
    }

    /**
     * Expands the execute method.
     * 
     * @param argProcessStructure
     *            Process structure collected from metafiles.
     */
    private void expandMethodProgress() {
        final BlancoCgMethod method = fCgFactory.createMethod("progress",
                "Whenever an item is processed in the process, it is called back as a progress report.");
        fCgInterface.getMethodList().add(method);
        method.setFinal(true);

        method.getParameterList().add(
                fCgFactory.createParameter("argProgressMessage",
                        "java.lang.String", "Message about the item currently processed."));
        method.setReturn(fCgFactory.createReturn("boolean",
                "It is false if you want to continue the process, or it is true  if you want to request to suspend the process."));
    }

    /**
     * Gets the name of the processing interface to be output to.
     * 
     * @param argProcessStructure
     * @return
     */
    static String getProcessInterfaceName(
            final BlancoBatchProcessStructure argProcessStructure) {
        return BlancoNameAdjuster.toClassName(argProcessStructure.getName())
                + "Process";
    }

    /**
     * Gets the name of the batch processing exception class.
     * 
     * @param argProcessStructure
     * @return
     */
    String getBatchProcessExceptionClassName(
            final BlancoBatchProcessStructure argProcessStructure) {
        if (BlancoStringUtil.null2Blank(
                argProcessStructure.getOutput().getEndBatchProcessException())
                .length() == 0) {
            throw new IllegalArgumentException(
                    "The method to get the name of BlancoBatchoProcessException class was called while the batch processing exception termination is OFF. This is inconsistent.");
        }

        if (BlancoStringUtil.null2Blank(fRuntimePackage).length() == 0) {
            return argProcessStructure.getPackage()
                    + ".BlancoBatchProcessException";
        } else {
            return fRuntimePackage + ".BlancoBatchProcessException";
        }
    }
}
