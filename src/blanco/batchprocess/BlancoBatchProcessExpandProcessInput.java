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

import blanco.batchprocess.stringgroup.BlancoBatchProcessBlancoTypeStringGroup;
import blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure;
import blanco.batchprocess.valueobject.BlancoBatchProcessStructure;
import blanco.commons.util.BlancoJavaSourceUtil;
import blanco.commons.util.BlancoStringUtil;
import blanco.valueobject.BlancoValueObjectXml2JavaClass;
import blanco.valueobject.valueobject.BlancoValueObjectClassStructure;
import blanco.valueobject.valueobject.BlancoValueObjectFieldStructure;

/**
 * 処理(blancoProcess)のためのバリューオブジェクトクラス。
 * 
 * 処理クラスのための入力バリューオブジェクトを生成します。
 */
class BlancoBatchProcessExpandProcessInput {
    /**
     * 自動生成するソースファイルの文字エンコーディング。
     */
    private String fEncoding = null;

    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * 収集された情報を元に、ソースコードを自動生成します。
     * 
     * @param argProcessStructure
     * @param argDirectoryTarget
     *            ソースコードの出力先フォルダ。
     */
    public void expandSourceFile(
            final BlancoBatchProcessStructure argProcessStructure,
            final File argDirectoryTarget) {
        final BlancoValueObjectClassStructure voStructure = new BlancoValueObjectClassStructure();
        voStructure
                .setName(getBatchProcessValueObjectInputClassName(argProcessStructure));
        voStructure.setPackage(argProcessStructure.getPackage()
                + ".valueobject");
        voStructure.setDescription("処理クラス ["
                + BlancoBatchProcessExpandProcess
                        .getProcessInterfaceName(argProcessStructure)
                + "]の入力バリューオブジェクトクラスです。");
        for (int index = 0; index < argProcessStructure.getInputItemList()
                .size(); index++) {
            final BlancoBatchProcessInputItemStructure inputItem = (BlancoBatchProcessInputItemStructure) argProcessStructure
                    .getInputItemList().get(index);

            final BlancoValueObjectFieldStructure voFieldStructure = new BlancoValueObjectFieldStructure();
            voFieldStructure.setName(inputItem.getName());

            switch (new BlancoBatchProcessBlancoTypeStringGroup()
                    .convertToInt(inputItem.getType())) {
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_STRING:
                voFieldStructure.setType("java.lang.String");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_INT:
                // 必須かどうかにかかわらず、常にプリミティブ型とします。
                voFieldStructure.setType("int");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_LONG:
                // 必須かどうかにかかわらず、常にプリミティブ型とします。
                voFieldStructure.setType("long");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_DECIMAL:
                voFieldStructure.setType("java.math.BigDecimal");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_BOOLEAN:
                // 必須かどうかにかかわらず、常にプリミティブ型とします。
                voFieldStructure.setType("boolean");
                break;
            }

            voFieldStructure.setDescription(BlancoJavaSourceUtil
                    .escapeStringAsJavaDoc(BlancoStringUtil
                            .null2Blank(inputItem.getDescription())));
            if (BlancoStringUtil.null2Blank(inputItem.getDefault()).length() > 0) {
                voFieldStructure.setDefault(inputItem.getDefault());
            }
            voStructure.getFieldList().add(voFieldStructure);
        }
        try {
            final BlancoValueObjectXml2JavaClass xml2java = new BlancoValueObjectXml2JavaClass();
            xml2java.setEncoding(fEncoding);
            xml2java.structure2Source(voStructure, argDirectoryTarget);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    static String getBatchProcessValueObjectInputClassName(
            final BlancoBatchProcessStructure argProcessStructure) {
        return BlancoBatchProcessExpandProcess
                .getProcessInterfaceName(argProcessStructure)
                + "Input";
    }
}
