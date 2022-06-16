package com.pwr.libraryrest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Author {
	public int id;
	public String first_name;
	public String last_name;
	public String birth_date;

	public Author(int id, String first_name, String last_name, String birth_date) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.birth_date = birth_date;
	}
	
	public static Author fromResultSet(ResultSet rs, boolean next) throws SQLException {
		if(next)rs.next();
		return new Author(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
	}

	public static Author fromResultSet(ResultSet rs) throws SQLException {
		return Author.fromResultSet(rs, true);
	}
}
