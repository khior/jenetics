/*
 * Java Genetic Algorithm Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 */
package org.jenetics.internal.collection;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @since 3.0
 * @version 3.0 &mdash; <em>$Date: 2014-08-01 $</em>
 */
public final class CharArrayProxy
	extends ArrayProxy<Character, CharArray, CharArrayProxy>
{
	private static final long serialVersionUID = 1L;

	public CharArrayProxy(final CharArray chars, final int start, final int end) {
		super(chars, start, end, CharArrayProxy::new, CharArray::copy);
	}

	public CharArrayProxy(final int length) {
		this(new CharArray(length), 0, length);
	}

	@Override
	public Character __get__(int index) {
		return array.values[index];
	}

	@Override
	public void __set__(int index, Character value) {
		array.values[index] = value;
	}

}
