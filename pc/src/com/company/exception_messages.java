package com.company;


import javax.swing.JOptionPane;

public class exception_messages {

    public static void show_message(String title,String message){

        if(server_requests.get_mode().equals("spy") && !(title.equals("New Message") || title.equals("Yeni Mesaj"))){}
        else {
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
