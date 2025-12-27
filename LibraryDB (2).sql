-- =========================
-- TẠO DATABASE
-- =========================
IF DB_ID('LibraryDB') IS NOT NULL
    DROP DATABASE LibraryDB;
GO

CREATE DATABASE LibraryDB;
GO
USE LibraryDB;
GO

-- =========================
-- BẢNG USERS
-- =========================
CREATE TABLE Users (
    username NVARCHAR(50) PRIMARY KEY,
    password NVARCHAR(50) NOT NULL,
    role NVARCHAR(20) NOT NULL
);

INSERT INTO Users VALUES ('admin', '123', 'Admin');

-- =========================
-- BẢNG SINH VIÊN
-- =========================
CREATE TABLE SinhVien (
    id INT IDENTITY(1,1) PRIMARY KEY,
    studentId NVARCHAR(10) UNIQUE NOT NULL,
    name NVARCHAR(100),
    class NVARCHAR(20)
);

INSERT INTO SinhVien (studentId, name, class) VALUES
('SV01', N'Nguyễn Văn A', 'CNTT1'),
('SV02', N'Trần Thị B', 'CNTT2'),
('SV03', N'Lê Văn C', 'KTTM1'),
('SV04', N'Trần Gia H', '23CN3');

-- =========================
-- BẢNG SÁCH
-- =========================
CREATE TABLE Book (
    id INT IDENTITY(1,1) PRIMARY KEY,
    bookId NVARCHAR(10) UNIQUE NOT NULL,
    title NVARCHAR(200),
    author NVARCHAR(100),
    quantity INT CHECK (quantity >= 0)
);

INSERT INTO Book (bookId, title, author, quantity) VALUES
('S01', N'Lập trình Java', N'Nguyễn Văn An', 3),
('S02', N'Cơ sở dữ liệu', N'Trần Văn Bình', 4),
('S03', N'Cấu trúc dữ liệu', N'Lê Văn Cường', 5),
('S04', N'Hệ điều hành', N'Phạm Văn Dũng', 2),
('S05', N'Truyện cười', N'Lê Thị Sáu', 5),
('S06', N'Truyện cổ tích', N'Nguyễn Văn M.', 7);

-- =========================
-- BẢNG MƯỢN SÁCH
-- =========================
CREATE TABLE Borrow (
    id INT IDENTITY(1,1) PRIMARY KEY,
    studentId NVARCHAR(10) NOT NULL,
    bookId NVARCHAR(10) NOT NULL,
    borrowDate DATE,
    returnDate DATE,
    quantity INT,
    status NVARCHAR(20),

    CONSTRAINT FK_Borrow_Student FOREIGN KEY (studentId)
        REFERENCES SinhVien(studentId),

    CONSTRAINT FK_Borrow_Book FOREIGN KEY (bookId)
        REFERENCES Book(bookId)
);

INSERT INTO Borrow (studentId, bookId, borrowDate, returnDate, quantity, status) VALUES
('SV01', 'S01', '2025-12-01', '2025-12-26', 1, NULL),
('SV02', 'S02', '2025-12-05', '2025-12-26', 1, NULL),
('SV01', 'S03', '2025-12-10', '2025-12-26', 1, NULL),
('SV01', 'S01', '2025-12-26', '2025-12-26', 1, NULL);
