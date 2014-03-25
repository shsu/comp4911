package ca.bcit.infosys.comp4911.services;

import java.io.StringWriter;

import ca.bcit.infosys.comp4911.domain.PLevel;
import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.domain.WorkPackage;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class UserResourceTest {
    private static final String url = "https://comp4911-stevenhsu.rhcloud.com/api";

    private JSONObject authorizationJSONObject;
    private String token;

    @Before
    public void setup() throws Exception {
        authorizationJSONObject = new JSONObject();
    }
    
    @Test
    public void testRetrieveAuthenticatedUserInfoHeader() throws Exception {
        final Response response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");

        // Token is placed inside the header
        given().auth().preemptive().basic(token, "").when().get(url + "/user").then().assertThat().body("username", equalTo("q"));
    }

    @Test
    public void testRetrieveAuthenticatedUserInfoQuery() throws Exception {
        final Response response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");

        // Token is placed inside the query
        when().get(url + "/user?token=" + token).then().assertThat().body("username", equalTo("q"));
    }

    @Test
    public void testRetrieveTokenGET() throws Exception {
        // Place user/pass inside the auth header then GET token.
        given().auth().preemptive().basic("q", "q").when().
          get(url + "/user/token").then().statusCode(200);
    }

    @Test
    public void testRetrieveTokenPOST() throws Exception {
        authorizationJSONObject.put("username", "q");
        authorizationJSONObject.put("password", "q");

        // Place user/pass inside JSON object then POST to token.
        given().contentType(ContentType.JSON).body(authorizationJSONObject.toString()).when().
          post(url + "/user/token").then().statusCode(200);
    }

    @Test
    public void testRetrieveTokenGETWithBadInput() throws Exception {
        given().auth().preemptive().basic("", "").when().
          get(url + "/user/token").then().statusCode(400);
        given().auth().preemptive().basic("q", "").when().
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
        given().auth().preemptive().basic("Q", "password").when().
          get(url + "/user/token").then().statusCode(401);

        given().auth().preemptive().basic("q", "PASSWORD").when().
          get(url + "/user/token").then().statusCode(401);
    }

    @Test
    public void testRetrieveTokenGETWithIncorrectCredentials() throws Exception {
        given().auth().preemptive().basic("bad_username", "password").when().
          get(url + "/user/token").then().statusCode(401);

        given().auth().preemptive().basic("q", "bad_password").when().
          get(url + "/user/token").then().statusCode(401);

        given().auth().preemptive().basic("bad_username", "bad_password").when().
          get(url + "/user/token").then().statusCode(401);
    }

    @Test
    public void testInvalidateToken() throws Exception {
        final Response response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");

        when().delete(url + "/user/token?token=" + token).then().statusCode(204);
    }

    @Test
    public void testInvalidateTokenFailure() throws Exception {
        when().delete(url + "/user/token").then().statusCode(401);
    }
    
    @Test
    public void testRetrieveUserProfile() throws Exception {
    	final Response response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");
        
        given().auth().preemptive().basic(token, "").when().get(url + "/user").then().statusCode(200).and().assertThat().body("username", equalTo("q"));
    }
    
    @Test
    public void testUpdateUserProfile() throws Exception {
    	Response response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        
        User user = given().auth().preemptive().basic(token, "").when().get(url + "/user").as(User.class);
        user.setUsername("qq");
        mapper.writeValue(writer, user);
        given().auth().preemptive().basic(token, "").contentType(ContentType.JSON).body(writer.toString()).when().put(url + "/user").then().statusCode(200);
        
        response = given().auth().preemptive().basic("qq", "q").when().get(url + "/user/token");
        token = (String) new JSONObject(response.asString()).get("token");
        given().auth().preemptive().basic(token, "").when().get(url + "/user").then().assertThat().body("username", equalTo("qq"));
        
        reverseChanges();
    }
    
    @Test
    public void testGetUserPermissions() throws Exception {
    	Response response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");
        
        given().auth().preemptive().basic(token, "").when().get(url + "/user/permissions").then().statusCode(200).body("name[0]", equalTo("Hr"))
        	.and().body("name[3]", equalTo("TimesheetApprover"));
    }
    
    @Test
    public void testGetAllAssignedProjects() throws Exception {
    	Response response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");
        
        Project[] projects = given().auth().preemptive().basic(token, "").when().get(url + "/user/projects").as(Project[].class);
        assertThat(projects[0].getProjectNumber(), equalTo(12345));
    }
    
    @Test
    public void testGetAllAssignedProjectsNoneAssigned() throws Exception {
    	Response response = given().auth().preemptive().basic("jedward", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");
        
        Project[] projects = given().auth().preemptive().basic(token, "").when().get(url + "/user/projects").as(Project[].class);
        assertThat(projects, emptyArray());
    }
    
    /* Not working at this time
    @Test
    public void testGetAllAssignedWorkPackages() throws Exception {
    	Response response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");
        
        WorkPackage[] workPackages = given().auth().preemptive().basic(token, "").when().get(url + "/user/work_packages").as(WorkPackage[].class);
        assertThat(workPackages[0].getWorkPackageNumber(), equalTo("A1112222"));
    }*/
    
    @Test
    public void testGetAllAssignedWorkPackagesNoneAssigned() throws Exception {
    	Response response = given().auth().preemptive().basic("awong", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");
        
        WorkPackage[] workPackages = given().auth().preemptive().basic(token, "").when().get(url + "/user/work_packages").as(WorkPackage[].class);
        assertThat(workPackages, emptyArray());
    }
    
    @After
    public void tearDown() throws Exception {
    	when().delete(url + "/user/token?token=" + token).then().statusCode(204);
    }
    
    private void reverseChanges() throws Exception {
    	Response response = given().auth().preemptive().basic("qq", "q").when().get(url + "/user/token");
        token = (String) new JSONObject(response.asString()).get("token");
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        
    	User user = given().auth().preemptive().basic(token, "").when().get(url + "/user").as(User.class);
        user.setUsername("q");
        mapper.writeValue(writer, user);
        given().auth().preemptive().basic(token, "").contentType(ContentType.JSON).body(writer.toString()).when().put(url + "/user");
    }
}
