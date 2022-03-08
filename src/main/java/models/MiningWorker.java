package models;

import services.MiningWorkerMonitorService;

public class MiningWorker {
    private String workerIp;
    private int workerPort;
    private String workerName;
    private String workerTag;
    private String notificationWav;
    private MiningWorkerMonitorService miningWorkerMonitorService;

    public MiningWorker(String workerIp, int workerPort, String workerName, String workerTag, String notificationWav) {
        this.workerIp = workerIp;
        this.workerPort = workerPort;
        this.workerName = workerName;
        this.workerTag = workerTag;
        this.notificationWav = notificationWav;
    }

    public String getNotificationWav() {
        return notificationWav;
    }

    public void setNotificationWav(String notificationWav) {
        this.notificationWav = notificationWav;
    }

    public String getWorkerTag() {
        return workerTag;
    }

    public void setWorkerTag(String workerTag) {
        this.workerTag = workerTag;
    }

    public MiningWorkerMonitorService getMiningWorkerMonitorService() {
        return miningWorkerMonitorService;
    }

    public void setMiningWorkerMonitorService(MiningWorkerMonitorService miningWorkerMonitorService) {
        this.miningWorkerMonitorService = miningWorkerMonitorService;
    }

    public String getWorkerIp() {
        return workerIp;
    }

    public void setWorkerIp(String workerIp) {
        this.workerIp = workerIp;
    }

    public int getWorkerPort() {
        return workerPort;
    }

    public void setWorkerPort(int workerPort) {
        this.workerPort = workerPort;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
}
