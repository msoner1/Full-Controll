package com.company;

import org.apache.http.HttpException;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Serverdan dinlenilen değerler burada yer alır.
 */
public class listening_requests {

    private String pc_request_php_file_name;
    private String have_closing_request;
    private String have_ss_request;
    private String have_cam_shot_request;
    private String have_usage_values_request;
    private String have_any_request;
    pc_process process = new pc_process();

    public listening_requests(){
        pc_request_php_file_name = "pc_requests.php";
        have_closing_request = "have_closing_request=1";
        have_ss_request = "have_ss_request=1";
        have_cam_shot_request = "have_cam_shot_request=1";
        have_usage_values_request = "have_usage_values_request=1";
        have_any_request = "have_any_request=1";
    }

    private void closing_listen() throws IOException, InterruptedException {

        int request = Integer.parseInt(server_requests.http_get_request(pc_request_php_file_name, have_closing_request));
        if(request == 1){
            server_requests.http_get_request(pc_request_php_file_name,"pc_status=0");
            server_requests.http_get_request(pc_request_php_file_name,"closing_request=0");
            process.pc_shutdown();
        }
    }
    private void ss_listen() throws IOException, AWTException, HttpException, URISyntaxException, InterruptedException {

        int request = Integer.parseInt(server_requests.http_get_request(pc_request_php_file_name, have_ss_request));
        if(request == 1){
            process.take_ss();
            server_requests.http_get_request(pc_request_php_file_name, "ss_request=0");
        }
    }
    private void cam_shot() throws IOException, HttpException, URISyntaxException, InterruptedException { //webcamden görüntü alacak.

        int request = Integer.parseInt(server_requests.http_get_request(pc_request_php_file_name, have_cam_shot_request));
        if(request == 1){
            process.cam_shot();
            server_requests.http_get_request(pc_request_php_file_name, "cam_shot_request=0");
        }
    }
    private void usage_values() throws IOException, InterruptedException { //pc ram cpu disk ve sıcaklık değerini gönderecek.

        int request = Integer.parseInt(server_requests.http_get_request(pc_request_php_file_name, have_usage_values_request));
        if(request == 1){
            if(System.getProperty("os.name").startsWith("Windows")){ //Only windows
                process.usage_values();
            }
            server_requests.http_get_request(pc_request_php_file_name, "usage_values_request=0");
        }
    }


    public void listen_all() throws IOException, AWTException, HttpException, URISyntaxException, InterruptedException {

        int request = Integer.parseInt(server_requests.http_get_request(pc_request_php_file_name, have_any_request));

        if(request == 1){
            closing_listen();
            ss_listen();
            cam_shot();
            usage_values();


            server_requests.http_get_request(pc_request_php_file_name, "any_request=0");
        }
    }
}
