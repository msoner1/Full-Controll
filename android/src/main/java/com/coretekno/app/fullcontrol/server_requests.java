package com.coretekno.app.fullcontrol;

import android.os.AsyncTask;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Server isteklerini yapma için oluşturulmuştur.
 */
public class server_requests {

    public static String web_url = "your_server_url";
    private static String client_token;
    private static String connect_id;
    private static String active_pc_id;
    private static String active_pc_name;

    public server_requests(String ci) throws IOException, InterruptedException, URISyntaxException, HttpException { //kurucu fonksiyon bu class yaratıldığnda bir bağlantı tokeni yaratır ve bunu daha sonraki isteklerde güvenlik amacıyla kullanımak için saklar
        connect_id = ci;

        String clnt="";
        String random_chars = "QWERTYUIOPASDFFGHJKLZXCVBNMqwertyuopilkjhgfdsazxcvbnm123456789";
        Random r = new Random();
        for (int i= 0;i<11;i++){
            clnt +=random_chars.charAt(r.nextInt(random_chars.length()));
        }
        client_token= clnt;
        do_background do_background = new do_background();
        do_background.execute("token_sign_up.php", "phone=1","client_token");

    }
    private static DefaultHttpClient getThreadSafeClient()  {

        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,

                mgr.getSchemeRegistry()), params);
        return client;
    }

    public static String get_active_pc_name() throws InterruptedException, URISyntaxException, HttpException, IOException {
        return active_pc_name;
    }
    public static String get_active_pc_id() throws InterruptedException, URISyntaxException, HttpException, IOException {
        return active_pc_id;
    }
    public static String http_get_request(String request_url,String requests){
        URL new_request_url = null;
        String incoming_response = null;
        try {
            new_request_url = new URL(web_url+request_url+"?"+requests+"&client_token=" + client_token +"&connect_id=" + connect_id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpResponse response;
        DefaultHttpClient myClient = getThreadSafeClient();
        HttpPost myConnection = null;
        try {
            myConnection = new HttpPost(String.valueOf(new_request_url));
            response = myClient.execute(myConnection);
            incoming_response = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return incoming_response;
    }

    public static String get_pc_values(){
        return http_get_request("user_files/"+connect_id+".xml",null);
    }

    public static String[] sign_up(String user_name,String email,String phone_brand) throws IOException, InterruptedException, SAXException, ParserConfigurationException, URISyntaxException, HttpException {
        URL url =new URL(web_url+"sign_up.php");
        String answer;
            //verileri http post isteğiyle gönderiyoruz...
            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection;
            myConnection = new HttpPost(String.valueOf(url));
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
            nameValuePair.add(new BasicNameValuePair("user_name", user_name));
            nameValuePair.add(new BasicNameValuePair("email", email));
            nameValuePair.add(new BasicNameValuePair("phone_brand", phone_brand));
            myConnection.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
            response = myClient.execute(myConnection);
            answer = EntityUtils.toString(response.getEntity(), "UTF-8");


        String[] responds = read_xml.read_sign_up(answer);
        return responds;
    }

    public class do_background extends AsyncTask<String,String[],String[]> {

        @Override
        protected void onPreExecute(){

        }
        @Override
        protected String[] doInBackground(String[] params){
            return new String[]{http_get_request(params[0],params[1]),params[2]};
        }

        @Override
        protected void onPostExecute(String[] r){
            if(r[1].equals("client_token")){
                client_token = r[0];
                do_background2 dob = new do_background2();
                dob.execute("phone_requests.php", "get_pc_id=1","pc_id");
            }
            else if(r[1].equals("pc_id")){
                active_pc_id = r[0];
                execute("phone_requests.php", "get_pc_name=1","pc_name");
            }
            else if(r[1].equals("pc_name")){
                active_pc_name = r[0];
            }
            else {}


        }

    }

    public class do_background2 extends AsyncTask<String,String[],String[]> {

        @Override
        protected void onPreExecute(){

        }
        @Override
        protected String[] doInBackground(String[] params){
            return new String[]{http_get_request(params[0],params[1]),params[2]};
        }

        @Override
        protected void onPostExecute(String[] r){
            if(r[1].equals("client_token")){
                client_token = r[0];
                execute("phone_requests.php", "get_pc_id=1","pc_id");
            }
            else if(r[1].equals("pc_id")){
                active_pc_id = r[0];
                do_background dob = new do_background();
                dob.execute("phone_requests.php", "get_pc_name=1","pc_name");
            }
            else if(r[1].equals("pc_name")){
                active_pc_name = r[0];
            }
            else {}


        }

    }

}
