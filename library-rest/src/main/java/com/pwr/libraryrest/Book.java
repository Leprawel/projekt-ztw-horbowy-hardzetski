package com.pwr.libraryrest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
	public int id;
	public String title;
	public int author_id;
	public String first_publication_date;

	public Book(int id, String title, int author_id, String first_publication_date) {
		this.id = id;
		this.title = title;
		this.author_id = author_id;
		this.first_publication_date = first_publication_date;
	}
	
	public static Book fromResultSet(ResultSet rs, boolean next) throws SQLException {
		if(next) rs.next();
		return new Book(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
	}

	public static Book fromResultSet(ResultSet rs) throws SQLException {
		return Book.fromResultSet(rs, true);
	}
}
