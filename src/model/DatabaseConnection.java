package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Kelas DatabaseConnection bertanggung jawab untuk membuat dan mengelola
 * koneksi ke database MySQL.
 * Pastikan driver JDBC MySQL (Connector/J) ada di classpath.
 */
public class DatabaseConnection {
    // URL JDBC, username, dan password untuk koneksi database
    // Sesuaikan dengan konfigurasi database Anda
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Ganti dengan username database Anda
    private static final String PASSWORD = ""; // Ganti dengan password database Anda

    private static Connection connection;

    // Metode untuk mendapatkan koneksi database
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Mendaftarkan driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Membuat koneksi baru
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                System.out.println("Koneksi ke database berhasil!");
            } catch (ClassNotFoundException e) {
                System.err.println("Driver JDBC tidak ditemukan: " + e.getMessage());
                throw new SQLException("Driver JDBC tidak ditemukan", e);
            } catch (SQLException e) {
                System.err.println("Koneksi ke database gagal: " + e.getMessage());
                throw e; // Lemparkan kembali SQLException agar bisa ditangani oleh pemanggil
            }
        }
        return connection;
    }

    // Metode untuk menutup koneksi (opsional, bisa dipanggil saat aplikasi ditutup)
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Koneksi database ditutup.");
            } catch (SQLException e) {
                System.err.println("Gagal menutup koneksi database: " + e.getMessage());
            }
        }
    }
}