package com.coretekno.app.fullcontrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @NonNull @Bind(R.id.pc_name) TextView pc_name;
    @NonNull @Bind(R.id.user_name) TextView user_name;
    @NonNull @Bind(R.id.user_connect_id) TextView user_connect_id;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.off_click) CardView off_click;
    @Bind(R.id.switch_spy) Switch switch_spy;
    @Bind(R.id.mouse_lock) Switch switch_mouse;
    @Bind(R.id.horn) Switch horn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.admin);
        setSupportActionBar(toolbar);

        request request = new request();
        request.execute("get_admin_switches","1","switches_status");

        user_name.setText(read_xml.get_user_name());
        user_connect_id.setText("Connect_id : "+read_xml.get_connect_id());

        pc_name.setText(server_requests.get_active_pc_name());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.app_name,R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @OnClick(R.id.off_click)public void off_click(View view){
        Snackbar.make(view, R.string.shutting_down, Snackbar.LENGTH_LONG)
                .show();
            request request = new request();
            request.execute("closing_request", "1", "off_click");

    }
    @OnClick(R.id.sleep_click)public void sleep_click(View view){
        Snackbar.make(view, R.string.sleeping, Snackbar.LENGTH_LONG)
                .show();
        request request = new request();
        request.execute("sleep_request", "1", "sleep_click");

    }
    @OnClick(R.id.lock_click)public void lock_click(View view){
        Snackbar.make(view, R.string.locking, Snackbar.LENGTH_LONG)
                .show();
        request request = new request();
        request.execute("pc_lock_request", "1", "lock_click");

    }
    @OnClick(R.id.switch_spy)public void switch_spy(View view){
        String status = null;
        if(switch_spy.isChecked()){
            status="1";
        }
        else{status="0";}
        request request = new request();
        request.execute("change_spy_mode", status, "spy_click");
    }
    @OnClick(R.id.mouse_lock)public void switch_mouse_lock(View view){
        String status = null;
        if(switch_mouse.isChecked()){
            status="1";
        }
        else{status="0";}
        request request = new request();
        request.execute("mouse_lock_request", status, "mouse_lock_click");
    }
    @OnClick(R.id.horn)public void horn_click(View view){
        String status = null;
        if(horn.isChecked()){
            status="1";
        }
        else{status="0";}
        request request = new request();
        request.execute("horn_request", status, "horn_click");
    }
    @OnClick(R.id.delete_software_click)public void delete_click(final View view){
        new AlertDialog.Builder(admin.this)
                .setTitle(R.string.pc_soft_delete)
                .setMessage(R.string.delete_message_desc)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(view, R.string.please_wait, Snackbar.LENGTH_LONG).show();
                        request request = new request();
                        request.execute("delete_software", "1", "delete_click");
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
    @OnClick(R.id.message_click)public void message_click(final View view){

        final EditText input = new EditText(this);

        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(99);
        input.setFilters(FilterArray);

        new AlertDialog.Builder(admin.this)
                .setTitle(R.string.send_message)
                .setView(input)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().matches("")) {
                            Snackbar.make(view, R.string.empty_message, Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(view, R.string.please_wait, Snackbar.LENGTH_LONG).show();
                            request request = new request();
                            request.execute("message_request",input.getText().toString(),"message_click");

                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }

    @Override
    public void onBackPressed(){
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
            Intent intent = new Intent(this,voice_send.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.admin) {

        } else if (id == R.id.cmd) {
            Intent intent = new Intent(this,cmd.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.settings) {
            Intent intent = new Intent(this,Settings_and_contribute.class);
            intent.putExtra("which_action","settings");
            startActivity(intent);
            finish();

        } else if (id == R.id.contribute) {
            Intent intent = new Intent(this,Settings_and_contribute.class);
            intent.putExtra("which_action","contribute");
            startActivity(intent);
            finish();

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected class request extends AsyncTask<String,String,String[]> {

        protected void onPreExecute() {

        }

        @Override
        protected String[] doInBackground(String[] params) {
            String response = null;
            String response2 = null;
            response = server_requests.http_get_request("phone_requests.php", "get_pc_status=1&computer_id=" + server_requests.get_active_pc_id());
            if(response.equals("1") || params[2].equals("switches_status")) {
                if(params[2].equals("message_click")){
                    try {
                        response2 = server_requests.http_post_request("phone_requests.php?"+params[0]+"=1&computer_id=" + server_requests.get_active_pc_id()+"&client_token="+server_requests.get_client_token()+"&connect_id="+read_xml.get_connect_id(),new String[]{"message"},new String[]{params[1]});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                response2 = server_requests.http_get_request("phone_requests.php", params[0] + "=" + params[1] + "&computer_id=" + server_requests.get_active_pc_id());
            }}

            return new String[]{params[2], response,response2};
        }

        protected void onPostExecute(String[] response) {
            if(response[0].equals("switches_status")){

                String[] datas = response[2].split("-");
                switch_spy.setChecked(datas[0].equals("1"));
                switch_mouse.setChecked(datas[1].equals("1"));
            }

            else {
                if (response[1].equals("1")) {

                } else {
                    Snackbar.make(off_click, R.string.already_closed, Snackbar.LENGTH_LONG)
                            .show();
                    if (response[0].equals("mouse_lock_click")) {
                        if (switch_mouse.isChecked()) {
                            switch_mouse.setChecked(false);
                        } else {
                            switch_mouse.setChecked(true);
                        }

                    }
                }
            }

        }
    }
}
