package com.hank.config;

import com.hank.speechengine.recognizer.Recognizer;
import lombok.Data;

@Data
public class UserGlobalConfig {
    private String userName = "Shubham";
    private Recognizer.Languages userSetLanguage = Recognizer.Languages.HINDI;
}
