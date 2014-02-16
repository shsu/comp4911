package ca.bcit.infosys.comp4911.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by steven on 2/14/14.
 * Edited by jung on 2/15/14.
 */
public class UserResourceTest {
    private static final String url = "https://comp4911-stevenhsu.rhcloud.com";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRetrieveAuthenticatedUserInfo() throws Exception {
        given().auth().preemptive().basic("admin@example.com", "password").when().
                get(url + "/user/token").then().statusCode(200);

        given().auth().preemptive().basic("noneExists@example.com", "password").when().
                get(url + "/user/token").then().statusCode(401);
    }

    @Test
    public void testRetrieveToken() throws Exception {

    }

    @Test
    public void testInvalidateToken() throws Exception {

    }
}
