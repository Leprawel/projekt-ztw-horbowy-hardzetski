package com.pwr.libraryrest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Reader {
	public int id;
	public String first_name;
	public String last_name;
	public String document_id;

	public Reader(int id, String first_name, String last_name, String document_id) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.document_id = document_id;
	}
	
	public static Reader fromResultSet(ResultSet rs, boolean next) throws SQLException {
		if(next)rs.next();
		return new Reader(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
	}

	public static Reader fromResultSet(ResultSet rs) throws SQLException {
		return Reader.fromResultSet(rs, true);
	}

}
