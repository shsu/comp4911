package ca.bcit.infosys.comp4911.services;

import com.jayway.restassured.authentication.AuthenticationScheme;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.preemptive;


public class UserResourceTest {
    private static final String url = "https://comp4911-stevenhsu.rhcloud.com";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testLogin() {
        given().auth().preemptive().basic("admin@example.com", "password").when().
          get(url + "/user/token").then().statusCode(200);
    }

    @After
    public void tearDown() throws Exception {

    }
}
