<?php
require 'Slim-2.6.2/Slim/Slim.php';
\Slim\Slim::registerAutoloader();
$app = new \Slim\Slim();
$db = new mysqli("localhost", "root", "", "UMMMobilePatients");
$db->set_charset("utf8");


$app->post('/formula', function () use ($app, $db) {

    if (isset($_POST["tabletID"]) && !empty($_POST["tabletID"])) {
        $tabletID = $_POST["tabletID"];


        #Get FormID for this tablet
        $result = $db->query("SELECT formID FROM ScheduledPatients WHERE tabletID = '$tabletID'");
        $formID = $result->fetch_assoc()["formID"];

        $resultTwo = $db->query("SELECT patientID FROM ScheduledResigningPatients WHERE tabletID = '$tabletID'");

        if ($result->num_rows == 1) {

            $app->response->write("<root><meta resign='0'>");

            #Get PatientID for this tablet
            $result = $db->query("SELECT patientID FROM ScheduledPatients WHERE tabletID = '$tabletID'");
            $patientID = $result->fetch_assoc()["patientID"];
            $app->response->write('<pID>' . $patientID . '</pID>');

            #Get Extern PatientID
            $result = $db->query("SELECT patientIDExt FROM Patients WHERE patientID = '$patientID'");
            $writing = $result->fetch_assoc()["patientIDExt"];
            $app->response->write('<pExamID>' . $writing . '</pExamID>');

            #Get First Name of Patient
            $result = $db->query("SELECT patientFirstName FROM Patients WHERE patientID = '$patientID'");
            $writing = $result->fetch_assoc()["patientFirstName"];
            $app->response->write('<pFirstName>' . $writing . '</pFirstName>');

            #Get Last Name of Patient
            $result = $db->query("SELECT patientName FROM Patients WHERE patientID = '$patientID'");
            $writing = $result->fetch_assoc()["patientName"];
            $app->response->write('<pLastName>' . $writing . '</pLastName>');

            #Get birth date of Patient
            $result = $db->query("SELECT birthDate FROM Patients WHERE patientID = '$patientID'");
            $writing = $result->fetch_assoc()["birthDate"];
            $app->response->write('<pDate>' . $writing . '</pDate>');

            #Get Formname for this Tabelt
            $result = $db->query("SELECT formName FROM Forms WHERE formID = $formID");
            $writing = $result->fetch_assoc()["formName"];
            $app->response->write('<name>' . $writing . '</name></meta><form>');

            $result = $db->query("SELECT XML FROM Pages WHERE formID = $formID");

            $app->response->setStatus(200);
            while ($row = $result->fetch_assoc()) {
                $app->response->write($row["XML"]);

            }
            $app->response->write("</form></root>");
            $app->response->getBody();

        } else if ($resultTwo->num_rows == 1) {

            $resultTwo = $db->query("SELECT XML FROM ScheduledResigningPatients WHERE tabletID = $tabletID");

            $app->response->setStatus(200);
            while ($row = $resultTwo->fetch_assoc()) {
                $app->response->write($row["XML"]);

            }
            $app->response->getBody();

        } else {
            $app->response->setStatus(404);
            echo 'No data found';
        }
    } else {
        $app->response->setStatus(400);
        echo 'Wrong parameters';
    }
});

$app->post('/filledformula', function () use ($app, $db) {
    if (isset($_POST["formula"]) && !empty($_POST["formula"]) && isset($_POST["macaddress"]) && !empty($_POST["macaddress"]) && isset($_POST["patientID"]) && !empty($_POST["patientID"])) {
        $formula = utf8_encode($_POST["formula"]);
        $macaddress = $_POST["macaddress"];
        $patientID = $_POST["patientID"];


        $result = $db->query("SELECT formID FROM ScheduledPatients WHERE patientID = '" . $patientID . "'");

        if ($result->num_rows == 1) {

            $formID = $result->fetch_assoc()["formID"];
            $result = $db->query("SELECT tabletID FROM Tablets WHERE MACAddress = '" . $macaddress . "'");

            $resultTwo = $db->query("SELECT sectionID FROM ScheduledPatients WHERE patientID = '" . $patientID . "'");
            if ($resultTwo->num_rows == 1) {
                $sectionID = $resultTwo->fetch_assoc()["sectionID"];

                $formula = trim($formula);
                $formula = addslashes($formula);

                if ($result->num_rows == 1) {

                    $db->query("INSERT INTO MTRADocuments(formID, XML, patientID, sectionID) VALUES ('$formID','$formula','$patientID','$sectionID');");
                    $app->response->setStatus(200);
                    echo 'Formula saved';
                } else {
                    $app->response->setStatus(400);
                    echo 'Wrong MACAddress';
                }
            } else {
                $app->response->setStatus(404);
                echo 'No Patient Data';
            }
        
        } else {
            $app->response->setStatus(404);
            echo 'Patient do not exists';
        }
    } else {
        $app->response->setStatus(400);
        echo 'Wrong parameters received';
    }
});

$app->post('/getTabletID', function () use ($app, $db) {
    if (isset($_POST["macAddress"]) && !empty($_POST["macAddress"])) {
        $macAddress = $_POST["macAddress"];
        $result = $db->query("SELECT tabletID FROM Tablets WHERE MACAddress = '" . $macAddress . "'");
        if ($result->num_rows == 1) {
            $writing = $result->fetch_assoc()["tabletID"];
            $app->response->write($writing);
        } else {
            $app->response->setStatus(404);
            echo 'Tablet not found';
        }
    } else {
        $app->response->setStatus(400);
        echo 'Wrong parameters received';
    }

});

$app->run();
?>
