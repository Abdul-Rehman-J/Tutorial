package apm.tutorial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("onCreate1 ");
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
        System.out.println("onCreate ");
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        super.unregisterReceiver(receiver);
    }

    @Override
    protected void onPause() {
        // when the screen is about to turn off
        if (ScreenReceiver.screenOff) {
            // this is the case when onPause() is called by the system due to a screen state change
            System.out.println("SCREEN TURNED OFF");
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            String screenOff = "MainScreen is OFF at : " + mydate;
            generateNoteOnSD(getApplicationContext(), screenOff);

        } else {
            // this is when onPause() is called when the screen state has not changed
            System.out.println("this is when onPause() is called when the screen state has not changed ");

        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        // only when screen turns on
        if (!ScreenReceiver.screenOff) {
            // this is when onResume() is called due to a screen state change
            System.out.println("SCREEN TURNED ON");
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            String screenOff = "MainScreen is ON at : " + mydate;
            generateNoteOnSD(getApplicationContext(), screenOff);
        } else {
            // this is when onResume() is called when the screen state has not changed
            System.out.println(" this is when onResume() is called when the screen state has not changed ");
        }
        super.onResume();
    }

    public void generateNoteOnSD(Context context, String sBody) {
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
            //Closing BufferedWriter Stream
            bw.close();

            System.out.println("Data successfully appended at the end of file");

        } catch (IOException ioe) {
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
    }
}
