<?php

switch ($incoming_request_key) {
        case 'any_request':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET any_request=?  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;
        case 'have_closing_request': //pc tarafından gönderilir kapatma isteği olup olamadığını kontrol eder.
        $sql_sorgu = $db->prepare("SELECT closing_request FROM computer_requests WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row['closing_request'];
        break;
        case 'have_ss_request': //pc tarafından gönderilir secreenshot isteği olup olamadığını kontrol eder.
        $sql_sorgu = $db->prepare("SELECT ss_request FROM computer_requests WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row['ss_request'];
        break;
        case 'have_cam_shot_request': //pc tarafından gönderilir cam shot isteği olup olamadığını kontrol eder.
        $sql_sorgu = $db->prepare("SELECT cam_shot_request FROM computer_requests WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row['cam_shot_request'];
        break;
        case 'have_usage_values_request': //pc tarafından gönderilir pc değerleri isteği olup olamadığını kontrol eder.
        $sql_sorgu = $db->prepare("SELECT usage_request FROM computer_requests WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row['usage_request'];
        break;
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
        case 'get_pc_status':
        $sql_sorgu = $db->prepare("SELECT pc_status FROM computer_requests WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row['pc_status'];
        break;
        case 'get_switches':
        $sql_sorgu = $db->prepare("SELECT system_on_notif,system_off_notif,system_overload_notif FROM phones WHERE connect_id = ?");
        $sql_sorgu->execute(array($incoming_connect_id));
        $row = $sql_sorgu->fetch();
        echo $row['system_on_notif']."-".$row['system_off_notif']."-".$row['system_overload_notif'];
        break;
        case 'get_pc_list':
        $sql_sorgu = $db->prepare("SELECT computer_id,computer_name FROM computers WHERE connect_id = ? LIMIT 10");
        $sql_sorgu->execute(array($incoming_connect_id));
        $row = $sql_sorgu->fetchAll();
        foreach($row as $n_row){
        echo $n_row['computer_id']."_".$n_row['computer_name']."_";
        }
        break;
        case 'get_admin_switches':
        $sql_sorgu = $db->prepare("SELECT spy_mode FROM users WHERE connect_id = ?");
        $sql_sorgu->execute(array($incoming_connect_id));
        $row = $sql_sorgu->fetch();
        $respond1 = $row['spy_mode'];
        $sql_sorgu = $db->prepare("SELECT mouse_lock_request FROM computer_requests WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $respond1."-".$row['mouse_lock_request'];
        break;
        case 'user_name_update': //user_name_update
        $sql_sorgu = $db->prepare("UPDATE users SET user_name=?  WHERE connect_id=?");
        $sql_sorgu->execute(array($incoming_request,$incoming_connect_id));
        break;
        case 'delete_system':
        $sql_sorgu = $db->prepare("DELETE FROM computer_requests WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_request));
        $sql_sorgu = $db->prepare("DELETE FROM computers WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_request));
        break;
        case 'update_system_on_notif':
        if($incoming_request=="true"){$incoming_request=1;}
        else{$incoming_request=0;}
        $sql_sorgu = $db->prepare("UPDATE phones SET system_on_notif=?  WHERE connect_id=?");
        $sql_sorgu->execute(array($incoming_request,$incoming_connect_id));
        break;
        case 'update_system_off_notif':
        if($incoming_request=="true"){$incoming_request=1;}
        else{$incoming_request=0;}
        $sql_sorgu = $db->prepare("UPDATE phones SET system_off_notif=?  WHERE connect_id=?");
        $sql_sorgu->execute(array($incoming_request,$incoming_connect_id));
        break;
        case 'update_system_overload_notif':
        if($incoming_request=="true"){$incoming_request=1;}
        else{$incoming_request=0;}
        $sql_sorgu = $db->prepare("UPDATE phones SET system_overload_notif=?  WHERE connect_id=?");
        $sql_sorgu->execute(array($incoming_request,$incoming_connect_id));
        break;
        case 'cmd_post':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET cmd_post=? WHERE computer_id = ?");
        $sql_sorgu->execute(array($_POST['cmd'],$incoming_computer_id));
        break;
        case 'cmd_response':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET cmd_response=? WHERE computer_id = ?");
        $sql_sorgu->execute(array($_POST['cmd'],$incoming_computer_id));
        break;
        case 'get_cmd_post':
        $sql_sorgu = $db->prepare("SELECT cmd_post FROM computer_requests WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row["cmd_post"];
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET cmd_post=0  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_computer_id));
        break;
        case 'get_cmd_response':
        $sql_sorgu = $db->prepare("SELECT cmd_response FROM computer_requests WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row["cmd_response"];
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET cmd_response=0  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_computer_id));
        break;
        case 'closing_request': //kapatma isteği oluşturur.
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET closing_request=? , any_request=1  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;
        case 'sleep_request':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET sleep_request=? , any_request=1  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;
        case 'get_sleep_request':
        $sql_sorgu = $db->prepare("SELECT sleep_request FROM computer_requests WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row["sleep_request"];
        break;
        case 'pc_lock_request':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET pc_lock_request=? , any_request=1  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;
        case 'get_pc_lock_request':
        $sql_sorgu = $db->prepare("SELECT pc_lock_request FROM computer_requests WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row["pc_lock_request"];
        break;
        case 'change_spy_mode':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET spy_mode=? WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;
        case 'get_spy_mode':
        $sql_sorgu = $db->prepare("SELECT spy_mode FROM computer_requests WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row["spy_mode"];
        break;
        case 'horn_request':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET horn_request=?  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;
        case 'get_horn_request':
        $sql_sorgu = $db->prepare("SELECT horn_request FROM computer_requests WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row["horn_request"];
        break;
        case 'delete_software':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET delete_software=? , any_request=1  WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;
        case 'message_request':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET message_request=?  WHERE computer_id = ?");
        $sql_sorgu->execute(array($_POST['message'],$incoming_computer_id));
        break;
        case 'get_messages':
        $sql_sorgu = $db->prepare("SELECT message_request FROM computer_requests WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row["message_request"];
        break;
        case 'mouse_lock_request':
        $sql_sorgu = $db->prepare("UPDATE computer_requests SET mouse_lock_request=? WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_request,$incoming_computer_id));
        break;
        case 'get_mouse_lock':
        $sql_sorgu = $db->prepare("SELECT mouse_lock_request FROM computer_requests WHERE computer_id=?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row["mouse_lock_request"];
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

        case 'have_any_request': //pc tarafından gönderilir herhangi bir istek olup olamadığını kontrol eder.
        $sql_sorgu = $db->prepare("SELECT any_request FROM computer_requests WHERE computer_id = ?");
        $sql_sorgu->execute(array($incoming_computer_id));
        $row = $sql_sorgu->fetch();
        echo $row['any_request'];
        break;


        default:
        echo 'hata';
        break;
}