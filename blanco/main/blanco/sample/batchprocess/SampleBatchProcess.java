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

import blanco.sample.batchprocess.valueobject.SampleProcessInput;

/**
 * バッチ処理クラス [SampleBatchProcess]。
 *
 * <P>バッチ処理の呼び出し例。</P>
 * <code>
 * java -classpath (クラスパス) blanco.sample.batchprocess.SampleBatchProcess -help
 * </code>
 */
public class SampleBatchProcess {
    /**
     * 正常終了。
     */
    public static final int END_SUCCESS = 0;

    /**
     * バッチ処理例外終了。内部的にBatchProcessExceptionが発生した場合。
     */
    public static final int END_BATCHPROCESS_EXCEPTION = 6;

    /**
     * 入力異常終了。内部的にjava.lang.IllegalArgumentExceptionが発生した場合。
     */
    public static final int END_ILLEGAL_ARGUMENT_EXCEPTION = 7;

    /**
     * 入出力例外終了。内部的にjava.io.IOExceptionが発生した場合。
     */
    public static final int END_IO_EXCEPTION = 8;

    /**
     * 異常終了。バッチの処理開始に失敗した場合、および内部的にjava.lang.Errorまたはjava.lang.RuntimeExceptionが発生した場合。
     */
    public static final int END_ERROR = 9;

    /**
     * コマンドラインから実行された際のエントリポイントです。
     *
     * @param args コンソールから引き継がれた引数。
     */
    public static final void main(final String[] args) {
        final SampleBatchProcess batchProcess = new SampleBatchProcess();

        // バッチ処理の引数。
        final SampleProcessInput input = new SampleProcessInput();

        boolean isNeedUsage = false;
        boolean isFieldRequireProcessed = false;

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
            } else if (arg.equals("-?") || arg.equals("-help")) {
                usage();
                System.exit(END_SUCCESS);
            } else {
                System.out.println("SampleBatchProcess: 入力パラメータ[" + arg + "]は無視されました。");
                isNeedUsage = true;
            }
        }

        if (isNeedUsage) {
            usage();
        }

        if( isFieldRequireProcessed == false) {
            System.out.println("SampleBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[require]に値が設定されていません。");
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
     * @return バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR, END_BATCHPROCESS_EXCEPTION のいずれかの値を戻します。正常終了の場合は0。不正入力終了の場合は7。異常終了の場合は9。
     * @throws IOException 入出力例外が発生した場合。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public int process(final SampleProcessInput input) throws IOException, IllegalArgumentException {
        // 入力パラメータをチェックします。
        validateInput(input);

        // この箇所でコンパイルエラーが発生する場合、SampleProcessインタフェースを実装して blanco.sample.batchprocessパッケージに SampleProcessImplクラスを作成することにより解決できる場合があります。
        final SampleProcess process = new SampleProcessImpl();

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
     * @return バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR, END_BATCHPROCESS_EXCEPTION のいずれかの値を戻します。正常終了の場合は0。不正入力終了の場合は7。異常終了の場合は9。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public final int execute(final SampleProcessInput input) throws IllegalArgumentException {
        try {
            System.out.println("SampleBatchProcess: begin");

            // バッチ処理の本体を実行します。
            int retCode = process(input);

            System.out.println("SampleBatchProcess: end (" + retCode + ")");
            return retCode;
        } catch (BlancoBatchProcessException ex) {
            System.out.println("SampleBatchProcess: バッチ処理例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            // バッチ処理例外終了。
            return END_BATCHPROCESS_EXCEPTION;
        } catch (IllegalArgumentException ex) {
            System.out.println("SampleBatchProcess: 入力例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            // 入力異常終了。
            return END_ILLEGAL_ARGUMENT_EXCEPTION;
        } catch (IOException ex) {
            System.out.println("SampleBatchProcess: 入出力例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            // 入力異常終了。
            return END_IO_EXCEPTION;
        } catch (RuntimeException ex) {
            System.out.println("SampleBatchProcess: ランタイム例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            ex.printStackTrace();
            // 異常終了。
            return END_ERROR;
        } catch (Error er) {
            System.out.println("SampleBatchProcess: ランタイムエラーが発生しました。バッチ処理を中断します。:" + er.toString());
            er.printStackTrace();
            // 異常終了。
            return END_ERROR;
        }
    }

    /**
     * このバッチ処理クラスの使い方の説明を標準出力に示すためのメソッドです。
     */
    public static final void usage() {
        System.out.println("SampleBatchProcess: Usage:");
        System.out.println("  java blanco.sample.batchprocess.SampleBatchProcess -require=値1 -dispstr=値2 -normalparam=値3");
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
        System.out.println("    -? , -help");
        System.out.println("      説明[使い方を表示します。]");
    }

    /**
     * このバッチ処理クラスの入力パラメータの妥当性チェックを実施するためのメソッドです。
     *
     * @param input バッチ処理の入力パラメータ。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public void validateInput(final SampleProcessInput input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: 処理開始失敗。入力パラメータ[input]にnullが与えられました。");
        }
        if (input.getRequire() == null) {
            throw new IllegalArgumentException("SampleBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[require]に値が設定されていません。");
        }
    }
}
