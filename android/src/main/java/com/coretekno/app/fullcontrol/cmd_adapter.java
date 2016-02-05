package com.coretekno.app.fullcontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class cmd_adapter extends ArrayAdapter<cmd_setting> {

    public cmd_adapter(Context context, List<cmd_setting> settings) {

        super(context, 0, settings);

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {


        cmd_setting setting = getItem(position);


        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cmd_list_view, parent, false);

        }

        TextView title = (TextView) convertView.findViewById(R.id.respond_or_user_name);

        TextView data = (TextView) convertView.findViewById(R.id.data);


        title.setText(setting.title);

        data.setText(setting.data);


        return convertView;

    }

}