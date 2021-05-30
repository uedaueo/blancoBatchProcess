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
 * Expand a batch processing abstract class.
 */
class BlancoBatchProcessExpandBatchProcess {
    /**
     * A programming language to be output.
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
     * Source file information for blancoCg to be used internally.
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * Class information for blancoCg to be used internally.
     */
    private BlancoCgClass fCgClass = null;

    /**
     * Character encoding of auto-generated source files.
     */
    private String fEncoding = null;

    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * Auto-generates source code based on the collected information.
     * 
     * @param argProcessStructure
     *            Process structure data collected from metafiles.
     * @param argRuntimePackage
     *            Runtime package.
     *            For null and zero-length strings, generates a runtime class for each definition document.
     * @param argTargetLang
     *            Target programming language.
     * @param argDirectoryTarget
     *            Output directory for the source code.ソースコードの出力先フォルダ。
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
        fCgClass = fCgFactory.createClass(
                getBatchProcessClassName(argProcessStructure), BlancoStringUtil
                        .null2Blank(argProcessStructure.getDescription()));
        fCgSourceFile.getClassList().add(fCgClass);

        fCgClass.setDescription("Batch process class ["
                + getBatchProcessClassName(argProcessStructure) + "]。");
        fCgClass.getLangDoc().getDescriptionList().add("");
        fCgClass.getLangDoc().getDescriptionList().add("<P>Example of a batch processing call.</P>");

        fCgClass.getLangDoc().getDescriptionList().add("<code>");
        fCgClass.getLangDoc().getDescriptionList().add(
                "java -classpath (classpath) " + argProcessStructure.getPackage()
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
            // Generates only if the value for batch process exception termination is set.

            fCgSourceFile.getImportList().add(
                    getBatchProcessExceptionClassName(argProcessStructure));

            if (BlancoStringUtil.null2Blank(fRuntimePackage).length() == 0) {
                // Generates runtime class in the same package for each definition document.
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
     * Expands the field.
     * 
     * @param argProcessStructure
     *           Process structure data collected from metafiles.
     */
    private void expandField(
            final BlancoBatchProcessStructure argProcessStructure) {

        {
            final BlancoCgField field = fCgFactory.createField("END_SUCCESS",
                    "int", "Normal end.");
            fCgClass.getFieldList().add(field);

            field.setAccess("public");
            field.setStatic(true);
            field.setFinal(true);
            field.setDefault(argProcessStructure.getOutput().getEndSuccess());
        }

        if (BlancoStringUtil.null2Blank(
                argProcessStructure.getOutput().getEndBatchProcessException())
                .length() > 0) {
            // Generates only if the value for batch process exception termination is set.

            final BlancoCgField field = fCgFactory.createField(
                    "END_BATCHPROCESS_EXCEPTION", "int",
                    "Termination due to Batch process exception. In the case that BatchProcessException is raised internally.");
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
                    "Termination due to abnormal input. In the case that java.lang.IllegalArgumentException is raised internally.");
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
                    "Termination due to I/O exception. In the case that java.io.IOException is raised internally.");
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
                            "Abnormal end. In the case that batch process fails to start or java.lang.Error or java.lang.RuntimeException is raised internally.");
            fCgClass.getFieldList().add(field);

            field.setAccess("public");
            field.setStatic(true);
            field.setFinal(true);
            field.setDefault(argProcessStructure.getOutput().getEndError());
        }
    }

    /**
     * Expands the main method.
     * 
     * @param argProcessStructure
     *            Process structure data collected from meta files.
     */
    private void expandMethodMain(
            final BlancoBatchProcessStructure argProcessStructure) {

        final BlancoCgMethod method = fCgFactory.createMethod("main",
                "The entry point when executed from the command line.");
        fCgClass.getMethodList().add(method);

        method.setStatic(true);
        method.setFinal(true);
        method.getParameterList().add(
                fCgFactory.createParameter("args", "java.lang.String[]",
                        "Agruments inherited from the console."));

        final List<java.lang.String> listLine = method.getLineList();
        listLine.add("final " + getBatchProcessClassName(argProcessStructure)
                + " batchProcess = new "
                + getBatchProcessClassName(argProcessStructure) + "();");
        listLine.add("");
        listLine.add("// Arguments for batch process.");
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
        listLine.add("// Parses command line arguments.");
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
                        + ": Failed to start the process. Tried to parse the field ["
                        + inputItem.getName()
                        + "] of the input parameter[input] as a number (int), but it failed.: \" + e.toString());");
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
                                + ": Failed to start the process. Tried to parse the field ["
                                + inputItem.getName()
                                + "] of the input parameter[input] as a number (long), but it failed.: \" + e.toString());");
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
                                + ": Failed to start the process. Tried to parse the field ["
                                + inputItem.getName()
                                + "] of the input parameter[input] as a number (decimal), but it failed.: \" + e.toString());");
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
                + ": The input parameter[\" + arg + \"] was ignored.\");");
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
                        + ": Failed to start the process. The required field value["
                        + inputItem.getName() + "] in the input parameter[input] is not set to a value.\");");
                listLine.add("System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);");
                listLine.add("}");
            }
        }
        listLine.add("");
        listLine.add("int retCode = batchProcess.execute(input);");
        listLine.add("");
        listLine.add("// Returns the exit code.");
        listLine.add("// Note: Please note that calling System.exit().");
        listLine.add("System.exit(retCode);");
    }

    /**
     * Expands the execute method.
     * 
     * @param argProcessStructure
     *            Process structure collected from meta files.
     */
    private void expandMethodExecute(
            final BlancoBatchProcessStructure argProcessStructure) {

        final BlancoCgMethod method = fCgFactory.createMethod("execute",
                "The entry point for instantiating a class and running a batch.");
        fCgClass.getMethodList().add(method);
        method.setFinal(true);
        method.getLangDoc().getDescriptionList().add("This method provides the following specifications.");
        method.getLangDoc().getDescriptionList().add("<ul>");
        method.getLangDoc().getDescriptionList()
                .add("<li>Checks the contents of the input parameters of the method.");
        method
                .getLangDoc()
                .getDescriptionList()
                .add(
                        "<li>Catches exceptions such as IllegalArgumentException, RuntimeException, Error, etc. and converts them to return values.");
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
                                        "Input parameters for batch process."));
        method.setReturn(fCgFactory.createReturn("int",
                getReturnJavadocDescription(argProcessStructure)));
        method.getThrowList().add(
                fCgFactory
                        .createException("java.lang.IllegalArgumentException",
                                "If an invalid input value is found."));

        final List<java.lang.String> listLine = method.getLineList();

        listLine.add("try {");
        if (argProcessStructure.getShowMessageBeginEnd()) {
            listLine.add("System.out.println(\""
                    + getBatchProcessClassName(argProcessStructure)
                    + ": begin\");");
            listLine.add("");
        }

        listLine.add("// Execute the main body of the batch process.");
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
            // Generates only if the value for batch process exception termination is set.

            listLine.add("} catch (BlancoBatchProcessException ex) {");
            listLine.add("System.out.println(\""
                    + getBatchProcessClassName(argProcessStructure)
                    + ": A batch process exception has occurred. Abort the batch process.:\" + ex.toString());");
            listLine.add("// Termination due to batch process exception.");
            listLine.add("return END_BATCHPROCESS_EXCEPTION;");
        }
        listLine.add("} catch (IllegalArgumentException ex) {");
        listLine.add("System.out.println(\""
                + getBatchProcessClassName(argProcessStructure)
                + ": An input exception has occurred. Abort the batch process.:\" + ex.toString());");
        listLine.add("// Termination due to abnormal input.");
        listLine.add("return END_ILLEGAL_ARGUMENT_EXCEPTION;");
        listLine.add("} catch (IOException ex) {");
        listLine.add("System.out.println(\""
                + getBatchProcessClassName(argProcessStructure)
                + ": An I/O exception has occurred. Abort the batch process.:\" + ex.toString());");
        listLine.add("// Termination due to abnormal input.");
        listLine.add("return END_IO_EXCEPTION;");
        listLine.add("} catch (RuntimeException ex) {");
        listLine.add("System.out.println(\""
                + getBatchProcessClassName(argProcessStructure)
                + ": A runtime exception has occurred. Abort the batch process.:\" + ex.toString());");
        listLine.add("ex.printStackTrace();");
        listLine.add("// Abnormal end.");
        listLine.add("return END_ERROR;");
        listLine.add("} catch (Error er) {");
        listLine.add("System.out.println(\""
                + getBatchProcessClassName(argProcessStructure)
                + ": A runtime exception has occurred. Abort the batch process.:\" + er.toString());");
        listLine.add("er.printStackTrace();");
        listLine.add("// Abnormal end.");
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
