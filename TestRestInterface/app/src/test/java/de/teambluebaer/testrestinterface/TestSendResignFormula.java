package de.teambluebaer.testrestinterface;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by chris on 09.06.15.
 */
public class TestSendResignFormula {



    @Test
    public void testWrongParametersResponseCode() {
        given().
                param("tabletID", "3").
                when().
                post("http://127.0.0.1/patientix/server/index.php/resignFormula").
                then().
                statusCode(400);
    }
    @Test
    public void testWrongParametersResponseString() {
        given().
                param("tabletID", "3").
                when().
                post("http://127.0.0.1/patientix/server/index.php/resignFormula").
                then().
                assertThat().
                body(equalTo("Wrong parameters received"));
    }


    @Test
    public void testRightFormulaSendResponseString(){
        given().
                param("formula", "<this><is><formula>formulä<is><this>").
                param("patientID", "5").
                post("http://127.0.0.1/patientix/server/index.php/resignFormula").
                then().
                statusCode(200).
                assertThat().
                body(equalTo("Formula saved"));
    }
    @Test
    public void testWrongPatientIDResponseCode(){
        given().
                param("formula", "<this><is><formula>formulä<is><this>").
                param("patientID", "121234").
                post("http://127.0.0.1/patientix/server/index.php/resignFormula").
                then().
                statusCode(400);
    }
    @Test
    public void testWrongPatientIDResponseString(){
        given().
                param("formula", "<this><is><formula>formulä<is><this>").
                param("patientID", "123241231").
                post("http://127.0.0.1/patientix/server/index.php/resignFormula").
                then().
                assertThat().
                body(equalTo("Something went wrong"));
    }
    @Test
    public void testWrongCharactersInPatientIDResponseString(){
        given().
                param("formula", "<this><is><formula>formulä<is><this>").
                param("patientID", "123241231asdf").
                post("http://127.0.0.1/patientix/server/index.php/resignFormula").
                then().
                assertThat().
                body(equalTo("Wrong parameters received"));
    }

    @Test
    public void testWrongCharactersInPatientIDResponseCode(){
        given().
                param("formula", "<this><is><formula>formulä<is><this>").
                param("patientID", "121234asdf").
                post("http://127.0.0.1/patientix/server/index.php/resignFormula").
                then().
                statusCode(400);
    }

}
