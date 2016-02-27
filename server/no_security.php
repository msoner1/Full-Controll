<?php

$incoming_connect_id = $_GET['connect_id'];
if(isset($_GET['computer_id'])){$incoming_computer_id = $_GET['computer_id'];}
$incoming_request_key = key($_GET);
$incoming_request = $_GET[$incoming_request_key];

require_once("db.php");
switch ($incoming_request_key) {

        case 'get_pc_list':
        $sql_sorgu = $db->prepare("SELECT computer_id,computer_name FROM computers WHERE connect_id = ? LIMIT 10");
        $sql_sorgu->execute(array($incoming_connect_id));
        $row = $sql_sorgu->fetchAll();
        foreach($row as $n_row){
            echo $n_row['computer_id']."_".$n_row['computer_name']."_";
        }
        break;
        case 'pc_is_open':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET pc_is_open=? WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        echo "deneme";
        break;
        case 'get_pc_is_open':
        $sql_sorgu = $db->prepare("SELECT pc_is_open FROM computer_requests WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row["pc_is_open"];
        break;

}
