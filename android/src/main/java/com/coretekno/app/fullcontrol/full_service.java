package com.coretekno.app.fullcontrol;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class full_service extends Service {

    private MyThread mythread;
    public boolean isRunning = false;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mythread  = new MyThread();
    }

    @Override
    public synchronized void onDestroy() {
        super.onDestroy();
        if(!isRunning){
            mythread.interrupt();
        }
    }

    @Override
    public synchronized void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if(!isRunning){
            mythread.start();
            isRunning = true;
        }
    }

    private static DefaultHttpClient getThreadSafeClient()  {

        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,

                mgr.getSchemeRegistry()), params);
        return client;
    }

    public String http_get_request(String request_url,String requests){
        URL new_request_url = null;
        String incoming_response = null;
        try {
            new_request_url = new URL("http://46.101.231.241/"+request_url+"?"+requests +"&connect_id="+read_xml.get_connect_id());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DefaultHttpClient myClient = null;
        try {
            HttpResponse response;
            myClient = getThreadSafeClient();
            HttpPost myConnection = null;
            myConnection = new HttpPost(String.valueOf(new_request_url));
            response = myClient.execute(myConnection);
            incoming_response = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        catch (Exception e){

            incoming_response = "error";
        }
        myClient.getConnectionManager().shutdown();
        return incoming_response;
    }

    public void readWebPage(){

        String a = http_get_request("no_security.php", "get_pc_list=1");
        String[] parts = a.split("_");

        for(int i = 0; i<parts.length-1;i+=2){

            if(http_get_request("no_security.php", "get_pc_is_open=1&computer_id=" + parts[i]).equals("1")){
                http_get_request("no_security.php", "pc_is_open=0&computer_id=" + parts[i]);

                String system_name =parts[i+1];

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.power)
                        .setLights(Color.RED, 3000, 3000)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setContentTitle("Sisteminiz Açıldı")
                        .setContentText(system_name + " açıldı.");
                Intent target = new Intent(this,MainActivity.class);
                PendingIntent content  = PendingIntent.getActivity(this, 0, target, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(content);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(i, builder.build());
            }

        }

    }

    class MyThread extends Thread{
        @Override
        public void run(){
            while(isRunning){
                try {
                    readWebPage();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    isRunning = false;
                    e.printStackTrace();
                }
            }
        }

    }
}
