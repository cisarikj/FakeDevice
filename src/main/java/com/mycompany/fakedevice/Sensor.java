package com.mycompany.fakedevice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Trieda, ktora reprezentuje fiktivny senzor
 *
 * @author Chalani
 */
public class Sensor implements Runnable {

    /**
     * JTextArea pre vypis logiv
     */
    private JTextArea textLog;
    /**
     * Informacie o senzore
     */
    private String info = "";
    /**
     * Spodna hranica generovania hodnot
     */
    private int value1 = 0;
    /**
     * Vrchna hranica generovania hodnot
     */
    private int value2 = 0;
    /**
     * Casovy interval pre odosielanie hodnot na server
     */
    private int interval = 0;
    /**
     * Zariadenie, ku ktoremu je senzor pripojeny
     */
    private String device = "";
    /**
     * Vlakno, v ktorom bezi samotne odosielanie
     */
    Thread t;

    /**
     * Konstruktor
     *
     * @param textLog logy
     * @param info informacie o senzore
     * @param value1 spodna hranica generovania hodnot
     * @param value2 vrchna hranica generovania hodnot
     * @param interval casovy interval odosielania
     * @param device zariadenie, ku ktoremu senzor patri
     */
    public Sensor(JTextArea textLog, String info, String value1, String value2, String interval, String device) {
        this.textLog = textLog;
        this.info = info;
        this.value1 = Integer.valueOf(value1);
        this.value2 = Integer.valueOf(value2);
        this.interval = Integer.valueOf(interval) * 1000;
        this.device = device;
    }

    /**
     * Run metoda. Periodicke odosielanie POST poziadaviek
     */
    @Override
    public void run() {

        textLog.append("Sensor " + info + " started measurement.\n");
        while (true) {
            try {
                int randomNum = ThreadLocalRandom.current().nextInt(value1, value2 + 1);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                sendData(dateFormat.format(date), String.valueOf(randomNum), device);
                textLog.append("Sensor " + info + " sends data " + randomNum + ". \n");
                Thread.sleep(interval);
            } catch (InterruptedException ex) {
                textLog.append("Sensor " + info + ": " + ex.getMessage());
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Spustenie vlakna
     */
    public void start() {
        t = new Thread(this, info);
        t.start();
    }

    /**
     * Metoda, ktora posle POST poziadavku na server s datami
     *
     * @param date datum
     * @param data uzitocne data
     * @param device zariadenie
     */
    public void sendData(String date, String data, String device) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

        try {

            HttpPost request = new HttpPost("http://localhost:8080/data");
            StringEntity params = new StringEntity("{\"date\":\"" + date + "\",\"data\":\"" + data + "\",\"device\":\"" + device + "\",\"info\":\"" + info + "\"} ");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ex) {
            textLog.append("Add Sensor: Error. " + ex.getMessage() + "\n");
        } finally {

        }
    }

}
