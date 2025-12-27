USE master;
GO

IF DB_ID('LibraryDB') IS NOT NULL
BEGIN
    ALTER DATABASE LibraryDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE LibraryDB;
END
GO


CREATE DATABASE LibraryDB;
GO

USE LibraryDB;
GO


CREATE TABLE Users (
    username NVARCHAR(50) PRIMARY KEY,
    password NVARCHAR(50) NOT NULL,
    role NVARCHAR(20) NOT NULL
);
GO

INSERT INTO Users VALUES ('admin', '123', 'Admin');
GO


CREATE TABLE Students (
    id INT IDENTITY PRIMARY KEY,
    studentId NVARCHAR(10) UNIQUE NOT NULL,
    name NVARCHAR(100),
    class NVARCHAR(20)
);
GO


CREATE TABLE Books (
    id INT IDENTITY PRIMARY KEY,
    bookId NVARCHAR(10) UNIQUE NOT NULL,
    title NVARCHAR(200),
    author NVARCHAR(100),
    quantity INT CHECK (quantity >= 0)
);
GO


CREATE TABLE Borrow (
    id INT IDENTITY PRIMARY KEY,
    studentId NVARCHAR(10) NOT NULL,
    bookId NVARCHAR(10) NOT NULL,
    borrowDate DATE,
    returnDate DATE,
    quantity INT CHECK (quantity > 0),
    status NVARCHAR(20),

    CONSTRAINT FK_Borrow_Student
        FOREIGN KEY (studentId) REFERENCES Students(studentId),

    CONSTRAINT FK_Borrow_Book
        FOREIGN KEY (bookId) REFERENCES Books(bookId)
);
GO


CREATE TRIGGER trg_Borrow_Insert
ON Borrow
AFTER INSERT
AS
BEGIN
    UPDATE b
    SET b.quantity = b.quantity - i.quantity
    FROM Books b
    JOIN inserted i ON b.bookId = i.bookId;
END;
GO


CREATE TRIGGER trg_Borrow_Return
ON Borrow
AFTER UPDATE
AS
BEGIN
    UPDATE b
    SET b.quantity = b.quantity + i.quantity
    FROM Books b
    JOIN inserted i ON b.bookId = i.bookId
    JOIN deleted d ON i.id = d.id
    WHERE d.status IS NULL AND i.status = N'Đã trả';
END;
GO

INSERT INTO Students (studentId, name, class) VALUES
('SV01', N'Nguyễn Văn A', 'CNTT1'),
('SV02', N'Trần Thị B', 'CNTT2'),
('SV03', N'Lê Văn C', 'KTTM1'),
('SV04', N'Trần Gia H', '23CN3');
GO

INSERT INTO Books (bookId, title, author, quantity) VALUES
('S01', N'Lập trình Java', N'Nguyễn Văn An', 3),
('S02', N'Cơ sở dữ liệu', N'Trần Văn Bình', 4),
('S03', N'Cấu trúc dữ liệu', N'Lê Văn Cường', 5),
('S04', N'Hệ điều hành', N'Phạm Văn Dũng', 2);
GO

INSERT INTO Borrow (studentId, bookId, borrowDate, quantity, status)
VALUES ('SV01', 'S01', GETDATE(), 1, NULL);
GO
