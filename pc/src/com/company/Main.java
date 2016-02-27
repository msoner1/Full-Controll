package com.company;

import org.apache.http.HttpException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

  //Bu program tamamen arkaplanda çalışan gui barındırmayan bir servisdir.

public class Main{
    private static reading_class xml_read = new reading_class();
    private static listening_requests listen = new listening_requests();
    private static outgoing_requests outgoing_requests = new outgoing_requests();
    private static pc_process pc_process = new pc_process();

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException, AWTException, HttpException, URISyntaxException, InterruptedException {

        xml_read.set_strings(xml_read.get_language());  //Tüm stringlerin ayarlanması.
        update_control_and_install.control();  //update kontrolü

        JukeBox.init();
        JukeBox.load("/horn.wav","horn");
        JukeBox.setVolume("horn",+6);

        int usage_values_timer = 0;//pc ram,cpu vb. değerlerinin her 60sn'de bir otomatik olarak gönderrilebilmesi için bu değişkene ihtiyaç var.
        //Döngü burdan başlıyor.

        while (true) {
            outgoing_requests.pc_is_open();
            listen.listen_all();
            listen.listen_all_2();
            listen.mouse_listen();
            if(usage_values_timer==60)
            {
                if(System.getProperty("os.name").startsWith("Windows")){ //Only windows
                    pc_process.usage_values();
                }
                usage_values_timer=0;
            }
            Thread.sleep(500);
            usage_values_timer++;
        }

    }

}
