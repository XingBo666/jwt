package com.xb.util;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class BaseEntity {

    @Column(name = "del_flag")
    private Integer delFlag;

    @Column(name = "create_time")
    private Date createTime;
}
