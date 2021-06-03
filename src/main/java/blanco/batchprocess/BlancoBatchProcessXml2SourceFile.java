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
import java.io.IOException;

import blanco.batchprocess.message.BlancoBatchProcessMessage;
import blanco.batchprocess.stringgroup.BlancoBatchProcessSupportedLangStringGroup;
import blanco.batchprocess.valueobject.BlancoBatchProcessStructure;

/**
 * Generates abstract parent class and source code for batch processing from "Batch Processing Definition Form" Excel format.
 * 
 * This class is responsible for generation of source code from intermediate XML files.
 * 
 * @author IGA Tosiki
 */
public class BlancoBatchProcessXml2SourceFile {
    /**
     * Message class.
     */
    protected final BlancoBatchProcessMessage fMsg = new BlancoBatchProcessMessage();

    /**
     * Character encoding of auto-generated source files.
     */
    private String fEncoding = null;

    /**
     * Specifies the character encoding of the generated source file.
     * 
     * @param argEncoding
     *            Character encoding of auto-generated source files.
     */
    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * Auto-generates source code from intermediate XML files.
     * 
     * @param argMetaXmlSourceFile
     *            An XML file that contains meta-information.
     * @param argRuntimePackage
     *            Runtime package.
     *            For null and zero-length strings, generates a runtime class for each definition document.
     * @param argTargetLang
     *            Target programming language.
     * @param argDirectoryTarget
     *            Output directory of the generated source code (specify the part excluding /main).
     * @throws IOException
     *             If an I/O exception occurs.
     */
    public void process(final File argMetaXmlSourceFile,
            final String argRuntimePackage, final String argTargetLang,
            final File argDirectoryTarget) throws IOException {

        final BlancoBatchProcessStructure[] structures = new BlancoBatchProcessXmlParser()
                .parse(argMetaXmlSourceFile);
        if (structures == null || structures.length == 0) {
            // No processing.
            return;
        }

        for (int index = 0; index < structures.length; index++) {
            // Executes automatic source code generation based on the result of parsing meta-information.
            structure2Source(structures[index], argRuntimePackage,
                    argTargetLang, argDirectoryTarget);
        }
    }

    /**
     * Auto-generates Java source code from a given class information value object.
     * 
     * @param argStructure
     *            Information.
     * @param argDirectoryTarget
     *            Output directory of the source code.
     * @throws IOException
     *             If an I/O exception occurs.
     */
    public void structure2Source(
            final BlancoBatchProcessStructure argStructure,
            final String argRuntimePackage, final String argTargetLang,
            final File argDirectoryTarget) throws IOException {
        final int targetLang = new BlancoBatchProcessSupportedLangStringGroup()
                .convertToInt(argTargetLang);
        if (targetLang == BlancoBatchProcessSupportedLangStringGroup.NOT_DEFINED) {
            throw new IllegalArgumentException(fMsg.getMbbpi002(argTargetLang));
        }

        final BlancoBatchProcessExpandBatchProcess expandBatchProcess = new BlancoBatchProcessExpandBatchProcess();
        expandBatchProcess.setEncoding(fEncoding);
        expandBatchProcess.expandSourceFile(argStructure, argRuntimePackage,
                targetLang, argDirectoryTarget);

        final BlancoBatchProcessExpandProcess expandProcess = new BlancoBatchProcessExpandProcess();
        expandProcess.setEncoding(fEncoding);
        expandProcess.expandSourceFile(argStructure, argRuntimePackage,
                targetLang, argDirectoryTarget);
    }
}
