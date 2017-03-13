package apm.tutorial;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    public EditText editText;
    public TextView textView;
    public Button save, load;

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaTutorial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   //-----------------------------------------------------------------------

        System.out.println("onCreate1 ");
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
        System.out.println("onCreate ");


  //--------------------------------------------------------------
      //  editText = (EditText) findViewById(R.id.editText);
       // textView = (TextView) findViewById(R.id.textView);
         save = (Button) findViewById(R.id.save);
        //load = (Button) findViewById(R.id.load);

        File dir = new File(path);
        dir.mkdirs();

    }
//-----------------------------------------
@Override
protected void onPause() {
    // when the screen is about to turn off
    if (ScreenReceiver.screenOff) {
        // this is the case when onPause() is called by the system due to a screen state change
        System.out.println("SCREEN TURNED OFF");
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
        } else {
            // this is when onResume() is called when the screen state has not changed
            System.out.println(" this is when onResume() is called when the screen state has not changed ");
        }
        super.onResume();
    }


    //-------------------------------
//    public void buttonSave (View view)
//    {
//        File file = new File (path + "/savedFile.txt");
//        String [] saveText = String.valueOf(editText.getText()).split(System.getProperty("line.separator"));
//
//        editText.setText("");
//
//        Toast.makeText(getApplicationContext(), "Data is Saved", Toast.LENGTH_LONG).show();
//
//        Save (file, saveText);
//    }

    public static void Save(File file, String[] data)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
