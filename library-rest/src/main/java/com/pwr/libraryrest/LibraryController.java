package com.pwr.libraryrest;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*")
@RestController
public class LibraryController {
	
	Database db;
	ObjectMapper mapper;
	
	LibraryController() throws SQLException{
		db = new Database();
		mapper = new ObjectMapper();
	}
	

	// AUTHOR CRUD
	@PostMapping("/author")
	public ResponseEntity<Object> postAuthor(@RequestBody Author author){
		try {
			String q = "INSERT INTO `author` (`id`, `first_name`, `last_name`, `birth_date`) VALUES (NULL, '"
					+ author.first_name + "', '" + author.last_name + "', '" + author.birth_date + "')";
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/author/{id}")
	public ResponseEntity<Object> getAuthor(@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok().body(Author.fromResultSet(db.query("SELECT * FROM `author` WHERE id="+id)));
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/author")
	public ResponseEntity<Object> putAuthor(@RequestBody Author author){
		try {
			String q = "UPDATE `author` SET `first_name` = '"+author.first_name+"', `last_name`='"+author.last_name
					+"', `birth_date` = '"+author.birth_date+"' WHERE `author`.`id` = "+author.id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/author/{id}")
	public ResponseEntity<Object> deleteAuthor(@PathVariable("id") String id){
		try {
			String q = "DELETE FROM `author` WHERE id="+ id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ALL AUTHORS
	@GetMapping("/authors")
	public ResponseEntity<Object> getAuthors() {
		try {
			ResultSet rs = db.query("SELECT * FROM `author`");
			ArrayList<Author> results = new ArrayList<Author>();
			while(rs.next()) {
				results.add(Author.fromResultSet(rs, false));
			}
			return ResponseEntity.ok().body(results);
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	// BOOK CRUD
	@PostMapping("/book")
	public ResponseEntity<Object> postBook(@RequestBody Book book){
		try {
			String q = "INSERT INTO `book` (`id`, `title`, `author_id`, `first_publication_date`)"
					+ "VALUES (NULL, '"+book.title+"', '"+Integer.toString(book.author_id)+"', '"+book.first_publication_date+"')";
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<Object> getBookById(@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok().body(Book.fromResultSet(db.query("SELECT * FROM `book` WHERE id="+id)));
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/book")
	public ResponseEntity<Object> putBook(@RequestBody Book book){
		try {
			String q = "UPDATE `book` SET `title` = '"+book.title+"', `author_id`="+book.author_id
					+", `first_publication_date` = '"+book.first_publication_date+"' WHERE `book`.`id` = "+book.id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<Object> deleteBook(@PathVariable("id") String id){
		try {
			String q = "DELETE FROM `book` WHERE id="+ id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ALL BOOKS
	@GetMapping("/books")
	public ResponseEntity<Object> getBooks() {
		try {
			ResultSet rs = db.query("SELECT * FROM `book`");
			ArrayList<Book> results = new ArrayList<Book>();
			while(rs.next()) {
				results.add(Book.fromResultSet(rs, false));
			}
			return ResponseEntity.ok().body(results);
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// ALL BOOKS WITH AUTHORS
	@GetMapping("/books_with_authors")
	public ResponseEntity<Object> getBooksWithAuthors(){
		try {
			ResultSet books = db.query("SELECT * FROM `book`");
			ArrayList<Book> results = new ArrayList<Book>();
			while(books.next()) {
				results.add(Book.fromResultSet(books, false));
			}

			ArrayList<ObjectNode> jsonArray = new ArrayList<ObjectNode>();
			for(Book book : results) {
				ResultSet author = db.query("SELECT * FROM `author` WHERE id="+book.author_id);
				ObjectNode jsonElement = mapper.valueToTree(book);
				jsonElement.set("author", mapper.valueToTree(Author.fromResultSet(author)));
				jsonArray.add(jsonElement);
			}
			
			return ResponseEntity.ok().body(jsonArray);
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	// BOOK_COPY CRD (no update)
	@PostMapping("/book_copy")
	public ResponseEntity<Object> postBookCopy(@RequestBody BookCopy book_copy){
		try {
			String q = "INSERT INTO `book_copy` (`id`, `book_id`)"
					+ "VALUES (NULL, "+book_copy.book_id+")";
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/book_copy/{id}")
	public ResponseEntity<Object> getBookCopyById(@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok().body(BookCopy.fromResultSet(db.query("SELECT * FROM `book_copy` WHERE id="+id)));
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/book_copy/{id}")
	public ResponseEntity<Object> deleteBookCopy(@PathVariable("id") String id){
		try {
			String q = "DELETE FROM `book_copy` WHERE id="+ id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("not_borrowed_books/{date}")
	public ResponseEntity<Object> bookNotBorrowedAtDay(@PathVariable("date") String date){
		try {
			
			String q = "WITH duplicated_copies AS\n"
					+ "(\n"
					+ "	SELECT subquery.*, ROW_NUMBER() OVER(PARTITION BY book_id ORDER BY title) AS 'rn'\n"
					+ "    FROM(\n"
					+ "        SELECT book_copy.id as book_copy_id, book_id, title, first_publication_date, author_id, first_name, last_name, birth_date\n"
					+ "        FROM rental\n"
					+ "        JOIN book_copy\n"
					+ "        ON book_copy.id = book_copy_id\n"
					+ "        JOIN book\n"
					+ "        ON book.id = book_id\n"
					+ "        JOIN author\n"
					+ "        ON book.author_id = author.id\n"
					+ "		   WHERE start_date > '" + date + "' OR end_date < '" + date + "'"
					+ "    ) subquery\n"
					+ ")\n"
					+ "SELECT *\n"
					+ "FROM duplicated_copies\n"
					+ "WHERE rn = 1";
			System.out.println(q);
			ResultSet rs = db.query(q);
			
			return ResponseEntity.ok().body(rsToJson(rs));

		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("all_book_copies/{date}")
	public ResponseEntity<Object> allBookCopiesWithAllData(@PathVariable("date") String date){
		try {
			String q = "WITH duplicated_copies AS\n"
					+ "(\n"
					+ "	SELECT subquery.*, ROW_NUMBER() OVER(PARTITION BY book_id ORDER BY is_borrowed DESC) AS 'rn'\n"
					+ "    FROM(\n"
					+ "        SELECT book_copy.id, book_id, title, first_publication_date, author_id, first_name, last_name, birth_date, start_date, end_date, \n"
					+ "        CASE\n"
					+ "            WHEN start_date > '"+date+"' OR end_date < '"+date+"' THEN \"false\"\n"
					+ "            ELSE \"true\"\n"
					+ "        END AS is_borrowed\n"
					+ "        FROM rental\n"
					+ "        JOIN book_copy\n"
					+ "        ON book_copy.id = book_copy_id\n"
					+ "        JOIN book\n"
					+ "        ON book.id = book_id\n"
					+ "        JOIN author\n"
					+ "        ON book.author_id = author.id\n"
					+ "    ) subquery\n"
					+ ")\n"
					+ "SELECT *\n"
					+ "FROM duplicated_copies\n"
					+ "WHERE rn = 1";
			System.out.println(q);
			ResultSet rs = db.query(q);
			
			return ResponseEntity.ok().body(rsToJson(rs));

		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// READER CRUD
	@PostMapping("/reader")
	public ResponseEntity<Object> postReader(@RequestBody Reader reader){
		try {
			String q = "INSERT INTO `reader` (`id`, `first_name`, `last_name`, `document_id`) VALUES (NULL, '"
					+ reader.first_name + "', '" + reader.last_name + "', '" + reader.document_id + "')";
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/reader/{id}")
	public ResponseEntity<Object> getReader(@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok().body(Reader.fromResultSet(db.query("SELECT * FROM `reader` WHERE id="+id)));
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/reader")
	public ResponseEntity<Object> putReader(@RequestBody Reader reader){
		try {
			String q = "UPDATE `reader` SET `first_name` = '"+reader.first_name+"', `last_name`='"+reader.last_name
					+"', `document_id` = '"+reader.document_id+"' WHERE `reader`.`id` = "+reader.id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/reader/{id}")
	public ResponseEntity<Object> deleteReader(@PathVariable("id") String id){
		try {
			String q = "DELETE FROM `reader` WHERE id="+ id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ALL READERS
	@GetMapping("/readers")
	public ResponseEntity<Object> getReaders() {
		try {
			ResultSet rs = db.query("SELECT * FROM `reader`");
			ArrayList<Reader> results = new ArrayList<Reader>();
			while(rs.next()) {
				results.add(Reader.fromResultSet(rs, false));
			}
			return ResponseEntity.ok().body(results);
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	// RENTAL CRUD
	@PostMapping("/rental")
	public ResponseEntity<Object> postRental(@RequestBody Rental rental){
		try {
			String q = "INSERT INTO `rental` (`id`, `start_date`, `end_date`, `book_copy_id`, reader_id) VALUES (NULL, '"
					+ rental.start_date + "', '" + rental.end_date + "', " + rental.book_copy_id + ", " + rental.reader_id + ")";
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/rental/{id}")
	public ResponseEntity<Object> getRental(@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok().body(Rental.fromResultSet(db.query("SELECT * FROM `rental` WHERE id="+id)));
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/rental")
	public ResponseEntity<Object> putRental(@RequestBody Rental rental){
		try {
			String q = "UPDATE `rental` SET `start_date` = '"+rental.start_date+"', `end_date`='"+rental.end_date
					+"', `book_copy_id` = "+rental.book_copy_id+", `reader_id` = "+rental.reader_id+"  WHERE `rental`.`id` = "+rental.id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/rental/{id}")
	public ResponseEntity<Object> deleteRental(@PathVariable("id") String id){
		try {
			String q = "DELETE FROM `rental` WHERE id="+ id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	// USER CRUD
	@PostMapping("/user")
	public ResponseEntity<Object> postUser(@RequestBody User user){
		try {
			String q = "INSERT INTO `user` (`id`, `username`, `password`, `role`) VALUES (NULL, '"
					+ user.username + "', '" + user.password + "', '" + user.role + "')";
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getUser(@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok().body(User.fromResultSet(db.query("SELECT * FROM `user` WHERE id="+id)));
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/user")
	public ResponseEntity<Object> putUser(@RequestBody User user){
		try {
			String q = "UPDATE `user` SET `username` = '"+user.username+"', `password`='"+user.password
					+"', `role` = '"+user.role+"' WHERE `user`.`id` = "+user.id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") String id){
		try {
			String q = "DELETE FROM `user` WHERE id="+ id;
			System.out.println(q);
			db.query(q);
			return ResponseEntity.ok("Server did not return error");
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// USER LOGIN PASSWORD CHECK
	@GetMapping("/user_password_check")
	public ResponseEntity<Object> getUserPasswordCheck(@RequestBody User user) {
		try {
			ResultSet result = db.query("SELECT * FROM `user` WHERE username LIKE'"+user.username
					+"' AND password LIKE '"+user.password+"'");
			if(result.first()) {
				return ResponseEntity.ok().body(User.fromResultSet(result, false).username);
			}
			else {
				return ResponseEntity.ok().body("");
			}
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	private List<Map<String, Object>> rsToJson(ResultSet rs) throws SQLException{
		List<Map<String, Object>> result = new ArrayList<>();
		while (rs.next()) {
		      Map<String, Object> row = new HashMap<>();
		      
		      for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
		           String colName = rs.getMetaData().getColumnName(i);
		           Object colVal = rs.getObject(i);
		           row.put(colName, colVal);
		      }
		      
		      result.add(row);
		}
	    return result;
	}
}
