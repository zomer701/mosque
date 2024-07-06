package com.live.mosque.data;

import lombok.Data;

import java.util.Map;

@Data
public class Mosque {
    private String uid;
    private Map<String, Object> meta;
}
