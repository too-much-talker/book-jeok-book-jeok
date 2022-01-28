package com.ssafy.bjbj.scrap;

import com.ssafy.bjbj.BookInfo;
import com.ssafy.bjbj.dao.BookInfoDao;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

public class BookInfoScrapper {

    static String yourDatabaseUsername = "kskim";
    static String yourDatabasePassword = "qnrwjrqnrwjr";

//    static String url = "jdbc:mysql://i6a305.p.ssafy.io:3306/bjbj?serverTimezone=UTC";
    static String url = "jdbc:mysql://localhost:3306/bjbj?serverTimezone=UTC";
    static String username = yourDatabaseUsername;
    static String password = yourDatabasePassword;
    static String driverName = "com.mysql.cj.jdbc.Driver";

    static final String BASE_URL = "https://book.interpark.com/api/bestSeller.api";
    static final String KEY = "537FCC46AF602A4DF0885D63D18F642A9B65DD8B174CA9F22F43DF50B2FF485D";
    static final String EQUAL = "=";
    static final String OUTPUT = "json";

    /**
     * 인터파크 카테고리 아이디
     */
    static final int[] categoryIds = new int[]{
            101, 102, 103, 104, 105, 107, 108, 109, 110, 111,
            112, 113, 114, 115, 116, 117, 118, 119, 120, 122,
            123, 124, 125, 126, 128, 129
    };

    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static void main(String[] args) throws Exception {
        // db connection init
        BookInfoDao.init(url, username, password, driverName);

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
                .append(BASE_URL)
                .append("?").append(encode("key", UTF_8)).append(EQUAL).append(KEY)
                .append("&").append(encode("output", UTF_8)).append(EQUAL).append(OUTPUT)
                .append("&").append(encode("categoryId", UTF_8)).append(EQUAL);

        for (int categoryId : categoryIds) {
            URL url = new URL(urlBuilder.toString() + categoryId);

            HttpsURLConnection conn = getHttpURLConnection(url);

            int responseCode = conn.getResponseCode();
            boolean isSuccess = 200 <= responseCode && responseCode <= 300;
            String response = getResponse(conn, isSuccess);

            if (isSuccess) {
                JSONParser parser = new JSONParser();
                JSONObject totalInfoJson = (JSONObject) parser.parse(response);
                JSONArray bookInfoJsons = (JSONArray) totalInfoJson.get("item");

                for (Object bookInfoJson : bookInfoJsons) {
                    BookInfo bookInfo = getBookInfo((JSONObject) bookInfoJson);

                    try {
                        BookInfoDao.insert(bookInfo);
                        System.out.println("[등록 완료] isbn : " + bookInfo.getIsbn());
                    } catch (SQLIntegrityConstraintViolationException e) {
                        System.out.println("[데이터 중복] isbn : " + bookInfo.getIsbn());
                    } catch (Exception e) {
                        System.out.println("[Error] database error");
                        e.printStackTrace();
                        System.out.println("url : " + urlBuilder.toString() + categoryId);
                        throw new Exception("에러 발생~");
                    }
                }
            } else {
                System.out.println("[Error] api error");
                System.out.println("url : " + urlBuilder.toString() + categoryId);
                throw new Exception("에러 발생~");
            }
        }
    }

    private static BookInfo getBookInfo(JSONObject bookInfoJson) {
        return BookInfo.builder()
                .isbn((String) bookInfoJson.get("isbn"))
                .title((String) bookInfoJson.get("title"))
                .author((String) bookInfoJson.get("author"))
                .description((String) bookInfoJson.get("description"))
                .price((int) (long) bookInfoJson.get("priceStandard"))
                .smallImgUrl((String) bookInfoJson.get("coverSmallUrl"))
                .largeImgUrl((String) bookInfoJson.get("coverLargeUrl"))
                .categoryId(Integer.parseInt((String) bookInfoJson.get("categoryId")))
                .categoryName(((String) bookInfoJson.get("categoryName")).split(">")[1])
                .publisher((String) bookInfoJson.get("publisher"))
                .publishDate(LocalDateTime.parse((String) bookInfoJson.get("pubDate") + "111111", dateFormatter))
                .build();
    }

    private static String getResponse(HttpsURLConnection conn, boolean isSuccess) throws IOException {
        BufferedReader br;
        if (isSuccess) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        conn.disconnect();
        br.close();

        return sb.toString();
    }

    private static HttpsURLConnection getHttpURLConnection(URL url) throws IOException {
        // 커넥션 객체 생성
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // HTTP 메서드 설정
        conn.setRequestMethod("GET");

        // Content Type 설정
        conn.setRequestProperty("Content-type", "application/json");

        return conn;
    }

}
