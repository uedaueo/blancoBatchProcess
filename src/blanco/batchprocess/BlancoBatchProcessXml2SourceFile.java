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
 * 「バッチ処理定義書」Excel様式からバッチ処理を処理する抽象親クラス・ソースコードを生成。
 * 
 * このクラスは、中間XMLファイルからソースコードを自動生成する機能を担います。
 * 
 * @author IGA Tosiki
 */
public class BlancoBatchProcessXml2SourceFile {
    /**
     * メッセージクラス。
     */
    protected final BlancoBatchProcessMessage fMsg = new BlancoBatchProcessMessage();

    /**
     * 自動生成するソースファイルの文字エンコーディング。
     */
    private String fEncoding = null;

    /**
     * 自動生成するソースファイルの文字エンコーディングを指定します。
     * 
     * @param argEncoding
     *            自動生成するソースファイルの文字エンコーディング。
     */
    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * 中間XMLファイルからソースコードを自動生成します。
     * 
     * @param argMetaXmlSourceFile
     *            メタ情報が含まれているXMLファイル。
     * @param argRuntimePackage
     *            ランタイムパッケージ。nullおよび長さ0の文字列の場合は定義書ごとにランタイムクラスを生成。
     * @param argTargetLang
     *            出力対象プログラミング言語。
     * @param argDirectoryTarget
     *            ソースコード生成先ディレクトリ (/mainを除く部分を指定します)。
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    public void process(final File argMetaXmlSourceFile,
            final String argRuntimePackage, final String argTargetLang,
            final File argDirectoryTarget) throws IOException {

        final BlancoBatchProcessStructure[] structures = new BlancoBatchProcessXmlParser()
                .parse(argMetaXmlSourceFile);
        if (structures == null || structures.length == 0) {
            // 処理しない。
            return;
        }

        for (int index = 0; index < structures.length; index++) {
            // メタ情報の解析結果をもとにソースコード自動生成を実行します。
            structure2Source(structures[index], argRuntimePackage,
                    argTargetLang, argDirectoryTarget);
        }
    }

    /**
     * 与えられたクラス情報バリューオブジェクトから、Javaソースコードを自動生成します。
     * 
     * @param argStructure
     *            情報。
     * @param argDirectoryTarget
     *            ソースコードの出力先ディレクトリ
     * @throws IOException
     *             入出力例外が発生した場合。
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
