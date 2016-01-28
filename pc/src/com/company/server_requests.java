package com.company;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

/**
 * Server isteklerini yapma için oluşturulmuştur.
 */
public class server_requests {
    private static String server_url="your_server_url";
    private static String client_token;
    private static String connect_id;
    private static int status = 1;

    public server_requests() throws MalformedURLException, InterruptedException { //kurucu fonksiyon bu class yaratıldığnda bir bağlantı tokeni yaratır ve bunu daha sonraki isteklerde güvenlik amacıyla kullanımak için saklar
        reading_class read_xml = new reading_class();
        connect_id = read_xml.get_connect_id();
        String computer_id = read_xml.get_computer_id();

        String clnt="";
        String random_chars = "QWERTYUIOPASDFFGHJKLZXCVBNMqwertyuopilkjhgfdsazxcvbnm123456789";
        Random r = new Random();
        for (int i= 0;i<11;i++){
            clnt +=random_chars.charAt(r.nextInt(random_chars.length()));
        }
        HttpClient httpclient = getThreadSafeClient();
        client_token= clnt;
        URL url_token_sign_up = new URL(server_url+"token_sign_up.php?client_token="+client_token+"&connect_id="+connect_id+"&computer_id="+computer_id);
        try {
            HttpResponse response = httpclient.execute(new HttpGet(String.valueOf(url_token_sign_up)));
            HttpEntity entity = response.getEntity();
            client_token = EntityUtils.toString(entity, "UTF-8");
            if(client_token.equals("hata")){
                if(status==1){ exception_messages.show_message(Set_strings.get_value("hata"),Set_strings.get_value("config_xml_hatasi"));status++;}
                Thread.sleep(5000);
                new server_requests();
            }
        }
        catch (Exception e){
            if(status==1){exception_messages.show_message(Set_strings.get_value("hata"), Set_strings.get_value("internet_yok"));status++;}
            Thread.sleep(5000);
            new server_requests();
        }
        httpclient.getConnectionManager().shutdown();
    }

    private static DefaultHttpClient getThreadSafeClient()  {

        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,

                mgr.getSchemeRegistry()), params);
        return client;
    }
    public int server_versiyon() throws MalformedURLException, InterruptedException {
        HttpClient httpclient = getThreadSafeClient();
        URL url_server_control = new URL(server_url+"versiyon.php");//Serverdaki surum bilgisine ulaşıcaz.
        int server_versiyon=0;
        try {
            HttpResponse response = httpclient.execute(new HttpGet(String.valueOf(url_server_control)));
            HttpEntity entity = response.getEntity();
            server_versiyon = Integer.parseInt(EntityUtils.toString(entity, "UTF-8"));
        }
        catch (Exception e){
            if(status==1){exception_messages.show_message(Set_strings.get_value("hata"), Set_strings.get_value("internet_yok"));status++;}
            Thread.sleep(5000);
            server_versiyon();
        }
        httpclient.getConnectionManager().shutdown();
        return server_versiyon;
    }

    public static String http_get_request(String php_file_name, String request) throws MalformedURLException, InterruptedException { //herhangi bir http get isteğini yapar.
        reading_class read_xml = new reading_class();
        HttpClient httpclient = getThreadSafeClient();
        URL url = new URL(server_url + php_file_name + "?" + request + "&client_token=" + client_token + "&computer_id=" + read_xml.get_computer_id());
        String answer = null;
        try {
            HttpResponse response = httpclient.execute(new HttpGet(String.valueOf(url)));
            HttpEntity entity = response.getEntity();
            answer = EntityUtils.toString(entity, "UTF-8");
            if (answer.equals("hata")) {
                exception_messages.show_message(Set_strings.get_value("hata"), Set_strings.get_value("beklenmedik"));
                new server_requests();
            }
        } catch (Exception e) {
            if(status == 1){exception_messages.show_message(Set_strings.get_value("hata"), Set_strings.get_value("internet_yok"));status++;}
            e.printStackTrace();
            Thread.sleep(5000);
            http_get_request(php_file_name, request);
        }
        httpclient.getConnectionManager().shutdown();
        return answer;
    }
    public static void upload_file_to_server(String file_dir,String file_type,String file_extension) throws IOException, HttpException, URISyntaxException {
        HttpClient httpclient = getThreadSafeClient();
        reading_class read_xml = new reading_class();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(server_url+"upload.php?file_name="+read_xml.get_connect_id()+file_extension+"&client_token="+client_token+"&computer_id="+read_xml.get_computer_id());
        File file = new File(file_dir);
        MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        ContentBody cbFile = new FileBody(file, file_type);
        mpEntity.addPart("userfile", cbFile);
        httppost.setEntity(mpEntity);
        HttpResponse response = null;
        response = httpclient.execute(httppost);
        httpclient.getConnectionManager().shutdown();

    }


}
