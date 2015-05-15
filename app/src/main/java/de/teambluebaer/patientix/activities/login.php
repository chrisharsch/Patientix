<?php
require 'Slim-2.6.2/Slim/Slim.php';
\Slim\Slim::registerAutoloader();
$app = new \Slim\Slim();
 $db = new mysqli("localhost", "root", "", "UMMMobilePatients");


$app->get('/hello/:name', function ($name) {
    echo "Hello, $name";
});

$app->post('/login', function () use ($app, $db){
    $name = $_POST["userName"];
    $PW = $_POST["userPW"];

    $result = $db->query("SELECT userID FROM Users WHERE userName = '".$name."' AND userPW = '".$PW."'");
   
    if($result->num_rows == 1) {
        $app->response->setStatus(200);
        echo json_encode('Login successful');
    }
    else {
        $app->response->setStatus(401);
        echo json_encode('Login unsuccessful');
    }
  
});
$app->run();
?>
