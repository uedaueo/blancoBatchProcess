/*
 * blanco Framework
 * Copyright (C) 2004-2006 IGA Tosiki
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.batchprocess.task;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import blanco.batchprocess.BlancoBatchProcessConstants;
import blanco.batchprocess.BlancoBatchProcessMeta2Xml;
import blanco.batchprocess.BlancoBatchProcessXml2SourceFile;
import blanco.batchprocess.message.BlancoBatchProcessMessage;
import blanco.batchprocess.task.valueobject.BlancoBatchProcessProcessInput;

/**
 * バッチ処理生成処理の実際の処理内容。
 */
public class BlancoBatchProcessProcessImpl implements BlancoBatchProcessProcess {
    /**
     * メッセージクラス。
     */
    protected final BlancoBatchProcessMessage fMsg = new BlancoBatchProcessMessage();

    protected BlancoBatchProcessProcessInput fInput;

    /**
     * 具体的なバッチ処理内容を記述するためのメソッドです。
     * 
     * このメソッドに実際の処理内容を記述します。
     * 
     * @param input
     *            バッチ処理の入力パラメータ。
     * @return バッチ処理の終了コード。正常終了の場合には、BlancoBatchProcessBatchProcess.END_SUCCESS。
     * @throws IOException
     *             入出力例外が発生した場合。
     * @throws IllegalArgumentException
     *             入力値に不正が見つかった場合。
     */
    public int execute(final BlancoBatchProcessProcessInput input)
            throws IOException, IllegalArgumentException {
        fInput = input;
        System.out.println("- " + BlancoBatchProcessConstants.PRODUCT_NAME
                + " (" + BlancoBatchProcessConstants.VERSION + ")");

        try {
            final File fileMetadir = new File(input.getMetadir());
            if (fileMetadir.exists() == false) {
                throw new IllegalArgumentException(fMsg.getMbbpa001(input
                        .getMetadir()));
            }

            // テンポラリディレクトリを作成。
            new File(input.getTmpdir()
                    + BlancoBatchProcessConstants.TARGET_SUBDIRECTORY).mkdirs();

            // 指定されたメタディレクトリを処理します。
            new BlancoBatchProcessMeta2Xml().processDirectory(fileMetadir,
                    input.getTmpdir()
                            + BlancoBatchProcessConstants.TARGET_SUBDIRECTORY);

            // XML化された中間ファイルからソースコードを生成
            final File[] fileMeta2 = new File(input.getTmpdir()
                    + BlancoBatchProcessConstants.TARGET_SUBDIRECTORY)
                    .listFiles();
            for (int index = 0; index < fileMeta2.length; index++) {
                if (fileMeta2[index].getName().endsWith(".xml") == false) {
                    continue;
                }

                if (progress("ファイル [" + fileMeta2[index].getName()
                        + "] を処理中...")) {
                    return BlancoBatchProcessBatchProcess.END_ERROR;
                }

                final BlancoBatchProcessXml2SourceFile xml2source = new BlancoBatchProcessXml2SourceFile();
                xml2source.setEncoding(input.getEncoding());
                xml2source.process(fileMeta2[index], input.getRuntimepackage(),
                        input.getTargetlang(), new File(input.getTargetdir()));
            }

            return BlancoBatchProcessBatchProcess.END_SUCCESS;
        } catch (TransformerException ex) {
            throw new IOException("XML変換の過程で例外が発生しました: " + ex.toString());
        }
    }

    /**
     * 処理の中でアイテムが処理されるたびに進捗報告としてコールバックします。
     * 
     * @param argProgressMessage
     *            現在処理しているアイテムに関するメッセージ。
     * @return 処理をそのまま継続する場合は false。処理中断をリクエストしたい場合は true。
     */
    public boolean progress(String argProgressMessage) {
        if (fInput.getVerbose()) {
            System.out.println(" " + argProgressMessage);
        }
        return false;
    }
}
