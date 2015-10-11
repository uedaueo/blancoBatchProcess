/*
 * blanco Framework
 * Copyright (C) 2004-2009 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.sample.batchprocess.valueobject;

/**
 * 処理クラス [SampleProcess]の入力バリューオブジェクトクラスです。
 */
public class SampleProcessInput {
    /**
     * 必須引数のサンプル。
     *
     * フィールド: [require]。
     */
    private String fRequire;

    /**
     * 表示文字列のサンプル。
     *
     * フィールド: [dispstr]。
     * デフォルト: [特に指定が無い場合の文字列]。
     */
    private String fDispstr = "特に指定が無い場合の文字列";

    /**
     * 必須でもなく、またデフォルトも持たない引数の例。
     *
     * フィールド: [normalparam]。
     */
    private String fNormalparam;

    /**
     * フィールド [require] の値を設定します。
     *
     * フィールドの説明: [必須引数のサンプル。]。
     *
     * @param argRequire フィールド[require]に設定する値。
     */
    public void setRequire(final String argRequire) {
        fRequire = argRequire;
    }

    /**
     * フィールド [require] の値を取得します。
     *
     * フィールドの説明: [必須引数のサンプル。]。
     *
     * @return フィールド[require]から取得した値。
     */
    public String getRequire() {
        return fRequire;
    }

    /**
     * フィールド [dispstr] の値を設定します。
     *
     * フィールドの説明: [表示文字列のサンプル。]。
     *
     * @param argDispstr フィールド[dispstr]に設定する値。
     */
    public void setDispstr(final String argDispstr) {
        fDispstr = argDispstr;
    }

    /**
     * フィールド [dispstr] の値を取得します。
     *
     * フィールドの説明: [表示文字列のサンプル。]。
     * デフォルト: [特に指定が無い場合の文字列]。
     *
     * @return フィールド[dispstr]から取得した値。
     */
    public String getDispstr() {
        return fDispstr;
    }

    /**
     * フィールド [normalparam] の値を設定します。
     *
     * フィールドの説明: [必須でもなく、またデフォルトも持たない引数の例。]。
     *
     * @param argNormalparam フィールド[normalparam]に設定する値。
     */
    public void setNormalparam(final String argNormalparam) {
        fNormalparam = argNormalparam;
    }

    /**
     * フィールド [normalparam] の値を取得します。
     *
     * フィールドの説明: [必須でもなく、またデフォルトも持たない引数の例。]。
     *
     * @return フィールド[normalparam]から取得した値。
     */
    public String getNormalparam() {
        return fNormalparam;
    }

    /**
     * このバリューオブジェクトの文字列表現を取得します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ文字列化の処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @return バリューオブジェクトの文字列表現。
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.sample.batchprocess.valueobject.SampleProcessInput[");
        buf.append("require=" + fRequire);
        buf.append(",dispstr=" + fDispstr);
        buf.append(",normalparam=" + fNormalparam);
        buf.append("]");
        return buf.toString();
    }
}
