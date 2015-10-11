/*******************************************************************************
 * blanco Framework
 * Copyright (C) 2004-2012 Toshiki IGA
 * 
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2004-2012 Toshiki IGA and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Toshiki IGA - initial API and implementation
 *******************************************************************************/
package blanco.batchprocess;

import blanco.constants.BlancoConstantsVersion;
import blanco.gettersetter.BlancoGetterSetter;

@BlancoConstantsVersion(prefix = "0.6.2-I")
public abstract class AbstractBlancoBatchProcessConstants {
	/**
	 * プロダクト名。英字で指定します。
	 */
	@BlancoGetterSetter(setter = false)
	public static final String PRODUCT_NAME = "BlancoDbTableAccessor";

	/**
	 * プロダクト名の小文字版。英字で指定します。
	 */
	public static final String PRODUCT_NAME_LOWER = "blancobatchprocess";

	/**
	 * 処理の過程で利用されるサブディレクトリ。
	 */
	public static final String TARGET_SUBDIRECTORY = "/batchprocess";
}
