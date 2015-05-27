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
        $result = $db->query("SELECT formID FROM ScheduledPatients WHERE tabletID = '$tabletID'");
        $formID = $result->fetch_assoc()["formID"];
        $result = $db->query("SELECT XML FROM Pages WHERE formID = $formID");
        if($result->num_rows > 0) {
            $app->response->setStatus(200);
            while($row = $result->fetch_assoc()) {
                $app->response->write($row["XML"]);

            }
            
            $app->response->getBody();
        }
        else {
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
