package com.hank.models;

import java.util.HashMap;
import java.util.List;

public class CommandRequest {
    private String command;
    private List<Intent> intents;
    private HashMap<String, Object> entities;
    private HashMap<String, Traits> traits;
}
