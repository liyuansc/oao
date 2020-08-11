package com.oao.security;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;

//TODO 此类未完成
public class OaoClientDetails extends BaseClientDetails {

    public OaoClientDetails(String clientId, String grantTypes, String authorities) {
        super(clientId, null, null, null, null);
    }


}
