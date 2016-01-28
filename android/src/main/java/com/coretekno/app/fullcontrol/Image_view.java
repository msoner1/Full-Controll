package com.coretekno.app.fullcontrol;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import org.apache.http.HttpException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Image_view extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Nullable @Bind(R.id.pc_name) TextView pc_name;
    @NonNull @Bind(R.id.user_name) TextView user_name;
    @NonNull @Bind(R.id.user_connect_id) TextView user_connect_id;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.imageView_) ImageView image;
    @Bind(R.id.imageView_pc) ImageView pc_image;
    @Bind(R.id.nav_view) NavigationView navigationView;

    private String img_url = null;
    private Bitmap mybitmap = null;
    Intent intent_incoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        intent_incoming=getIntent();

        if(Locale.getDefault().getLanguage().equals("tr")){
            pc_image.setImageResource(R.drawable.desktop_border);
        }
        else {
            pc_image.setImageResource(R.drawable.desktop_border_en);
        }

        if(intent_incoming.getStringExtra("which_action").equals("ss")){
            img_url = server_requests.web_url + "user_files/" + read_xml.get_connect_id()+".jpeg";
            toolbar.setTitle(R.string.ss);
            callAsynchronousTask("ss_request=1");
        }
        else {
            img_url = server_requests.web_url + "user_files/" + read_xml.get_connect_id()+".png";
            toolbar.setTitle(R.string.cam);
            callAsynchronousTask("cam_shot_request=1");
        }
        setSupportActionBar(toolbar);
        try {
            pc_name.setText(server_requests.get_active_pc_name());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        user_name.setText(read_xml.get_user_name());
        user_connect_id.setText("Connect_id : "+read_xml.get_connect_id());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.app_name,R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }
    @OnClick(R.id.imageView_)public void image_click(View view){
        Intent intent = new Intent(this,Image_web_view.class);
        intent.putExtra("url", img_url);
        intent.putExtra("which_action", intent_incoming.getStringExtra("which_action"));
        startActivity(intent);
    }

    Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        Random r = new Random();
                        int ramdom_value = 100000+r.nextInt(10000);
                        FileOutputStream stream = new FileOutputStream(String.valueOf(Environment.getExternalStorageDirectory())+File.separator+"fullControl"+File.separator+ramdom_value+".jpeg");

                        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                        mybitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                        byte[] byteArray = outstream.toByteArray();

                        stream.write(byteArray);
                        stream.close();

                        Toast.makeText(getApplicationContext(), "Downloading Completed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + String.valueOf(Environment.getExternalStorageDirectory()) + File.separator + "fullControl/" + ramdom_value + ".jpeg"), "image/*");
                        startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    break;
                default:
                    break;
            }
        }
    };

    @OnClick(R.id.btn_download)public void download_image(){
        Toast.makeText(getApplicationContext(),"Downloading...Please Wait",Toast.LENGTH_LONG).show();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(String.valueOf(Environment.getExternalStorageDirectory())+File.separator+"fullControl");
            file.mkdir();
                download download = new download();
                download.execute(img_url);
        }
        else {
            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.pc_values) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.ss) {
            Intent intent = new Intent(this,Image_view.class);
            intent.putExtra("which_action","ss");
            startActivity(intent);
            finish();

        } else if (id == R.id.cam_shot) {
            Intent intent = new Intent(this,Image_view.class);
            intent.putExtra("which_action","cam_shot");
            startActivity(intent);
            finish();

        }  else if (id == R.id.voice_send) {

        } else if (id == R.id.admin) {

        } else if (id == R.id.cmd) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.contribute) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void callAsynchronousTask(final String rq) {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            request refresh = new request();
                            refresh.execute(rq);
                        } catch (Exception e) {

                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000);
    }
    protected class request extends AsyncTask<String,String,String> {

        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String[] params) {
            String response = null;
            try {
                response = server_requests.http_get_request("phone_requests.php", "get_pc_status=1&computer_id=" + server_requests.get_active_pc_id());
                if(response.equals("1")){
                    server_requests.http_get_request("phone_requests.php", params[0]+"&computer_id=" + server_requests.get_active_pc_id());
                    return "00";
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "null";
        }
        protected void onPostExecute(String response) {
            if(response.equals("00")) {
                Picasso.with(getApplicationContext()).load(img_url).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(image);
            }
            else {
                pc_image.setImageResource(R.drawable.desktop_border_off);
            }
        }
    }

    protected class download extends AsyncTask<String,Bitmap,Bitmap>{

        @Override
        protected Bitmap doInBackground(String[] params) {
            Bitmap myBitmap = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);

            }
            catch (Exception e){

            }
            return myBitmap;

        }

        protected void onPostExecute(Bitmap resp){
            mybitmap = resp;
            myHandler.sendEmptyMessage(0);
        }
    }
}
