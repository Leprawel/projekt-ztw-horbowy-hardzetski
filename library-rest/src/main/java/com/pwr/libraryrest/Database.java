package com.pwr.libraryrest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private Connection database;
	private Statement statement;
	
	public Database() throws SQLException {
		database = DriverManager.getConnection("jdbc:mariadb://localhost:3306/library", "library_rest_service", "password");
		statement = database.createStatement();
	}
	
	public ResultSet query(String query) throws SQLException {
		return statement.executeQuery(query);
	}
	
	
}
