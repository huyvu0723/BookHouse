package vu.huy.bookhouse.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


// TinLM 30/10/2018  Read link to stream

public class ReadStream {

    public static HttpURLConnection getPostConnection(URL urll) {
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) urll.openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    // read Stream
    public static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
