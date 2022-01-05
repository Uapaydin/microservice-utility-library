package com.turkcell.microserviceutil.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String token;
    private String authorities;
    private String retailerId;

    private List<String> getMappedAuthorities(){
        if(StringUtils.isEmpty(authorities)){
            return new ArrayList<String>();
        }else if(authorities.indexOf(",")>0){
            return Arrays.asList(authorities.split(","));
        }
        return new ArrayList<String>();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", authorities=" + authorities +
                '}';
    }

}
