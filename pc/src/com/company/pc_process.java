package com.company;

import org.apache.http.HttpException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import com.github.sarxos.webcam.Webcam;

/**
 * İstek gelidkten sonra tüm işlemler burada yapılır.ör: kapatma , ss çekme vb..
 */
public class pc_process extends math{
    private String file_dir = "C:\\users\\"+System.getProperty("user.name")+"\\full_control\\";
    private reading_class read_xml = new reading_class();
    ArrayList<Integer> cpu_values = new ArrayList<Integer>();

    public void pc_shutdown() throws IOException {
        Process p = Runtime.getRuntime().exec("shutdown /f /t 0 /s");
    }
    public String cmd(String command) throws IOException {
        String command_respond = "";
        String while_loop;
        try{
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader command_reader = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            while ((while_loop=command_reader.readLine()) != null) {
                command_respond += while_loop;
            }
            if(command_respond.length() > 1000){command_respond="This is so long...";}
            command_reader.close();

        }
        catch (Exception e){
            command_respond = "Error Command...";
        }
        return command_respond;
    }

    public void take_ss() throws AWTException, IOException, HttpException, URISyntaxException {

        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(image, "jpeg", new File(file_dir+read_xml.get_connect_id()+".jpeg"));
        server_requests.upload_file_to_server(file_dir + read_xml.get_connect_id() +".jpeg" , "image/jpeg",".jpeg"); //connect id adıyla kaydediyoruz.
    }
    public void cam_shot() throws IOException, HttpException, URISyntaxException {

        try {
            Webcam webcam = Webcam.getDefault();
            if(webcam == null){
                server_requests.upload_file_to_server(file_dir + "cam_not.png", "image/png", ".png"); //connect id adıyla kaydediyoruz.
            }
            else {
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();
            BufferedImage img = webcam.getImage();
            webcam.close();
            ImageIO.write(img, "png", new File(file_dir + read_xml.get_connect_id() + ".png"));
            server_requests.upload_file_to_server(file_dir + read_xml.get_connect_id() + ".png", "image/png", ".png"); //connect id adıyla kaydediyoruz.
            }
        }
        catch (Exception e){

        }

    }
    public void usage_values(){
        String cpu_value;
        String ram_value;
        String temp_value;

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
            Random r = new Random();
            cpu_values.add(Integer.parseInt(cpu_value));

            if(cpu_values.size()>5){
                int avg = 0;
                for(int i = 1; i < 6; i++){
                    avg += cpu_values.get(cpu_values.size()-i);
                }
                avg /= 5;
                if(avg<48){
                    avg = 45 + r.nextInt(15);
                }
                temp_value=Integer.toString(avg);

            }
            else {
                temp_value = Integer.toString(30 + r.nextInt(8)+r.nextInt(8));
            }

            /////////////////////////////////////////////////////////////////////////////////////////////

            String usage_values_content = "<usage_values>\n" +
                    "\t<cpu>%"+cpu_value+"</cpu>\n" +
                    "\t<ram>%"+ram_value+"</ram>\n" +
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
    public void message(String message){
        exception_messages.show_message(Set_strings.get_value("new_message"),message);
    }
    public void record_play() throws IOException {
        server_requests.download_3gp(file_dir+"record.3gp");
        JukeBox.play_record(file_dir+"record.3gp");

    }

    public void horn(int status){
        if(status == 1) {
            if(!JukeBox.isPlaying("horn")){
                JukeBox.loop("horn");
            }
        }
        else {
            JukeBox.stop("horn");
        }
    }
    public void pc_lock() throws IOException {
        Process p = Runtime.getRuntime().exec("rundll32.exe user32.dll, LockWorkStation");
    }
    public void pc_reboot() throws IOException {
        Process p = Runtime.getRuntime().exec("shutdown /f /t 0 /r");

    }
    public void mouse_lock(Boolean lock_value) {

        if(lock_value){
            Random r = new Random();
            try {
                new Robot().mouseMove(r.nextInt(700), r.nextInt(500));
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }


    }

    public void pc_delete() throws IOException {
        File config = new File("config.xml");
        if(config.exists()){
            config.delete();
        }

        System.exit(0);

    }
}
