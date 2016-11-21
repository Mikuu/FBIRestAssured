package com.restassured.apitest.demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.w3c.dom.events.EventException;

import java.util.regex.Pattern;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.endsWithPath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertTrue;
import static io.restassured.matcher.ResponseAwareMatcherComposer.and;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.*;

public class PracticeResponse {

    //  Practice using Parameters in Request.
    //  Google Book API reference  https://developers.google.com/books/docs/v1/reference/volumes/list
    //  Google Sheets API reference  https://developers.google.com/sheets/reference/rest/

    private static String token = "ya29.CjGdA3vloTX91tfAcgG7t3yPYKc-RHyXI4VR_8QwY7rNDd1ffs91kSEzX-L5ss1OSWlP";
    private static String spreadsheetId = "1bJsN2ji2kZKmOMVqn4eaoxve-qqJVP65nQqX6GIg2i4";

    @Test
    public void testGetAllResponse() throws EventException {
        String response =
                given()
                .when().get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=1").asString();
        System.out.print(response);

    }

    @Test
    public void testGetGoogleSheetResponse(){
        String response =
                given()
                        .auth().oauth2(this.token)
                        .when().get("https://sheets.googleapis.com/v4/spreadsheets/1bJsN2ji2kZKmOMVqn4eaoxve-qqJVP65nQqX6GIg2i4/values/People!A1:E4").asString();
        System.out.print(response);
    }

    @Test
    public void testExtractFragmentFromGetGoogleSheetResponse(){
        String mondayLunch =
                given()
                        .auth().oauth2(this.token)
                .when()
                        .get("https://sheets.googleapis.com/v4/spreadsheets/1bJsN2ji2kZKmOMVqn4eaoxve-qqJVP65nQqX6GIg2i4/values/foodPlan!B4:B7")
                .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .path("values[1][0]");
        System.out.print("Monday Lunch -> " + mondayLunch);
    }

    @Test
    public void testExtractFragmentFromGoogleBookResponse() throws EventException {
        String title =
                given()
                        .when()
                        .get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=1")
                        .then()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .path("items[0].volumeInfo.title");
        System.out.print("Title -> "+title);

    }

    @Test
    public void testExtractAllResponse() throws EventException {
        Response response =
                given()
                .when()
                        .get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=1")
                .then()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .assertThat()
                        .statusCode(200)
                .extract()
                        .response();

        int totalItems = response.path("totalItems");
        String title = response.path("items[0].volumeInfo.title");
        String publisher = response.path("items[0].volumeInfo.publisher");
        Headers allHeaders = response.headers();
        Map<String, String> allCookies = response.getCookies();

        System.out.print("\nAll Headers -> \n"+allHeaders.toString());
        System.out.print("\nAll Cookies -> \n"+allCookies.toString());
        System.out.print("\nTotal Items: "+totalItems+"\nTitle: "+title+"\nPublisher: "+publisher);
    }

    @Test
    public void testUsePartInResponse1() throws EventException {
        given()
        .when()
                .get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=1")
        .then()
                .log().all()
                .assertThat()
                .body("items[0].selfLink", new ResponseAwareMatcher<Response>() {
                                                public Matcher<?> matcher(Response response) {
                                                    return equalTo("https://www.googleapis.com/books/v1/volumes/" + response.path("items[0].id"));
                                                }
                });
    }

    @Test
    public void testUsePartInResponse2() throws EventException {
        given()
        .when()
                .get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=1")
        .then()
                .log().all()
                .assertThat()
                .body("items[0].selfLink", response -> equalTo("https://www.googleapis.com/books/v1/volumes/" + response.path("items[0].id")));
    }

    @Test
    public void testUsePartInResponse3() throws EventException {
        given()
        .when()
                .get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=1")
        .then()
                .assertThat()
                .body("items[0].selfLink", endsWithPath("items[0].id"))
                .body("items[0].selfLink", and(startsWith("https://www.googleapis.com/"), endsWithPath("items[0].id")));
    }

    @Test
    public void testResponseTime() throws EventException {
        given()
        .when()
                .get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=1")
        .then()
                .assertThat()
                .statusCode(200)
                .time(lessThan(5000L));
    }

    @Test
    public void testResponseBasicUsage() throws EventException {
        given()
        .when()
                .get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=2")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("kind", equalTo("books#volumes"))
                .body("items.id", hasItems("0dge3Xh6EjUC", "0FsUAAAAQAAJ"));

    }

    @Test
    public void testJsonSchema() throws EventException {
        given()
        .when()
                .get("https://www.googleapis.com/books/v1/volumes?q=cucumber&maxResults=10")
        .then()
                .log().all()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("google_book_schema.json"));

    }

    @Test
    public void testGroovyCollection1() throws EventException {
        given()
                .param("q", "cucumber")
                .param("maxResults", 10)
        .when()
                .get("https://www.googleapis.com/books/v1/volumes")
        .then()
                .log().all()
                .assertThat()
                .body("items.findAll {it.volumeInfo.pageCount > 300}.volumeInfo.title", hasItems("Advances in Sea Cucumber Aquaculture and Management",
                                "A Treatise on the Culture of the Cucumber"));

    }

    @Test
    public void testGroovyCollection2() throws EventException {
        given()
                .param("filter", "paid-ebooks")
                .param("q", "a")
                .param("maxResults", 10)
        .when()
                .get("https://www.googleapis.com/books/v1/volumes")
        .then()
                .log().all()
                .assertThat()
                .body("items.findAll {it.saleInfo.listPrice.amount > 200}.size()", greaterThanOrEqualTo(3))
                .body("items.collect {it.saleInfo.retailPrice.amount}.sum()", greaterThan(700.00))
                .body("items*.saleInfo.retailPrice.amount.sum()", greaterThan(700.00))
                .statusCode(200);
    }

    @Test
    public void testGroovyCollection3() throws EventException {
        given()
                .param("q", "a")
                .param("maxResults", 10)
        .when()
                .get("https://www.googleapis.com/books/v1/volumes")
        .then()
                .log().all()
                .assertThat()
                .body("items.unique {it.saleInfo.saleability}.size()", lessThanOrEqualTo(3))
                .statusCode(200);

    }

    @Test
    public void testSpecification() throws EventException {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .addParam("q", "a")
                .addParam("maxRequest", 10)
                .build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200);
        responseSpecBuilder.expectResponseTime(lessThan(5000L));
        responseSpecBuilder.expectBody("kind", equalTo("books#volumes"));
        ResponseSpecification responseSpec = responseSpecBuilder.build();

        Response response =
        given()
                .spec(requestSpec)
        .when()
                .get("https://www.googleapis.com/books/v1/volumes")
        .then()
                .assertThat()
                .spec(responseSpec)
        .extract()
                .response();

        assertTrue(Pattern.matches("\\d+", response.path("totalItems").toString()));

    }

}
