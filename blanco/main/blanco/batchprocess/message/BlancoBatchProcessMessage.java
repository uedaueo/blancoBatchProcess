/*
 * このソースコードは blanco Frameworkによって自動生成されています。
 */
package blanco.batchprocess.message;

/**
 * blancoBatchProcessのメッセージクラス。プログラム中で利用されるメッセージを格納します。
 */
public class BlancoBatchProcessMessage {
    /**
     * メッセージをプロパティファイル対応させるための内部的に利用するリソースバンドルクラス。
     */
    protected final BlancoBatchProcessMessageResourceBundle fBundle = new BlancoBatchProcessMessageResourceBundle();

    /**
     * メッセージ定義ID[BlancoBatchProcess]、キー[MBBPI001]の文字列を取得します。
     *
     * No.2:
     * 文字列[バッチ処理定義ID[{0}]のパッケージ名が指定されていません。]
     *
     * @param arg0 置換文字列{0}の値。
     * @return メッセージ文字列。
     */
    public String getMbbpi001(final String arg0) {
        if (arg0 == null) {
            throw new IllegalArgumentException("メソッド[getMbbpi001]のパラメータ[arg0]にnullが与えられました。しかし、このパラメータにnullを与えることはできません。");
        }

        return "[MBBPI001] " + fBundle.getMbbpi001(arg0);
    }

    /**
     * メッセージ定義ID[BlancoBatchProcess]、キー[MBBPI002]の文字列を取得します。
     *
     * No.3:
     * 文字列[サポートしない出力プログラミング言語処理系[{0}]が指定されました。]
     *
     * @param arg0 置換文字列{0}の値。
     * @return メッセージ文字列。
     */
    public String getMbbpi002(final String arg0) {
        if (arg0 == null) {
            throw new IllegalArgumentException("メソッド[getMbbpi002]のパラメータ[arg0]にnullが与えられました。しかし、このパラメータにnullを与えることはできません。");
        }

        return "[MBBPI002] " + fBundle.getMbbpi002(arg0);
    }

    /**
     * メッセージ定義ID[BlancoBatchProcess]、キー[MBBPI003]の文字列を取得します。
     *
     * No.4:
     * 文字列[バッチ処理定義ID[{0}]において、入力パラメータ[{1}]に「必須」と「デフォルト」が同時に指定されています。これらは同時に指定することはできません。]
     *
     * @param arg0 置換文字列{0}の値。
     * @param arg1 置換文字列{1}の値。
     * @return メッセージ文字列。
     */
    public String getMbbpi003(final String arg0, final String arg1) {
        if (arg0 == null) {
            throw new IllegalArgumentException("メソッド[getMbbpi003]のパラメータ[arg0]にnullが与えられました。しかし、このパラメータにnullを与えることはできません。");
        }
        if (arg1 == null) {
            throw new IllegalArgumentException("メソッド[getMbbpi003]のパラメータ[arg1]にnullが与えられました。しかし、このパラメータにnullを与えることはできません。");
        }

        return "[MBBPI003] " + fBundle.getMbbpi003(arg0, arg1);
    }

    /**
     * メッセージ定義ID[BlancoBatchProcess]、キー[MBBPA001]の文字列を取得します。
     *
     * No.7:
     * 文字列[メタディレクトリ[{0}]が存在しません。]
     *
     * @param arg0 置換文字列{0}の値。
     * @return メッセージ文字列。
     */
    public String getMbbpa001(final String arg0) {
        if (arg0 == null) {
            throw new IllegalArgumentException("メソッド[getMbbpa001]のパラメータ[arg0]にnullが与えられました。しかし、このパラメータにnullを与えることはできません。");
        }

        return "[MBBPA001] " + fBundle.getMbbpa001(arg0);
    }
}
