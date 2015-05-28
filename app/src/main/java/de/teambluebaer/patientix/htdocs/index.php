<?php
require 'Slim-2.6.2/Slim/Slim.php';
\Slim\Slim::registerAutoloader();
$app = new \Slim\Slim();
$db = new mysqli("localhost", "root", "", "UMMMobilePatients");
$db->set_charset("utf8");

#$db = mysqli_init();
#$db->options(MYSLI_INIT_COMMAND, 'SET NAMES \'utf8\'');
#$db->real_connect("localhost", "root", "", "UMMMobilePatients");


$app->post('/formula', function () use ($app, $db){
    
    if(isset($_POST["tabletID"]) && !empty($_POST["tabletID"])) {
        $tabletID = $_POST["tabletID"];


        $app->response->write("<root><metadata>");

        #Get FormID for this tablet
        $result = $db->query("SELECT formID FROM ScheduledPatients WHERE tabletID = '$tabletID'");
        $formID = $result->fetch_assoc()["formID"];
        if($result->num_rows==1){

        #Get PatientID for this tablet
        $result = $db->query("SELECT patientID FROM ScheduledPatients WHERE tabletID = '$tabletID'");
        $patientID = $result->fetch_assoc()["patientID"];
        $app->response->write('<pID>'.$patientID.'</pID>');

        #Get Extern PatientID
        #$result = $db->query("SELECT patientIDExt FROM Patients WHERE patientID = '$patientID'");
        #$writing = $result->fetch_assoc()["patientIDExt"];
        #$app->response->write('<pExamID>'.$writing.'</pExamID>');

        #Get First Name of Patient
        $result = $db->query("SELECT patientFirstName FROM Patients WHERE patientID = '$patientID'");
        $writing = $result->fetch_assoc()["patientFirstName"];
        $app->response->write('<pFirstName>'.$writing.'</pFirstName>');
                   
        #Get Last Name of Patient
        $result = $db->query("SELECT patientName FROM Patients WHERE patientID = '$patientID'");
        $writing = $result->fetch_assoc()["patientName"];
        $app->response->write('<pLastName>'.$writing.'</pLastName>');

        #Get birth date of Patient
        $result = $db->query("SELECT birthDate FROM Patients WHERE patientID = '$patientID'");
        $writing = $result->fetch_assoc()["birthDate"];
        $app->response->write('<pDate>'.$writing.'</pDate>');




        #Get Formname for this Tabelt
        $result = $db->query("SELECT formName FROM Forms WHERE formID = $formID");
        $writing = $result->fetch_assoc()["formName"];
        $app->response->write('<name>'.$writing.'</name></metadata><formData>');

        $result = $db->query("SELECT XML FROM Pages WHERE formID = $formID");
        
            $app->response->setStatus(200);
            while($row = $result->fetch_assoc()) {
                $app->response->write($row["XML"]);

            }
            $app->response->write("</formData></root>");
            $app->response->getBody();
        
    }else{
        $app->response->setStatus(404);
        echo json_encode('No Data found');
    }
}
});

$app->post('/filledformula', function() use ($app, $db){
    if(isset($_POST["isFormula"]) && !empty($_POST["isFormula"]) && isset($_POST["formula"]) && !empty($_POST["formula"]) && isset($_POST["macaddress"]) && !empty($_POST["macaddress"]) && isset($_POST["patientID"]) && !empty($_POST["patientID"])){
        $isFormula = $_POST["isFormula"];
        $formula= utf8_encode($_POST["formula"]);
        $macaddress = $_POST["macaddress"];
        $patientID = $_POST["patientID"];
   
       # string $regDate = date('Y-m-d h:m:s');
        $app->response->write($formula);

        $result = $db->query("SELECT formID FROM ScheduledPatients WHERE patientID = '".$patientID."'");
        $formID = $result->fetch_assoc()["formID"];
        $result = $db->query("SELECT tabletID FROM Tablets WHERE MACAddress = '".$macaddress."'");

        $formula = trim($formula);
        $formula = addslashes($formula);

        if($result->num_rows == 1) {
            if($isFormula == true){
                $db->query("INSERT INTO MTRADocuments(formID, XML, patientID) VALUES ('$formID','$formula','$patientID');");
                $app->response->setStatus(200);
            }else{
                $app->response->setStatus(404);
            }
        }else{
                $app->response->setStatus(405);
        }
    }else{
        $app->response->setStatus(406);
    }
});

$app->run();
?>
