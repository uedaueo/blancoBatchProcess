/*
 * blanco Framework
 * Copyright (C) 2004-2009 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.batchprocess.valueobject;

/**
 * blancoBatchProcess が自動生成するバッチ処理クラスの出力をあらわします。
 */
public class BlancoBatchProcessOutputStructure {
    /**
     * 正常終了時の戻り値。
     *
     * フィールド: [END_SUCCESS]。
     * デフォルト: ["0"]。
     */
    private String fEndSuccess = "0";

    /**
     * バッチ処理例外終了時の戻り値。指定されている場合に BlancoBatchProcessException が生成されます。指定されない場合には BlancoBatchProcessException は生成されません。
     *
     * フィールド: [END_BATCH_PROCESS_EXCEPTION]。
     */
    private String fEndBatchProcessException;

    /**
     * 入力異常終了時の戻り値。
     *
     * フィールド: [END_ILLEGAL_ARGUMENT_EXCEPTION]。
     * デフォルト: ["7"]。
     */
    private String fEndIllegalArgumentException = "7";

    /**
     * 入出力例外終了の戻り値。
     *
     * フィールド: [END_IO_EXCEPTION]。
     * デフォルト: ["8"]。
     */
    private String fEndIoException = "8";

    /**
     * 異常終了時の戻り値。
     *
     * フィールド: [END_ERROR]。
     * デフォルト: ["9"]。
     */
    private String fEndError = "9";

    /**
     * 説明。
     *
     * フィールド: [DESCRIPTION]。
     */
    private String fDescription;

    /**
     * フィールド [END_SUCCESS] の値を設定します。
     *
     * フィールドの説明: [正常終了時の戻り値。]。
     *
     * @param argEndSuccess フィールド[END_SUCCESS]に設定する値。
     */
    public void setEndSuccess(final String argEndSuccess) {
        fEndSuccess = argEndSuccess;
    }

    /**
     * フィールド [END_SUCCESS] の値を取得します。
     *
     * フィールドの説明: [正常終了時の戻り値。]。
     * デフォルト: ["0"]。
     *
     * @return フィールド[END_SUCCESS]から取得した値。
     */
    public String getEndSuccess() {
        return fEndSuccess;
    }

    /**
     * フィールド [END_BATCH_PROCESS_EXCEPTION] の値を設定します。
     *
     * フィールドの説明: [バッチ処理例外終了時の戻り値。指定されている場合に BlancoBatchProcessException が生成されます。指定されない場合には BlancoBatchProcessException は生成されません。]。
     *
     * @param argEndBatchProcessException フィールド[END_BATCH_PROCESS_EXCEPTION]に設定する値。
     */
    public void setEndBatchProcessException(final String argEndBatchProcessException) {
        fEndBatchProcessException = argEndBatchProcessException;
    }

    /**
     * フィールド [END_BATCH_PROCESS_EXCEPTION] の値を取得します。
     *
     * フィールドの説明: [バッチ処理例外終了時の戻り値。指定されている場合に BlancoBatchProcessException が生成されます。指定されない場合には BlancoBatchProcessException は生成されません。]。
     *
     * @return フィールド[END_BATCH_PROCESS_EXCEPTION]から取得した値。
     */
    public String getEndBatchProcessException() {
        return fEndBatchProcessException;
    }

    /**
     * フィールド [END_ILLEGAL_ARGUMENT_EXCEPTION] の値を設定します。
     *
     * フィールドの説明: [入力異常終了時の戻り値。]。
     *
     * @param argEndIllegalArgumentException フィールド[END_ILLEGAL_ARGUMENT_EXCEPTION]に設定する値。
     */
    public void setEndIllegalArgumentException(final String argEndIllegalArgumentException) {
        fEndIllegalArgumentException = argEndIllegalArgumentException;
    }

    /**
     * フィールド [END_ILLEGAL_ARGUMENT_EXCEPTION] の値を取得します。
     *
     * フィールドの説明: [入力異常終了時の戻り値。]。
     * デフォルト: ["7"]。
     *
     * @return フィールド[END_ILLEGAL_ARGUMENT_EXCEPTION]から取得した値。
     */
    public String getEndIllegalArgumentException() {
        return fEndIllegalArgumentException;
    }

    /**
     * フィールド [END_IO_EXCEPTION] の値を設定します。
     *
     * フィールドの説明: [入出力例外終了の戻り値。]。
     *
     * @param argEndIoException フィールド[END_IO_EXCEPTION]に設定する値。
     */
    public void setEndIoException(final String argEndIoException) {
        fEndIoException = argEndIoException;
    }

    /**
     * フィールド [END_IO_EXCEPTION] の値を取得します。
     *
     * フィールドの説明: [入出力例外終了の戻り値。]。
     * デフォルト: ["8"]。
     *
     * @return フィールド[END_IO_EXCEPTION]から取得した値。
     */
    public String getEndIoException() {
        return fEndIoException;
    }

    /**
     * フィールド [END_ERROR] の値を設定します。
     *
     * フィールドの説明: [異常終了時の戻り値。]。
     *
     * @param argEndError フィールド[END_ERROR]に設定する値。
     */
    public void setEndError(final String argEndError) {
        fEndError = argEndError;
    }

    /**
     * フィールド [END_ERROR] の値を取得します。
     *
     * フィールドの説明: [異常終了時の戻り値。]。
     * デフォルト: ["9"]。
     *
     * @return フィールド[END_ERROR]から取得した値。
     */
    public String getEndError() {
        return fEndError;
    }

    /**
     * フィールド [DESCRIPTION] の値を設定します。
     *
     * フィールドの説明: [説明。]。
     *
     * @param argDescription フィールド[DESCRIPTION]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [DESCRIPTION] の値を取得します。
     *
     * フィールドの説明: [説明。]。
     *
     * @return フィールド[DESCRIPTION]から取得した値。
     */
    public String getDescription() {
        return fDescription;
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
        buf.append("blanco.batchprocess.valueobject.BlancoBatchProcessOutputStructure[");
        buf.append("END_SUCCESS=" + fEndSuccess);
        buf.append(",END_BATCH_PROCESS_EXCEPTION=" + fEndBatchProcessException);
        buf.append(",END_ILLEGAL_ARGUMENT_EXCEPTION=" + fEndIllegalArgumentException);
        buf.append(",END_IO_EXCEPTION=" + fEndIoException);
        buf.append(",END_ERROR=" + fEndError);
        buf.append(",DESCRIPTION=" + fDescription);
        buf.append("]");
        return buf.toString();
    }
}
