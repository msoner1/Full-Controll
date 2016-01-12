<?php

$incoming_token = $_GET['client_token'];
$incoming_connect_id = $_GET['connect_id'];
$incoming_request_key = key($_GET);
$incoming_request = intval($_GET[$incoming_request_key]);


require_once("db.php");
$sql_sorgu = $db->prepare("SELECT client_token FROM phone_requests WHERE connect_id = ?");
$sql_sorgu->execute(array($incoming_connect_id));
$row = $sql_sorgu->fetch();
if($row['client_token'] == $incoming_token){
    if($incoming_request == 1 || $incoming_request == 0){}
    else{echo 'hata';die();}
}
else{echo 'hata';die();}

switch ($incoming_request_key) {

    case 'get_pc_id':
        $sql_sorgu = $db->prepare("SELECT computer_id FROM computers WHERE connect_id = ?");
        $sql_sorgu->execute(array($incoming_connect_id));
        $row = $sql_sorgu->fetch();
        echo $row['computer_id'];
        break;
    case 'get_pc_name':
        $sql_sorgu = $db->prepare("SELECT computer_name FROM computers WHERE connect_id = ?");
        $sql_sorgu->execute(array($incoming_connect_id));
        $row = $sql_sorgu->fetch();
        echo $row['computer_name'];
        break;
    case 'have_any_request': //telefon tarafından gönderilir herhangi bir istek olup olamadığını kontrol eder.
        $sql_sorgu = $db->prepare("SELECT any_request FROM phone_requests WHERE connect_id = ?");
        $sql_sorgu->execute(array($incoming_connect_id));
        $row = $sql_sorgu->fetch();
        echo $row['any_request'];
        break;


    default:
        echo 'hata';
        break;
}