package com.coretekno.app.fullcontrol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView pc_name;
    private TextView pc_status;
    private TextView ram_value;
    private TextView cpu_value;
    private TextView temp_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Anlık Sistem Bilgileri");
        setSupportActionBar(toolbar);

        TextView user_name = (TextView) findViewById(R.id.user_name);
        TextView user_connect_id = (TextView) findViewById(R.id.user_connect_id);

        this.pc_name = (TextView) findViewById(R.id.pc_name);
        this.pc_status = (TextView) findViewById(R.id.pc_status);
        this.ram_value = (TextView) findViewById(R.id.ram_value);
        this.cpu_value = (TextView) findViewById(R.id.cpu_value);
        this.temp_value = (TextView) findViewById(R.id.temp_value);

        Intent intent=getIntent();
        user_name.setText(intent.getStringExtra("user_name"));
        user_connect_id.setText("Connect_id : " + intent.getStringExtra("connect_id"));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Yenileniyor.Lütfen Bekleyin.", Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.app_name,R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cam_shot) {
            // Handle the camera action
        } else if (id == R.id.ss) {

        } else if (id == R.id.pc_values) {

        } else if (id == R.id.nav_manage) {

        }  else if (id == R.id.settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
