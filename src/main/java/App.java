import io.ConfigParser;
import models.MiningWorker;
import services.MiningWorkerMonitorService;
import sun.misc.Signal;

import java.util.ArrayList;

public class App {


    public static void main(String[] args) {

        System.out.println("Command line mining rig monitor.... starting...."+System.lineSeparator());

        ConfigParser configParser = new ConfigParser();
        ArrayList<MiningWorker> miningWorkers = configParser.parse();
        printWorkerDetails(miningWorkers);

        if(miningWorkers.size()==0) System.out.println("No worker is configured in monitor.conf file");

        else{
            startMonitoringService(miningWorkers);
        }

        Signal.handle(new Signal("INT"), sig -> System.out.println("close event"));


        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startMonitoringService(ArrayList<MiningWorker> miningWorkers){
        for(MiningWorker miningWorker: miningWorkers){
            MiningWorkerMonitorService monitorService = new MiningWorkerMonitorService(miningWorker);
            monitorService.start();
            miningWorker.setMiningWorkerMonitorService(monitorService);
        }
    }

    public static void printWorkerDetails(ArrayList<MiningWorker> miningWorkers){
        for(int i = 0 ; i < miningWorkers.size() ; i ++){
            MiningWorker miningWorker = miningWorkers.get(i);
            System.out.println(i+".\tworker:\t"+miningWorker.getWorkerName()+"\n\tip:\t\t"+miningWorker.getWorkerIp()+"\tport:\t"+miningWorker.getWorkerPort()+System.lineSeparator());
        }
    }

}
