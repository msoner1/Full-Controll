package com.coretekno.app.fullcontrol;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.firezenk.audiowaves.Visualizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class voice_send extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Nullable @Bind(R.id.pc_name) TextView pc_name;
    @NonNull @Bind(R.id.user_name) TextView user_name;
    @NonNull @Bind(R.id.user_connect_id) TextView user_connect_id;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.voice_record) FloatingActionButton fab_voice_send;
    @Bind(R.id.record_time_text) TextView record_time_text;
    @Bind(R.id.record_text) TextView record_text;

    Boolean recording = false;
    int record_time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_send);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.voice_send);
        setSupportActionBar(toolbar);

        user_name.setText(read_xml.get_user_name());
        user_connect_id.setText("Connect_id : "+read_xml.get_connect_id());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.app_name,R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        ((Visualizer) findViewById(R.id.visual)).startListening();
    }
    @OnClick(R.id.voice_record)public void voice_send_click(View v){
        if(recording){
            record_time_text.setText(Integer.toString(record_time++));
            record_text.setText(R.string.for_recording);
            recording=false;
            fab_voice_send.setImageResource(R.drawable.mic);
        }
        else {
            record_text.setText(R.string.recording);
            record_time_text.setText(Integer.toString(record_time++));
            recording = true;
            fab_voice_send.setImageResource(R.drawable.mic_dis);
        }
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
}
