package ca.bcit.infosys.comp4911.services;

import com.google.common.base.Strings;
import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertFalse;

public class UsersResourceTest {
    private static final String url = "https://comp4911-stevenhsu.rhcloud.com";

    private String token;

    @Before
    public void setUp() throws Exception {
        final Response response = given().auth().preemptive().basic("admin@example.com", "password").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");
        assertFalse(Strings.isNullOrEmpty(token));
    }

    @Test
    public void testRetrieveAll() throws Exception {
        //when().get(url + "/<RESOURCE_NAME>?token=" + token).then()...
    }

    @Test
    public void testCreateUser() throws Exception {

    }

    @Test
    public void testRetrieveUser() throws Exception {

    }

    @Test
    public void testUpdateUser() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        when().delete(url + "/user/token?token=" + token).then().statusCode(204);
    }
}
