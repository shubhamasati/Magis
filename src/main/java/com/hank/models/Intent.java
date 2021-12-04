package com.hank.models;

import lombok.Data;

@Data
public class Intent {
    private String name;
    private long id;
    private double confidence;
}
