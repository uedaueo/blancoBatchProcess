/*
 * blanco Framework
 * Copyright (C) 2004-2009 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.sample.batchprocess;

import java.io.IOException;
import java.math.BigDecimal;

import blanco.sample.batchprocess.valueobject.Sample2ProcessInput;

/**
 * バッチ処理クラス [Sample2BatchProcess]。
 *
 * <P>バッチ処理の呼び出し例。</P>
 * <code>
 * java -classpath (クラスパス) blanco.sample.batchprocess.Sample2BatchProcess -help
 * </code>
 */
public class Sample2BatchProcess {
    /**
     * 正常終了。
     */
    public static final int END_SUCCESS = 0;

    /**
     * 入力異常終了。内部的にjava.lang.IllegalArgumentExceptionが発生した場合。
     */
    public static final int END_ILLEGAL_ARGUMENT_EXCEPTION = 97;

    /**
     * 入出力例外終了。内部的にjava.io.IOExceptionが発生した場合。
     */
    public static final int END_IO_EXCEPTION = 98;

    /**
     * 異常終了。バッチの処理開始に失敗した場合、および内部的にjava.lang.Errorまたはjava.lang.RuntimeExceptionが発生した場合。
     */
    public static final int END_ERROR = 99;

    /**
     * コマンドラインから実行された際のエントリポイントです。
     *
     * @param args コンソールから引き継がれた引数。
     */
    public static final void main(final String[] args) {
        final Sample2BatchProcess batchProcess = new Sample2BatchProcess();

        // バッチ処理の引数。
        final Sample2ProcessInput input = new Sample2ProcessInput();

        boolean isNeedUsage = false;
        boolean isFieldRequireProcessed = false;
        boolean isFieldFieldIntReqProcessed = false;
        boolean isFieldFieldLongReqProcessed = false;
        boolean isFieldFieldDecimalReqProcessed = false;
        boolean isFieldFieldBooleanReqProcessed = false;

        // コマンドライン引数の解析をおこないます。
        for (int index = 0; index < args.length; index++) {
            String arg = args[index];
            if (arg.startsWith("-require=")) {
                input.setRequire(arg.substring(9));
                isFieldRequireProcessed = true;
            } else if (arg.startsWith("-dispstr=")) {
                input.setDispstr(arg.substring(9));
            } else if (arg.startsWith("-normalparam=")) {
                input.setNormalparam(arg.substring(13));
            } else if (arg.startsWith("-field_int_req=")) {
                try {
                    input.setFieldIntReq(Integer.parseInt(arg.substring(15)));
                } catch (NumberFormatException e) {
                    System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]のフィールド[field_int_req]を数値(int)としてパースを試みましたが失敗しました。: " + e.toString());
                    System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
                }
                isFieldFieldIntReqProcessed = true;
            } else if (arg.startsWith("-field_int=")) {
                try {
                    input.setFieldInt(Integer.parseInt(arg.substring(11)));
                } catch (NumberFormatException e) {
                    System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]のフィールド[field_int]を数値(int)としてパースを試みましたが失敗しました。: " + e.toString());
                    System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
                }
            } else if (arg.startsWith("-field_long_req=")) {
                try {
                    input.setFieldLongReq(Long.parseLong(arg.substring(16)));
                } catch (NumberFormatException e) {
                    System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]のフィールド[field_long_req]を数値(long)としてパースを試みましたが失敗しました。: " + e.toString());
                    System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
                }
                isFieldFieldLongReqProcessed = true;
            } else if (arg.startsWith("-field_long=")) {
                try {
                    input.setFieldLong(Long.parseLong(arg.substring(12)));
                } catch (NumberFormatException e) {
                    System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]のフィールド[field_long]を数値(long)としてパースを試みましたが失敗しました。: " + e.toString());
                    System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
                }
            } else if (arg.startsWith("-field_decimal_req=")) {
                try {
                    input.setFieldDecimalReq(new BigDecimal(arg.substring(19)));
                } catch (NumberFormatException e) {
                    System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]のフィールド[field_decimal_req]を数値(decimal)としてパースを試みましたが失敗しました。: " + e.toString());
                    System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
                }
                isFieldFieldDecimalReqProcessed = true;
            } else if (arg.startsWith("-field_decimal=")) {
                try {
                    input.setFieldDecimal(new BigDecimal(arg.substring(15)));
                } catch (NumberFormatException e) {
                    System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]のフィールド[field_decimal]を数値(decimal)としてパースを試みましたが失敗しました。: " + e.toString());
                    System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
                }
            } else if (arg.startsWith("-field_boolean_req=")) {
                input.setFieldBooleanReq(Boolean.valueOf(arg.substring(19)).booleanValue());
                isFieldFieldBooleanReqProcessed = true;
            } else if (arg.startsWith("-field_boolean=")) {
                input.setFieldBoolean(Boolean.valueOf(arg.substring(15)).booleanValue());
            } else if (arg.equals("-?") || arg.equals("-help")) {
                usage();
                System.exit(END_SUCCESS);
            } else {
                System.out.println("Sample2BatchProcess: 入力パラメータ[" + arg + "]は無視されました。");
                isNeedUsage = true;
            }
        }

        if (isNeedUsage) {
            usage();
        }

        if( isFieldRequireProcessed == false) {
            System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[require]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldFieldIntReqProcessed == false) {
            System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[field_int_req]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldFieldLongReqProcessed == false) {
            System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[field_long_req]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldFieldDecimalReqProcessed == false) {
            System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[field_decimal_req]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldFieldBooleanReqProcessed == false) {
            System.out.println("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[field_boolean_req]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }

        int retCode = batchProcess.execute(input);

        // 終了コードを戻します。
        // ※注意：System.exit()を呼び出している点に注意してください。
        System.exit(retCode);
    }

    /**
     * 具体的なバッチ処理内容を記述するためのメソッドです。
     *
     * このメソッドに実際の処理内容を記述します。
     *
     * @param input バッチ処理の入力パラメータ。
     * @return バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR のいずれかの値を戻します。正常終了の場合は0。
     * @throws IOException 入出力例外が発生した場合。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public int process(final Sample2ProcessInput input) throws IOException, IllegalArgumentException {
        // 入力パラメータをチェックします。
        validateInput(input);

        // この箇所でコンパイルエラーが発生する場合、Sample2Processインタフェースを実装して blanco.sample.batchprocessパッケージに Sample2ProcessImplクラスを作成することにより解決できる場合があります。
        final Sample2Process process = new Sample2ProcessImpl();

        // 処理の本体を実行します。
        final int retCode = process.execute(input);

        return retCode;
    }

    /**
     * クラスをインスタンス化してバッチを実行する際のエントリポイントです。
     *
     * このメソッドは下記の仕様を提供します。
     * <ul>
     * <li>メソッドの入力パラメータの内容チェック。
     * <li>IllegalArgumentException, RuntimeException, Errorなどの例外をcatchして戻り値へと変換。
     * </ul>
     *
     * @param input バッチ処理の入力パラメータ。
     * @return バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR のいずれかの値を戻します。正常終了の場合は0。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public final int execute(final Sample2ProcessInput input) throws IllegalArgumentException {
        try {
            System.out.println("Sample2BatchProcess: begin");

            // バッチ処理の本体を実行します。
            int retCode = process(input);

            System.out.println("Sample2BatchProcess: end (" + retCode + ")");
            return retCode;
        } catch (IllegalArgumentException ex) {
            System.out.println("Sample2BatchProcess: 入力例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            // 入力異常終了。
            return END_ILLEGAL_ARGUMENT_EXCEPTION;
        } catch (IOException ex) {
            System.out.println("Sample2BatchProcess: 入出力例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            // 入力異常終了。
            return END_IO_EXCEPTION;
        } catch (RuntimeException ex) {
            System.out.println("Sample2BatchProcess: ランタイム例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            ex.printStackTrace();
            // 異常終了。
            return END_ERROR;
        } catch (Error er) {
            System.out.println("Sample2BatchProcess: ランタイムエラーが発生しました。バッチ処理を中断します。:" + er.toString());
            er.printStackTrace();
            // 異常終了。
            return END_ERROR;
        }
    }

    /**
     * このバッチ処理クラスの使い方の説明を標準出力に示すためのメソッドです。
     */
    public static final void usage() {
        System.out.println("Sample2BatchProcess: Usage:");
        System.out.println("  java blanco.sample.batchprocess.Sample2BatchProcess -require=値1 -dispstr=値2 -normalparam=値3 -field_int_req=値4 -field_int=値5 -field_long_req=値6 -field_long=値7 -field_decimal_req=値8 -field_decimal=値9 -field_boolean_req=値10 -field_boolean=値11");
        System.out.println("    -require");
        System.out.println("      説明[必須引数のサンプル。]");
        System.out.println("      型[文字列]");
        System.out.println("      必須パラメータ");
        System.out.println("    -dispstr");
        System.out.println("      説明[表示文字列のサンプル。]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[特に指定が無い場合の文字列]");
        System.out.println("    -normalparam");
        System.out.println("      説明[必須でもなく、またデフォルトも持たない引数の例。]");
        System.out.println("      型[文字列]");
        System.out.println("    -field_int_req");
        System.out.println("      型[数値(int)]");
        System.out.println("      必須パラメータ");
        System.out.println("    -field_int");
        System.out.println("      型[数値(int)]");
        System.out.println("    -field_long_req");
        System.out.println("      型[数値(long)]");
        System.out.println("      必須パラメータ");
        System.out.println("    -field_long");
        System.out.println("      型[数値(long)]");
        System.out.println("    -field_decimal_req");
        System.out.println("      型[数値(decimal)]");
        System.out.println("      必須パラメータ");
        System.out.println("    -field_decimal");
        System.out.println("      型[数値(decimal)]");
        System.out.println("    -field_boolean_req");
        System.out.println("      型[真偽]");
        System.out.println("      必須パラメータ");
        System.out.println("    -field_boolean");
        System.out.println("      型[真偽]");
        System.out.println("    -? , -help");
        System.out.println("      説明[使い方を表示します。]");
    }

    /**
     * このバッチ処理クラスの入力パラメータの妥当性チェックを実施するためのメソッドです。
     *
     * @param input バッチ処理の入力パラメータ。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public void validateInput(final Sample2ProcessInput input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: 処理開始失敗。入力パラメータ[input]にnullが与えられました。");
        }
        if (input.getRequire() == null) {
            throw new IllegalArgumentException("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[require]に値が設定されていません。");
        }
        if (input.getFieldDecimalReq() == null) {
            throw new IllegalArgumentException("Sample2BatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[field_decimal_req]に値が設定されていません。");
        }
    }
}
