package io;

import models.MiningWorker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ConfigParser {

    public ArrayList<MiningWorker> parse() {
        ArrayList<MiningWorker> miningWorkers = new ArrayList<>();
        String fileStringData = readFileStringData();
        if(fileStringData != null && fileStringData.length() > 0){
            try{
                JSONArray workerConfigArr = new JSONArray(fileStringData);
                Iterator<Object> iterator = workerConfigArr.iterator();
                while(iterator.hasNext()){
                    try{
                        JSONObject workerConfig = (JSONObject) iterator.next();
                        String miner    = workerConfig.getString("miner");
                        String ip       = workerConfig.getString("ip");
                        String port     = workerConfig.getString("port");
                        String tag      = workerConfig.getString("name");
                        String wav      = workerConfig.getString("notificationWav");
                        if(notNullAndNotEmpty(miner, ip, port)){
                            try{
                                MiningWorker miningWorker = new MiningWorker(ip, Integer.parseInt(port), miner, tag, wav);
                                miningWorkers.add(miningWorker);
                            }catch (Exception e){
                                System.out.println("port should contain a numerical string");
                            }
                        }else{
                            System.out.println("Invalid config values: \n" +
                                    "miner: "+miner+System.lineSeparator()+
                                    "ip: "+ip+System.lineSeparator()+
                                    "port: "+port+System.lineSeparator());
                        }
                    }catch (Exception e){
                        System.out.println("Error occurred while getting json object from array iterator");
                    }
                }
            }catch (Exception e){
                System.out.println("Error occurred while parsing monitor.conf, invalid json array");
            }
        }else{
            System.out.println("File monitor.conf should contain worker's miner software, ip, api port");
        }
        return miningWorkers;
    }

    private String readFileStringData() {
        FileReader fileReader;
        try {
            fileReader = new FileReader("monitor.conf");
        } catch (FileNotFoundException e) {
            System.out.println("File monitor.conf is not found!");
            return null;
        }
        int dataRead;
        StringBuilder fileData = new StringBuilder();
        while(true){
            try {
                if ((dataRead = fileReader.read()) == -1) break;
            } catch (IOException e) {
                System.out.println("Error occurred while reading data from file monitor.conf");
                return null;
            }
            fileData.append((char) dataRead);
        }
        return fileData.toString();
    }

    boolean notNullAndNotEmpty(String... args){
        for(String arg: args){
            if(arg == null || arg.length() == 0){
                return false;
            }
        }
        return true;
    }
}
