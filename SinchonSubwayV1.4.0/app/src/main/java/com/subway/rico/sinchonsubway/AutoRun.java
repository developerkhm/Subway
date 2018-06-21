package com.subway.rico.sinchonsubway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.subway.rico.sinchonsubway.exit.ExitActivity;

import org.apache.log4j.Logger;

public class AutoRun extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {


        Logger logger = Logger.getLogger(AutoRun.class.getSimpleName());
        logger.info("AutoRun test");


        String action = intent.getAction();
        if(action.equals("android.intent.action.BOOT_COMPLETED")){
            context.startActivity(new Intent(context, MainActivity.class));
        }
////        if(action.equals("android.intent.action.QUICKBOOT_POWERON")){
////            context.startActivity(new Intent(context, MainActivity.class));
////        }
    }
}
