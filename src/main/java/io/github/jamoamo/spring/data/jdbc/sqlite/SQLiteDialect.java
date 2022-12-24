/*
 * The MIT License
 *
 * Copyright 2022 James Amoore.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.jamoamo.spring.data.jdbc.sqlite;

import org.springframework.data.relational.core.dialect.AbstractDialect;
import org.springframework.data.relational.core.dialect.ArrayColumns;
import org.springframework.data.relational.core.dialect.LimitClause;
import org.springframework.data.relational.core.dialect.LockClause;
import org.springframework.data.relational.core.sql.LockOptions;

/**
 * Implements a Dialect for SQLite.
 * 
 * @author James Amoore
 */
public final class SQLiteDialect extends AbstractDialect
{
	public static final SQLiteDialect INSTANCE = new SQLiteDialect();

	@Override
	public LockClause lock()
	{
		return new LockClause()
		{

			@Override
			public String getLock(LockOptions lockOptions)
			{
				return "WITH LOCK";
			}

			@Override
			public LockClause.Position getClausePosition()
			{
				return LockClause.Position.AFTER_ORDER_BY;
			}
		};
	}

	@Override
	public ArrayColumns getArraySupport()
	{
		return ArrayColumns.Unsupported.INSTANCE;
	}

	@Override
	public LimitClause limit()
	{
		return new LimitClause()
		{

			@Override
			public String getLimit(long limit)
			{
				return "LIMIT " + limit;
			}

			@Override
			public String getOffset(long offset)
			{
				return String.format("LIMIT %d, 18446744073709551615", offset);
			}

			@Override
			public String getLimitOffset(long limit, long offset)
			{

				return String.format("LIMIT %s, %s", offset, limit);
			}

			@Override
			public LimitClause.Position getClausePosition()
			{
				return LimitClause.Position.AFTER_ORDER_BY;
			}
		};
	}
}
