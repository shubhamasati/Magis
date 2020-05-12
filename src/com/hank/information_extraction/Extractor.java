package com.hank.information_extraction;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javazoom.jl.decoder.JavaLayerException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hank
 */
public class Extractor {
    public static String getInfo(String term) throws MalformedURLException, IOException, JavaLayerException {
        String extract = null;
        String subject = term;
        URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exsentences=6&exintro=&explaintext=&exsectionformat=plain&titles=" + subject.replace(" ", "%20"));
        String text = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) 
        {
            String line = null;
            while (null != (line = br.readLine())) {
                line = line.trim();
                if (true) {
                    text += line;
                }
            }
        }
     
        
        JSONObject json = new JSONObject(text);
        JSONObject query = json.getJSONObject("query");
        JSONObject pages = query.getJSONObject("pages");
        
        for(String key: pages.keySet()) 
        {
            JSONObject page = pages.getJSONObject(key);
            extract = page.getString("extract");
        }
        return extract;
    }

}
