package com.mycompany.fakedevice;

import javax.swing.JTextArea;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Trieda, ktora reprezentuje zariadenie
 *
 * @author Chalani
 */
public class Device {

    /**
     * JTextArea pre vypisovanie logov
     */
    private JTextArea textLog;

    /**
     * Konstruktor
     *
     * @param textLog textArea pre logy
     * @param name nazov zariadenia
     * @param IP ip zariadenia
     */
    public Device(JTextArea textLog, String name, String IP) {
        this.textLog = textLog;
        sendDevice(IP, name);
    }

    /**
     * Metoda na odoslanie POST poziadavky na server. Pridanie noveho zariadenia
     *
     * @param ip ip zariadenia
     * @param name meno zariadenia
     */
    public void sendDevice(String ip, String name) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

        try {

            HttpPost request = new HttpPost("http://localhost:8080/device");
            StringEntity params = new StringEntity("{\"ip\":\"" + ip + "\",\"name\":\"" + name + "\"} ");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ex) {
            textLog.append("Add Device: Error. " + ex.getMessage() + "\n");
        } finally {

        }

    }

}
