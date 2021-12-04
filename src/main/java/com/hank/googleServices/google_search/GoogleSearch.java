package com.hank.google_search;


import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hank
 */
public class GoogleSearch {
 
   public static void search(String term) throws URISyntaxException, IOException
   {
       String s = "https://www.google.co.in/search?q="+term;
       URI uri= new URI(s);
       Desktop.getDesktop().browse(uri);
   
   }
   public static String getSearchTerm(String term) throws UnsupportedEncodingException, IOException 
   {
        
        String title1=null;
        String google = "http://www.google.com/search?q=";
        String search = term;
        String charset = "UTF-8";
        String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
        //System.out.println(Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get());
        Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");

        for (Element link : links) {
            String title = link.text();
            String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
            url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

            if (!url.startsWith("http")) {
                continue; // Ads/news/etc.
            }

                if(url.contains("https://en.wikipedia.org/"))
                {  
                    String s[]=title.split("-");
                    title1=s[0].trim();
                    break;
                }
             }
              return title1;
           }
 
}
