package com.restassured.apitest.workshop;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.w3c.dom.events.EventException;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;


public class PracticeRequest {

    //  Practice using Parameters in Request.
    //  Google Book API reference  https://developers.google.com/books/docs/v1/reference/volumes/list
    //  Google Sheets API reference  https://developers.google.com/sheets/reference/rest/
    private static String token = "ya29.CjAgA6VXsseWe5_KRy1w7zHYTMv5cBxaj8JApmkyCAEA0fibo63sR3U1yP5jKjNtBEE";

    //https://developers.google.com/books/docs/v1/reference/volumes/list
    //发送get请求，返回书名为含有cucumber的书，并返回二个结果
    @Test
    public void testGoogleBookAPIDataInURL() throws EventException {

    }

    //https://developers.google.com/books/docs/v1/reference/volumes/list
    //使用参数
    //https://github.com/rest-assured/rest-assured/wiki/Usage#getting-response-data
    @Test
    public void testGoogleBookAPIDataInParameters() throws EventException {

    }

    //https://developers.google.com/sheets/reference/rest/v4/spreadsheets.values/update
    //发送PUT请求，增加GoogleSheet中的一行数据
    //https://github.com/rest-assured/rest-assured/wiki/Usage#getting-response-data
    @Test
    public void testGoogleSheetsAPIDataInBody() throws EventException {

    }

    //https://developers.google.com/sheets/reference/rest/v4/spreadsheets.values/update
    //发送PUT请求，Update GoogleSheet中的一行数据 - Object Mapping
    //https://github.com/rest-assured/rest-assured/wiki/Usage#object-mapping
    @Test
    public void testGoogleSheetsAPIMapDataInBody() throws EventException {

    }

    //https://developers.google.com/sheets/reference/rest/v4/spreadsheets/request#updatecellsrequest
    //发送post请求，修改Favorite Color列的背景色
    @Test
    public void testGoogleSheetsPost() throws EventException {

    }
}
