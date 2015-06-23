package de.teambluebaer.patientix.helper;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;


/**
 * Copyright 2015 By Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Authors:
 * Simon Sauerzapf
 * Maren Dietrich
 * Chris Harsch
 *
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
            br = new BufferedReader(new FileReader(path + "/config.txt"));

            String temp;
            String config = "";

            do{
                temp = br.readLine();
                config = config + temp;
            }while(temp != null && !temp.isEmpty());
            br.close();

            String[] splitConfig = config.split("\"");

            boolean isPIN = false, isPing = false, isIP = false;

            for(String s : splitConfig){
                Log.d("read s",s);

                if(isPIN){
                    Constants.PIN = passwordHash(s);
                    isPIN = false;
                }else if(isIP){
                    Constants.SERVER_URL = s;
                    isIP = false;
                }else if(isPing){
                    Constants.PING = Integer.parseInt(s);
                    isPing = false;
                }

                if(s.contains("PIN")){
                    isPIN = true;
                }else if(s.contains("serverip")){
                    isIP = true;
                }else if(s.contains("ping")){
                    isPing = true;
                }

            }

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


    /**
     * This method converts the bytes of String to hex
     *
     * @param data byte Array to convert
     * @return String of hexcode
     */
    private static String convertByteToHex(byte data[]) {
        StringBuffer hexData = new StringBuffer();
        for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
            hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));
        return hexData.toString();
    }

    /**
     * This method is used to hash the password or pin
     *
     * @param textToHash String of text to hash
     * @return String which is hashed
     */
    private static String passwordHash(String textToHash) {
        try {
            final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            sha512.update(textToHash.getBytes());
            return convertByteToHex(sha512.digest());
        } catch (Exception e) {
            Log.d("HashFail", e.toString());
        }
        return null;
    }
}
