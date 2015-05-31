package de.teambluebaer.patientix.testcases.restfulHelper;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by chris on 31.05.15.
 */
public class TestSendFormulaData {


    @Test
    public void testWrongParametersResponseCode() {
        given().
                param("tabletID", "3").
                when().
                post("http://127.0.0.1/index.php/filledformula").
                then().
                statusCode(400);
    }
    @Test
    public void testWrongParametersResponseString() {
        given().
                param("tabletID", "3").
                when().
                post("http://127.0.0.1/index.php/filledformula").
                then().
                assertThat().
                body(equalTo("Wrong parameters received"));
    }


    @Test
    public void testRightFormulaSendResponseCode(){
        given().
                param("isFormula", "true").
                param("formula", "<this><is><formula>formulä<is><this>").
                param("macaddress","CC:07:AB:B8:E5:83").
                param("patientID", "12").
                post("http://127.0.0.1/index.php/filledformula").
                then().
                statusCode(200);
    }
    @Test
    public void testRightFormulaSendResponseString(){
        given().
                param("isFormula", "true").
                param("formula", "<this><is><formula>formulä<is><this>").
                param("macaddress","CC:07:AB:B8:E5:83").
                param("patientID", "12").
                post("http://127.0.0.1/index.php/filledformula").
                then().
                assertThat().
                body(equalTo("Formula saved"));
    }
    @Test
    public void testWrongMacAddressResponseCode(){
        given().
                param("isFormula", "true").
                param("formula", "<this><is><formula>formulä<is><this>").
                param("macaddress","CC:07:ABasdfasdoifo:B8:E5:83").
                param("patientID", "12").
                post("http://127.0.0.1/index.php/filledformula").
                then().
                statusCode(400);
    }
    @Test
    public void testWrongMacAddressResponseString(){
        given().
                param("isFormula", "true").
                param("formula", "<this><is><formula>formulä<is><this>").
                param("macaddress","CC:07:ABasdfasdoifo:B8:E5:83").
                param("patientID", "12").
                post("http://127.0.0.1/index.php/filledformula").
                then().
                assertThat().
                body(equalTo("Wrong MACAddress"));
    }
    @Test
    public void testWrongStringForWrintingInDatabaseResponseCode(){
        given().
                param("isFormula", "false").
                param("formula", "<this><is><formula>formulä<is><this>").
                param("macaddress","CC:07:AB:B8:E5:83").
                param("patientID", "12").
                post("http://127.0.0.1/index.php/filledformula").
                then().
                statusCode(406);
    }
    @Test
    public void testWrongStringForWrintingInDatabaseResponseString(){
        given().
                param("isFormula", "false").
                param("formula", "<this><is><formula>formulä<is><this>").
                param("macaddress","CC:07:AB:B8:E5:83").
                param("patientID", "12").
                post("http://127.0.0.1/index.php/filledformula").
                then().
                assertThat().
                body(equalTo("No formula data"));

    }


}
