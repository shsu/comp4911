package ca.bcit.infosys.comp4911.services;

import com.jayway.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class UserResourceTest {
    private static final String url = "https://comp4911-stevenhsu.rhcloud.com";

    private JSONObject authorizationJSONObject;

    @Before
    public void setup() throws Exception {
        authorizationJSONObject = new JSONObject();
    }

    @Test
    public void testRetrieveAuthenticatedUserInfo() throws Exception {
        given().auth().preemptive().basic("admin@example.com", "password").when().
                get(url + "/user/token").then().assertThat().body("user_id", equalTo(1));
        }

    @Test
    public void testRetrieveTokenGET() throws Exception {
        given().auth().preemptive().basic("admin@example.com", "password").when().
          get(url + "/user/token").then().assertThat().body("user_id", equalTo(1));
    }

    @Test
    public void testRetrieveTokenPOST() throws Exception {
        authorizationJSONObject.put("username", "admin@example.com");
        authorizationJSONObject.put("password", "password");
        given().contentType(ContentType.JSON).body(authorizationJSONObject.toString()).when().
          post(url + "/user/token").then().assertThat().body("user_id", equalTo(1));
    }

    @Test
    public void testRetrieveTokenGETWithBadInput() throws Exception {
        given().auth().preemptive().basic("", "").when().
          get(url + "/user/token").then().statusCode(400);
        given().auth().preemptive().basic("admin@example.com", "").when().
          get(url + "/user/token").then().statusCode(400);
        given().auth().preemptive().basic("", "password").when().
          get(url + "/user/token").then().statusCode(400);
    }

    @Test
    public void testRetrieveTokenPOSTWithBadInput1() throws Exception {
        given().contentType(ContentType.JSON).body(authorizationJSONObject.toString()).when().
          post(url + "/user/token").then().statusCode(400);
    }

    @Test
    public void testRetrieveTokenPOSTWithBadInput2() throws Exception {
        authorizationJSONObject.put("username","");
        given().contentType(ContentType.JSON).body(authorizationJSONObject.toString()).when().
          post(url + "/user/token").then().statusCode(400);
    }

    @Test
    public void testRetrieveTokenPOSTWithBadInput3() throws Exception {
        authorizationJSONObject.put("password","");
        given().contentType(ContentType.JSON).body(authorizationJSONObject.toString()).when().
          post(url + "/user/token").then().statusCode(400);
    }

    @Test
    public void testRetrieveTokenGETWithIncorrectCase() throws Exception {
        given().auth().preemptive().basic("ADMIN@EXAMPLE.COM", "password").when().
          get(url + "/user/token").then().statusCode(401);

        given().auth().preemptive().basic("admin@example.com", "PASSWORD").when().
          get(url + "/user/token").then().statusCode(401);
    }

    @Test
    public void testRetrieveTokenGETWithIncorrectCredentials() throws Exception {
        given().auth().preemptive().basic("bad_username", "password").when().
          get(url + "/user/token").then().statusCode(401);

        given().auth().preemptive().basic("admin@example.com", "bad_password").when().
          get(url + "/user/token").then().statusCode(401);

        given().auth().preemptive().basic("bad_username", "bad_password").when().
          get(url + "/user/token").then().statusCode(401);
    }

    @Test
    public void testInvalidateToken() throws Exception {
        when().delete(url + "/user/token").then().statusCode(204);
    }
}
