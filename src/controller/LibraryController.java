package controller;

import model.Book;
import model.BookDAO;
import view.LibraryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * LibraryController bertindak sebagai perantara antara LibraryView (antarmuka pengguna)
 * dan BookDAO (model data). Ini menangani input pengguna dari view,
 * memprosesnya dengan menggunakan model, dan kemudian memperbarui view.
 * Ini adalah implementasi dari pola desain MVC.
 */
public class LibraryController {
    private final LibraryView view;
    private final BookDAO bookDAO;

    public LibraryController(LibraryView view, BookDAO bookDAO) {
        this.view = view;
        this.bookDAO = bookDAO;

        // Menambahkan listener ke komponen view
        this.view.addAddButtonListener(new AddButtonListener());
        this.view.addUpdateButtonListener(new UpdateButtonListener());
        this.view.addDeleteButtonListener(new DeleteButtonListener());
        this.view.addClearButtonListener(new ClearButtonListener());
        this.view.addTableMouseListener(new TableMouseListener());

        // Muat data awal saat aplikasi dimulai
        loadAllBooks();
    }

    // Memuat semua buku dan menampilkannya di view
    private void loadAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        view.displayBooks(books);
    }

    // Listener untuk tombol "Tambah"
    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String title = view.getTitleField();
                String author = view.getAuthorField();
                if (title.isEmpty() || author.isEmpty()) {
                    view.showError("Judul dan Penulis tidak boleh kosong.");
                    return;
                }
                int year = Integer.parseInt(view.getYearField());
                double storyline = Double.parseDouble(view.getStorylineField());
                double language = Double.parseDouble(view.getLanguageField());
                double originality = Double.parseDouble(view.getOriginalityField());

                if (year <= 0 || storyline < 0 || storyline > 5 || language < 0 || language > 5 || originality < 0 || originality > 5) {
                    view.showError("Input tidak valid. Pastikan tahun > 0 dan skor antara 0-5.");
                    return;
                }

                Book newBook = new Book(title, author, year, storyline, language, originality);
                bookDAO.addBook(newBook);
                loadAllBooks(); // Muat ulang data tabel
                view.clearFields();
                view.showSuccessMessage("Data Buku Berhasil Ditambahkan");
            } catch (NumberFormatException ex) {
                view.showError("Format angka tidak valid untuk Tahun atau Skor.");
            } catch (Exception ex) {
                view.showError("Terjadi kesalahan: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // Listener untuk tombol "Update"
    class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedId = view.getSelectedBookId();
            if (selectedId == -1) {
                view.showError("Pilih buku yang akan diperbarui dari tabel.");
                return;
            }

            try {
                String title = view.getTitleField();
                String author = view.getAuthorField();
                if (title.isEmpty() || author.isEmpty()) {
                    view.showError("Judul dan Penulis tidak boleh kosong.");
                    return;
                }
                int year = Integer.parseInt(view.getYearField());
                double storyline = Double.parseDouble(view.getStorylineField());
                double language = Double.parseDouble(view.getLanguageField());
                double originality = Double.parseDouble(view.getOriginalityField());

                if (year <= 0 || storyline < 0 || storyline > 5 || language < 0 || language > 5 || originality < 0 || originality > 5) {
                    view.showError("Input tidak valid. Pastikan tahun > 0 dan skor antara 0-5.");
                    return;
                }

                Book updatedBook = new Book(title, author, year, storyline, language, originality);
                updatedBook.setId(selectedId); // Set ID untuk buku yang akan diupdate
                bookDAO.updateBook(updatedBook);
                loadAllBooks();
                view.clearFields();
                view.showSuccessMessage("Data Buku Berhasil Diperbarui");
            } catch (NumberFormatException ex) {
                view.showError("Format angka tidak valid untuk Tahun atau Skor.");
            } catch (Exception ex) {
                view.showError("Terjadi kesalahan saat memperbarui: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // Listener untuk tombol "Delete"
    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedId = view.getSelectedBookId();
            if (selectedId == -1) {
                view.showError("Pilih buku yang akan dihapus dari tabel.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view,
                    "Apakah Anda yakin ingin menghapus buku ini?",
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    bookDAO.deleteBook(selectedId);
                    loadAllBooks();
                    view.clearFields();
                    view.showSuccessMessage("Data Buku Berhasil Dihapus");
                } catch (Exception ex) {
                    view.showError("Terjadi kesalahan saat menghapus: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    // Listener untuk tombol "Clear"
    class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearFields();
        }
    }

    // Listener untuk klik pada tabel (mengisi form dengan data baris yang dipilih)
    class TableMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int selectedRow = view.getSelectedBookId(); // Ini sebenarnya mengambil ID, kita butuh data baris
            JTable table = (JTable) e.getSource();
            int row = table.getSelectedRow();

            if (row >= 0) { // Pastikan baris valid dipilih
                // Ambil data dari model tabel, bukan langsung dari bookDAO untuk efisiensi
                // ID, Judul, Penulis, Tahun, Alur, Bahasa, Orisinalitas
                // Kolom ID (indeks 0) tidak perlu diisi ke form, tapi berguna untuk update/delete
                view.setTitleField(table.getValueAt(row, 1).toString());
                view.setAuthorField(table.getValueAt(row, 2).toString());
                view.setYearField(table.getValueAt(row, 3).toString());
                view.setStorylineField(table.getValueAt(row, 4).toString());
                view.setLanguageField(table.getValueAt(row, 5).toString());
                view.setOriginalityField(table.getValueAt(row, 6).toString());
            }
        }
    }
}
