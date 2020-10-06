package blanco.batchprocess.resourcebundle;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * BlancoBatchProcessが利用するリソースバンドルを蓄えます。
 *
 * リソースバンドル定義: [BlancoBatchProcess]。<BR>
 * このクラスはリソースバンドル定義書から自動生成されたリソースバンドルクラスです。<BR>
 * 既知のロケール<BR>
 * <UL>
 * <LI>ja
 * </UL>
 */
public class BlancoBatchProcessResourceBundle {
    /**
     * リソースバンドルオブジェクト。
     *
     * 内部的に実際に入力を行うリソースバンドルを記憶します。
     */
    private ResourceBundle fResourceBundle;

    /**
     * BlancoBatchProcessResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoBatchProcess]、デフォルトのロケール、呼び出し側のクラスローダを使用して、リソースバンドルを取得します。
     */
    public BlancoBatchProcessResourceBundle() {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/batchprocess/resourcebundle/BlancoBatchProcess");
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * BlancoBatchProcessResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoBatchProcess]、指定されたロケール、呼び出し側のクラスローダを使用して、リソースバンドルを取得します。
     *
     * @param locale ロケールの指定
     */
    public BlancoBatchProcessResourceBundle(final Locale locale) {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/batchprocess/resourcebundle/BlancoBatchProcess", locale);
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * BlancoBatchProcessResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoBatchProcess]、指定されたロケール、指定されたクラスローダを使用して、リソースバンドルを取得します。
     *
     * @param locale ロケールの指定
     * @param loader クラスローダの指定
     */
    public BlancoBatchProcessResourceBundle(final Locale locale, final ClassLoader loader) {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/batchprocess/resourcebundle/BlancoBatchProcess", locale, loader);
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
     * bundle[BlancoBatchProcess], key[METAFILE_DISPLAYNAME]
     *
     * [バッチ処理定義書] (ja)<br>
     *
     * @return key[METAFILE_DISPLAYNAME]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMetafileDisplayname() {
        // 初期値として定義書の値を利用します。
        String strFormat = "バッチ処理定義書";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("METAFILE_DISPLAYNAME");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }
}
