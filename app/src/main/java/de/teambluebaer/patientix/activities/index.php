<?php
require 'Slim-2.6.2/Slim/Slim.php';
\Slim\Slim::registerAutoloader();
$app = new \Slim\Slim();
#$db = new mysqli("localhost", "root", "", "UMMMobilePatients");
#$db->set_charset("utf-8");

$db = mysqli_init();
$db->options(MYSLI_INIT_COMMAND, 'SET NAMES \'utf8\'');
$db->real_connect("localhost", "root", "", "UMMMobilePatients");




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

$app->post('/login', function () use ($app, $db){
    if(isset($_POST["userName"]) && !empty($_POST["userName"]) && isset($_POST["userPW"]) && !empty($_POST["userPW"])) {
        $name = $_POST["userName"];
        $PW = $_POST["userPW"];

        $result = $db->query("SELECT userID FROM Users WHERE userName = '".$name."' AND userPW = '".$PW."'");
       
        if($result->num_rows == 1) {
            $app->response->setStatus(200);
            echo json_encode('Login successful');
        }else {
            $app->response->setStatus(401);
            echo json_encode('Login unsuccessful');
        }
        
    }else {
        $app->response->setStatus(401);
        echo json_encode('Login unsuccessful');
    }
});
$app->post('/filledformula', function() use ($app,$db){
    //TODO

});

$app->run();
?>
