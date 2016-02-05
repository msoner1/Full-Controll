package com.coretekno.app.fullcontrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.apache.http.HttpException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Settings_and_contribute extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @NonNull @Bind(R.id.user_name) TextView user_name;
    @NonNull @Bind(R.id.user_connect_id) TextView user_connect_id;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.view_change) ViewStub viewStub;

    Switch switch_on_notif;
    Switch switch_off_notif;
    Switch switch_overload_notif;

    private ArrayList<String> systems_name = new ArrayList<String>();
    private ArrayList<String> systems_id = new ArrayList<String>();

    Intent intent_incoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_and_contribute);
        ButterKnife.bind(this);

        intent_incoming = getIntent();

        if(intent_incoming.getStringExtra("which_action").equals("settings")){

            final request request = new request();
            request.execute("get_switches", "1", "notif");

            toolbar.setTitle(R.string.settings);
            viewStub.setLayoutResource(R.layout.content_settings);
            View inflated = viewStub.inflate();
            final EditText change_user_name = (EditText) findViewById(R.id.e_user_name);
            final Spinner system_list=(Spinner) findViewById(R.id.spinner_systems);
            request fill_spinner = new request();
            fill_spinner.execute("get_pc_list", "1", "systems");

            change_user_name.setHint(read_xml.get_user_name());
            Button change_user_name_button = (Button) findViewById(R.id.change_username);
            change_user_name_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user_name.setText(String.valueOf(change_user_name.getText()));
                    Snackbar.make(v, R.string.username_changed, Snackbar.LENGTH_LONG).show();

                    request request = new request();
                    request.execute("user_name_update", String.valueOf(change_user_name.getText()), "null");

                    read_xml.set_username(String.valueOf(change_user_name.getText()));
                    try {
                        read_xml.update_xml_value("name", String.valueOf(change_user_name.getText()));
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }
                }
            });

            Button change_rest = (Button) findViewById(R.id.change_rest);
            change_rest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v,system_list.getSelectedItem().toString()+ " " +getResources().getText(R.string.rest), Snackbar.LENGTH_SHORT).show();

                    server_requests.set_active_pc_name(system_list.getSelectedItem().toString());
                    server_requests.set_active_pc_id(systems_id.get((int) system_list.getSelectedItemId()));

                }
            });
            Button delete_system = (Button) findViewById(R.id.delete_system);
            delete_system.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(Settings_and_contribute.this)
                            .setTitle(R.string.delete)
                            .setMessage("Are you sure you want to delete "+system_list.getSelectedItem().toString()+"?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    request request_delete = new request();
                                    request_delete.execute("delete_system",systems_id.get((int) system_list.getSelectedItemId()),"null");

                                    systems_id.remove(system_list.getSelectedItem());
                                    systems_name.remove(system_list.getSelectedItem());
                                    ArrayAdapter<String> adaptor=new ArrayAdapter<String>
                                            (system_list.getContext(),android.R.layout.simple_spinner_dropdown_item, systems_name);
                                    system_list.setAdapter(adaptor);
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
            });

            switch_on_notif = (Switch)findViewById(R.id.switch_system_on);
            switch_off_notif = (Switch)findViewById(R.id.switch_system_off);
            switch_overload_notif = (Switch)findViewById(R.id.switch_system_overload);

            switch_off_notif.setChecked(true);
            switch_on_notif.setChecked(true);
            switch_overload_notif.setChecked(true);

            switch_on_notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    request request = new request();
                    request.execute("update_system_on_notif",String.valueOf(isChecked),"null");
                }
            });

            switch_off_notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    request request = new request();
                    request.execute("update_system_off_notif", String.valueOf(isChecked),"null");
                }
            });

            switch_overload_notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    request request = new request();
                    request.execute("update_system_overload_notif", String.valueOf(isChecked),"null");
                }
            });


        }
        else {
            toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
            toolbar.setTitle(R.string.contribute);
            viewStub.setLayoutResource(R.layout.content_contribute);
            View inflated = viewStub.inflate();

            Typeface font =Typeface.createFromAsset(getAssets(), "fonts/Courgette-Regular.ttf");
            TextView description = (TextView) findViewById(R.id.textView8);
            Button source = (Button) findViewById(R.id.source);
            Button dilek = (Button) findViewById(R.id.dilek);
            description.setTypeface(font);
            source.setTypeface(font);
            dilek.setTypeface(font);

            dilek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://app.coretekno.com/full_control/#contact"));
                    startActivity(i);
                }
            });

            source.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/msoner1/Full-Controll"));
                    startActivity(i);
                }
            });
        }
        setSupportActionBar(toolbar);
        user_name.setText(read_xml.get_user_name());
        user_connect_id.setText("Connect_id : " + read_xml.get_connect_id());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.app_name,R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

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

        }else if (id == R.id.voice_send) {
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


    protected class request extends AsyncTask<String,String,String[]> {

        protected void onPreExecute() {

        }

        @Override
        protected String[] doInBackground(String[] params) {
            String response = null;
            response = server_requests.http_get_request("phone_requests.php", params[0]+"="+params[1]+"&computer_id=" + server_requests.get_active_pc_id());

            return new String[]{params[2], response};
        }

        protected void onPostExecute(String[] response) {
            if(response[0].equals("notif")){
                String[] parts = response[1].split("-");
                switch_on_notif.setChecked(parts[0].equals("1"));
                switch_off_notif.setChecked(parts[1].equals("1"));
                switch_overload_notif.setChecked(parts[2].equals("1"));
            }
            else if(response[0].equals("systems")){
                String[] parts = response[1].split("_");

                for(int i = 0; i<parts.length-1;i+=2){
                    systems_id.add(parts[i]);
                    systems_name.add(parts[i+1]);
                }

                final Spinner system_list=(Spinner) findViewById(R.id.spinner_systems);
                ArrayAdapter<String> adaptor=new ArrayAdapter<String>
                        (system_list.getContext(),android.R.layout.simple_spinner_dropdown_item, systems_name);
                system_list.setAdapter(adaptor);
            }
        }
    }
}
