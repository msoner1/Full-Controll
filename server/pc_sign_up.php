<?php

$incoming_pc_name = $_GET['pc_name'];
$incoming_connect_id = $_GET['connect_id'];

if(!empty($incoming_pc_name)){

    require_once("db.php");

    $sql_sorgu = $db->prepare("SELECT connect_id FROM users WHERE connect_id =?");
    $sql_sorgu->execute(array($incoming_connect_id));
    $row = $sql_sorgu->fetch();
    if(empty($row)){echo 'hata';die();}
    else {

        $sql_sorgu = $db->prepare("INSERT INTO computers (pc_name) VALUES(?,?)");
        $sql_sorgu->execute(array($incoming_phone_model, $connect_id));


        $out_xml = '<app_info>
      <connect_id>' . $connect_id . '</connect_id></app_info>';
        echo $out_xml;

    }
}
else{
    echo 'hata';}