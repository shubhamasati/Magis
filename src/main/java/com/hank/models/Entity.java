package com.hank.models;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private long id;
    private String body;
    private String name;
    private String role;
    private int start;
    private int end;
    private double confidence;
    private List<Object> entities = new ArrayList<>();
    private boolean suggested;
    private String value;
    private String type;
    private String domain;
    private String from;
    private String grain;
    private String normalized;
    private String resolved;
    private String second;
    private String to;
    private String unit;
    private String values;

    class From {
        private String unit;
        private String product;
        private String value;
        private String grain;
    }
}
