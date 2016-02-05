<?php

$incoming_token = $_GET['client_token'];
$incoming_connect_id = $_GET['connect_id'];
if(isset($_GET['computer_id'])){$incoming_computer_id = $_GET['computer_id'];}
$incoming_request_key = key($_GET);
$incoming_request = $_GET[$incoming_request_key];


require_once("db.php");
$sql_sorgu = $db->prepare("SELECT client_token FROM phone_requests WHERE connect_id = ?");
$sql_sorgu->execute(array($incoming_connect_id));
$row = $sql_sorgu->fetch();
if($row['client_token'] == $incoming_token){

}
else{echo 'hata';die();}

require_once("sample_requests.php");