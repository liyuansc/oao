package com.oao.common.model;

import lombok.Data;

@Data
public class OaoGrantedAuthority {
    public final static String DELIMITER = "-";
    private String id;
    private String code;

    public OaoGrantedAuthority(String id, String code) {
        this.id = id;
        this.code = code;
    }

    public static OaoGrantedAuthority parse(String text) {
        String[] arr = text.split(DELIMITER);
        return new OaoGrantedAuthority(arr[0], arr[1]);
    }

    public String getAuthority() {
        return String.join(DELIMITER, this.id, this.code);
    }
}
