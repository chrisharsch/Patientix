package de.teambluebaer.patientix.testcases.restfulHelper;


import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Chris on 31.05.2015.
 */
public class TestReceiveFormula {

    @Test
    public void testRandomExtension() {

        given().
                param("key1", "value1").
                param("key2", "value2").
                when().
                post("http://127.0.0.1/somewhere").
                then().
                statusCode(404);
    }

    @Test
    public void testTabletThere() {
        given().
                param("tabletID", "3").
                when().
                post("http://127.0.0.1/index.php/formula").
                then().
                statusCode(200);
    }

    @Test
    public void testWrongDataSendResponseCode() {
        given().
                param("tableID", "3").
                when().
                post("http://127.0.0.1/index.php/formula").
                then().
                statusCode(400);
    }

    @Test
    public void testWrongDataSendResponseString() {
        given().
                param("tableID", "3").
                when().
                post("http://127.0.0.1/index.php/formula").
                then().
                assertThat().
                body(equalTo("Wrong parameters"));
    }

    @Test
    public void testWrongTabletIDResponseCode() {
        given().
                param("tabletID", "300").
                when().
                post("http://127.0.0.1/index.php/formula").
                then().
                statusCode(404);
    }

    @Test
    public void testWrongTabletIDResponseString() {
        given().
                param("tabletID", "300").
                when().
                post("http://127.0.0.1/index.php/formula").
                then().
                assertThat().
                body(equalTo("No data found"));
    }

    @Test
    public void testNoDataResponseCode() {
        given().
                when().
                post("http://127.0.0.1/index.php/formula").
                then().
                statusCode(400);
    }

    @Test
    public void testNoDataResponseString() {
        given().
                when().
                post("http://127.0.0.1/index.php/formula").
                then().
                assertThat().
                body(equalTo("Wrong parameters"));
    }

    @Test
    public void testWrongUrlButExistingResponseCode() {
        given().
                when().
                post("http://127.0.0.1/index.php").
                then().
                statusCode(404);
    }

    @Test
    public void testWrongUrlButExistingResponseString() {
        given().
                when().
                post("http://127.0.0.1/index.php").
                then().
                assertThat().
                body(equalTo("<html><head><title>404 Page Not Found</title><style>body{margin:0;padding:30px;font:12px/1.5 Helvetica,Arial,Verdana,sans-serif;}h1{margin:0;font-size:48px;font-weight:normal;line-height:48px;}strong{display:inline-block;width:65px;}</style></head><body><h1>404 Page Not Found</h1><p>The page you are looking for could not be found. Check the address bar to ensure your URL is spelled correctly. If all else fails, you can visit our home page at the link below.</p><a href=\"/index.php/\">Visit the Home Page</a></body></html>"));
    }

    @Test
    public void testGetOnPostUrlResponseCode() {
        get("http://127.0.0.1/index.php/fomula").
                then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void testGetOnPostUrlResponseString() {
        get("http://127.0.0.1/index.php/fomula").
                then().
                assertThat().
                assertThat().
                body(equalTo("<html><head><title>404 Page Not Found</title><style>body{margin:0;padding:30px;font:12px/1.5 Helvetica,Arial,Verdana,sans-serif;}h1{margin:0;font-size:48px;font-weight:normal;line-height:48px;}strong{display:inline-block;width:65px;}</style></head><body><h1>404 Page Not Found</h1><p>The page you are looking for could not be found. Check the address bar to ensure your URL is spelled correctly. If all else fails, you can visit our home page at the link below.</p><a href=\"/index.php/\">Visit the Home Page</a></body></html>"));
    }

    @Test
    public void testRightBody() {
        String expected = "<root><meta><pID>9</pID><pExamID>343467682</pExamID><pFirstName>Katharine Gerda</pFirstName><pLastName>Herschel</pLastName><pDate>1980-10-10</pDate><name>CT Aufklärung</name></meta><formData><page>\n" +
                "                    <row>\n" +
                "                        <text text=\"Informationen für unsere Patienten zur Computertomographie\" size=\"20\"></text>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "                        <text text=\"Was ist Computertomographie?\" size=\"15\"></text>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "                        <text text=\"Die Computertomographie, auch CT genannt, ist neben der Kernspintomographie eine weitere moderne Form der diagnostischen Abbildung des menschlichen Körpers. In der Computertomographie werden Röntgenstrahlen zur Bilderstellung verwendet.\" size=\"14\"></text>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "                        <text text=\"Wie bereite ich mich auf die Untersuchung vor?\" size=\"15\"></text>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "                        <text text=\"Wird bei Ihnen eine CT-Untersuchung ohne Kontrastmittel (nativ) durchgeführt, ist keine besondere Vorbereitung notwendig. Sie können wie üblich essen und trinken. Bei einigen Untersuchungen ist die Gabe von Kontrastmittel nötig, um die diagnostische Information der CT-Untersuchung zu verbessern. Diese Kontrastmittel enthalten einen Jod - Komplex, sind in der Regel gut verträglich und werden über einen  venösen Zugang in der Ellenbeuge oder an der Hand eingespritzt. In vielen Fällen beobachten Patienten ein intensives Wärmegefühl im gesamten Körper oder einen ungewohnten Geschmack im Mund.\n" +
                "        Dies ist eine normale Reaktion, keine Unverträglichkeit! In seltenen Fällen kann es zu einem Austritt des Kontrastmittels aus dem Gefäß kommen (so genanntes Paravasat), was zu einer Schwellung und Gewebsuntergang führen kann, in schweren Fällen kann es zu einem Kompartmentsyndrom führen.\n" +
                "        In extrem seltenen Fällen wurden auch mittelgradige und schwere Unverträglichkeitsreaktionen auf die Kontrastmittelgabe mit Komplikationen beobachtet. Sollte solch eine Unverträglichkeit bereits bei einer vorangegangenen Untersuchung bei Ihnen aufgetreten sein, so bitten wir Sie, dieses unbedingt anzugeben. Sollte eine unvorhergesehene Komplikation auftreten, so stehen alle nötigen Möglichkeiten zur sofortigen Behandlung zur Verfügung. Da das Kontrastmittel wird über die Nieren ausgeschieden wird sollten sie nach der Untersuchung viel trinken.\n" +
                "        Ist eine Kontrastmittelgabe für die Untersuchung notwendig, müssen eine Nierenfunktionsstörung und/oder eine Schilddrüsenfunktionsstörung ausgeschlossen werden. Dazu werden zwei Blutwerte bestimmt, Kreatinin (Nierenwert) und TSH (Schilddrüsenwert). Auch müssen metforminhaltige Antidiabetika nach der Untersuchung für 48 Stunden abgesetzt werden, da sonst Wechselwirkungen mit dem Kontrastmittel auftreten können!\" size=\"14\"></text>\n" +
                "                    </row>\n" +
                "                </page>\n" +
                "\n" +
                "<page>\n" +
                "                    <row>\n" +
                "                        <text text=\"Ist die Untersuchung schmerzhaft oder gefährlich?\" size=\"15\"></text>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "                        <text text=\"Die CT-Untersuchung ist ein schmerzloses Verfahren. Während der Untersuchung verspüren Sie nichts. Die Strahlenbelastung ist deutlich höher als bei einer konventionellen Röntgenuntersuchung. Deshalb muss sorgfältig geprüft werden, ob eine eventuelle Schädigung durch die Röntgenstrahlung den Informationsgewinn aufwiegt.  Da Kinder, Jugendliche und ungeborene Kinder besonders strahlenempfindlich sind, wird bei diesem Personenkreis die Indikation zu einer CT-Untersuchung besonders streng überprüft! Teilen Sie dem Arzt deshalb unbedingt mit, ob eine Schwangerschaft besteht, auch wenn Sie nicht sicher sind!\" size=\"14\"></text>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "                        <text text=\"Wie lange dauert die Untersuchung?\" size=\"15\"></text>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "                        <text text=\"Standarduntersuchungen in der CT dauern meist 5-10 Minuten, wobei Vorbereitungszeiten wie Kontrastmittelzugang legen und Umkleidezeiten nicht mit eingerechnet sind.\" size=\"14\"></text>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "                        <text text=\"Wie läuft die Untersuchung ab?\" size=\"15\"></text>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "                        <text text=\"Sie werden vom CT-Personal auf einem Untersuchungstisch gelagert und in den Untersuchungsring (Gantry) gefahren. Sie stehen ständig unter ärztlicher Beobachtung und sind in Sprechverbindung mit dem Untersuchungspersonal. Während der Untersuchung sollten sie ganz ruhig und entspannt liegen. Sollte eine Dünndarmuntersuchung notwendig sein, erhalten Sie ca. 30-40 Minuten vor der CT eine Trinklösung, was die Abgrenzung des Darmes vom restlichen Gewebe vereinfacht. Bei einer Dickdarmuntersuchung ist es manchmal erforderlich, über ein Darmrohr Luft oder Wasser einzubringen. Für Herzuntersuchungen ist es notwendig, Ihnen ein EKG anzulegen.\" size=\"14\"></text>\n" +
                "                    </row>\n" +
                "                </page>\n" +
                "<page><row><input text=\"Name, Vorname\"></input></row></page><page>\n" +
                "                    <row>\n" +
                "\n" +
                "    <text text=\"Wurde bei Ihnen bereits eine CT/MRT Untersuchung durchgeführt? Wenn ja, wo und welches Organ?\" size=\"15\"></text>\n" +
                "                        </row>\n" +
                "                    <row>\n" +
                "<radiobutton text=\"Ja\"></radiobutton>\n" +
                "                        <radiobutton text=\"Nein\"></radiobutton>\n" +
                "                    </row>\n" +
                "    </page><page>\n" +
                "                    <row>\n" +
                "                        <text text=\"Traten bei früheren Untersuchungen Kontrastmittel-Reaktionen auf z.B.: Hautausschlag, Erbrechen o.Ä.?\" size=\"15\"></text>\n" +
                "                        </row>\n" +
                "                    <row>\n" +
                "<radiobutton text=\"Ja\"></radiobutton>\n" +
                "                        <radiobutton text=\"Nein\"></radiobutton>\n" +
                "                    </row>\n" +
                "                   </page><page>\n" +
                "                    <row>\n" +
                "                        <text text=\"Ist eine Stoffwechsel- (Diabetes) oder Funktionsstörung (Nieren) bekannt? Nehmen sie Metformin ein?\" size=\"15\"></text>\n" +
                "                        </row>\n" +
                "                    <row>\n" +
                "<radiobutton text=\"Ja\"></radiobutton>\n" +
                "                        <radiobutton text=\"Nein\"></radiobutton>\n" +
                "                    </row>\n" +
                "                    </page><page>\n" +
                "                    <row>\n" +
                "                        <text text=\"Besteht eine Schilddrüsenüberfunktion?\" size=\"15\"></text>\n" +
                "                        </row>\n" +
                "                    <row>\n" +
                "<radiobutton text=\"Ja\"></radiobutton>\n" +
                "                        <radiobutton text=\"Nein\"></radiobutton>\n" +
                "                    </row>\n" +
                "                    </page><page>\n" +
                "                    <row>\n" +
                "                        <text text=\"Besteht bei Ihnen eine Allergie oder Überempfindlichkeit gegen Medikamente (Jod, Penicillin etc), Pflaster, Latex, örtliche Beträubungsmittel etc?\" size=\"15\"></text>\n" +
                "                        </row>\n" +
                "                    <row>\n" +
                "<radiobutton text=\"Ja\"></radiobutton>\n" +
                "                        <radiobutton text=\"Nein\"></radiobutton>\n" +
                "                    </row>\n" +
                "                    </page><page>\n" +
                "                    <row>\n" +
                "                        <text text=\"Nehmen Sie regelmäßig Medikamente ein (Antidiabetika, Marcumar, Aspirin, Herzmedikamente etc.)?\" size=\"15\"></text>\n" +
                "                        </row>\n" +
                "                    <row>\n" +
                "<radiobutton text=\"Ja\"></radiobutton>\n" +
                "                        <radiobutton text=\"Nein\"></radiobutton>\n" +
                "                    </row>\n" +
                "                    </page><page>\n" +
                "                    <row>\n" +
                "                        <text text=\"Könnten Sie schwanger sein oder stillen Sie?\" size=\"15\"></text>\n" +
                "                        </row>\n" +
                "                    <row>\n" +
                "<radiobutton text=\"Ja\"></radiobutton>\n" +
                "                        <radiobutton text=\"Nein\"></radiobutton>\n" +
                "                    </row>\n" +
                "                </page><page>\n" +
                "                    <row>\n" +
                "                        <text text=\"Meine Fragen wurden vollständig und verständlich beantwortet, ich willige hiermit in die geplante Untersuchung ein.\" size=\"15\"></text>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "<radiobutton text=\"Ich möchte eine Kopie erhalten\"></radiobutton>\n" +
                "                        <radiobutton text=\"Ich wünsche keine Kopie\"></radiobutton>\n" +
                "                    </row>\n" +
                "\n" +
                "                </page>\n" +
                "</formData></root>";

        given().
                param("tabletID", "3").
                when().
                post("http://127.0.0.1/index.php/formula").
                then().
                assertThat().
                body(equalTo(expected));
    }


}
