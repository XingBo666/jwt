package com.xb.util;

import lombok.Data;
import lombok.Getter;

@Getter
public class UserTokenModel {

    private Long id;

    public UserTokenModel setId(Long id){
        this.id = id;
        return this;
    }
}
