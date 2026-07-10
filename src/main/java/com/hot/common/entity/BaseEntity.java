package com.hot.common.entity;

import lombok.Data;

@Data
public class BaseEntity {

    private String userid;
    private Integer pageNum;
    private Integer pageSize;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;


}
