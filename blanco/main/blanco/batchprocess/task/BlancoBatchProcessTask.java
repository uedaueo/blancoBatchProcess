/*
 * blanco Framework
 * Copyright (C) 2004-2009 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.batchprocess.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import blanco.batchprocess.task.valueobject.BlancoBatchProcessProcessInput;

/**
 * Apache Antタスク [BlancoBatchProcess]のクラス。
 *
 * バッチ処理定義書からソースコードを自動生成するためのAntTaskです。<br>
 * このクラスでは、Apache Antタスクで一般的に必要なチェックなどのコーディングを肩代わりします。
 * 実際の処理は パッケージ[blanco.batchprocess.task]にBlancoBatchProcessBatchProcessクラスを作成して記述してください。<br>
 * <br>
 * Antタスクへの組み込み例<br>
 * <pre>
 * &lt;taskdef name=&quot;blancobatchprocess&quot; classname=&quot;blanco.batchprocess.task.BlancoBatchProcessTask">
 *     &lt;classpath&gt;
 *         &lt;fileset dir=&quot;lib&quot; includes=&quot;*.jar&quot; /&gt;
 *         &lt;fileset dir=&quot;lib.ant&quot; includes=&quot;*.jar&quot; /&gt;
 *     &lt;/classpath&gt;
 * &lt;/taskdef&gt;
 * </pre>
 */
public class BlancoBatchProcessTask extends Task {
    /**
     * バッチ処理定義書からソースコードを自動生成するためのAntTaskです。
     */
    protected BlancoBatchProcessProcessInput fInput = new BlancoBatchProcessProcessInput();

    /**
     * フィールド [metadir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldMetadirProcessed = false;

    /**
     * フィールド [targetdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetdirProcessed = false;

    /**
     * フィールド [tmpdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTmpdirProcessed = false;

    /**
     * フィールド [encoding] に値がセットされたかどうか。
     */
    protected boolean fIsFieldEncodingProcessed = false;

    /**
     * フィールド [targetlang] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetlangProcessed = false;

    /**
     * フィールド [runtimepackage] に値がセットされたかどうか。
     */
    protected boolean fIsFieldRuntimepackageProcessed = false;

    /**
     * verboseモードで動作させるかどうか。
     *
     * @param arg verboseモードで動作させるかどうか。
     */
    public void setVerbose(final boolean arg) {
        fInput.setVerbose(arg);
    }

    /**
     * verboseモードで動作させるかどうか。
     *
     * @return verboseモードで動作させるかどうか。
     */
    public boolean getVerbose() {
        return fInput.getVerbose();
    }

    /**
     * Antタスクの[metadir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 1<br>
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setMetadir(final String arg) {
        fInput.setMetadir(arg);
        fIsFieldMetadirProcessed = true;
    }

    /**
     * Antタスクの[metadir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 1<br>
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getMetadir() {
        return fInput.getMetadir();
    }

    /**
     * Antタスクの[targetdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 2<br>
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetdir(final String arg) {
        fInput.setTargetdir(arg);
        fIsFieldTargetdirProcessed = true;
    }

    /**
     * Antタスクの[targetdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 2<br>
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetdir() {
        return fInput.getTargetdir();
    }

    /**
     * Antタスクの[tmpdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 3<br>
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。<br>
     *
     * @param arg セットしたい値
     */
    public void setTmpdir(final String arg) {
        fInput.setTmpdir(arg);
        fIsFieldTmpdirProcessed = true;
    }

    /**
     * Antタスクの[tmpdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 3<br>
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。<br>
     * デフォルト値[tmp]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTmpdir() {
        return fInput.getTmpdir();
    }

    /**
     * Antタスクの[encoding]アトリビュートのセッターメソッド。
     *
     * 項目番号: 4<br>
     * 自動生成するソースファイルの文字エンコーディングを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setEncoding(final String arg) {
        fInput.setEncoding(arg);
        fIsFieldEncodingProcessed = true;
    }

    /**
     * Antタスクの[encoding]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 4<br>
     * 自動生成するソースファイルの文字エンコーディングを指定します。<br>
     *
     * @return このフィールドの値
     */
    public String getEncoding() {
        return fInput.getEncoding();
    }

    /**
     * Antタスクの[targetlang]アトリビュートのセッターメソッド。
     *
     * 項目番号: 5<br>
     * ターゲットとなるプログラミング言語処理系名。java, cs, jsが選択可能<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetlang(final String arg) {
        fInput.setTargetlang(arg);
        fIsFieldTargetlangProcessed = true;
    }

    /**
     * Antタスクの[targetlang]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 5<br>
     * ターゲットとなるプログラミング言語処理系名。java, cs, jsが選択可能<br>
     * デフォルト値[java]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetlang() {
        return fInput.getTargetlang();
    }

    /**
     * Antタスクの[runtimepackage]アトリビュートのセッターメソッド。
     *
     * 項目番号: 6<br>
     * ランタイムクラスを生成する生成先を指定します。無指定の場合には 定義書の値を基準に生成されます。<br>
     *
     * @param arg セットしたい値
     */
    public void setRuntimepackage(final String arg) {
        fInput.setRuntimepackage(arg);
        fIsFieldRuntimepackageProcessed = true;
    }

    /**
     * Antタスクの[runtimepackage]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 6<br>
     * ランタイムクラスを生成する生成先を指定します。無指定の場合には 定義書の値を基準に生成されます。<br>
     *
     * @return このフィールドの値
     */
    public String getRuntimepackage() {
        return fInput.getRuntimepackage();
    }

    /**
     * Antタスクのメイン処理。Apache Antから このメソッドが呼び出されます。
     *
     * @throws BuildException タスクとしての例外が発生した場合。
     */
    @Override
    public final void execute() throws BuildException {
        System.out.println("BlancoBatchProcessTask begin.");

        // 項目番号[1]、アトリビュート[metadir]は必須入力です。入力チェックを行います。
        if (fIsFieldMetadirProcessed == false) {
            throw new BuildException("必須アトリビュート[metadir]が設定されていません。処理を中断します。");
        }

        if (getVerbose()) {
            System.out.println("- verbose:[true]");
            System.out.println("- metadir:[" + getMetadir() + "]");
            System.out.println("- targetdir:[" + getTargetdir() + "]");
            System.out.println("- tmpdir:[" + getTmpdir() + "]");
            System.out.println("- encoding:[" + getEncoding() + "]");
            System.out.println("- targetlang:[" + getTargetlang() + "]");
            System.out.println("- runtimepackage:[" + getRuntimepackage() + "]");
        }

        try {
            // 実際のAntタスクの主処理を実行します。
            // この箇所でコンパイルエラーが発生する場合、BlancoBatchProcessProcessインタフェースを実装して blanco.batchprocess.taskパッケージに BlancoBatchProcessProcessImplクラスを作成することにより解決できる場合があります。
            final BlancoBatchProcessProcess proc = new BlancoBatchProcessProcessImpl();
            if (proc.execute(fInput) != BlancoBatchProcessBatchProcess.END_SUCCESS) {
                throw new BuildException("タスクは異常終了しました。");
            }
        } catch (IllegalArgumentException e) {
            if (getVerbose()) {
                e.printStackTrace();
            }
            throw new BuildException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中に例外が発生しました。処理を中断します。" + e.toString());
        } catch (Error e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中にエラーが発生しました。処理を中断します。" + e.toString());
        }
    }
}
