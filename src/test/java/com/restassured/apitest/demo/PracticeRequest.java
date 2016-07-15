package com.restassured.apitest.demo;

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

    private static String token = "ya29.CjAhAxtUkDMOySU5j8dzJYsmRE22D0ughsXKC6Q6fg2QBdvddUSfTk4k_WH6QjWFgeg";
    private static String spreadID ="14jSUH8DoGN3k-QqIV6qIocW-ZYlN_RL507SXjYN7AgM";

    @Test
    public void testGoogleBookAPIDataInURL() throws EventException {
        given()
        .when()
                .get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=2")
        .then()
                .log().all()
                .contentType(ContentType.JSON)
                .assertThat()
                    .statusCode(200);
    }

    @Test
    public void testGoogleBookAPIDataInParameters() throws EventException {
        given()
                .param("q", "cucumber")
                .param("maxResults", 1)
                .param("download", "epub")
                .param("langRestrict", "chinese")
                .param("libraryRestrict", "no-restrict")
        .when()
                .get("https://www.googleapis.com/books/v1/volumes")
        .then()
                .log().all()
                .contentType(ContentType.JSON)
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void testGoogleSheetsAPIDataInBody() throws EventException {
        String postData = "{\n" +
                "  \"range\": \"People!A5:E5\",\n" +
                "  \"majorDimension\": \"ROWS\",\n" +
                "  \"values\": [\n" +
                "  [\"Benjamin\",\n" +
                "  \"Puxley\",\n" +
                "  \"Male\",\n" +
                "  \"Green\",\n" +
                "  \"Apple\"\n" +
                "  ]]\n" +
                "}";

        given()
                .auth().oauth2(this.token)
                .pathParam("spreadsheet_id", this.spreadID)
                .pathParam("range", "People!A5:E5")
                .param("valueInputOption", "USER_ENTERED")
                .body(postData)
                .when()
                .put("https://sheets.googleapis.com/v4/spreadsheets/{spreadsheet_id}/values/{range}")
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testGoogleSheetsAPIMapDataInBody() throws EventException {
        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("range", "Book!A5:E5");
        postData.put("majorDimension", "ROWS");
        postData.put("values", asList(asList("J.K Rowling", "fairy tale", "280", "", "Harry Potter")));

        given()
                .log().all()
                .auth().oauth2(this.token)
                .pathParam("spreadsheet_id", this.spreadID)
                .pathParam("range", "Book!A5:E5")
                .param("valueInputOption", "USER_ENTERED")
                .body(postData)
                .when()
                .put("https://sheets.googleapis.com/v4/spreadsheets/{spreadsheet_id}/values/{range}")
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testGoogleSheetsAPIObjectDataInBody() throws EventException {
        RequestData postData = new RequestData();
        postData.setRange("Book!A5:E5");
        postData.setMajorDimension("ROWS");
        postData.setValues(asList(asList("J.K Rowling", "fairy tale", "280", "", "Harry Potter")));

        given()
                .auth().oauth2(this.token)
                .pathParam("spreadsheet_id", this.spreadID)
                .pathParam("range", "Book!A5:E5")
                .param("valueInputOption", "USER_ENTERED")
                .body(postData)
        .when()
                .put("https://sheets.googleapis.com/v4/spreadsheets/{spreadsheet_id}/values/{range}")
        .then()
                .log().all()
                .contentType(ContentType.JSON)
                .assertThat()
                .statusCode(200);
    }

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

        given()
                .log().all()
                .urlEncodingEnabled(false)
                .pathParam("spreadsheet_id", this.spreadID)
                .auth().oauth2(this.token)
                .body(postData)
        .when()
                .post("https://sheets.googleapis.com/v4/spreadsheets/{spreadsheet_id}:batchUpdate")
        .then()
                .log().all()
                .contentType(ContentType.JSON)
                .assertThat()
                .statusCode(200);
    }

}
