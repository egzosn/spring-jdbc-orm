/*
 * Copyright (c) 2008, 2009, 2011 Oracle, Inc. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.  The Eclipse Public License is available
 * at http://www.eclipse.org/legal/epl-v10.html and the Eclipse Distribution License
 * is available at http://www.eclipse.org/org/documents/edl-v10.php.
 */
package com.egzosn.infrastructure.database.jdbc.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {
	/**
	 * (Optional) The name of the table.
	 * <p/>
	 * Defaults to the entity name.
	 * @return table name
	 */
	String name() default "";

	/**
     *   是否需要 {@link Column} 进行字段映射，默认 true 需要 {@link Column}进行字段列标识
	 *   当 columnFlag 值为 false 时 没有 {@link Column} 进行标识时则用字段名称
	 *
	 * @return whether column flag is required
	 */
	boolean columnFlag()  default true;

}
