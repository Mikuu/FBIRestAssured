package com.restassured.demo.apitest;

import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;
import io.restassured.module.jsv.JsonSchemaValidator.*;
import org.junit.Test;
import org.w3c.dom.events.EventException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class TestGoogleSheetAPI {

    private static String token = "ya29.CjAbAxCGw6hiqdJQTzf532ZxzHvZDpd5NBoxB2M1TFbfJD6MAUCjLcqfa91MLw7i-mc";

    @Test
    public void testGetGoogleSheets() throws EventException {
        given()
                .auth().oauth2(this.token)
        .when()
                .get("https://sheets.googleapis.com/v4/spreadsheets/1bJsN2ji2kZKmOMVqn4eaoxve-qqJVP65nQqX6GIg2i4/values/mySheet!A3:H7")
        .then()
                .contentType(ContentType.JSON)
                .assertThat()
                    .statusCode(200)
                    .body("range", equalTo("mySheet!A3:H7"))
                    .body("majorDimension", equalTo("ROWS"));


    }



}
