package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main implements Runnable {
    private String deviceId;
    public Main(String deviceId){
        this.deviceId=deviceId;
    }
    Random rand = new Random();
    public static void main(String[] args) throws IOException, InterruptedException  {


        Thread newThread = new Thread(new Main("Freezer17"));
        newThread.run();  //should be start();

    }
    @Override
    public void run(){
        String type = "application/json; charset=UTF-8";
        String accessToken="639bc2d0-97cf-328a-a238-bfbcea848e43";
        while (true) {
            int temp = rand.nextInt(50) + 1, humidity = rand.nextInt(100) + 1, powerConsumption = rand.nextInt(50) + 1;
            String json = "{\"temperature\":" + temp + ",\"humidity\":" + humidity + ",\"powerConsumption\":" + powerConsumption + "}";
            byte[] out = json.getBytes(StandardCharsets.UTF_8);
         try{
            URL u = new URL("http://localhost:8280/api/device-mgt/v1.0/device/agent/events/publish/FreezerManager/" + deviceId);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("authorization", " Bearer " + accessToken);
            conn.setRequestProperty("Content-Type", type);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(out);
            os.flush();
            System.out.println(conn.getResponseMessage());
            TimeUnit.SECONDS.sleep(5);}
            catch (Exception e){
    e.printStackTrace();
            }
        }
    }

}
