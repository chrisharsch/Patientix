package de.teambluebaer.patientix.testcases.restfulHelper;

import junit.framework.TestCase;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.*;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
/**
 * Created by Chris on 31.05.2015.
 */
public class Restfulltest extends TestCase{

    @Test
    public void testCaseOne(){
        given().param("tabletID", "3").
                when().
                post("/index.php/formula").
                then().
                body("greeting.firstName", equalTo("John"));
    }

    @Test
    public void testCaseTwo(){

        given().
                param("tabletID", "3").
                when().
                post("/index.php/formula").
                then().
                statusCode(200);
    }

}
