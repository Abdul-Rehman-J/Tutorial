package apm.tutorial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;
//import static apm.tutorial.MainActivity.Save;

/**
 * Created by abdul on 13/03/2017.
 */

public class ScreenReceiver extends BroadcastReceiver {
    public static boolean screenOff;

    @Override
    public void onReceive(Context context, Intent intent) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("onReceive ");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
            System.out.println("SCREEN TURNED OFF BroadcastReceiver");
            String mydate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            String screenOff = "broadScreen is off at : " + mydate;
            generateNoteOnSD(context, screenOff, android_id, " : screen is off");

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;
            System.out.println("SCREEN TURNED ON BroadcastReceiver");
            String mydate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            String screenONN = "broadScreen is ONN at : " + mydate;
            generateNoteOnSD(context, screenONN, android_id, " : screen is on");

        }
        Intent i = new Intent(context, UpdateService.class);
        i.putExtra("screen_state", screenOff);
        context.startService(i);
    }

    public void generateNoteOnSD(Context context, String sBody, String id, String status) {
        try {
            String content = sBody;
            String dir = Environment.getExternalStorageDirectory() + File.separator + "myDirectory";
            //create folder
            File folder = new File(dir); //folder name
            folder.mkdirs();

            //create file
            File file = new File(dir, "ScreenData.txt");


            FileWriter fw = new FileWriter(file, true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.write(content);
            pw.println("");
            pw.write("Device id is" + id);
            pw.println("");
            pw.write("Device status is" + status);
            pw.println("");
            pw.println("--------------------------");
            //Closing BufferedWriter Stream
            bw.close();

            System.out.println("Data successfully appended at the end of file");

        } catch (IOException ioe) {
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }

    }
}

