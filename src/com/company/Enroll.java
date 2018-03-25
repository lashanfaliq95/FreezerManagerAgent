package com.company;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.company.Main.accessToken;

public class Enroll {

    public static void main(String[] args) {
        Random rand = new Random();
        for (int i = 23; i < 250; i++) {
            String deviceid = "Freezer" + i;
            System.out.println(deviceid);
            int temp = rand.nextInt(50) + 1, humidity = rand.nextInt(100) + 1;
            String json = "{ \"name\": " + deviceid + ", \"type\": \"FreezerManager\", \"description\": \"freezer\", \"deviceIdentifier\":" + deviceid + ", \"enrolmentInfo\": {\"ownership\": \"BYOD\", \"status\": \"ACTIVE\", \"owner\": \"admin\"} ,\"properties\": [{\"name\":\"groupID\",\"value\":\"GroupOne\"},{\"name\":\"latitude\",\"value\":" + humidity + "},{\"name\":\"longitude\",\"value\":" + temp + "}]}";
            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            try {
                URL u = new URL("http://localhost:8280/api/device-mgt/v1.0/device/agent/enroll");
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("accept", "application/json");
                conn.setRequestProperty("authorization", " Bearer " + accessToken);
                conn.setRequestProperty("Content-Type", "FreezerManager");
                conn.setRequestMethod("POST");
                OutputStream os = conn.getOutputStream();
                os.write(out);
                os.flush();
                System.out.println(conn.getResponseMessage());
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
