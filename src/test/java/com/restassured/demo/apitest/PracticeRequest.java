package com.restassured.demo.apitest;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.w3c.dom.events.EventException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;



public class PracticeRequest {

    //  Practice using Parameters in Request.
    //  Google Book API reference  https://developers.google.com/books/docs/v1/reference/volumes/list
    //  Google Sheets API reference  https://developers.google.com/sheets/reference/rest/

    private static String token = "ya29.CjAeA1vu3kaQMREU_EtSg8zFdU9EkirwNOoqcRMN1XDhNRVemckR97yhD4IFvjzqFl4";

    @Test
    public void testGoogleBookAPIDataInURL() throws EventException {
        given()
        .when()
                .get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=1")
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
                "  \"range\": \"mySheet!B4:B7\",\n" +
                "  \"majorDimension\": \"ROWS\",\n" +
                "  \"values\": [\n" +
                "    [\"^_^\"],\n" +
                "    [\"~_~\"],\n" +
                "    [\"#_#\"],\n" +
                "    [\"@_@\"]\n" +
                "  ],\n" +
                "}";

        given()
                .auth().oauth2(this.token)
                .pathParam("spreadsheet_id", "1bJsN2ji2kZKmOMVqn4eaoxve-qqJVP65nQqX6GIg2i4")
                .pathParam("range", "mySheet!B4:B7")
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
        postData.put("range", "demoSheet!B7:E7");
        postData.put("majorDimension", "ROWS");
        postData.put("values", asList(asList("Miya", "1st May", "Star", "HeHe")));

        given()
                .log().all()
                .auth().oauth2(this.token)
                .pathParam("spreadsheet_id", "1bJsN2ji2kZKmOMVqn4eaoxve-qqJVP65nQqX6GIg2i4")
                .pathParam("range", "demoSheet!B7:E7")
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
        postData.setRange("demoSheet!B8:E8");
        postData.setMajorDimension("ROWS");
        postData.setValues(asList(asList("Miya8", "1st May", "Star", "HeHe")));

        given()
                .auth().oauth2(this.token)
                .pathParam("spreadsheet_id", "1bJsN2ji2kZKmOMVqn4eaoxve-qqJVP65nQqX6GIg2i4")
                .pathParam("range", "demoSheet!B8:E8")
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

}
