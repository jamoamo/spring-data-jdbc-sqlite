/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.jamoamo.spring.data.jdbc.sqlite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;

/**
 *
 * @author James Amoore
 */
public final class SQLiteDialectProvider
	 implements DialectResolver.JdbcDialectProvider
{
	@Override
	public Optional<Dialect> getDialect(JdbcOperations operations)
	{
		return Optional.ofNullable(
			 operations.execute((ConnectionCallback<Dialect>) SQLiteDialectProvider::getSqliteDialect));
	}

	private static Dialect getSqliteDialect(Connection connection)
		 throws SQLException
	{
		DatabaseMetaData metaData = connection.getMetaData();
		String name = metaData.getDatabaseProductName()
			 .toLowerCase(Locale.ROOT);
		if(name.contains("sqlite"))
		{
			return SQLiteDialect.INSTANCE;
		}
		return null;
	}

}
