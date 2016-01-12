<?php

$incoming_token = $_GET['client_token'];
$incoming_connect_id = $_GET['connect_id'];
if(isset($_GET['computer_id'])){$incoming_computer_id = $_GET['computer_id'];}


if(strlen($incoming_connect_id)==6 && strlen($incoming_token)==11){

    $new_token = $incoming_token.substr($incoming_connect_id,-3);

    require_once("db.php");

        $sql_sorgu = $db->prepare("SELECT connect_id FROM users WHERE connect_id =?");
        $sql_sorgu->execute(array($incoming_connect_id));
        $row = $sql_sorgu->fetch();

        if($row['connect_id'] == $incoming_connect_id){
            if(isset($_GET['phone'])){
                $sql_sorgu = $db->prepare("UPDATE phone_requests SET client_token = ? WHERE connect_id=?");
                $sql_sorgu ->execute(array($new_token,$incoming_connect_id));
            }
            else{
                $sql_sorgu = $db->prepare("UPDATE computer_requests SET client_token = ? WHERE computer_id=?");
                $sql_sorgu ->execute(array($new_token,$incoming_computer_id));
            }
            echo $new_token;

        }

        else{
            echo 'hata';

        }


}
else{
    echo 'hata';}