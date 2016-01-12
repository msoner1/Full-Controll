<?php
$incoming_token = $_GET['client_token'];
$incoming_computer_id = $_GET['computer_id'];

$dizin = 'user_files/'.$_GET['file_name'];
$yuklenecek_dosya = $dizin;

require_once("db.php");
$sql_sorgu = $db->prepare("SELECT client_token FROM computer_requests WHERE computer_id = ?");
$sql_sorgu->execute(array($incoming_computer_id));
$row = $sql_sorgu->fetch();

if($row['client_token'] == $incoming_token){
    move_uploaded_file($_FILES['userfile']['tmp_name'], $yuklenecek_dosya);
}
else{echo 'hata';die();}


