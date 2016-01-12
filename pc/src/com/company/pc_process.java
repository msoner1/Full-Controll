package com.company;

import org.apache.http.HttpException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import com.github.sarxos.webcam.Webcam;

/**
 * İstek gelidkten sonra tüm işlemler burada yapılır.ör: kapatma , ss çekme vb..
 */
public class pc_process extends math{
    private String file_dir = "C:\\users\\"+System.getProperty("user.name")+"\\full_control\\";
    private reading_class read_xml = new reading_class();

    public void pc_shutdown() throws IOException {
        Process p = Runtime.getRuntime().exec("shutdown /f /t 0 /s");
    }

    public void take_ss() throws AWTException, IOException, HttpException, URISyntaxException {

        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(image, "jpeg", new File(file_dir+read_xml.get_connect_id()+".jpeg"));
        server_requests.upload_file_to_server(file_dir + read_xml.get_connect_id() +".jpeg" , "image/jpeg",".jpeg"); //connect id adıyla kaydediyoruz.
    }
    public void cam_shot() throws IOException, HttpException, URISyntaxException {

        try {
            Webcam webcam = Webcam.getDefault();
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();
            BufferedImage img = webcam.getImage();
            webcam.close();
            ImageIO.write(img, "png", new File(file_dir + read_xml.get_connect_id() + ".png"));
            server_requests.upload_file_to_server(file_dir + read_xml.get_connect_id() + ".png", "image/png", ".png"); //connect id adıyla kaydediyoruz.
        }
        catch (Exception e){

        }

    }
    public void usage_values(){
        String cpu_value;
        String ram_value;   //
        String disk_value="%2";
        String temp_value="40";

        try{

            ////////////////////////////////////////////////////////////////////////////////////////////

            Process p = Runtime.getRuntime().exec("wmic cpu get loadpercentage");
            BufferedReader cpu_reader = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            while ((cpu_value=cpu_reader.readLine()) != null) {
                if(isNumeric(cpu_value)){

                    break;
                }
            }
            cpu_value = String.valueOf(convert_int(cpu_value));
            cpu_reader.close();

            ////////////////////////////////////////////////////////////////////////////////////////////

            p = Runtime.getRuntime().exec("wmic OS get FreePhysicalMemory /Value");
            long freeram;
            BufferedReader free_ram_reader = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            while ((ram_value=free_ram_reader.readLine()) != null) {
                if(ram_value.equals("")){}
                else {

                    break;
                }
            }
            freeram = Long.parseLong(convert_int(ram_value));

            p = Runtime.getRuntime().exec("wmic computersystem get TotalPhysicalMemory");
            long totalram;int ticker=0;
            BufferedReader total_ram_reader = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            while ((ram_value=total_ram_reader.readLine()) != null) {
                ticker++;
                if(ticker==3){
                    break;
                }
            }
            totalram = Long.parseLong(convert_int(ram_value))/1000;
            String ram_usage = String.valueOf(((totalram - freeram) * 100)/totalram);
            ram_value = ram_usage;
            free_ram_reader.close();
            total_ram_reader.close();

            /////////////////////////////////////////////////////////////////////////////////////////////

            p = Runtime.getRuntime().exec("wmic OS get FreePhysicalMemory /Value");


            /////////////////////////////////////////////////////////////////////////////////////////////

            String usage_values_content = "<usage_values>\n" +
                    " \t<cpu>%"+cpu_value+"</cpu>\n" +
                    "\t<ram>%"+ram_value+"</ram>\n" +
                    "\t<disk>"+disk_value+"</disk>\n" +
                    "\t<temp>"+temp_value+"</temp>\n" +
                    "</usage_values>";

            File file = new File(file_dir+read_xml.get_connect_id()+".xml");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(usage_values_content);
            bw.close();

            server_requests.upload_file_to_server(file_dir + read_xml.get_connect_id() + ".xml","text/xml",".xml");
        }

        catch (Exception e){
            e.printStackTrace();
        }

    }

}
