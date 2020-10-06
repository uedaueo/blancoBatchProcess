package blanco.batchprocess.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * メッセージ定義[BlancoBatchProcess]が内部的に利用するリソースバンドルクラス。
 *
 * リソースバンドル定義: [BlancoBatchProcessMessage]。<BR>
 * このクラスはリソースバンドル定義書から自動生成されたリソースバンドルクラスです。<BR>
 */
class BlancoBatchProcessMessageResourceBundle {
    /**
     * リソースバンドルオブジェクト。
     *
     * 内部的に実際に入力を行うリソースバンドルを記憶します。
     */
    private ResourceBundle fResourceBundle;

    /**
     * BlancoBatchProcessMessageResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoBatchProcessMessage]、デフォルトのロケール、呼び出し側のクラスローダを使用して、リソースバンドルを取得します。
     */
    public BlancoBatchProcessMessageResourceBundle() {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/batchprocess/message/BlancoBatchProcessMessage");
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * BlancoBatchProcessMessageResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoBatchProcessMessage]、指定されたロケール、呼び出し側のクラスローダを使用して、リソースバンドルを取得します。
     *
     * @param locale ロケールの指定
     */
    public BlancoBatchProcessMessageResourceBundle(final Locale locale) {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/batchprocess/message/BlancoBatchProcessMessage", locale);
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * BlancoBatchProcessMessageResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoBatchProcessMessage]、指定されたロケール、指定されたクラスローダを使用して、リソースバンドルを取得します。
     *
     * @param locale ロケールの指定
     * @param loader クラスローダの指定
     */
    public BlancoBatchProcessMessageResourceBundle(final Locale locale, final ClassLoader loader) {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/batchprocess/message/BlancoBatchProcessMessage", locale, loader);
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * 内部的に保持しているリソースバンドルオブジェクトを取得します。
     *
     * @return 内部的に保持しているリソースバンドルオブジェクト。
     */
    public ResourceBundle getResourceBundle() {
        return fResourceBundle;
    }

    /**
     * bundle[BlancoBatchProcessMessage], key[MBBPI001]
     *
     * [バッチ処理定義ID[{0}]のパッケージ名が指定されていません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[MBBPI001]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMbbpi001(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "バッチ処理定義ID[{0}]のパッケージ名が指定されていません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("MBBPI001");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoBatchProcessMessage], key[MBBPI002]
     *
     * [サポートしない出力プログラミング言語処理系[{0}]が指定されました。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[MBBPI002]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMbbpi002(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "サポートしない出力プログラミング言語処理系[{0}]が指定されました。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("MBBPI002");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoBatchProcessMessage], key[MBBPI003]
     *
     * [バッチ処理定義ID[{0}]において、入力パラメータ[{1}]に「必須」と「デフォルト」が同時に指定されています。これらは同時に指定することはできません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @return key[MBBPI003]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMbbpi003(final String arg0, final String arg1) {
        // 初期値として定義書の値を利用します。
        String strFormat = "バッチ処理定義ID[{0}]において、入力パラメータ[{1}]に「必須」と「デフォルト」が同時に指定されています。これらは同時に指定することはできません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("MBBPI003");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoBatchProcessMessage], key[MBBPA001]
     *
     * [メタディレクトリ[{0}]が存在しません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[MBBPA001]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMbbpa001(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "メタディレクトリ[{0}]が存在しません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("MBBPA001");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }
}
