package model;

/**
 * Kelas Book merepresentasikan entitas buku dalam perpustakaan.
 * Ini mencakup informasi seperti judul, penulis, tahun terbit,
 * dan skor untuk alur cerita, gaya bahasa, serta orisinalitas.
 * Rating buku dihitung secara otomatis berdasarkan skor-skor tersebut.
 *
 * Konsep OOP yang diterapkan:
 * - Enkapsulasi: Semua field bersifat private dan diakses melalui getter dan setter.
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private int publicationYear;
    private double storylineScore;
    private double languageStyleScore;
    private double originalityScore;
    private double rating;

    // Konstruktor
    public Book(String title, String author, int publicationYear, double storylineScore, double languageStyleScore, double originalityScore) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.storylineScore = storylineScore;
        this.languageStyleScore = languageStyleScore;
        this.originalityScore = originalityScore;
        this.calculateRating(); // Hitung rating saat objek dibuat
    }

    public Book() {
        // Konstruktor default
    }

    // Getter dan Setter (Enkapsulasi)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        if (publicationYear > 0) { // Validasi sederhana
            this.publicationYear = publicationYear;
        }
    }

    public double getStorylineScore() {
        return storylineScore;
    }

    public void setStorylineScore(double storylineScore) {
        if (storylineScore >= 0 && storylineScore <= 5) {
            this.storylineScore = storylineScore;
            this.calculateRating();
        }
    }

    public double getLanguageStyleScore() {
        return languageStyleScore;
    }

    public void setLanguageStyleScore(double languageStyleScore) {
        if (languageStyleScore >= 0 && languageStyleScore <= 5) {
            this.languageStyleScore = languageStyleScore;
            this.calculateRating();
        }
    }

    public double getOriginalityScore() {
        return originalityScore;
    }

    public void setOriginalityScore(double originalityScore) {
        if (originalityScore >= 0 && originalityScore <= 5) {
            this.originalityScore = originalityScore;
            this.calculateRating();
        }
    }

    public double getRating() {
        return rating;
    }

    // Metode untuk menghitung rating
    public void calculateRating() {
        this.rating = (this.storylineScore + this.languageStyleScore + this.originalityScore) / 3.0;
    }

    // Override toString untuk representasi string yang mudah dibaca (opsional)
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", storylineScore=" + storylineScore +
                ", languageStyleScore=" + languageStyleScore +
                ", originalityScore=" + originalityScore +
                ", rating=" + String.format("%.2f", rating) +
                '}';
    }
}