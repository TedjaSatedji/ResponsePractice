package model;

import java.util.List;

/**
 * Interface BookDAO (Data Access Object) mendefinisikan operasi standar
 * yang dapat dilakukan pada data buku (CRUD).
 * Ini adalah bagian dari pola desain DAO dan menerapkan konsep Interface dalam OOP.
 */
public interface BookDAO {
    void addBook(Book book);        // Create
    Book getBookById(int id);       // Read
    List<Book> getAllBooks();       // Read All
    void updateBook(Book book);     // Update
    void deleteBook(int id);        // Delete
}
