import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import model.BookDAO;
import model.BookDAOImpl;
import view.LibraryView;
import controller.LibraryController;

/**
 * Kelas MainApp adalah titik masuk utama untuk aplikasi perpustakaan digital.
 * Ini menginisialisasi Model (DAO), View (GUI), dan Controller,
 * lalu menjalankan aplikasi.
 */
public class MainApp {
    public static void main(String[] args) {
        // Mengatur Look and Feel (opsional, untuk tampilan yang lebih modern)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Gagal mengatur Look and Feel: " + e.getMessage());
        }

        // Menjalankan aplikasi di Event Dispatch Thread (EDT) Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Inisialisasi Model (DAO)
                BookDAO bookDAO = new BookDAOImpl();

                // Inisialisasi View
                LibraryView libraryView = new LibraryView();

                // Inisialisasi Controller
                new LibraryController(libraryView, bookDAO);

                // Tampilkan View
                libraryView.setVisible(true);
            }
        });
    }
}