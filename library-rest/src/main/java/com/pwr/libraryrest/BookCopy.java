package com.pwr.libraryrest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookCopy {
	public int id;
	public int book_id;

	public BookCopy(int id, int book_id) {
		this.id = id;
		this.book_id = book_id;
	}
	
	public static BookCopy fromResultSet(ResultSet rs, boolean next) throws SQLException {
		if(next) rs.next();
		return new BookCopy(rs.getInt(1), rs.getInt(2));
	}

	public static BookCopy fromResultSet(ResultSet rs) throws SQLException {
		return BookCopy.fromResultSet(rs, true);
	}

}
