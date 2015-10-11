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

import java.math.BigDecimal;

/**
 * 処理クラス [Sample2Process]の入力バリューオブジェクトクラスです。
 */
public class Sample2ProcessInput {
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
     * フィールド: [field_int_req]。
     */
    private int fFieldIntReq;

    /**
     * フィールド: [field_int]。
     */
    private int fFieldInt;

    /**
     * フィールド: [field_long_req]。
     */
    private long fFieldLongReq;

    /**
     * フィールド: [field_long]。
     */
    private long fFieldLong;

    /**
     * フィールド: [field_decimal_req]。
     */
    private BigDecimal fFieldDecimalReq;

    /**
     * フィールド: [field_decimal]。
     */
    private BigDecimal fFieldDecimal;

    /**
     * フィールド: [field_boolean_req]。
     */
    private boolean fFieldBooleanReq;

    /**
     * フィールド: [field_boolean]。
     */
    private boolean fFieldBoolean;

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
     * フィールド [field_int_req] の値を設定します。
     *
     * フィールドの説明: []。
     *
     * @param argFieldIntReq フィールド[field_int_req]に設定する値。
     */
    public void setFieldIntReq(final int argFieldIntReq) {
        fFieldIntReq = argFieldIntReq;
    }

    /**
     * フィールド [field_int_req] の値を取得します。
     *
     * フィールドの説明: []。
     *
     * @return フィールド[field_int_req]から取得した値。
     */
    public int getFieldIntReq() {
        return fFieldIntReq;
    }

    /**
     * フィールド [field_int] の値を設定します。
     *
     * フィールドの説明: []。
     *
     * @param argFieldInt フィールド[field_int]に設定する値。
     */
    public void setFieldInt(final int argFieldInt) {
        fFieldInt = argFieldInt;
    }

    /**
     * フィールド [field_int] の値を取得します。
     *
     * フィールドの説明: []。
     *
     * @return フィールド[field_int]から取得した値。
     */
    public int getFieldInt() {
        return fFieldInt;
    }

    /**
     * フィールド [field_long_req] の値を設定します。
     *
     * フィールドの説明: []。
     *
     * @param argFieldLongReq フィールド[field_long_req]に設定する値。
     */
    public void setFieldLongReq(final long argFieldLongReq) {
        fFieldLongReq = argFieldLongReq;
    }

    /**
     * フィールド [field_long_req] の値を取得します。
     *
     * フィールドの説明: []。
     *
     * @return フィールド[field_long_req]から取得した値。
     */
    public long getFieldLongReq() {
        return fFieldLongReq;
    }

    /**
     * フィールド [field_long] の値を設定します。
     *
     * フィールドの説明: []。
     *
     * @param argFieldLong フィールド[field_long]に設定する値。
     */
    public void setFieldLong(final long argFieldLong) {
        fFieldLong = argFieldLong;
    }

    /**
     * フィールド [field_long] の値を取得します。
     *
     * フィールドの説明: []。
     *
     * @return フィールド[field_long]から取得した値。
     */
    public long getFieldLong() {
        return fFieldLong;
    }

    /**
     * フィールド [field_decimal_req] の値を設定します。
     *
     * フィールドの説明: []。
     *
     * @param argFieldDecimalReq フィールド[field_decimal_req]に設定する値。
     */
    public void setFieldDecimalReq(final BigDecimal argFieldDecimalReq) {
        fFieldDecimalReq = argFieldDecimalReq;
    }

    /**
     * フィールド [field_decimal_req] の値を取得します。
     *
     * フィールドの説明: []。
     *
     * @return フィールド[field_decimal_req]から取得した値。
     */
    public BigDecimal getFieldDecimalReq() {
        return fFieldDecimalReq;
    }

    /**
     * フィールド [field_decimal] の値を設定します。
     *
     * フィールドの説明: []。
     *
     * @param argFieldDecimal フィールド[field_decimal]に設定する値。
     */
    public void setFieldDecimal(final BigDecimal argFieldDecimal) {
        fFieldDecimal = argFieldDecimal;
    }

    /**
     * フィールド [field_decimal] の値を取得します。
     *
     * フィールドの説明: []。
     *
     * @return フィールド[field_decimal]から取得した値。
     */
    public BigDecimal getFieldDecimal() {
        return fFieldDecimal;
    }

    /**
     * フィールド [field_boolean_req] の値を設定します。
     *
     * フィールドの説明: []。
     *
     * @param argFieldBooleanReq フィールド[field_boolean_req]に設定する値。
     */
    public void setFieldBooleanReq(final boolean argFieldBooleanReq) {
        fFieldBooleanReq = argFieldBooleanReq;
    }

    /**
     * フィールド [field_boolean_req] の値を取得します。
     *
     * フィールドの説明: []。
     *
     * @return フィールド[field_boolean_req]から取得した値。
     */
    public boolean getFieldBooleanReq() {
        return fFieldBooleanReq;
    }

    /**
     * フィールド [field_boolean] の値を設定します。
     *
     * フィールドの説明: []。
     *
     * @param argFieldBoolean フィールド[field_boolean]に設定する値。
     */
    public void setFieldBoolean(final boolean argFieldBoolean) {
        fFieldBoolean = argFieldBoolean;
    }

    /**
     * フィールド [field_boolean] の値を取得します。
     *
     * フィールドの説明: []。
     *
     * @return フィールド[field_boolean]から取得した値。
     */
    public boolean getFieldBoolean() {
        return fFieldBoolean;
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
        buf.append("blanco.sample.batchprocess.valueobject.Sample2ProcessInput[");
        buf.append("require=" + fRequire);
        buf.append(",dispstr=" + fDispstr);
        buf.append(",normalparam=" + fNormalparam);
        buf.append(",field_int_req=" + fFieldIntReq);
        buf.append(",field_int=" + fFieldInt);
        buf.append(",field_long_req=" + fFieldLongReq);
        buf.append(",field_long=" + fFieldLong);
        buf.append(",field_decimal_req=" + fFieldDecimalReq);
        buf.append(",field_decimal=" + fFieldDecimal);
        buf.append(",field_boolean_req=" + fFieldBooleanReq);
        buf.append(",field_boolean=" + fFieldBoolean);
        buf.append("]");
        return buf.toString();
    }
}
