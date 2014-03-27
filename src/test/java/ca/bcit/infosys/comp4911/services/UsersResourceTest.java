package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.domain.PLevel;
import ca.bcit.infosys.comp4911.domain.User;
import com.google.common.base.Strings;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsersResourceTest {
    private static final String url = "https://comp4911-stevenhsu.rhcloud.com/api";

    private String token;
    private int DbCount;

    @Before
    public void setUp() throws Exception {
        final Response response = given().auth().preemptive().basic("username0@example.com", "password").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");
        assertFalse(Strings.isNullOrEmpty(token));
    }

    @Test
    public void testRetrieveAll() throws Exception {

        given().when().get(url + "/user?token=" + token).then().statusCode(200);

        User[] users = given().when().get(url + "/users?token=" + token).as(User[].class);

        DbCount = given().when().get(url + "/users?token=" + token).as(User[].class).length;
        assertTrue(users.length == DbCount);

    }

    @Test
    public void testCreateUser() throws Exception {

        //Creating new user for inserting.
        Date startDate = setDate(1, 1, 2010);

        final User newUser = new User(
                "newGrad", "newGrad", "FName2", "Lname2", startDate, false, "Active", 40, 0, 14,
                67890123, 56789012, 56789012, PLevel.SS);

        //Loging in as user q.
        Response response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        String token2 = (String) new JSONObject(response.asString()).get("token");

        //Serializing the new user object into Jason.
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, newUser);

        //Inserting the new user object and check the status code returned 201 == created.
        response = given().auth().preemptive().basic(token2, "").contentType(ContentType.JSON).body(writer.toString()).when().post(url + "/users");
        response.then().statusCode(201);

        //Checking if new user persisted by loging in as the new user.
        response = given().auth().preemptive().basic("newGrad", "newGrad").when().get(url + "/user/token");
        token2 = (String) new JSONObject(response.asString()).get("token");
        given().auth().preemptive().basic(token2, "").when().get(url + "/user").then().assertThat().body("username", equalTo("newGrad"));
    }

    @Test
    public void testRetrieveUser() throws Exception {
/*
        //Testing unauthorized user retrieving a record.
        Response response = given().when().get(url + "/user?token=" + token + "/1");
        response.then().statusCode(401);

        //Loging in as user q.
        response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        String token2 = (String) new JSONObject(response.asString()).get("token");

        response = given().when().get(url + "/user?token=" + token2 + "/2");
        response.then().statusCode(200);

        Gson gson = new Gson();

        User retrievedUser = gson.fromJson(response.asString(), User.class);

        assertEquals(retrievedUser.getUsername(), "username0@example.com");*/
    }

    //@Test
    public void testUpdateUser() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        when().delete(url + "/user/token?token=" + token).then().statusCode(204);
    }

    private Date setDate(int month, int day, int year)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, day);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.YEAR, year);
        Date theDate = c.getTime();
        return theDate;
    }
}
