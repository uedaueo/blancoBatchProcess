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
import java.util.ArrayList;
import java.util.List;

import blanco.batchprocess.message.BlancoBatchProcessMessage;
import blanco.batchprocess.stringgroup.BlancoBatchProcessBlancoTypeStringGroup;
import blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure;
import blanco.batchprocess.valueobject.BlancoBatchProcessOutputStructure;
import blanco.batchprocess.valueobject.BlancoBatchProcessStructure;
import blanco.commons.util.BlancoStringUtil;
import blanco.xml.bind.BlancoXmlBindingUtil;
import blanco.xml.bind.BlancoXmlUnmarshaller;
import blanco.xml.bind.valueobject.BlancoXmlDocument;
import blanco.xml.bind.valueobject.BlancoXmlElement;

/**
 * Extracts information from the "Batch Processing Definition Form" Excel format.
 * 
 * This class is responsible for extracting information from intermediate XML files.
 * 
 * @author IGA Tosiki
 */
public class BlancoBatchProcessXmlParser {
    /**
     * A message class.
     */
    protected final BlancoBatchProcessMessage fMsg = new BlancoBatchProcessMessage();

    /**
     * Parses an XML document in an intermediate XML file to get an array of information.
     * 
     * @param argMetaXmlSourceFile
     *            An intermediate XML file.
     * @return An array of information obtained as a result of parsing.
     */
    public BlancoBatchProcessStructure[] parse(final File argMetaXmlSourceFile) {
        final BlancoXmlDocument documentMeta = new BlancoXmlUnmarshaller()
                .unmarshal(argMetaXmlSourceFile);
        if (documentMeta == null) {
            return null;
        }

        return parse(documentMeta);
    }

    /**
     * Parses an XML document in an intermediate XML file to get an array of value object information.
     * 
     * @param argXmlDocument
     *            XML document of an intermediate XML file.
     * @return An array of value object information obtained as a result of parsing.
     */
    public BlancoBatchProcessStructure[] parse(
            final BlancoXmlDocument argXmlDocument) {
        final List<BlancoBatchProcessStructure> listStructure = new ArrayList<BlancoBatchProcessStructure>();
        // Gets the root element.
        final BlancoXmlElement elementRoot = BlancoXmlBindingUtil
                .getDocumentElement(argXmlDocument);
        if (elementRoot == null) {
            // The process is aborted if there is no root element.
            return null;
        }

        // Gets a list of sheets (Excel sheets).
        final List<blanco.xml.bind.valueobject.BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(elementRoot, "sheet");

        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            final BlancoXmlElement elementSheet = listSheet.get(index);

            final List<blanco.xml.bind.valueobject.BlancoXmlElement> listCommon = BlancoXmlBindingUtil
                    .getElementsByTagName(elementSheet,
                            "blancobatchprocess-common");
            if (listCommon.size() == 0) {
                // Skips if there is no common.
                continue;
            }

            // Processes only the first item.
            final BlancoXmlElement elementCommon = listCommon.get(0);
            final String name = BlancoXmlBindingUtil.getTextContent(
                    elementCommon, "name");
            if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
                // Skips the process if name is empty.
                continue;
            }

            final BlancoBatchProcessStructure processStructure = parseElementSheet(
                    elementSheet, elementCommon);
            if (processStructure != null) {
                // Memorizes the obtained information.
                listStructure.add(processStructure);
            }
        }

        final BlancoBatchProcessStructure[] result = new BlancoBatchProcessStructure[listStructure
                .size()];
        listStructure.toArray(result);
        return result;
    }

    /**
     * Parses the "sheet" XML element in the intermediate XML file to get the value object information.
     * 
     * @param argElementSheet
     *            "sheet" XML element in the intermediate XML file.
     * @return Value object information obtained as a result of parsing.
     *           Null is returned if "name" is not found.
     */
    public BlancoBatchProcessStructure parseElementSheet(
            final BlancoXmlElement argElementSheet,
            final BlancoXmlElement argElementCommon) {
        final BlancoBatchProcessStructure processStructure = new BlancoBatchProcessStructure();
        // Gets the input parameter information.
        final BlancoXmlElement elementInparameterList = BlancoXmlBindingUtil
                .getElement(argElementSheet,
                        "blancobatchprocess-inparameter-list");

        // Gets the output parameter information.
        final BlancoXmlElement elementOutput = BlancoXmlBindingUtil.getElement(
                argElementSheet, "blancobatchprocess-output");

        // Gets detailed information from the sheet.
        processStructure.setName(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "name"));
        processStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "package"));

        if (BlancoStringUtil.null2Blank(processStructure.getPackage()).trim()
                .length() == 0) {
            throw new IllegalArgumentException(fMsg
                    .getMbbpi001(processStructure.getName()));
        }

        if (BlancoXmlBindingUtil
                .getTextContent(argElementCommon, "description") != null) {
            processStructure.setDescription(BlancoXmlBindingUtil
                    .getTextContent(argElementCommon, "description"));
        }

        if (BlancoXmlBindingUtil.getTextContent(argElementCommon, "suffix") != null) {
            processStructure.setSuffix(BlancoXmlBindingUtil.getTextContent(
                    argElementCommon, "suffix"));
        }

        if (elementInparameterList == null) {
            return null;
        }

        // Gets the contents of the list.
        final List<blanco.xml.bind.valueobject.BlancoXmlElement> listField = BlancoXmlBindingUtil
                .getElementsByTagName(elementInparameterList, "inparameter");
        for (int indexField = 0; indexField < listField.size(); indexField++) {
            final BlancoXmlElement elementField = listField.get(indexField);

            final BlancoBatchProcessInputItemStructure inputItem = new BlancoBatchProcessInputItemStructure();

            inputItem.setName(BlancoXmlBindingUtil.getTextContent(elementField,
                    "name"));
            if (BlancoStringUtil.null2Blank(inputItem.getName()).length() == 0) {
                continue;
            }

            if (BlancoStringUtil.null2Blank(
                    BlancoXmlBindingUtil.getTextContent(elementField, "type"))
                    .length() > 0) {
                inputItem.setType(BlancoXmlBindingUtil.getTextContent(
                        elementField, "type"));
            }
            inputItem.setRequire("true".equals(BlancoXmlBindingUtil
                    .getTextContent(elementField, "require")));
            inputItem.setDefault(BlancoXmlBindingUtil.getTextContent(
                    elementField, "default"));
            inputItem.setDescription(BlancoXmlBindingUtil.getTextContent(
                    elementField, "description"));

            if (new BlancoBatchProcessBlancoTypeStringGroup()
                    .convertToInt(inputItem.getType()) == BlancoBatchProcessBlancoTypeStringGroup.NOT_DEFINED) {
                // TODO Message definition documentation has not been implemented.
                throw new IllegalArgumentException("An unsupported type["
                        + inputItem.getType() + "] has been given");
            }

            if (inputItem.getRequire() && inputItem.getDefault() != null) {
                throw new IllegalArgumentException(fMsg.getMbbpi003(
                        processStructure.getName(), inputItem.getName()));
            }

            processStructure.getInputItemList().add(inputItem);
        }

        // Gets the output value.
        {
            final BlancoBatchProcessOutputStructure outputStructure = new BlancoBatchProcessOutputStructure();

            if (BlancoStringUtil.null2Blank(
                    BlancoXmlBindingUtil.getTextContent(elementOutput,
                            "end-success")).length() > 0) {
                // TODO Whether to raise an exception if this is not present.
                outputStructure.setEndSuccess(BlancoXmlBindingUtil
                        .getTextContent(elementOutput, "end-success"));
            }

            if (BlancoStringUtil.null2Blank(
                    BlancoXmlBindingUtil.getTextContent(elementOutput,
                            "end-batchprocess-exception")).length() > 0) {
                outputStructure
                        .setEndBatchProcessException(BlancoXmlBindingUtil
                                .getTextContent(elementOutput,
                                        "end-batchprocess-exception"));
            }

            if (BlancoStringUtil.null2Blank(
                    BlancoXmlBindingUtil.getTextContent(elementOutput,
                            "end-illegal-argument-exception")).length() > 0) {
                outputStructure
                        .setEndIllegalArgumentException(BlancoXmlBindingUtil
                                .getTextContent(elementOutput,
                                        "end-illegal-argument-exception"));
            }

            if (BlancoStringUtil.null2Blank(
                    BlancoXmlBindingUtil.getTextContent(elementOutput,
                            "end-io-exception")).length() > 0) {
                outputStructure.setEndIoException(BlancoXmlBindingUtil
                        .getTextContent(elementOutput, "end-io-exception"));
            }

            if (BlancoStringUtil.null2Blank(
                    BlancoXmlBindingUtil.getTextContent(elementOutput,
                            "end-error")).length() > 0) {
                outputStructure.setEndError(BlancoXmlBindingUtil
                        .getTextContent(elementOutput, "end-error"));
            }

            outputStructure.setDescription(BlancoXmlBindingUtil.getTextContent(
                    elementOutput, "description"));

            processStructure.setOutput(outputStructure);
        }

        return processStructure;
    }
}
