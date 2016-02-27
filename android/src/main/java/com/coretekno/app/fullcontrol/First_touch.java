package com.coretekno.app.fullcontrol;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ironsource.mobilcore.AdUnitEventListener;
import com.ironsource.mobilcore.MobileCore;

/**
 * Ana aktiviti dosyamızdır uygulama buradan baslar
 */
public class First_touch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo!=null){
            if(netInfo.isConnectedOrConnecting()){
                do_background do_background = new do_background();
                do_background.execute();
            }
            else { //internet yok
                setContentView(R.layout.no_internet);
                Button exit = (Button)findViewById(R.id.button_exit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        System.exit(0);
                    }
                });
            }

        }
        else { //internet yok
            setContentView(R.layout.no_internet);
            Button exit = (Button)findViewById(R.id.button_exit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    System.exit(0);
                }
            });
        }

        MobileCore.setAdUnitEventListener(new AdUnitEventListener() {
            @Override
            public void onAdUnitEvent(MobileCore.AD_UNITS adUnit, EVENT_TYPE eventType,
                                      MobileCore.AD_UNIT_TRIGGER... trigger) {
                // Handle events here
            }
        });

        MobileCore.init(this, "M6B45N0KWBQBDRQ60SKHW9TLTOR3", MobileCore.LOG_TYPE.PRODUCTION, MobileCore.AD_UNITS.INTERSTITIAL);

        MobileCore.loadAdUnit(MobileCore.AD_UNITS.INTERSTITIAL,
                MobileCore.AD_UNIT_TRIGGER.APP_START);
    }

    public class do_background extends AsyncTask<Void,Integer,Integer>{


        @Override
        protected Integer doInBackground(Void... params) {
            Create_config_xml is_xml_exist = new Create_config_xml(getApplicationContext());
            if(!is_xml_exist.exist_config_xml()){
                return 0;
            }
            else {
                return 1;
            }

        }

        @Override
        protected void onPostExecute(Integer result){
            if(result==0){
                Intent intend = new Intent(getApplicationContext(),Login.class);
                startActivity(intend);
            }
            else {
                Intent intend = new Intent(getApplicationContext(),read_xml.class);
                startActivity(intend);
            }

        }
    }
}
