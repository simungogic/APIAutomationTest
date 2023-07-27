package Utility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class HttpHelper {
    public static Response sendGetReq(String uri) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .extract().response();
    }

    public static Response sendPostReq(String uri, JSONObject reqParams) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(reqParams.toString())
                .when()
                .post(uri)
                .then()
                .extract().response();
    }

    public static Response sendDeleteReq(String uri, Integer id) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .pathParams(Map.of(
                        "uri", uri,
                        "id", id
                ))
                .when()
                .delete("/{uri}/{id}")
                .then()
                .extract().response();
    }
}
