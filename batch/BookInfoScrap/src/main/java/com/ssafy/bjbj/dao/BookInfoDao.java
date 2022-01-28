package com.ssafy.bjbj.dao;

import com.ssafy.bjbj.BookInfo;

import java.sql.*;

public class BookInfoDao {

    static Connection conn = null;

    static String createdBy = "bjbj_admin@bjbj.com";
    static String lastModifiedBy = "bjbj_admin@bjbj.com";

    public static void insert(BookInfo bookInfo) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            StringBuilder sql = new StringBuilder();
            sql
                    .append("INSERT INTO tb_book_info").append(" ")
                    .append("(")
                    .append("isbn, title, author, description, price, small_img_url, large_img_url, category_id, category_name, publisher, publication_date").append(", ")
                    .append("star_rating, is_deleted").append(", ")
                    .append("created_date, created_by, last_modified_date, last_modified_by")
                    .append(")").append(" ")
                    .append("values").append(" ")
                    .append("(")
                    .append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?").append(", ")
                    .append("0, false").append(", ")
                    .append("now(), ?, now(), ?")
                    .append(")");

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, bookInfo.getIsbn());
            pstmt.setString(2, bookInfo.getTitle());
            pstmt.setString(3, bookInfo.getAuthor());
            pstmt.setString(4, bookInfo.getDescription());
            pstmt.setInt(5, bookInfo.getPrice());
            pstmt.setString(6, bookInfo.getSmallImgUrl());
            pstmt.setString(7, bookInfo.getLargeImgUrl());
            pstmt.setInt(8, bookInfo.getCategoryId());
            pstmt.setString(9, bookInfo.getCategoryName());
            pstmt.setString(10, bookInfo.getPublisher());
            pstmt.setDate(11, Date.valueOf(bookInfo.getPublicationDate().toLocalDate()));
            pstmt.setString(12, createdBy);
            pstmt.setString(13, lastModifiedBy);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public static void init(String url, String username, String password, String driverName) {
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("[Error] driver load error");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("[Error] connection error");
            e.printStackTrace();
        }
    }

}
