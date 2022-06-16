package com.pwr.libraryrest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	public int id;
	public String username;
	public String password;
	public String role;

	public User(int id, String username, String password, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public static User fromResultSet(ResultSet rs, boolean next) throws SQLException {
		if(next)rs.next();
		return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
	}

	public static User fromResultSet(ResultSet rs) throws SQLException {
		return User.fromResultSet(rs, true);
	}

}
