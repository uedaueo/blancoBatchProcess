/*
 * blanco Framework
 * Copyright (C) 2004-2006 IGA Tosiki
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.batchprocess;

import java.io.File;

import blanco.batchprocess.stringgroup.BlancoBatchProcessSupportedLangStringGroup;
import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * blancoBatchProcessのための例外クラスを出力します。
 */
class BlancoBatchProcessExpandException {
    /**
     * 出力対象となるプログラミング言語。
     */
    private int fTargetLang = BlancoBatchProcessSupportedLangStringGroup.NOT_DEFINED;

    /**
     * 内部的に利用するblancoCg用ファクトリ。
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * 内部的に利用するblancoCg用ソースファイル情報。
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * 内部的に利用するblancoCg用クラス情報。
     */
    private BlancoCgClass fCgClass = null;

    /**
     * 収集された情報を元に、ソースコードを自動生成します。
     * 
     * @param argRuntimePackage
     *            メタファイルから収集できた処理構造データ。
     * @param argTargetLang
     *            出力対象プログラミング言語。
     * @param argDirectoryTarget
     *            ソースコードの出力先フォルダ。
     */
    public void expandSourceFile(final String argRuntimePackage,
            final int argTargetLang, final File argDirectoryTarget) {
        fTargetLang = argTargetLang;

        // 従来と互換性を持たせるため、/mainサブフォルダに出力します。
        final File fileBlancoMain = new File(argDirectoryTarget
                .getAbsolutePath()
                + "/main");

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(argRuntimePackage, null);
        fCgSourceFile.setEncoding("UTF-8"); // FIXME set encoding from input info.
        fCgClass = fCgFactory.createClass("BlancoBatchProcessException",
                "バッチ処理において例外が発生した際に利用されます。blancoBatchProcessの例外です。");
        fCgSourceFile.getClassList().add(fCgClass);

        fCgClass.getExtendClassList().add(
                fCgFactory.createType("java.lang.RuntimeException"));

        // シリアルIDの警告を抑制。
        fCgClass.getAnnotationList().add("SuppressWarnings(\"serial\")");

        // コンストラクタを追加します。
        BlancoCgUtil.addConstructorForException(fCgFactory, fCgClass);

        switch (fTargetLang) {
        case BlancoBatchProcessSupportedLangStringGroup.JAVA:
            BlancoCgTransformerFactory.getJavaSourceTransformer().transform(
                    fCgSourceFile, fileBlancoMain);
            break;
        default:
            break;
        }
    }
}
