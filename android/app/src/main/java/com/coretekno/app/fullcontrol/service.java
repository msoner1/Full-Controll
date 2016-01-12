package com.coretekno.app.fullcontrol;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by soner on 11.01.2016.
 */
public class service extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public service(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {


    }
}
