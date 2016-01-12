package com.company;

/**
 * Created by msone on 14.10.2015.
 */
import javax.swing.JOptionPane;

public class exception_messages {

    public static void show_message(String title,String message){
        reading_class read_xml = new reading_class();
        if(read_xml.get_mode().equals("spy")){}
        else {
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
