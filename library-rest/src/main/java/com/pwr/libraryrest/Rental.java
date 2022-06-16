package com.pwr.libraryrest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Rental {
	public int id;
	public String start_date;
	public String end_date;
	public int book_copy_id;
	public int reader_id;

	public Rental(int id, String start_date, String end_date, int book_copy_id, int reader_id) {
		this.id = id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.book_copy_id = book_copy_id;
		this.reader_id = reader_id;
	}
	
	public static Rental fromResultSet(ResultSet rs, boolean next) throws SQLException {
		if(next)rs.next();
		return new Rental(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
	}

	public static Rental fromResultSet(ResultSet rs) throws SQLException {
		return Rental.fromResultSet(rs, true);
	}

}
