package com.company;

import java.net.MalformedURLException;

/**
 * Servera iletilen tüm istekler burada yer alacak.
 */
public class outgoing_requests {

    private String pc_request_php_file_name;
    private String pc_open_request;

    public outgoing_requests(){
        pc_request_php_file_name = "pc_requests.php";
        pc_open_request = "pc_status=1";
    }

    public void pc_is_open() throws MalformedURLException, InterruptedException {
        server_requests.http_get_request(pc_request_php_file_name,pc_open_request);
    }
    
}
