package com.coretekno.app.fullcontrol;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by msone on 24.10.2015.
 */
public class Create_config_xml{

    private final File config;
    public Create_config_xml(Context context){
         config = new File(context.getFilesDir()+"/config.xml");
    }

    public boolean exist_config_xml(){

            return config.exists();

    }
    public void create_config_xml(String connect_id,String user_name,String phone_brand) throws IOException {

        String config_xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><app_info>\n      <connect_id>"+connect_id+"</connect_id>\n     <name>"+user_name+"</name>\n      <phone_brand>"+phone_brand+"</phone_brand>\n\n</app_info>";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(config));
        bufferedWriter.write(config_xml);
        bufferedWriter.close();

    }
}
