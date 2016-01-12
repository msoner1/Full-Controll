package com.coretekno.app.fullcontrol;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_devam_et = (Button)findViewById(R.id.btn_devam_et);
        ImageView button_back =(ImageView)findViewById(R.id.back_login);
        final View layout_yardim = (View)findViewById(R.id.layout_yardim);
        final View layout_login = (View)findViewById(R.id.layout_login);
        layout_login.setVisibility(View.VISIBLE);layout_yardim.setVisibility(View.GONE);layout_login.bringToFront();
        TextView yardim_text = (TextView)findViewById(R.id.yardim);
        final EditText user_name = (EditText)findViewById(R.id.e_user_name);
        final EditText email = (EditText)findViewById(R.id.e_mail);

        Bundle extras = getIntent().getExtras();
        if(extras != null){

            Snackbar.make(findViewById(android.R.id.content), extras.getString("hata"), Snackbar.LENGTH_LONG)
                    .show();
        }

        yardim_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout_login.animate().translationY(layout_login.getHeight()).alpha(1.0f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                layout_login.setVisibility(View.GONE);
                            }
                        });
                layout_yardim.setVisibility(View.VISIBLE);
                layout_yardim.bringToFront();

            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_login.animate().translationY(0).alpha(0.0f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                layout_login.setVisibility(View.VISIBLE);
                                layout_login.setAlpha(0.0f);
                            }
                        });
                layout_yardim.setVisibility(View.GONE);
                layout_login.bringToFront();

            }
        });

        btn_devam_et.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        do_background sign_up = new do_background();
                        sign_up.execute(user_name.getText().toString(), email.getText().toString());

                    }
                }
        );

    }


    public class do_background extends AsyncTask<String,Void,Integer>{

        @Override
        protected void onPreExecute(){
            setContentView(R.layout.loading_screen);
        }

        @Override
        protected Integer doInBackground(String[] params){
            Create_config_xml create = new Create_config_xml(getApplicationContext());
            try {
                String email=params[1];
                String user_name=params[0];
                String phone_brand= Build.MANUFACTURER+" "+Build.MODEL;

                String[] server_respond= server_requests.sign_up(user_name,email,phone_brand);
                String connect_id= server_respond[0];
                if(connect_id.equals("hata_bos")){
                    return 0;
                }
                else if(connect_id.equals("hata_email")){
                    return -1;
                }
                else if(connect_id.equals("hata_gecersiz_email")){
                    return -2;
                }
                else {
                create.create_config_xml(connect_id, user_name, phone_brand);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                return -3;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer r){
            if(r==1){
                Intent intend = new Intent(getApplicationContext(),read_xml.class);
                startActivity(intend);}
            else {
                Intent intend = new Intent(getApplicationContext(),Login.class);
                if(r==0){intend.putExtra("hata","Lütfen alanları doldurun.");}
                else if(r==-1){intend.putExtra("hata","Bu email sistemimizde kayıtlıdır.");}
                else if(r==-2){intend.putExtra("hata","Email adresiniz geçersizdir.");}
                else if(r==-3){intend.putExtra("hata","Beklenmedik hata.Yeniden deneyin.");}
                startActivity(intend);
            }

        }
    }
}
