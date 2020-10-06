package blanco.batchprocess.valueobject;

/**
 * blancoBatchProcess が自動生成するバッチ処理クラスの入力をあらわします。
 */
public class BlancoBatchProcessInputItemStructure {
    /**
     * パラメータ名を指定します。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * 型を示します。
     *
     * フィールド: [type]。
     * デフォルト: [&quot;blanco:string&quot;]。
     */
    private String fType = "blanco:string";

    /**
     * 必須項目であるかどうか。
     *
     * フィールド: [require]。
     * デフォルト: [false]。
     */
    private boolean fRequire = false;

    /**
     * デフォルト値。
     *
     * フィールド: [default]。
     */
    private String fDefault;

    /**
     * 説明。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [パラメータ名を指定します。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [パラメータ名を指定します。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [type] の値を設定します。
     *
     * フィールドの説明: [型を示します。]。
     *
     * @param argType フィールド[type]に設定する値。
     */
    public void setType(final String argType) {
        fType = argType;
    }

    /**
     * フィールド [type] の値を取得します。
     *
     * フィールドの説明: [型を示します。]。
     * デフォルト: [&quot;blanco:string&quot;]。
     *
     * @return フィールド[type]から取得した値。
     */
    public String getType() {
        return fType;
    }

    /**
     * フィールド [require] の値を設定します。
     *
     * フィールドの説明: [必須項目であるかどうか。]。
     *
     * @param argRequire フィールド[require]に設定する値。
     */
    public void setRequire(final boolean argRequire) {
        fRequire = argRequire;
    }

    /**
     * フィールド [require] の値を取得します。
     *
     * フィールドの説明: [必須項目であるかどうか。]。
     * デフォルト: [false]。
     *
     * @return フィールド[require]から取得した値。
     */
    public boolean getRequire() {
        return fRequire;
    }

    /**
     * フィールド [default] の値を設定します。
     *
     * フィールドの説明: [デフォルト値。]。
     *
     * @param argDefault フィールド[default]に設定する値。
     */
    public void setDefault(final String argDefault) {
        fDefault = argDefault;
    }

    /**
     * フィールド [default] の値を取得します。
     *
     * フィールドの説明: [デフォルト値。]。
     *
     * @return フィールド[default]から取得した値。
     */
    public String getDefault() {
        return fDefault;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [説明。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [説明。]。
     *
     * @return フィールド[description]から取得した値。
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
        buf.append("blanco.batchprocess.valueobject.BlancoBatchProcessInputItemStructure[");
        buf.append("name=" + fName);
        buf.append(",type=" + fType);
        buf.append(",require=" + fRequire);
        buf.append(",default=" + fDefault);
        buf.append(",description=" + fDescription);
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
    public void copyTo(final BlancoBatchProcessInputItemStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoBatchProcessInputItemStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fType
        // Type: java.lang.String
        target.fType = this.fType;
        // Name: fRequire
        // Type: boolean
        target.fRequire = this.fRequire;
        // Name: fDefault
        // Type: java.lang.String
        target.fDefault = this.fDefault;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
    }
}
