package com.coretekno.app.fullcontrol;

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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class cmd extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Nullable @Bind(R.id.pc_name) TextView pc_name;
    @NonNull @Bind(R.id.user_name) TextView user_name;
    @NonNull @Bind(R.id.user_connect_id) TextView user_connect_id;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.cmd_edittext) EditText cmd_text;
    @Bind(R.id.cmd_listview) ListView cmd_list;
    @Bind(R.id.top_of_cmd) TextView top_cmd;
    @Bind(R.id.cmd_write_layout) LinearLayout write_layout;

   List<cmd_setting> messages = new ArrayList<>();
    cmd_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmd);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.cmd);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        user_name.setText(read_xml.get_user_name());
        user_connect_id.setText("Connect_id : " + read_xml.get_connect_id());

        adapter = new cmd_adapter(this,messages);
        cmd_list.setAdapter(adapter);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.app_name,R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        response_listener();
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

    @OnClick(R.id.button_send_cmd)public void button_send(View v){
        String text = cmd_text.getText().toString();
        if(!text.isEmpty()) {
            add_view(text,user_name.getText().toString()+" :");
            request request = new request();
            request.execute("cmd_post", text, "button_send");
            cmd_text.setText("");
            top_cmd.setText(R.string.waiting_respond);
            write_layout.setVisibility(View.GONE);
        }
        else {
            Snackbar.make(cmd_text, R.string.empty_message, Snackbar.LENGTH_LONG)
                    .show();
        }
    }
    public void add_view(String response,String who){

        messages.add(new cmd_setting(who,response));
        adapter.notifyDataSetChanged();
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
            Intent intent = new Intent(this,admin.class);
            startActivity(intent);
            finish();
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

    public void response_listener() {
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                request request = new request();
                request.execute("get_cmd_response","1","response_listener");
            }
        };
        timer.schedule(doAsynchronousTask, 0, 500);
    }


    protected class request extends AsyncTask<String,String,String[]> {

        protected void onPreExecute() {

        }

        @Override
        protected String[] doInBackground(String[] params) {
            String response = null;
            String response2 = null;
            try {
                response = server_requests.http_get_request("phone_requests.php", "get_pc_status=1&computer_id=" + server_requests.get_active_pc_id());
                if(response.equals("1")) {
                    if(params[2].equals("button_send")){
                        response2 = server_requests.http_post_request("phone_requests.php?"+params[0]+"=1&computer_id=" + server_requests.get_active_pc_id()+"&client_token="+server_requests.get_client_token()+"&connect_id="+read_xml.get_connect_id(),new String[]{"cmd"},new String[]{params[1]});
                    }
                    else {
                        response2 = server_requests.http_get_request("phone_requests.php", params[0] + "=" + params[1] + "&computer_id=" + server_requests.get_active_pc_id());
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new String[]{params[2], response,response2};
        }

        protected void onPostExecute(String[] response) {
            if(response[1].equals("1")){
                if(response[0].equals("response_listener") && !response[2].equals("0")){
                    add_view(response[2],"Your System :");
                    top_cmd.setText("");
                    write_layout.setVisibility(View.VISIBLE);
                }
            }
            else {
                if(!response[0].equals("response_listener")) {
                    Snackbar.make(cmd_text, R.string.already_closed, Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        }
    }
}
