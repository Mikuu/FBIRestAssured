package com.restassured.apitest.workshop;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.w3c.dom.events.EventException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;


public class PracticeRequest {

    //  Practice using Parameters in Request.
    //  Google Book API reference  https://developers.google.com/books/docs/v1/reference/volumes/list
    //  Google Sheets API reference  https://developers.google.com/sheets/reference/rest/
    private static String token = "ya29.CjAlA12VWMPXVS63lCPGZU8uS9gxb_wfa0vw8vdVr4NSe9eAfiKOXXn79PAvp84yy8k";
    private static String spreadID ="14jSUH8DoGN3k-QqIV6qIocW-ZYlN_RL507SXjYN7AgM";

    //https://developers.google.com/books/docs/v1/reference/volumes/list
    //发送get请求，返回书名为含有cucumber的书，并返回两个结果
    @Test
    public void testGoogleBookAPIDataInURL() throws EventException {

    }



    //https://developers.google.com/books/docs/v1/reference/volumes/list
    //使用参数
    //https://github.com/rest-assured/rest-assured/wiki/Usage#syntactic-sugar
    @Test
    public void testGoogleBookAPIDataInParameters() throws EventException {

    }



    //https://developers.google.com/sheets/reference/rest/v4/spreadsheets.values/update
    //https://developers.google.com/sheets/samples/writing
    //发送PUT请求，增加GoogleSheet中的一行数据
    //https://github.com/rest-assured/rest-assured/wiki/Usage#request-body
    @Test
    public void testGoogleSheetsAPIDataInBody() throws EventException {

    }



    //https://developers.google.com/sheets/reference/rest/v4/spreadsheets.values/update
    //发送PUT请求，Update GoogleSheet中的一行数据 - Object Mapping
    //https://github.com/rest-assured/rest-assured/wiki/Usage#create-json-from-a-hashmap
    @Test
    public void testGoogleSheetsAPIMapDataInBody() throws EventException {

    }



    //https://developers.google.com/sheets/reference/rest/v4/spreadsheets.values/update
    //发送PUT请求，Update GoogleSheet中的一行数据 - 序列化
    //https://github.com/rest-assured/rest-assured/wiki/Usage#serialization
    @Test
    public void testGoogleSheetsAPIObjectDataInBody(){

    }



    //https://developers.google.com/sheets/reference/rest/v4/spreadsheets/batchUpdate
    //发送post请求，修改Favorite Color列的背景色
    @Test
    public void testGoogleSheetsPost() throws EventException {
        String postData = "{\n" +
                "  \"requests\": [\n" +
                "    {\n" +
                "      \"updateCells\": {\n" +
                "        \"start\": {\n" +
                "          \"sheetId\": 0,\n" +
                "          \"rowIndex\": 1,\n" +
                "          \"columnIndex\": 3\n" +
                "        },\n" +
                "        \"rows\": [\n" +
                "          {\n" +
                "            \"values\": [\n" +
                "              {\n" +
                "                \"userEnteredFormat\": {\"backgroundColor\": {\"red\": 0.2,\n" +
                "                  \"green\": 0.5,\n" +
                "                  \"blue\": 0.9}}\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ],\n" +
                "        \"fields\": \"userEnteredFormat.backgroundColor\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
