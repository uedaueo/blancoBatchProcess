/*
 * blanco Framework
 * Copyright (C) 2004-2006 IGA Tosiki
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.batchprocess.task;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import blanco.batchprocess.BlancoBatchProcessConstants;
import blanco.batchprocess.BlancoBatchProcessMeta2Xml;
import blanco.batchprocess.BlancoBatchProcessXml2SourceFile;
import blanco.batchprocess.message.BlancoBatchProcessMessage;
import blanco.batchprocess.task.valueobject.BlancoBatchProcessProcessInput;

/**
 * Actual process contents of the batch processing generation.
 */
public class BlancoBatchProcessProcessImpl implements BlancoBatchProcessProcess {
    /**
     * Message class.
     */
    protected final BlancoBatchProcessMessage fMsg = new BlancoBatchProcessMessage();

    protected BlancoBatchProcessProcessInput fInput;

    /**
     * A method to describe the specific batch processing contents.
     * 
     * This method is used to describe the actual process.
     * 
     * @param input
     *            Input parameters for batch process.
     * @return The exit code for batch process.END_SUCCESS in case of normal completion.
     * @throws IOException
     *             If an I/O exception occurs.
     * @throws IllegalArgumentException
     *             If an invalid input value is found.
     */
    public int execute(final BlancoBatchProcessProcessInput input)
            throws IOException, IllegalArgumentException {
        fInput = input;
        System.out.println("- " + BlancoBatchProcessConstants.PRODUCT_NAME
                + " (" + BlancoBatchProcessConstants.VERSION + ")");

        try {
            final File fileMetadir = new File(input.getMetadir());
            if (fileMetadir.exists() == false) {
                throw new IllegalArgumentException(fMsg.getMbbpa001(input
                        .getMetadir()));
            }

            // Creates a temporary directory.
            new File(input.getTmpdir()
                    + BlancoBatchProcessConstants.TARGET_SUBDIRECTORY).mkdirs();

            // Processes the specified meta directory.
            new BlancoBatchProcessMeta2Xml().processDirectory(fileMetadir,
                    input.getTmpdir()
                            + BlancoBatchProcessConstants.TARGET_SUBDIRECTORY);

            // Generates source code from XML-ized intermediate files.
            final File[] fileMeta2 = new File(input.getTmpdir()
                    + BlancoBatchProcessConstants.TARGET_SUBDIRECTORY)
                    .listFiles();
            for (int index = 0; index < fileMeta2.length; index++) {
                if (fileMeta2[index].getName().endsWith(".xml") == false) {
                    continue;
                }

                if (progress("Processing file [" + fileMeta2[index].getName()
                        + "] ...")) {
                    return BlancoBatchProcessBatchProcess.END_ERROR;
                }

                final BlancoBatchProcessXml2SourceFile xml2source = new BlancoBatchProcessXml2SourceFile();
                xml2source.setEncoding(input.getEncoding());
                xml2source.process(fileMeta2[index], input.getRuntimepackage(),
                        input.getTargetlang(), new File(input.getTargetdir()));
            }

            return BlancoBatchProcessBatchProcess.END_SUCCESS;
        } catch (TransformerException ex) {
            throw new IOException("An exception has occurred during the XML conversion process.: " + ex.toString());
        }
    }

    /**
     * Whenever an item is processed in the process, it is called back as a progress report.
     * 
     * @param argProgressMessage
     *            Message about the item currently processed.
     * @return It is false if you want to continue the process, or it is true  if you want to request to suspend the process.
     */
    public boolean progress(String argProgressMessage) {
        if (fInput.getVerbose()) {
            System.out.println(" " + argProgressMessage);
        }
        return false;
    }
}
