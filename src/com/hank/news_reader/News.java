package com.hank.news_reader;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class News extends DefaultHandler {

       //private Account acct;
       private String temp;
       //private ArrayList<Account> accList = new ArrayList<Account>();
       
       /** The main method sets things up for parsing
     * @param args
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException */
       public static void getNews(String[] args) throws IOException, SAXException, ParserConfigurationException 
       {
           
             // URLs for Hind news ---
           
               //  0. Top Stories India : "https://news.google.com/news/rss/headlines/section/topic/NATION.hi_in?ned=hi_in&hl=hi&gl=IN"
               //  1. sports news : "https://news.google.com/news/rss/headlines/section/topic/SPORTS?ned=hi_in&hl=hi" 
               //  2. World News : "https://news.google.com/news/rss/headlines/section/topic/WORLD.hi_in?ned=hi_in&hl=hi&gl=IN "
               //  3. Bussiness news :  "https://news.google.com/news/headlines/section/topic/BUSINESS.hi_in?ned=hi_in&hl=hi&gl=IN"
               //  4. Entertainment news : "https://news.google.com/news/rss/headlines/section/topic/ENTERTAINMENT.hi_in?ned=hi_in&hl=hi&gl=IN"
           
           
           
              // URLs for English news ---
                  
           
                  //  0. Top Stories  india : "https://news.google.com/news/rss/headlines/section/topic/NATION.en_in/India?ned=in&hl=en-IN&gl=IN"                 
                  //  1. Sports news :  "https://news.google.com/news/rss/headlines/section/topic/SPORTS.en_in/Sport?ned=in&hl=en-IN&gl=IN"
                  //  2. Bussiness news : "https://news.google.com/news/rss/headlines/section/topic/BUSINESS.en_in/Business?ned=in&hl=en-IN&gl=IN"
                  //  3. Entertainmemt news : "https://news.google.com/news/rss/headlines/section/topic/ENTERTAINMENT.en_in/Entertainment?ned=in&hl=en-IN&gl=IN"
                  //  4. Technology news : "https://news.google.com/news/rss/headlines/section/topic/TECHNOLOGY.en_in/Technology?ned=in&hl=en-IN&gl=IN"
                  //  5. World news : "https://news.google.com/news/rss/headlines/section/topic/WORLD.en_in/World?ned=in&hl=en-IN&gl=IN" 
                  
              
           
               URL url=new URL("https://news.google.com/news/rss/headlines/section/topic/ENTERTAINMENT.hi_in?ned=hi_in&hl=hi&gl=IN");
              //Create a "parser factory" for creating SAX parsers
              SAXParserFactory spfac = SAXParserFactory.newInstance();

              //Now use the parser factory to create a SAXParser object
              SAXParser sp = spfac.newSAXParser();

              //Create an instance of this class; it defines all the handler methods
              News handler = new News();

              //Finally, tell the parser to parse the input and notify the handler
              sp.parse(url.openConnection().getInputStream(), handler);
             
              

       }


       /*
        * When the parser encounters plain text (not XML elements),
        * it calls(this method, which accumulates them in a string buffer
        */
       
       public void characters(char[] buffer, int start, int length) {
              temp = new String(buffer, start, length);
       }
      
       /*
        * Every time the parser encounters the beginning of a new element,
        * it calls this method, which resets the string buffer
        */ 
       public void startElement(String uri, String localName,
                     String qName, Attributes attributes) throws SAXException {
              temp = "";
              if (qName.equalsIgnoreCase("Item")) {
                     

              }
       }

       /*
        * When the parser encounters the end of an element, it calls this method
        */
       public void endElement(String uri, String localName, String qName)
                     throws SAXException {

              if (qName.equalsIgnoreCase("Item")) {
                     

              } else if (qName.equalsIgnoreCase("title")) {
                     System.out.println(temp);
              } 
       }

       
      
}
