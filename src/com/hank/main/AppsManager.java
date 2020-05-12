package com.hank.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class AppsManager 
{
    static Process notepad, wmplayer, word, vlc, powerpoint, wordpad, chrome, calc, camera, excel, controlPanel, taskManage, 
            regedit, logout, stutdown;
            
    static String notepad_path = "notepad.exe";
    static String wmplayer_path = "C:\\Program Files (x86)\\Windows Media Player\\wmplayer.exe";
    static String word_path = "C:\\Program Files\\Microsoft Office\\Office16\\WINWORD.EXE";
    static String vlc_path = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";
    static String powerpoint_path= "C:\\Program Files\\Microsoft Office\\Office16\\POWERPNT.EXE";
    static String wordpad_path = "C:\\Program Files (x86)\\Windows NT\\Accessories\\wordpad.exe";
    static String chrome_path= "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
    static String cmd_path = "cmd.exe";
    static String calc_path = "calc.exe";
    static String camera_path = "";
    static String excel_path = "C:\\Program Files\\Microsoft Office\\Office16\\EXCEL.EXE";
    static String controlPanel_path = "control panel";
    static String taskManager_path = "taskmgr";
    static String regedit_path = "regedit";
    static String logout_path = "";
    static String stutdown_path = " ";
    
    static Runtime r = Runtime.getRuntime();
    
    static Robot robot ;
    
    public static void open(String s) throws IOException, InterruptedException, AWTException
    {
        switch(s)
        {
            case "notepad" :
                notepad = r.exec(notepad_path);
                break;
            case "wordpad" :
                wordpad = r.exec(wordpad_path);
                break;
            case "office word" :
                word = r.exec(word_path);
                break;
            case "excel":
                excel = r.exec(excel_path);
                break;
            case "powerpoint" :
                powerpoint = r.exec(powerpoint_path);
                break;
            case "chrome" :
                chrome = r.exec(chrome_path);
                break;
            case "calc":
                calc = r.exec(calc_path);
                break;
            case "vlc":
                vlc = r.exec(vlc_path);
                break;
            case "wmplayer" :
                wmplayer = r.exec(wmplayer_path);
                break;
            case "task manager" :
                taskManage = r.exec(taskManager_path);
                break;
            case "control panel" :
                controlPanel = r.exec(controlPanel_path);
                break;
            case "logout" :
                r.exec(logout_path);
                break;
            case "desktop" :
                     robot = new Robot();
                     System.err.println("I am here");
                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_D);
                     
                     break;
        }
    }
    
    public static void close(String s)
    {
        switch(s)
        {
            case "notepad" :
                notepad.destroy();
                break;
            case "wordpad" :
                wordpad.destroy();
                break;
            case "office word" :
                word.destroy();
                break;
            case "excel":
                excel.destroy();
                break;
            case "powerpoint" :
                powerpoint.destroy();
                break;
            case "chrome" :
                chrome.destroy();
                break;
            case "calc":
                calc.destroyForcibly();
                break;
            case "vlc":
                vlc.destroy();
                break;
            case "wmplayer" :
                wmplayer.destroy();
                break;
            case "task manager" :
                taskManage.destroy();
                break;
            case "control panel" :
                controlPanel.destroyForcibly();
                break;
    }
  }
    
   public static void playVideo(String url) throws IOException
   {
       
       String s[] = {vlc_path , url};
       if(vlc == null)
           vlc = r.exec(s);
       else
       {
           vlc.destroy();
           vlc = r.exec(s);
       }
   }
}