<?php

$incoming_token = $_GET['client_token'];
$incoming_connect_id = $_GET['connect_id'];
if(isset($_GET['computer_id'])){$incoming_computer_id = $_GET['computer_id'];}
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
    case 'get_pc_status': //pc on/off durumunu verir
        $sql_sorgu = $db->prepare("SELECT pc_status FROM computer_requests WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row['pc_status'];
        break;
    case 'closing_request': //kapatma isteği oluşturur.
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET closing_request=? , any_request=1  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;

    case 'ss_request': //Secreenshot isteği yapar.
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET ss_request=? , any_request=1  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;
    case 'cam_shot_request': //Camera shot isteği yapar.
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET cam_shot_request=? , any_request=1  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;

    case 'pc_status': //pc tarafından gönderilir 1 ise açıktır.
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET pc_status=?  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));

        break;
    case 'usage_values_request': //pc değerleri için istek yapar(cpu,ram...).
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET usage_request=? , any_request=1  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));

        break;
    case 'any_request':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET any_request=?  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));

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