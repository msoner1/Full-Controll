<?php

$incoming_token = $_GET['client_token'];
$incoming_computer_id = $_GET['computer_id'];
$incoming_request_key = key($_GET);
$incoming_request = intval($_GET[$incoming_request_key]);


    require_once("db.php");
    $sql_sorgu = $db->prepare("SELECT client_token FROM computer_requests WHERE computer_id = ?");
    $sql_sorgu->execute(array($incoming_computer_id));
    $row = $sql_sorgu->fetch();
    if($row['client_token'] == $incoming_token){
        if($incoming_request == 1 || $incoming_request == 0){}
        else{echo 'hata';die();}
    }
    else{echo 'hata';die();}

     require_once("sample_requests.php");