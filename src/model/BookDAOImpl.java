package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementasi dari BookDAO yang menggunakan JDBC untuk berinteraksi
 * dengan database. Kelas ini menangani semua operasi SQL terkait buku.
 */
public class BookDAOImpl implements BookDAO {

    @Override
    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, publication_year, storyline_score, language_style_score, originality_score, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getPublicationYear());
            pstmt.setDouble(4, book.getStorylineScore());
            pstmt.setDouble(5, book.getLanguageStyleScore());
            pstmt.setDouble(6, book.getOriginalityScore());
            book.calculateRating(); // Pastikan rating terhitung sebelum disimpan
            pstmt.setDouble(7, book.getRating());
            pstmt.executeUpdate();

            // Mendapatkan ID yang digenerate oleh database
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("Buku berhasil ditambahkan: " + book.getTitle());
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan buku: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book book = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    book = mapResultSetToBook(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil buku berdasarkan ID: " + e.getMessage());
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua buku: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publication_year = ?, storyline_score = ?, language_style_score = ?, originality_score = ?, rating = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getPublicationYear());
            pstmt.setDouble(4, book.getStorylineScore());
            pstmt.setDouble(5, book.getLanguageStyleScore());
            pstmt.setDouble(6, book.getOriginalityScore());
            book.calculateRating(); // Pastikan rating terhitung ulang sebelum disimpan
            pstmt.setDouble(7, book.getRating());
            pstmt.setInt(8, book.getId());
            pstmt.executeUpdate();
            System.out.println("Buku berhasil diperbarui: " + book.getTitle());
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui buku: " + e.getMessage());
        }
    }

    @Override
    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Buku dengan ID " + id + " berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Error saat menghapus buku: " + e.getMessage());
        }
    }

    // Metode helper untuk memetakan ResultSet ke objek Book
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublicationYear(rs.getInt("publication_year"));
        book.setStorylineScore(rs.getDouble("storyline_score"));
        book.setLanguageStyleScore(rs.getDouble("language_style_score"));
        book.setOriginalityScore(rs.getDouble("originality_score"));
        // Rating diambil langsung dari database, karena sudah dihitung saat insert/update
        // atau bisa dihitung ulang jika perlu: book.calculateRating();
        book.calculateRating(); // Hitung ulang untuk konsistensi jika ada perubahan skor
        // Jika rating dari DB mau dipakai langsung:
        // book.rating = rs.getDouble("rating"); // Perlu setter untuk rating jika mau cara ini
        return book;
    }
}