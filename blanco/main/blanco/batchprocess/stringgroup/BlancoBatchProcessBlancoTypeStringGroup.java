package blanco.batchprocess.stringgroup;

/**
 * サポートするblanco型の一覧をあらわします。
 */
public class BlancoBatchProcessBlancoTypeStringGroup {
    /**
     * No.1 説明:文字列。
     */
    public static final int BLANCO_STRING = 1;

    /**
     * No.2 説明:整数(int)。
     */
    public static final int BLANCO_INT = 2;

    /**
     * No.3 説明:整数(long)。
     */
    public static final int BLANCO_LONG = 3;

    /**
     * No.4 説明:数値(decimal)。
     */
    public static final int BLANCO_DECIMAL = 4;

    /**
     * No.5 説明:真偽。
     */
    public static final int BLANCO_BOOLEAN = 5;

    /**
     * 未定義。文字列グループ以外の文字列または定数が未定義のもの。
     */
    public static final int NOT_DEFINED = -1;

    /**
     * 文字列グループに含まれる文字列であるかどうかを判定します。
     *
     * @param argCheck チェックを行いたい文字列。
     * @return 文字列グループに含まれていればture。グループに含まれない文字列であればfalse。
     */
    public boolean match(final String argCheck) {
        // No.1
        // 説明:文字列。
        if ("blanco:string".equals(argCheck)) {
            return true;
        }
        // No.2
        // 説明:整数(int)。
        if ("blanco:int".equals(argCheck)) {
            return true;
        }
        // No.3
        // 説明:整数(long)。
        if ("blanco:long".equals(argCheck)) {
            return true;
        }
        // No.4
        // 説明:数値(decimal)。
        if ("blanco:decimal".equals(argCheck)) {
            return true;
        }
        // No.5
        // 説明:真偽。
        if ("blanco:boolean".equals(argCheck)) {
            return true;
        }
        return false;
    }

    /**
     * 文字列グループに含まれる文字列であるかどうかを、大文字小文字を区別せず判定します。
     *
     * @param argCheck チェックを行いたい文字列。
     * @return 文字列グループに含まれていればture。グループに含まれない文字列であればfalse。
     */
    public boolean matchIgnoreCase(final String argCheck) {
        // No.1
        // 説明:文字列。
        if ("blanco:string".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.2
        // 説明:整数(int)。
        if ("blanco:int".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.3
        // 説明:整数(long)。
        if ("blanco:long".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.4
        // 説明:数値(decimal)。
        if ("blanco:decimal".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.5
        // 説明:真偽。
        if ("blanco:boolean".equalsIgnoreCase(argCheck)) {
            return true;
        }
        return false;
    }

    /**
     * 文字列から定数に変換します。
     *
     * 定数が未定義の場合や 与えられた文字列が文字列グループ外の場合には NOT_DEFINED を戻します。
     *
     * @param argCheck 変換を行いたい文字列。
     * @return 定数に変換後の値。
     */
    public int convertToInt(final String argCheck) {
        // No.1
        // 説明:文字列。
        if ("blanco:string".equals(argCheck)) {
            return BLANCO_STRING;
        }
        // No.2
        // 説明:整数(int)。
        if ("blanco:int".equals(argCheck)) {
            return BLANCO_INT;
        }
        // No.3
        // 説明:整数(long)。
        if ("blanco:long".equals(argCheck)) {
            return BLANCO_LONG;
        }
        // No.4
        // 説明:数値(decimal)。
        if ("blanco:decimal".equals(argCheck)) {
            return BLANCO_DECIMAL;
        }
        // No.5
        // 説明:真偽。
        if ("blanco:boolean".equals(argCheck)) {
            return BLANCO_BOOLEAN;
        }

        // 該当する定数が見つかりませんでした。
        return NOT_DEFINED;
    }

    /**
     * 定数から文字列に変換します。
     *
     * 定数と対応づく文字列に変換します。
     *
     * @param argCheck 変換を行いたい文字定数。
     * @return 文字列に変換後の値。NOT_DEFINEDの場合には長さ0の文字列。
     */
    public String convertToString(final int argCheck) {
        // No.1
        // 説明:文字列。
        if (argCheck == BLANCO_STRING) {
            return "blanco:string";
        }
        // No.2
        // 説明:整数(int)。
        if (argCheck == BLANCO_INT) {
            return "blanco:int";
        }
        // No.3
        // 説明:整数(long)。
        if (argCheck == BLANCO_LONG) {
            return "blanco:long";
        }
        // No.4
        // 説明:数値(decimal)。
        if (argCheck == BLANCO_DECIMAL) {
            return "blanco:decimal";
        }
        // No.5
        // 説明:真偽。
        if (argCheck == BLANCO_BOOLEAN) {
            return "blanco:boolean";
        }
        // 未定義。
        if (argCheck == NOT_DEFINED) {
            return "";
        }

        // いずれにも該当しませんでした。
        throw new IllegalArgumentException("与えられた値(" + argCheck + ")は文字列グループ[BlancoBatchProcessBlancoType]では定義されない値です。");
    }
}
