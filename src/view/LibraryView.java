package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener; // Untuk controller
import java.awt.event.MouseAdapter; // Untuk klik tabel

/**
 * Kelas LibraryView bertanggung jawab untuk menampilkan antarmuka pengguna (GUI)
 * aplikasi perpustakaan digital. Ini menggunakan Swing untuk komponen GUI.
 * View ini berinteraksi dengan LibraryController untuk menangani aksi pengguna.
 */
public class LibraryView extends JFrame {

    // Komponen GUI
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField titleField, authorField, yearField, storylineField, languageField, originalityField;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private JLabel statusLabel; // Untuk menampilkan pesan status sementara

    // Kolom untuk tabel
    private final String[] columnNames = {"ID", "Judul Buku", "Penulis", "Tahun", "Alur", "Bahasa", "Orisinalitas", "Rating"};

    public LibraryView() {
        setTitle("Perpustakaan Digital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600); // Ukuran disesuaikan agar mirip gambar
        setLocationRelativeTo(null); // Tengah layar

        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        // Tabel
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat sel tabel tidak bisa diedit langsung
            }
        };
        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Hanya satu baris bisa dipilih
        bookTable.getTableHeader().setReorderingAllowed(false); // Mencegah reorder kolom

        // Input Fields
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        yearField = new JTextField(5);
        storylineField = new JTextField(5);
        languageField = new JTextField(5);
        originalityField = new JTextField(5);

        // Buttons
        addButton = new JButton("Tambah");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");

        // Status Label (opsional, untuk feedback singkat)
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.BLUE);
    }

    private void layoutComponents() {
        // Panel Utama
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Border layout dengan gap
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Panel Tabel (Kiri)
        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel Form (Kanan)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Jarak antar komponen
        gbc.anchor = GridBagConstraints.WEST; // Rata kiri

        // Judul Buku
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Judul Buku:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; formPanel.add(titleField, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; // Reset

        // Penulis
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Penulis:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(authorField, gbc);
        gbc.fill = GridBagConstraints.NONE;

        // Tahun Terbit
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Tahun Terbit:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(yearField, gbc);
        gbc.fill = GridBagConstraints.NONE;

        // Alur Cerita
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Alur Cerita (0-5):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(storylineField, gbc);
        gbc.fill = GridBagConstraints.NONE;

        // Gaya Bahasa
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("Gaya Bahasa (0-5):"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(languageField, gbc);
        gbc.fill = GridBagConstraints.NONE;

        // Orisinalitas
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(new JLabel("Orisinalitas (0-5):"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(originalityField, gbc);
        gbc.fill = GridBagConstraints.NONE;


        // Panel Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // FlowLayout untuk tombol
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        // Status Label (di bawah tombol)
        gbc.gridy = 7; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(statusLabel, gbc);

        // Menambahkan panel form ke panel utama di sisi kanan
        formPanel.setPreferredSize(new Dimension(400, 0)); // Increase width if needed
        mainPanel.add(formPanel, BorderLayout.EAST);

        add(mainPanel);
    }

    // Metode untuk mendapatkan nilai dari input fields
    public String getTitleField() { return titleField.getText(); }
    public String getAuthorField() { return authorField.getText(); }
    public String getYearField() { return yearField.getText(); }
    public String getStorylineField() { return storylineField.getText(); }
    public String getLanguageField() { return languageField.getText(); }
    public String getOriginalityField() { return originalityField.getText(); }

    // Metode untuk mengatur nilai ke input fields
    public void setTitleField(String text) { titleField.setText(text); }
    public void setAuthorField(String text) { authorField.setText(text); }
    public void setYearField(String text) { yearField.setText(text); }
    public void setStorylineField(String text) { storylineField.setText(text); }
    public void setLanguageField(String text) { languageField.setText(text); }
    public void setOriginalityField(String text) { originalityField.setText(text); }

    // Metode untuk membersihkan input fields
    public void clearFields() {
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        storylineField.setText("");
        languageField.setText("");
        originalityField.setText("");
        statusLabel.setText(" "); // Bersihkan status
        bookTable.clearSelection(); // Hapus seleksi di tabel
    }

    // Metode untuk menampilkan data buku di tabel
    public void displayBooks(java.util.List<model.Book> books) {
        tableModel.setRowCount(0); // Hapus semua baris yang ada
        for (model.Book book : books) {
            Object[] row = {
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublicationYear(),
                    String.format("%.1f", book.getStorylineScore()), // Format skor
                    String.format("%.1f", book.getLanguageStyleScore()),
                    String.format("%.1f", book.getOriginalityScore()),
                    String.format("%.2f", book.getRating()) // Format rating
            };
            tableModel.addRow(row);
        }
    }

    // Metode untuk mendapatkan ID buku yang dipilih di tabel
    public int getSelectedBookId() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            return (int) tableModel.getValueAt(selectedRow, 0); // Kolom ID adalah kolom pertama (indeks 0)
        }
        return -1; // Tidak ada yang dipilih
    }

    // Metode untuk menampilkan pesan dialog
    public void showMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    // Metode untuk menampilkan pesan error
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Metode untuk menampilkan pesan sukses (sesuai gambar)
    public void showSuccessMessage(String message) {
        // Membuat dialog kustom yang mirip dengan gambar
        JDialog dialog = new JDialog(this, "Message", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10,10));

        JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.informationIcon")); // Icon info
        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        dialog.add(iconLabel, BorderLayout.WEST);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        dialog.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanelDialog = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanelDialog.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        buttonPanelDialog.add(okButton);
        dialog.add(buttonPanelDialog, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }


    // Listener untuk tombol (akan dihubungkan ke controller)
    public void addAddButtonListener(ActionListener listener) { addButton.addActionListener(listener); }
    public void addUpdateButtonListener(ActionListener listener) { updateButton.addActionListener(listener); }
    public void addDeleteButtonListener(ActionListener listener) { deleteButton.addActionListener(listener); }
    public void addClearButtonListener(ActionListener listener) { clearButton.addActionListener(listener); }
    public void addTableMouseListener(MouseAdapter listener) { bookTable.addMouseListener(listener); }
}