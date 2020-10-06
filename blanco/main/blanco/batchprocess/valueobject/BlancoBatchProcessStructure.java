package blanco.batchprocess.valueobject;

import java.util.List;

/**
 * blancoBatchProcess が自動生成するバッチ処理クラスの各種情報をあらわします。
 */
public class BlancoBatchProcessStructure {
    /**
     * メッセージ定義IDを指定します。必須項目です。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * パッケージ名を指定します。必須項目です。
     *
     * フィールド: [package]。
     */
    private String fPackage;

    /**
     * メッセージ定義の説明を記載します。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * クラス名の後方に付与されるサフィックス。
     *
     * フィールド: [suffix]。
     */
    private String fSuffix;

    /**
     * フィールドを保持するリスト。
     *
     * フィールド: [inputItemList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure&gt;()]。
     */
    private List<blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure> fInputItemList = new java.util.ArrayList<blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure>();

    /**
     * 戻り値を記憶するフィールド。
     *
     * フィールド: [output]。
     */
    private BlancoBatchProcessOutputStructure fOutput;

    /**
     * バッチの開始・終了メッセージを表示するかどうか。blancoBatchProcess単体で利用する場合にはtrueを設定します。他プロダクトへの組み込み時はfalseを設定します。
     *
     * フィールド: [showMessageBeginEnd]。
     * デフォルト: [true]。
     */
    private boolean fShowMessageBeginEnd = true;

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [メッセージ定義IDを指定します。必須項目です。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [メッセージ定義IDを指定します。必須項目です。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [package] の値を設定します。
     *
     * フィールドの説明: [パッケージ名を指定します。必須項目です。]。
     *
     * @param argPackage フィールド[package]に設定する値。
     */
    public void setPackage(final String argPackage) {
        fPackage = argPackage;
    }

    /**
     * フィールド [package] の値を取得します。
     *
     * フィールドの説明: [パッケージ名を指定します。必須項目です。]。
     *
     * @return フィールド[package]から取得した値。
     */
    public String getPackage() {
        return fPackage;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [メッセージ定義の説明を記載します。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [メッセージ定義の説明を記載します。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [suffix] の値を設定します。
     *
     * フィールドの説明: [クラス名の後方に付与されるサフィックス。]。
     *
     * @param argSuffix フィールド[suffix]に設定する値。
     */
    public void setSuffix(final String argSuffix) {
        fSuffix = argSuffix;
    }

    /**
     * フィールド [suffix] の値を取得します。
     *
     * フィールドの説明: [クラス名の後方に付与されるサフィックス。]。
     *
     * @return フィールド[suffix]から取得した値。
     */
    public String getSuffix() {
        return fSuffix;
    }

    /**
     * フィールド [inputItemList] の値を設定します。
     *
     * フィールドの説明: [フィールドを保持するリスト。]。
     *
     * @param argInputItemList フィールド[inputItemList]に設定する値。
     */
    public void setInputItemList(final List<blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure> argInputItemList) {
        fInputItemList = argInputItemList;
    }

    /**
     * フィールド [inputItemList] の値を取得します。
     *
     * フィールドの説明: [フィールドを保持するリスト。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure&gt;()]。
     *
     * @return フィールド[inputItemList]から取得した値。
     */
    public List<blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure> getInputItemList() {
        return fInputItemList;
    }

    /**
     * フィールド [output] の値を設定します。
     *
     * フィールドの説明: [戻り値を記憶するフィールド。]。
     *
     * @param argOutput フィールド[output]に設定する値。
     */
    public void setOutput(final BlancoBatchProcessOutputStructure argOutput) {
        fOutput = argOutput;
    }

    /**
     * フィールド [output] の値を取得します。
     *
     * フィールドの説明: [戻り値を記憶するフィールド。]。
     *
     * @return フィールド[output]から取得した値。
     */
    public BlancoBatchProcessOutputStructure getOutput() {
        return fOutput;
    }

    /**
     * フィールド [showMessageBeginEnd] の値を設定します。
     *
     * フィールドの説明: [バッチの開始・終了メッセージを表示するかどうか。blancoBatchProcess単体で利用する場合にはtrueを設定します。他プロダクトへの組み込み時はfalseを設定します。]。
     *
     * @param argShowMessageBeginEnd フィールド[showMessageBeginEnd]に設定する値。
     */
    public void setShowMessageBeginEnd(final boolean argShowMessageBeginEnd) {
        fShowMessageBeginEnd = argShowMessageBeginEnd;
    }

    /**
     * フィールド [showMessageBeginEnd] の値を取得します。
     *
     * フィールドの説明: [バッチの開始・終了メッセージを表示するかどうか。blancoBatchProcess単体で利用する場合にはtrueを設定します。他プロダクトへの組み込み時はfalseを設定します。]。
     * デフォルト: [true]。
     *
     * @return フィールド[showMessageBeginEnd]から取得した値。
     */
    public boolean getShowMessageBeginEnd() {
        return fShowMessageBeginEnd;
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
        buf.append("blanco.batchprocess.valueobject.BlancoBatchProcessStructure[");
        buf.append("name=" + fName);
        buf.append(",package=" + fPackage);
        buf.append(",description=" + fDescription);
        buf.append(",suffix=" + fSuffix);
        buf.append(",inputItemList=" + fInputItemList);
        buf.append(",output=" + fOutput);
        buf.append(",showMessageBeginEnd=" + fShowMessageBeginEnd);
        buf.append("]");
        return buf.toString();
    }

    /**
     * このバリューオブジェクトを指定のターゲットに複写します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ複写処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoBatchProcessStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoBatchProcessStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fPackage
        // Type: java.lang.String
        target.fPackage = this.fPackage;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fSuffix
        // Type: java.lang.String
        target.fSuffix = this.fSuffix;
        // Name: fInputItemList
        // Type: java.util.List
        if (this.fInputItemList != null) {
            final java.util.Iterator<blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure> iterator = this.fInputItemList.iterator();
            for (; iterator.hasNext();) {
                blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure loopSource = iterator.next();
                blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure loopTarget = null;
                // フィールド[generics]はサポート外の型[blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure]です。
                target.fInputItemList.add(loopTarget);
            }
        }
        // Name: fOutput
        // Type: blanco.batchprocess.valueobject.BlancoBatchProcessOutputStructure
        // フィールド[fOutput]はサポート外の型[blanco.batchprocess.valueobject.BlancoBatchProcessOutputStructure]です。
        // Name: fShowMessageBeginEnd
        // Type: boolean
        target.fShowMessageBeginEnd = this.fShowMessageBeginEnd;
    }
}
