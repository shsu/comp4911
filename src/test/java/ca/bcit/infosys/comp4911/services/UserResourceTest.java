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

        //valid username + valid password expecting HTTP code 200
        given().auth().preemptive().basic("admin@example.com", "password").when().
                get(url + "/user/token").then().statusCode(200);

        //invalid username + invalid password expecting HTTP code 401
        given().auth().preemptive().basic("noneExists@example.com", "password").when().
                get(url + "/user/token").then().statusCode(401);

        //invalid username + invalid password expecting HTTP code 401
        given().auth().preemptive().basic("", "").when().
                get(url + "/user/token").then().statusCode(400);

        //invalid username + invalid password expecting HTTP code 401
        given().auth().preemptive().basic("noneExists", "password").when().
                get(url + "/user/token").then().statusCode(401);

        //valid username + invalid password expecting HTTP code 401
        given().auth().preemptive().basic("admin@example.com", "nonExistsPassword").when().
                get(url + "/user/token").then().statusCode(401);

        //valid username + invalid password expecting HTTP code 401
        given().auth().preemptive().basic("admin@example.com", " Password").when().
                get(url + "/user/token").then().statusCode(401);

        //valid username + invalid password expecting HTTP code 401
        given().auth().preemptive().basic("admin@example.com", "Password ").when().
                get(url + "/user/token").then().statusCode(401);

        //valid username + invalid password expecting HTTP code 401
        given().auth().preemptive().basic("admin@example.com", "PASSWORD").when().
                get(url + "/user/token").then().statusCode(401);

        //valid username + invalid password expecting HTTP code 401
        given().auth().preemptive().basic("admin@example.com", "").when().
                get(url + "/user/token").then().statusCode(400);

        //invalid username + valid password expecting HTTP code 401
        given().auth().preemptive().basic("Admin@example.com", "password").when().
                get(url + "/user/token").then().statusCode(401);

        //invalid username + valid password expecting HTTP code 401
        given().auth().preemptive().basic("admin", "password").when().
                get(url + "/user/token").then().statusCode(401);

        //invalid username + valid password expecting HTTP code 401
        given().auth().preemptive().basic("aadmin@example.com", "password").when().
                get(url + "/user/token").then().statusCode(401);
        }

    @Test
    public void testRetrieveToken() throws Exception {

    }

    @Test
    public void testInvalidateToken() throws Exception {

    }
}
