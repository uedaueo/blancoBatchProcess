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
import java.util.List;

import blanco.batchprocess.stringgroup.BlancoBatchProcessBlancoTypeStringGroup;
import blanco.batchprocess.stringgroup.BlancoBatchProcessSupportedLangStringGroup;
import blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure;
import blanco.batchprocess.valueobject.BlancoBatchProcessStructure;
import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoJavaSourceUtil;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;

/**
 * バッチ処理抽象クラスを展開します。
 */
class BlancoBatchProcessExpandBatchProcess {
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
    private BlancoCgClass fCgClass = null;

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
        fCgClass = fCgFactory.createClass(
                getBatchProcessClassName(argProcessStructure), BlancoStringUtil
                        .null2Blank(argProcessStructure.getDescription()));
        fCgSourceFile.getClassList().add(fCgClass);

        fCgClass.setDescription("バッチ処理クラス ["
                + getBatchProcessClassName(argProcessStructure) + "]。");
        fCgClass.getLangDoc().getDescriptionList().add("");
        fCgClass.getLangDoc().getDescriptionList().add("<P>バッチ処理の呼び出し例。</P>");

        fCgClass.getLangDoc().getDescriptionList().add("<code>");
        fCgClass.getLangDoc().getDescriptionList().add(
                "java -classpath (クラスパス) " + argProcessStructure.getPackage()
                        + "." + getBatchProcessClassName(argProcessStructure)
                        + " -help");
        fCgClass.getLangDoc().getDescriptionList().add("</code>");

        expandField(argProcessStructure);
        expandMethodMain(argProcessStructure);
        expandMethodProcess(argProcessStructure);
        expandMethodExecute(argProcessStructure);
        expandMethodUsage(argProcessStructure);
        expandValidateInput(argProcessStructure);

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
     * フィールドを展開します。
     * 
     * @param argProcessStructure
     *            メタファイルから収集できた処理構造データ。
     */
    private void expandField(
            final BlancoBatchProcessStructure argProcessStructure) {

        {
            final BlancoCgField field = fCgFactory.createField("END_SUCCESS",
                    "int", "正常終了。");
            fCgClass.getFieldList().add(field);

            field.setAccess("public");
            field.setStatic(true);
            field.setFinal(true);
            field.setDefault(argProcessStructure.getOutput().getEndSuccess());
        }

        if (BlancoStringUtil.null2Blank(
                argProcessStructure.getOutput().getEndBatchProcessException())
                .length() > 0) {
            // バッチ処理例外終了の値が設定されている場合にのみ生成します。

            final BlancoCgField field = fCgFactory.createField(
                    "END_BATCHPROCESS_EXCEPTION", "int",
                    "バッチ処理例外終了。内部的にBatchProcessExceptionが発生した場合。");
            fCgClass.getFieldList().add(field);

            field.setAccess("public");
            field.setStatic(true);
            field.setFinal(true);
            field.setDefault(argProcessStructure.getOutput()
                    .getEndBatchProcessException());
        }

        {
            final BlancoCgField field = fCgFactory.createField(
                    "END_ILLEGAL_ARGUMENT_EXCEPTION", "int",
                    "入力異常終了。内部的にjava.lang.IllegalArgumentExceptionが発生した場合。");
            fCgClass.getFieldList().add(field);

            field.setAccess("public");
            field.setStatic(true);
            field.setFinal(true);
            field.setDefault(argProcessStructure.getOutput()
                    .getEndIllegalArgumentException());
        }

        {
            final BlancoCgField field = fCgFactory.createField(
                    "END_IO_EXCEPTION", "int",
                    "入出力例外終了。内部的にjava.io.IOExceptionが発生した場合。");
            fCgClass.getFieldList().add(field);

            field.setAccess("public");
            field.setStatic(true);
            field.setFinal(true);
            field.setDefault(argProcessStructure.getOutput()
                    .getEndIoException());
        }

        {
            final BlancoCgField field = fCgFactory
                    .createField(
                            "END_ERROR",
                            "int",
                            "異常終了。バッチの処理開始に失敗した場合、および内部的にjava.lang.Errorまたはjava.lang.RuntimeExceptionが発生した場合。");
            fCgClass.getFieldList().add(field);

            field.setAccess("public");
            field.setStatic(true);
            field.setFinal(true);
            field.setDefault(argProcessStructure.getOutput().getEndError());
        }
    }

    /**
     * main メソッドを展開します。
     * 
     * @param argProcessStructure
     *            メタファイルから収集できた処理構造データ。
     */
    private void expandMethodMain(
            final BlancoBatchProcessStructure argProcessStructure) {

        final BlancoCgMethod method = fCgFactory.createMethod("main",
                "コマンドラインから実行された際のエントリポイントです。");
        fCgClass.getMethodList().add(method);

        method.setStatic(true);
        method.setFinal(true);
        method.getParameterList().add(
                fCgFactory.createParameter("args", "java.lang.String[]",
                        "コンソールから引き継がれた引数。"));

        final List<java.lang.String> listLine = method.getLineList();
        listLine.add("final " + getBatchProcessClassName(argProcessStructure)
                + " batchProcess = new "
                + getBatchProcessClassName(argProcessStructure) + "();");
        listLine.add("");
        listLine.add("// バッチ処理の引数。");
        final String valueObjectClassname = BlancoBatchProcessExpandProcessInput
                .getBatchProcessValueObjectInputClassName(argProcessStructure);
        fCgSourceFile.getImportList().add(
                argProcessStructure.getPackage() + ".valueobject."
                        + valueObjectClassname);
        listLine.add("final " + valueObjectClassname + " input = new "
                + valueObjectClassname + "();");
        listLine.add("");
        listLine.add("boolean isNeedUsage = false;");

        for (int index = 0; index < argProcessStructure.getInputItemList()
                .size(); index++) {
            final BlancoBatchProcessInputItemStructure inputItem = (BlancoBatchProcessInputItemStructure) argProcessStructure
                    .getInputItemList().get(index);
            if (inputItem.getRequire()) {
                listLine.add("boolean isField"
                        + BlancoNameAdjuster.toClassName(inputItem.getName())
                        + "Processed = false;");
            }
        }

        listLine.add("");
        listLine.add("// コマンドライン引数の解析をおこないます。");
        listLine.add("for (int index = 0; index < args.length; index++) {");
        listLine.add("String arg = args[index];");
        for (int index = 0; index < argProcessStructure.getInputItemList()
                .size(); index++) {
            final BlancoBatchProcessInputItemStructure inputItem = (BlancoBatchProcessInputItemStructure) argProcessStructure
                    .getInputItemList().get(index);
            String line = "";
            if (index == 0) {
            } else {
                line += "} else ";
            }
            line += "if (arg.startsWith(\"-" + inputItem.getName() + "=\")) {";
            listLine.add(line);
            switch (new BlancoBatchProcessBlancoTypeStringGroup()
                    .convertToInt(inputItem.getType())) {
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_STRING:
                listLine.add("input.set"
                        + BlancoNameAdjuster.toClassName(inputItem.getName())
                        + "(arg.substring("
                        + (inputItem.getName().length() + 2) + "));");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_INT:
                listLine.add("try {");
                listLine.add("input.set"
                        + BlancoNameAdjuster.toClassName(inputItem.getName())
                        + "(Integer.parseInt(arg.substring("
                        + (inputItem.getName().length() + 2) + ")));");
                listLine.add("} catch (NumberFormatException e) {");
                listLine.add("System.out.println(\""
                        + getBatchProcessClassName(argProcessStructure)
                        + ": 処理開始失敗。入力パラメータ[input]のフィールド["
                        + inputItem.getName()
                        + "]を数値(int)としてパースを試みましたが失敗しました。: \" + e.toString());");
                listLine.add("System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);");
                listLine.add("}");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_LONG:
                listLine.add("try {");
                listLine.add("input.set"
                        + BlancoNameAdjuster.toClassName(inputItem.getName())
                        + "(Long.parseLong(arg.substring("
                        + (inputItem.getName().length() + 2) + ")));");
                listLine.add("} catch (NumberFormatException e) {");
                listLine
                        .add("System.out.println(\""
                                + getBatchProcessClassName(argProcessStructure)
                                + ": 処理開始失敗。入力パラメータ[input]のフィールド["
                                + inputItem.getName()
                                + "]を数値(long)としてパースを試みましたが失敗しました。: \" + e.toString());");
                listLine.add("System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);");
                listLine.add("}");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_DECIMAL:
                fCgSourceFile.getImportList().add("java.math.BigDecimal");
                listLine.add("try {");
                listLine.add("input.set"
                        + BlancoNameAdjuster.toClassName(inputItem.getName())
                        + "(new BigDecimal(arg.substring("
                        + (inputItem.getName().length() + 2) + ")));");
                listLine.add("} catch (NumberFormatException e) {");
                listLine
                        .add("System.out.println(\""
                                + getBatchProcessClassName(argProcessStructure)
                                + ": 処理開始失敗。入力パラメータ[input]のフィールド["
                                + inputItem.getName()
                                + "]を数値(decimal)としてパースを試みましたが失敗しました。: \" + e.toString());");
                listLine.add("System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);");
                listLine.add("}");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_BOOLEAN:
                listLine.add("input.set"
                        + BlancoNameAdjuster.toClassName(inputItem.getName())
                        + "(Boolean.valueOf(arg.substring("
                        + (inputItem.getName().length() + 2)
                        + ")).booleanValue());");
                break;
            }
            if (inputItem.getRequire()) {
                listLine.add("isField"
                        + BlancoNameAdjuster.toClassName(inputItem.getName())
                        + "Processed = true;");
            }
        }
        listLine.add((argProcessStructure.getInputItemList().size() == 0 ? ""
                : "} else ")
                + "if (arg.equals(\"-?\") || arg.equals(\"-help\")) {");
        listLine.add("usage();");
        listLine.add("System.exit(END_SUCCESS);");
        listLine.add("} else {");
        listLine.add("System.out.println(\""
                + getBatchProcessClassName(argProcessStructure)
                + ": 入力パラメータ[\" + arg + \"]は無視されました。\");");
        listLine.add("isNeedUsage = true;");
        listLine.add("}");
        listLine.add("}");
        listLine.add("");
        listLine.add("if (isNeedUsage) {");
        listLine.add("usage();");
        listLine.add("}");
        listLine.add("");
        for (int index = 0; index < argProcessStructure.getInputItemList()
                .size(); index++) {
            final BlancoBatchProcessInputItemStructure inputItem = (BlancoBatchProcessInputItemStructure) argProcessStructure
                    .getInputItemList().get(index);
            if (inputItem.getRequire()) {
                listLine.add("if( isField"
                        + BlancoNameAdjuster.toClassName(inputItem.getName())
                        + "Processed == false) {");
                listLine.add("System.out.println(\""
                        + getBatchProcessClassName(argProcessStructure)
                        + ": 処理開始失敗。入力パラメータ[input]の必須フィールド値["
                        + inputItem.getName() + "]に値が設定されていません。\");");
                listLine.add("System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);");
                listLine.add("}");
            }
        }
        listLine.add("");
        listLine.add("int retCode = batchProcess.execute(input);");
        listLine.add("");
        listLine.add("// 終了コードを戻します。");
        listLine.add("// ※注意：System.exit()を呼び出している点に注意してください。");
        listLine.add("System.exit(retCode);");
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
                "クラスをインスタンス化してバッチを実行する際のエントリポイントです。");
        fCgClass.getMethodList().add(method);
        method.setFinal(true);
        method.getLangDoc().getDescriptionList().add("このメソッドは下記の仕様を提供します。");
        method.getLangDoc().getDescriptionList().add("<ul>");
        method.getLangDoc().getDescriptionList()
                .add("<li>メソッドの入力パラメータの内容チェック。");
        method
                .getLangDoc()
                .getDescriptionList()
                .add(
                        "<li>IllegalArgumentException, RuntimeException, Errorなどの例外をcatchして戻り値へと変換。");
        method.getLangDoc().getDescriptionList().add("</ul>");

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
                                        "バッチ処理の入力パラメータ。"));
        method.setReturn(fCgFactory.createReturn("int",
                getReturnJavadocDescription(argProcessStructure)));
        method.getThrowList().add(
                fCgFactory
                        .createException("java.lang.IllegalArgumentException",
                                "入力値に不正が見つかった場合。"));

        final List<java.lang.String> listLine = method.getLineList();

        listLine.add("try {");
        if (argProcessStructure.getShowMessageBeginEnd()) {
            listLine.add("System.out.println(\""
                    + getBatchProcessClassName(argProcessStructure)
                    + ": begin\");");
            listLine.add("");
        }

        listLine.add("// バッチ処理の本体を実行します。");
        listLine.add("int retCode = process(input);");
        listLine.add("");
        if (argProcessStructure.getShowMessageBeginEnd()) {
            listLine.add("System.out.println(\""
                    + getBatchProcessClassName(argProcessStructure)
                    + ": end (\" + retCode + \")\");");
        }
        listLine.add("return retCode;");

        if (BlancoStringUtil.null2Blank(
                argProcessStructure.getOutput().getEndBatchProcessException())
                .length() > 0) {
            // バッチ処理例外終了の値が設定されている場合にのみ生成します。

            listLine.add("} catch (BlancoBatchProcessException ex) {");
            listLine.add("System.out.println(\""
                    + getBatchProcessClassName(argProcessStructure)
                    + ": バッチ処理例外が発生しました。バッチ処理を中断します。:\" + ex.toString());");
            listLine.add("// バッチ処理例外終了。");
            listLine.add("return END_BATCHPROCESS_EXCEPTION;");
        }
        listLine.add("} catch (IllegalArgumentException ex) {");
        listLine.add("System.out.println(\""
                + getBatchProcessClassName(argProcessStructure)
                + ": 入力例外が発生しました。バッチ処理を中断します。:\" + ex.toString());");
        listLine.add("// 入力異常終了。");
        listLine.add("return END_ILLEGAL_ARGUMENT_EXCEPTION;");
        listLine.add("} catch (IOException ex) {");
        listLine.add("System.out.println(\""
                + getBatchProcessClassName(argProcessStructure)
                + ": 入出力例外が発生しました。バッチ処理を中断します。:\" + ex.toString());");
        listLine.add("// 入力異常終了。");
        listLine.add("return END_IO_EXCEPTION;");
        listLine.add("} catch (RuntimeException ex) {");
        listLine.add("System.out.println(\""
                + getBatchProcessClassName(argProcessStructure)
                + ": ランタイム例外が発生しました。バッチ処理を中断します。:\" + ex.toString());");
        listLine.add("ex.printStackTrace();");
        listLine.add("// 異常終了。");
        listLine.add("return END_ERROR;");
        listLine.add("} catch (Error er) {");
        listLine.add("System.out.println(\""
                + getBatchProcessClassName(argProcessStructure)
                + ": ランタイムエラーが発生しました。バッチ処理を中断します。:\" + er.toString());");
        listLine.add("er.printStackTrace();");
        listLine.add("// 異常終了。");
        listLine.add("return END_ERROR;");
        listLine.add("}");
    }

    /**
     * process メソッドを展開します。
     * 
     * @param argProcessStructure
     *            メタファイルから収集できた処理構造データ。
     */
    private void expandMethodProcess(
            final BlancoBatchProcessStructure argProcessStructure) {

        final BlancoCgMethod method = fCgFactory.createMethod("process",
                "具体的なバッチ処理内容を記述するためのメソッドです。");
        fCgClass.getMethodList().add(method);

        method.getLangDoc().getDescriptionList().add("このメソッドに実際の処理内容を記述します。");
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
                                        "バッチ処理の入力パラメータ。"));
        method.setReturn(fCgFactory.createReturn("int",
                getReturnJavadocDescription(argProcessStructure)));
        method.getThrowList().add(
                fCgFactory.createException("java.io.IOException",
                        "入出力例外が発生した場合。"));
        method.getThrowList().add(
                fCgFactory
                        .createException("java.lang.IllegalArgumentException",
                                "入力値に不正が見つかった場合。"));

        final List<java.lang.String> listLine = method.getLineList();
        listLine.add("// 入力パラメータをチェックします。");
        listLine.add("validateInput(input);");
        listLine.add("");
        listLine.add("// この箇所でコンパイルエラーが発生する場合、"
                + BlancoNameAdjuster.toClassName(argProcessStructure.getName())
                + "Processインタフェースを実装して " + argProcessStructure.getPackage()
                + "パッケージに "
                + BlancoNameAdjuster.toClassName(argProcessStructure.getName())
                + "ProcessImplクラスを作成することにより解決できる場合があります。");
        listLine.add("final "
                + BlancoNameAdjuster.toClassName(argProcessStructure.getName())
                + "Process process = new "
                + BlancoNameAdjuster.toClassName(argProcessStructure.getName())
                + "ProcessImpl();");
        listLine.add("");
        listLine.add("// 処理の本体を実行します。");
        listLine.add("final int retCode = process.execute(input);");
        listLine.add("");
        listLine.add("return retCode;");
    }

    /**
     * execute メソッドを展開します。
     * 
     * @param argProcessStructure
     *            メタファイルから収集できた処理構造データ。
     */
    private void expandMethodUsage(
            final BlancoBatchProcessStructure argProcessStructure) {

        final BlancoCgMethod method = fCgFactory.createMethod("usage",
                "このバッチ処理クラスの使い方の説明を標準出力に示すためのメソッドです。");
        fCgClass.getMethodList().add(method);

        method.setStatic(true);
        method.setFinal(true);

        final List<java.lang.String> listLine = method.getLineList();
        listLine.add("System.out.println(\""
                + getBatchProcessClassName(argProcessStructure)
                + ": Usage:\");");
        {
            String strArg = "System.out.println(\"  java "
                    + argProcessStructure.getPackage() + "."
                    + getBatchProcessClassName(argProcessStructure);
            for (int index = 0; index < argProcessStructure.getInputItemList()
                    .size(); index++) {
                final BlancoBatchProcessInputItemStructure inparam = (BlancoBatchProcessInputItemStructure) argProcessStructure
                        .getInputItemList().get(index);
                strArg += " -" + inparam.getName() + "=値" + (index + 1) + "";
            }
            strArg += "\");";
            listLine.add(strArg);
        }

        for (int index = 0; index < argProcessStructure.getInputItemList()
                .size(); index++) {
            final BlancoBatchProcessInputItemStructure inputItem = (BlancoBatchProcessInputItemStructure) argProcessStructure
                    .getInputItemList().get(index);
            listLine.add("System.out.println(\"    -" + inputItem.getName()
                    + "\");");
            if (inputItem.getDescription() != null) {
                listLine.add("System.out.println(\"      説明["
                        + BlancoJavaSourceUtil
                                .escapeStringAsJavaSource(inputItem
                                        .getDescription()) + "]\");");
            }
            switch (new BlancoBatchProcessBlancoTypeStringGroup()
                    .convertToInt(inputItem.getType())) {
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_STRING:
                listLine.add("System.out.println(\"      型[文字列]\");");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_INT:
                listLine.add("System.out.println(\"      型[数値(int)]\");");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_LONG:
                listLine.add("System.out.println(\"      型[数値(long)]\");");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_DECIMAL:
                listLine.add("System.out.println(\"      型[数値(decimal)]\");");
                break;
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_BOOLEAN:
                listLine.add("System.out.println(\"      型[真偽]\");");
                break;
            }
            if (inputItem.getRequire()) {
                listLine.add("System.out.println(\"      必須パラメータ\");");
            }
            if (inputItem.getDefault() != null) {
                listLine.add("System.out.println(\"      デフォルト値["
                        + BlancoJavaSourceUtil
                                .escapeStringAsJavaSource(inputItem
                                        .getDefault()) + "]\");");
            }
        }
        listLine.add("System.out.println(\"    -? , -help\");");
        listLine.add("System.out.println(\"      説明[使い方を表示します。]\");");
    }

    /**
     * validateInput メソッドを展開します。
     * 
     * @param argProcessStructure
     *            メタファイルから収集できた処理構造データ。
     */
    private void expandValidateInput(
            final BlancoBatchProcessStructure argProcessStructure) {

        final BlancoCgMethod method = fCgFactory.createMethod("validateInput",
                "このバッチ処理クラスの入力パラメータの妥当性チェックを実施するためのメソッドです。");
        fCgClass.getMethodList().add(method);

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
                                        "バッチ処理の入力パラメータ。"));
        method.getThrowList().add(
                fCgFactory
                        .createException("java.lang.IllegalArgumentException",
                                "入力値に不正が見つかった場合。"));

        final List<java.lang.String> listLine = method.getLineList();

        listLine.add("if (input == null) {");
        listLine
                .add("throw new IllegalArgumentException(\"BlancoBatchProcessBatchProcess: 処理開始失敗。入力パラメータ[input]にnullが与えられました。\");");
        listLine.add("}");

        for (int index = 0; index < argProcessStructure.getInputItemList()
                .size(); index++) {
            final BlancoBatchProcessInputItemStructure inputItem = (BlancoBatchProcessInputItemStructure) argProcessStructure
                    .getInputItemList().get(index);
            switch (new BlancoBatchProcessBlancoTypeStringGroup()
                    .convertToInt(inputItem.getType())) {
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_STRING:
            case BlancoBatchProcessBlancoTypeStringGroup.BLANCO_DECIMAL:
                // 必須かどうかで型が変わらないもののみチェックします。
                if (inputItem.getRequire()) {
                    listLine.add("if (input.get"
                            + BlancoNameAdjuster.toClassName(inputItem
                                    .getName()) + "() == null) {");
                    listLine.add("throw new IllegalArgumentException(\""
                            + getBatchProcessClassName(argProcessStructure)
                            + ": 処理開始失敗。入力パラメータ[input]の必須フィールド値["
                            + inputItem.getName() + "]に値が設定されていません。\");");
                    listLine.add("}");
                }
                break;
            }
        }
    }

    /**
     * 出力先となるバッチ処理クラス名を取得します。
     * 
     * @param argProcessStructure
     * @return
     */
    static String getBatchProcessClassName(
            final BlancoBatchProcessStructure argProcessStructure) {
        return BlancoNameAdjuster.toClassName(argProcessStructure.getName())
                + BlancoStringUtil.null2Blank(argProcessStructure.getSuffix());
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

    /**
     * バッチのメソッドの戻り値のJavaDoc説明を取得します。
     * 
     * @param argProcessStructure
     * @return
     */
    private static String getReturnJavadocDescription(
            final BlancoBatchProcessStructure argProcessStructure) {
        return "バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR"
                + (BlancoStringUtil.null2Blank(
                        argProcessStructure.getOutput()
                                .getEndBatchProcessException()).length() == 0 ? ""
                        : ", END_BATCHPROCESS_EXCEPTION")
                + " のいずれかの値を戻します。"
                + BlancoStringUtil.null2Blank(argProcessStructure.getOutput()
                        .getDescription());
    }
}
