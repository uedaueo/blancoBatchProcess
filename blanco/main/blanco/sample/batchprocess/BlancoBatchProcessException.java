/*
 * blanco Framework
 * Copyright (C) 2004-2009 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.sample.batchprocess;

/**
 * バッチ処理において例外が発生した際に利用されます。blancoBatchProcessの例外です。
 */
@SuppressWarnings("serial")
public class BlancoBatchProcessException extends RuntimeException {
    /**
     * 詳細メッセージを持たない例外を構築します。
     */
    public BlancoBatchProcessException() {
        super();
    }

    /**
     * 指定された詳細メッセージを持つ例外を構築します。
     *
     * @param message 詳細メッセージ。
     */
    public BlancoBatchProcessException(final String message) {
        super(message);
    }

    /**
     * 指定された詳細メッセージおよび原因を指定して例外を構築します。
     *
     * @param message 詳細メッセージ。
     * @param cause 原因。
     */
    public BlancoBatchProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * 原因を指定して例外を構築します。
     *
     * @param cause 原因。
     */
    public BlancoBatchProcessException(final Throwable cause) {
        super(cause);
    }
}
