<?php

$incomming_user_name = $_POST['user_name'];
$incomming_user_email = $_POST['email'];
$incomming_phone_model = $_POST['phone_brand'];


if(!empty($incomming_user_name) && !empty($incomming_user_email)){

    $incomming_user_email=str_replace(" ","",$incomming_user_email);$incomming_user_name=str_replace(" ","",$incomming_user_name);
    if(!filter_var($incomming_user_email, FILTER_VALIDATE_EMAIL)){
        echo "hata_gecersiz_email";die();
    }

    require_once("db.php");
    $connect_id = create_connect_id();

    $sql_sorgu = $db->prepare("SELECT user_email FROM users WHERE user_email =?");
    $sql_sorgu->execute(array($incomming_user_email));
    $row = $sql_sorgu->fetch();
    if(!empty($row)){echo 'hata_email';die();}

        $sql_sorgu = $db->prepare("INSERT INTO phones (phone_model,connect_id) VALUES(?,?)");
        $sql_sorgu->execute(array($incomming_phone_model, $connect_id));

        $sql_sorgu = $db->prepare("INSERT INTO phone_requests (connect_id) VALUES(?)");
        $sql_sorgu->execute(array($connect_id));

        $sql_sorgu = $db->prepare("INSERT INTO users (user_name,user_email,user_level,connect_id) VALUES(?,?,?,?)");
        $sql_sorgu->execute(array($incomming_user_name, $incomming_user_email, 1, $connect_id));

    $out_xml='<app_info>
      <connect_id>'.$connect_id.'</connect_id></app_info>';
    echo $out_xml;

}
else{
    echo 'hata_bos';}

function create_connect_id(){
    global $db;
    $chars = "QWERTYUIOPASDFFGHJKLZXCVBNMqwertyuopilkjhgfdsazxcvbnm123456789";
    $connect_id=substr(str_shuffle(str_repeat($chars,6)),0,6);
    $sql_sorgu = $db->prepare("SELECT connect_id FROM users WHERE connect_id=?");
    $sql_sorgu->execute(array($connect_id));
    $row = $sql_sorgu->fetch();
    if(!empty($row)){
            create_connect_id();
    }
    return $connect_id;
}