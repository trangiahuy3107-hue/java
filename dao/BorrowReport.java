package dao;

import java.sql.Date;

public class BorrowReport {
    private String studentId;
    private String bookId;
    private Date borrowDate;
    private Date returnDate;
    private int quantity;

    public BorrowReport(String studentId, String bookId, Date borrowDate, Date returnDate, int quantity) {
        this.studentId = studentId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.quantity = quantity;
    }

    // Getter
    public String getStudentId() { return studentId; }
    public String getBookId() { return bookId; }
    public Date getBorrowDate() { return borrowDate; }
    public Date getReturnDate() { return returnDate; }
    public int getQuantity() { return quantity; }
}
