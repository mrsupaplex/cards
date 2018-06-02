package com.apps.brz.cardstack;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ophir on 4/19/2017.
 */

public class FilesHandler {

    private static String s;

    public static boolean writeToFile(Context context, String filename, String data) {
        File file = new File(context.getFilesDir(), filename);
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            String s = e.getMessage();
            return false;
        }
    }

    public static String readFromFile(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        try
        {
            InputStream instream = context.openFileInput(filename);
            if (instream != null)
            {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line,line1 = "";
                try
                {
                    while ((line = buffreader.readLine()) != null)
                        line1+=line;
                    return line1;
                }catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        catch (Exception e)
        {
            String error="";
            error=e.getMessage();
            return null;
        }
        return null;
    }


}


