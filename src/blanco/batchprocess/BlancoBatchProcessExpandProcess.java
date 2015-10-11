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
 * 処理(blancoProcess)のためのメインクラス。
 * 
 * 処理インタフェースを展開します。
 */
class BlancoBatchProcessExpandProcess {
    /**
     * 出力対象となるプログラミング言語。
     */
    private int fTargetLang = BlancoBatchProcessSupportedLangStringGroup.NOT_DEFINED;

    /**
     * ランタイムパッケージ。
     */
    private String fRuntimePackage = null;

    /**
     * 内部的に利用するblancoCg用ファクトリ。
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * 内部的に利用するblancoCg用ソースファイル情報。
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * 内部的に利用するblancoCg用クラス情報。
     */
    private BlancoCgInterface fCgInterface = null;

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
     *            メタファイルから収集できた処理構造データ。
     * @param argRuntimePackage
     *            ランタイムパッケージ。nullおよび長さ0の文字列の場合は定義書ごとにランタイムクラスを生成。
     * @param argTargetLang
     *            出力対象プログラミング言語。
     * @param argDirectoryTarget
     *            ソースコードの出力先フォルダ。
     */
    public void expandSourceFile(
            final BlancoBatchProcessStructure argProcessStructure,
            final String argRuntimePackage, final int argTargetLang,
            final File argDirectoryTarget) {
        fRuntimePackage = argRuntimePackage;
        fTargetLang = argTargetLang;

        // 従来と互換性を持たせるため、/mainサブフォルダに出力します。
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

        fCgInterface.setDescription("処理 ["
                + getProcessInterfaceName(argProcessStructure) + "]インタフェース。");
        fCgInterface.getLangDoc().getDescriptionList().add(
                "このインタフェースを継承して [" + argProcessStructure.getPackage()
                        + "]パッケージに["
                        + getProcessInterfaceName(argProcessStructure)
                        + "]クラスを作成して実際のバッチ処理を実装してください。<br>");
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
            // バッチ処理例外終了の値が設定されている場合にのみ生成します。

            fCgSourceFile.getImportList().add(
                    getBatchProcessExceptionClassName(argProcessStructure));

            if (BlancoStringUtil.null2Blank(fRuntimePackage).length() == 0) {
                // 定義書ごとに同一パッケージにランタイムクラスを生成。
                new BlancoBatchProcessExpandException().expandSourceFile(
                        argProcessStructure.getPackage(), fTargetLang,
                        argDirectoryTarget);
            } else {
                // ランタイムパッケージ指定があるので、指定のランタイムパッケージにクラスを生成。
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
     * execute メソッドを展開します。
     * 
     * @param argProcessStructure
     *            メタファイルから収集できた処理構造データ。
     */
    private void expandMethodExecute(
            final BlancoBatchProcessStructure argProcessStructure) {
        final BlancoCgMethod method = fCgFactory.createMethod("execute",
                "クラスをインスタンス化して処理を実行する際のエントリポイントです。");
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
                                        "処理の入力パラメータ。"));
        method.setReturn(fCgFactory.createReturn("int", "処理の実行結果。"));
        method.getThrowList().add(
                fCgFactory.createException("java.io.IOException",
                        "入出力例外が発生した場合。"));
        method.getThrowList().add(
                fCgFactory
                        .createException("java.lang.IllegalArgumentException",
                                "入力値に不正が見つかった場合。"));
    }

    /**
     * execute メソッドを展開します。
     * 
     * @param argProcessStructure
     *            メタファイルから収集できた処理構造データ。
     */
    private void expandMethodProgress() {
        final BlancoCgMethod method = fCgFactory.createMethod("progress",
                "処理の中でアイテムが処理されるたびに進捗報告としてコールバックします。");
        fCgInterface.getMethodList().add(method);
        method.setFinal(true);

        method.getParameterList().add(
                fCgFactory.createParameter("argProgressMessage",
                        "java.lang.String", "現在処理しているアイテムに関するメッセージ。"));
        method.setReturn(fCgFactory.createReturn("boolean",
                "処理をそのまま継続する場合は false。処理中断をリクエストしたい場合は true。"));
    }

    /**
     * 出力先となる処理インタフェース名を取得します。
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
     * バッチ処理例外クラス名を取得します。
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
                    "バッチ処理例外終了がOFFであるのに、BlancoBatchProcessException クラス名取得のメソッドが呼び出されました。矛盾しています");
        }

        if (BlancoStringUtil.null2Blank(fRuntimePackage).length() == 0) {
            return argProcessStructure.getPackage()
                    + ".BlancoBatchProcessException";
        } else {
            return fRuntimePackage + ".BlancoBatchProcessException";
        }
    }
}
