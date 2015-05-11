<?php

if(isset($_GET["userName"] && !empty($_GET["userName"] && isset($_GET["userPW"]) && !empty($_GET["userPW"])) {
    $db = new mysqli("localhost", "root", "", "UMMMobilePatients");
    $name = $_GET["userName"];
    $PW = $_GET["userPW"];
    $result = db->query("SELECT userID FROM Users WHERE userName = '$name' AND userPW = $'PW'");
    if($result->num_rows == 1) {
        echo "200";
    }
    else {
        echo "400";
    }
}

?>


