package de.teambluebaer.testrestinterface;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by chris on 09.06.15.
 */
public class TestGetTabletID {
    @Test
    public void testWrongParametersResponseCode() {
        given().
                param("tabletID", "3").
                when().
                post("http://127.0.0.1/patientix/server/index.php/getTabletID").
                then().
                statusCode(400);
    }

    @Test
    public void testWrongParametersResponseString() {
        given().
                param("tabletID", "3").
                when().
                post("http://127.0.0.1/patientix/server/index.php/getTabletID").
                then().
                assertThat().
                body(equalTo("Wrong parameters received"));
    }

    @Test
    public void testRightMacAddressResponseString() {
        given().
                param("macAddress", "DE:AD:BE:EF:10:28").
                when().
                post("http://127.0.0.1/patientix/server/index.php/getTabletID").
                then().
                assertThat().
                body(equalTo("5"));
    }

    @Test
    public void testRightMacAddressResponseCode() {
        given().
                param("macAddress", "DE:AD:BE:EF:10:28").
                when().
                post("http://127.0.0.1/patientix/server/index.php/getTabletID").
                then().
                statusCode(200);
    }
    @Test
    public void testGetOnPostUrlResponseCode() {
        get("http://127.0.0.1/patientix/server/index.php/getTabletID").
                then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void testGetOnPostUrlResponseString() {
        get("http://127.0.0.1/patientix/server/index.php/getTabletID").
                then().
                assertThat().
                assertThat().
                body(equalTo("<html><head><title>404 Page Not Found</title><style>body{margin:0;padding:30px;font:12px/1.5 Helvetica,Arial,Verdana,sans-serif;}h1{margin:0;font-size:48px;font-weight:normal;line-height:48px;}strong{display:inline-block;width:65px;}</style></head><body><h1>404 Page Not Found</h1><p>The page you are looking for could not be found. Check the address bar to ensure your URL is spelled correctly. If all else fails, you can visit our home page at the link below.</p><a href=\"/patientix/server/index.php/\">Visit the Home Page</a></body></html>"));
    }

}
