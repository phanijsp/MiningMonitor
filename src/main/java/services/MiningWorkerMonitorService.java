package services;

import miners.Miner;
import io.ConsoleColors;
import models.MiningAlgorithm;
import models.MiningWorker;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MiningWorkerMonitorService extends Thread{


    private final MiningWorker miningWorker;

    private final Miner miner;

    private boolean stopFlag = false;

    private boolean spamConsoleStop = false;

    public void stopService(){
        this.stopFlag = true;
    }

    public MiningWorkerMonitorService(MiningWorker miningWorker){
        this.miningWorker = miningWorker;
        this.miner = Miner.valueOf(miningWorker.getWorkerName());
    }

    @Override
    public void run(){
        System.out.println(new Date()+"\tStarting monitor service for worker:\t"+
                miningWorker.getWorkerName()+"\t"+miningWorker.getWorkerIp()+"\t"+miningWorker.getWorkerPort());
        while(!stopFlag){
            System.out.println();
            try {
                Thread.sleep(3000);

                URL url = new URL("http://"+miningWorker.getWorkerIp()+":"+miningWorker.getWorkerPort());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                String response = new String(httpURLConnection.getInputStream().readAllBytes());

                if(miner.isInstanceOf(response)){
                    System.out.println(new Date()+"\tWorker stats:\t"+ConsoleColors.BLUE+miningWorker.getWorkerTag()+ConsoleColors.RESET+"\t"+
                            miningWorker.getWorkerName()+"\t"+miningWorker.getWorkerIp());
                    for(MiningAlgorithm miningAlgorithm: miner.getMiningAlgorithms(response)){
                           System.out.println(new Date()+"\tAlgorithm:\t\t"+miningAlgorithm.getName().substring(0,Math.min(6,miningAlgorithm.getName().length()))+"\t\t"+
                                   ConsoleColors.GREEN_BRIGHT +miningAlgorithm.getHashRate()+" "+miningAlgorithm.getUnit()+ConsoleColors.RESET);
                    }
                }else{
                    System.out.println(new Date()+"\tInvalid api response for worker:\t"+miningWorker.getWorkerName()+"\t"+miningWorker.getWorkerIp()+"\t"+miningWorker.getWorkerPort());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                System.out.println(new Date()+"\tInvalid Ip/Port for worker:\t"+miningWorker.getWorkerName()+"\t"+miningWorker.getWorkerIp()+"\t"+miningWorker.getWorkerPort());
            } catch (IOException e) {
                System.out.println(new Date()+ConsoleColors.RED_BRIGHT+"\tWorker Error:\t"+ConsoleColors.BLUE+miningWorker.getWorkerTag()+ConsoleColors.RESET+"\t"+miningWorker.getWorkerName()+"\t"+miningWorker.getWorkerIp()+ConsoleColors.RESET);
                Sound sound = new Sound(Path.of(miningWorker.getNotificationWav()));
                spamConsoleStop = false;
                try {
                    new Thread(()->{
                        while(!spamConsoleStop) {
                            try {
                                Thread.sleep(50);
                                System.out.println(new Date()+ConsoleColors.RED_BRIGHT+"\tWorker Error:\t"+
                                        ConsoleColors.BLUE+miningWorker.getWorkerTag()+ConsoleColors.RESET+"\t"+miningWorker.getWorkerName()+"\t"+
                                        miningWorker.getWorkerIp()+ConsoleColors.RESET);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                            }).start();
                    sound.play();
                    spamConsoleStop = true;
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    static class Sound {

        private final Path wavPath;
        private final CyclicBarrier barrier = new CyclicBarrier(2);

        Sound(final Path wavPath) {

            this.wavPath = wavPath;
        }


        public void play() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

            try (final AudioInputStream audioIn = AudioSystem.getAudioInputStream(wavPath.toFile());
                 final Clip clip = AudioSystem.getClip()) {

                listenForEndOf(clip);
                clip.open(audioIn);
                clip.start();
                waitForSoundEnd();
            }
        }

        private void listenForEndOf(final Clip clip) {

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) waitOnBarrier();
            });
        }

        private void waitOnBarrier() {

            try {

                barrier.await();
            } catch (final InterruptedException ignored) {
            } catch (final BrokenBarrierException e) {

                throw new RuntimeException(e);
            }
        }

        private void waitForSoundEnd() {

            waitOnBarrier();
        }
    }
}
