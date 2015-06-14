package de.teambluebaer.patientix.helper;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by chris on 14.06.15.
 */
public class InsertConfig {
    private static final String pathOfConfig = Environment.getExternalStorageDirectory() + "/.patientix";

    public static void getConfig(){
        createConfigIfNotExists(pathOfConfig);
    }

    /**
     * creates the direction for the config file
     *
     * @param path the path that should created
     */
    private static void createDirIfNotExists(String path) {


        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating folder");
            }
        }
    }

    /**
     * Creates the Configfile if it not exists
     *
     * @param path The path where the file will be stored
     */

    private static void createConfigIfNotExists(String path) {
        createDirIfNotExists(path);
        File file = new File(path + "/config.txt");

        if (!file.exists()) {
            try {
                OutputStream fo = new FileOutputStream(file);
                fo.write(Constants.CONFIGURATION.getBytes());
                fo.close();
                file.createNewFile();
                Log.d("File", "Config.txt created");
            } catch (IOException e) {
                Log.d("FileCreationExeption", e.toString());

            }
        } else {
            getConfigData(path);
        }
    }

    /**
     * Gets the data of the config file and set it up in config
     *
     * @param path the path where the config file is stored
     */
    private static void getConfigData(String path) {
        BufferedReader br = null;
        try {
            String lineOne = "";
            String lineTwo = "";
            br = new BufferedReader(new FileReader(path + "/config.txt"));
            while (!lineOne.contains("ip")) {
                lineOne = br.readLine();
            }
            while (!lineTwo.contains("ping")) {
                lineTwo = br.readLine();
            }

            String tempIP = lineOne.substring(lineOne.indexOf('"') + 1, lineOne.lastIndexOf('"'));
            int tempPing = Integer.parseInt(lineTwo.substring(lineTwo.indexOf('"') + 1, lineTwo.lastIndexOf('"')));

            Constants.SERVER_URL = tempIP;
            Constants.PING = tempPing * 1000;

            Log.d("ConfigLineOne", lineOne);
            Log.d("ConfigLineTwo", lineTwo);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
