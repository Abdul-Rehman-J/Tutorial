package apm.tutorial;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.File;

import static android.R.attr.path;
import static apm.tutorial.MainActivity.Save;

/**
 * Created by abdul on 13/03/2017.
 */

public class ScreenReceiver extends BroadcastReceiver {
    public static boolean screenOff;
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("onReceive ");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
            System.out.println("SCREEN TURNED OFF on BroadcastReceiver");
            File file = new File (path + "/savedFile.txt");
         String saveText = String.valueOf(editText.getText()).split(System.getProperty("line.separator"));
            Toast.makeText(context, "Data is Saved", Toast.LENGTH_LONG).show();

            Save (file, saveText);


        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;
            System.out.println("SCREEN TURNED ON on BroadcastReceiver");
        }
        Intent i = new Intent(context, UpdateService.class);
        i.putExtra("screen_state", screenOff);
        context.startService(i);
    }
    }

