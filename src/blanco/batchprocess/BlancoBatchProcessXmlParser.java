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
 * 「バッチ処理定義書」Excel様式から情報を抽出します。
 * 
 * このクラスは、中間XMLファイルから情報抽出する機能を担います。
 * 
 * @author IGA Tosiki
 */
public class BlancoBatchProcessXmlParser {
    /**
     * メッセージクラス。
     */
    protected final BlancoBatchProcessMessage fMsg = new BlancoBatchProcessMessage();

    /**
     * 中間XMLファイルのXMLドキュメントをパースして、情報の配列を取得します。
     * 
     * @param argMetaXmlSourceFile
     *            中間XMLファイル。
     * @return パースの結果得られた情報の配列。
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
     * 中間XMLファイル形式のXMLドキュメントをパースして、バリューオブジェクト情報の配列を取得します。
     * 
     * @param argXmlDocument
     *            中間XMLファイルのXMLドキュメント。
     * @return パースの結果得られたバリューオブジェクト情報の配列。
     */
    public BlancoBatchProcessStructure[] parse(
            final BlancoXmlDocument argXmlDocument) {
        final List<BlancoBatchProcessStructure> listStructure = new ArrayList<BlancoBatchProcessStructure>();
        // ルートエレメントを取得します。
        final BlancoXmlElement elementRoot = BlancoXmlBindingUtil
                .getDocumentElement(argXmlDocument);
        if (elementRoot == null) {
            // ルートエレメントが無い場合には処理中断します。
            return null;
        }

        // sheet(Excelシート)のリストを取得します。
        final List<blanco.xml.bind.valueobject.BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(elementRoot, "sheet");

        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            final BlancoXmlElement elementSheet = listSheet.get(index);

            final List<blanco.xml.bind.valueobject.BlancoXmlElement> listCommon = BlancoXmlBindingUtil
                    .getElementsByTagName(elementSheet,
                            "blancobatchprocess-common");
            if (listCommon.size() == 0) {
                // commonが無い場合にはスキップします。
                continue;
            }

            // 最初のアイテムのみ処理しています。
            final BlancoXmlElement elementCommon = listCommon.get(0);
            final String name = BlancoXmlBindingUtil.getTextContent(
                    elementCommon, "name");
            if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
                // nameが空の場合には処理をスキップします。
                continue;
            }

            final BlancoBatchProcessStructure processStructure = parseElementSheet(
                    elementSheet, elementCommon);
            if (processStructure != null) {
                // 得られた情報を記憶します。
                listStructure.add(processStructure);
            }
        }

        final BlancoBatchProcessStructure[] result = new BlancoBatchProcessStructure[listStructure
                .size()];
        listStructure.toArray(result);
        return result;
    }

    /**
     * 中間XMLファイル形式の「sheet」XMLエレメントをパースして、バリューオブジェクト情報を取得します。
     * 
     * @param argElementSheet
     *            中間XMLファイルの「sheet」XMLエレメント。
     * @return パースの結果得られたバリューオブジェクト情報。「name」が見つからなかった場合には nullを戻します。
     */
    public BlancoBatchProcessStructure parseElementSheet(
            final BlancoXmlElement argElementSheet,
            final BlancoXmlElement argElementCommon) {
        final BlancoBatchProcessStructure processStructure = new BlancoBatchProcessStructure();
        // 入力パラメータ情報を取得します。
        final BlancoXmlElement elementInparameterList = BlancoXmlBindingUtil
                .getElement(argElementSheet,
                        "blancobatchprocess-inparameter-list");

        // 出力パラメータ情報を取得します。
        final BlancoXmlElement elementOutput = BlancoXmlBindingUtil.getElement(
                argElementSheet, "blancobatchprocess-output");

        // シートから詳細な情報を取得します。
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

        // 一覧の内容を取得します。
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
                // TODO メッセージ定義書化が未実施。
                throw new IllegalArgumentException("サポートしない型["
                        + inputItem.getType() + "]が与えられました");
            }

            if (inputItem.getRequire() && inputItem.getDefault() != null) {
                throw new IllegalArgumentException(fMsg.getMbbpi003(
                        processStructure.getName(), inputItem.getName()));
            }

            processStructure.getInputItemList().add(inputItem);
        }

        // 出力値を取得します。
        {
            final BlancoBatchProcessOutputStructure outputStructure = new BlancoBatchProcessOutputStructure();

            if (BlancoStringUtil.null2Blank(
                    BlancoXmlBindingUtil.getTextContent(elementOutput,
                            "end-success")).length() > 0) {
                // TODO これが無かったら例外を発生すべき？
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
