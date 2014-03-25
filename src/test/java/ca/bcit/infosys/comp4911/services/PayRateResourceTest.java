package ca.bcit.infosys.comp4911.services;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ca.bcit.infosys.comp4911.domain.PayRate;

import com.jayway.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;


public class PayRateResourceTest {
	private static final String url = "https://comp4911-stevenhsu.rhcloud.com/api";

    private String token;
    
    @Before
    public void setup() throws Exception {
    	final Response response = given().auth().preemptive().basic("q", "q").when().get(url + "/user/token");
        response.then().statusCode(200);
        token = (String) new JSONObject(response.asString()).get("token");
    }
    
    @Test
    public void testRetrieveAllPayRates() throws Exception {    	
    	PayRate[] payRates = given().auth().preemptive().basic(token, "").when().get(url + "/pay_rates").as(PayRate[].class);
    	assertThat(payRates[0].getId(), equalTo(10));
    	assertThat(payRates[48].getId(), equalTo(58));
    	assertThat(payRates[28].getId(), equalTo(38));
    }
    
    @Test
    public void testRetrieveAllPayRatesSpecific() throws Exception {
    	given().auth().preemptive().basic(token, "").when().get(url + "/pay_rates").then().assertThat().body("id[3]", equalTo(13)).and().body("rate[3]", equalTo(175.0f));
    }
    
    @Test
    public void testRetrieveAllPayLevelRates() throws Exception {
    	PayRate[] payRates = given().auth().preemptive().basic(token, "").when().get(url + "/pay_rates/P5").as(PayRate[].class);
    	assertThat(payRates[0].getId(), equalTo(14));
    	assertThat(payRates[6].getId(), equalTo(56));
    }
    
    @Test
    public void testRetrieveAllPayLevelRatesNonExistentLevel() throws Exception {
    	given().auth().preemptive().basic(token, "").when().get(url + "/pay_rates/P10").then().statusCode(404);
    }
    
    @Test
    public void testRetrieveAllPayLevelRatesSpecific() throws Exception {
    	given().auth().preemptive().basic(token, "").when().get(url + "/pay_rates/P5").then().assertThat().body("id[0]", equalTo(14)).and().body("rate[0]", equalTo(215.0f));
    }
    
    @Test
    public void testRetrievePayRateExistingYear() throws Exception {
    	given().auth().preemptive().basic(token, "").when().get(url + "/pay_rates/P5/2014").then().statusCode(200).and().assertThat().body("rate", equalTo(215.0f));
    }
    
    @Test
    public void testRetrievePayRateNonExistentYear() throws Exception {
    	given().auth().preemptive().basic(token, "").when().get(url + "/pay_rates/P5/2049").then().statusCode(401);
    }
}
