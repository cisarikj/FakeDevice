package com.mycompany.fakedevice;

import javax.swing.JTextArea;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Trieda, ktora reprezentuje uzivatela Metody na odoslanie POST poziadavky
 *
 * @author Chalani
 */
public class User {

    /**
     * JTextArea pre logy
     */
    private JTextArea textLog;

    /**
     * Konstruktor
     *
     * @param textLog logy
     * @param name uzivatelske meno
     * @param password uzivatelske heslo
     * @param role uzivatelske prava
     */
    public User(JTextArea textLog, String name, String password, String role) {
        this.textLog = textLog;
        sendUser(name, password, role);
    }

    /**
     * Metoda, ktora vykona POST poziadavku na server s uzivatelskymi datami pre
     * pridanie Usera
     *
     * @param name uzivatelske meno
     * @param password uzivatelske heslo
     * @param role uzivatelske prava
     */
    public void sendUser(String name, String password, String role) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

        try {

            HttpPost request = new HttpPost("http://localhost:8080/user");
            StringEntity params = new StringEntity("{\"name\":\"" + name + "\",\"password\":\"" + password + "\",\"role\":\"" + role + "\"} ");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ex) {
            textLog.append("Add User: Error. " + ex.getMessage() + "\n");
        } finally {

        }

    }
}
