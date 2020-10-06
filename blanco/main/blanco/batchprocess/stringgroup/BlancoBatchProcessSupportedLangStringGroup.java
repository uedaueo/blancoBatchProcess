package blanco.batchprocess.stringgroup;

/**
 * サポートする言語の一覧を保持します。
 */
public class BlancoBatchProcessSupportedLangStringGroup {
    /**
     * No.1 説明:Java言語。
     */
    public static final int JAVA = 1;

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
        // 説明:Java言語。
        if ("java".equals(argCheck)) {
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
        // 説明:Java言語。
        if ("java".equalsIgnoreCase(argCheck)) {
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
        // 説明:Java言語。
        if ("java".equals(argCheck)) {
            return JAVA;
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
        // 説明:Java言語。
        if (argCheck == JAVA) {
            return "java";
        }
        // 未定義。
        if (argCheck == NOT_DEFINED) {
            return "";
        }

        // いずれにも該当しませんでした。
        throw new IllegalArgumentException("与えられた値(" + argCheck + ")は文字列グループ[BlancoBatchProcessSupportedLang]では定義されない値です。");
    }
}
