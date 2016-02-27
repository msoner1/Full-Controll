package com.coretekno.app.fullcontrol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ironsource.mobilcore.AdUnitEventListener;
import com.ironsource.mobilcore.MobileCore;

import org.apache.http.HttpException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Nullable @Bind(R.id.pc_name) TextView pc_name;
    @Nullable @Bind(R.id.pc_status) TextView pc_status;
    @Nullable @Bind(R.id.ram_value) TextView ram_value;
    @Nullable @Bind(R.id.cpu_value) TextView cpu_value;
    @Nullable @Bind(R.id.temp_value) TextView temp_value;
    @NonNull @Bind(R.id.user_name) TextView user_name;
    @NonNull @Bind(R.id.user_connect_id) TextView user_connect_id;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.fab) FloatingActionButton fab;

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.system_info);
        setSupportActionBar(toolbar);

        user_name.setText(read_xml.get_user_name());
        user_connect_id.setText("Connect_id : " + read_xml.get_connect_id());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.app_name,R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        callAsynchronousTask();
        refresh refresh = new refresh();
        refresh.equals("null");


        startService(new Intent(this,full_service.class));

        MobileCore.showInterstitial(this, MobileCore.AD_UNIT_TRIGGER.APP_START, null);
    }
    @OnClick(R.id.power_click)
    public void power_click(View view){
        if(pc_status.getText().equals("OFF")){
            Snackbar.make(view, R.string.already_closed, Snackbar.LENGTH_LONG)
                    .show();
        }
        else {
            request request = new request();
            request.execute("closing_request=1");
            Snackbar.make(view, R.string.shutting_down, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @OnClick(R.id.fab)
    public void fab_click(View view){
        if(pc_status.getText().equals("OFF")){
            Snackbar.make(view, R.string.already_closed, Snackbar.LENGTH_LONG)
                    .show();
        }
        else {
            Snackbar.make(view, R.string.fab_click, Snackbar.LENGTH_LONG)
                    .show();
            refresh refresh = new refresh();
            refresh.execute("fab_click");
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cam_shot) {
            Intent intent = new Intent(this,Image_view.class);
            intent.putExtra("which_action","cam_shot");
            startActivity(intent);
            timer.cancel();
            timer.purge();
            finish();
        } else if (id == R.id.ss) {
            Intent intent = new Intent(this,Image_view.class);
            intent.putExtra("which_action","ss");
            startActivity(intent);
            timer.cancel();
            timer.purge();
            finish();

        } else if (id == R.id.voice_send) {
            Intent intent = new Intent(this,voice_send.class);
            startActivity(intent);
            timer.cancel();
            timer.purge();
            finish();

        } else if (id == R.id.admin) {
            Intent intent = new Intent(this,admin.class);
            startActivity(intent);
            timer.cancel();
            timer.purge();
            finish();

        } else if (id == R.id.cmd) {
            Intent intent = new Intent(this,cmd.class);
            startActivity(intent);
            timer.cancel();
            timer.purge();
            finish();

        } else if (id == R.id.settings) {
            Intent intent = new Intent(this,Settings_and_contribute.class);
            intent.putExtra("which_action","settings");
            startActivity(intent);
            timer.cancel();
            timer.purge();
            finish();

        } else if (id == R.id.contribute) {
            Intent intent = new Intent(this,Settings_and_contribute.class);
            intent.putExtra("which_action","contribute");
            startActivity(intent);
            timer.cancel();
            timer.purge();
            finish();

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void callAsynchronousTask() {
        final Handler handler = new Handler();

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            refresh refresh = new refresh();
                            refresh.execute("null");
                        } catch (Exception e) {

                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 30000);
    }

    protected class refresh extends AsyncTask<String,String,String> {

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String[] params) {

            String response = "0";
            try {
                if (params[0].equals("fab_click")) {
                    server_requests.http_get_request("phone_requests.php", "usage_values_request=1&computer_id=" + server_requests.get_active_pc_id());
                    Thread.sleep(2000);
                }
                response = server_requests.http_get_request("phone_requests.php", "get_pc_status=1&computer_id=" + server_requests.get_active_pc_id());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (response.equals("1")) {
                return server_requests.get_pc_values();
            } else {
                return "0";
            }


        }

        protected void onPostExecute(String response) {

                pc_name.setText(server_requests.get_active_pc_name());


            if (response.equals("0")) {
                pc_status.setText("OFF");
                ram_value.setText("...");
                cpu_value.setText("...");
                temp_value.setText("...℃");
                pc_status.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.power_off));
            } else {
                String[] values = new String[3];
                try {
                    values = read_xml.parse(response);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                pc_status.setText("ON");
                ram_value.setText(values[1]);
                cpu_value.setText(values[0]);
                temp_value.setText(values[2] + "℃");
                pc_status.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.power_on));
            }

        }
    }

        protected class request extends AsyncTask<String,String,String>{

            protected void onPreExecute(){

            }
            @Override
            protected String doInBackground(String[] params) {
                String response = server_requests.http_get_request("phone_requests.php", params[0]+"&computer_id=" + server_requests.get_active_pc_id());

                return response;
            }
            protected void onPostExecute(String response) {

                    pc_status.setText("OFF");
                    ram_value.setText("...");
                    cpu_value.setText("...");
                    temp_value.setText("...℃");
                    pc_status.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.power_off));

            }
        }
}
