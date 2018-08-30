package com.example.testcft;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;

import java.util.ArrayList;
import java.util.List;

public class DataPostSender {
    private static int counter = 0;


    public static void send(Document document, String sendURL) {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(sendURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("data", document.asXML()));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = httpclient.execute(httppost);

            System.out.println(response.getStatusLine().getStatusCode() == 200 ? "xml sending" : "something wrong");

            ((CloseableHttpClient) httpclient).close();
            counter++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStat() {
        return "Files sent: " + counter;
    }
}
