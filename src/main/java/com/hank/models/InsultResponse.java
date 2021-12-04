package com.hank.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsultResponse {
    private long number;
    private String language;
    private String insult;
    private String created;
    private String shown;
    private String createdBy;
    private boolean active;
    private String comment;
}
