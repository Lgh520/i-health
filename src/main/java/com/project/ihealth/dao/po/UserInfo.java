package com.project.ihealth.dao.po;

import lombok.Data;

@Data
public class UserInfo {
    private String userId;

    private String passwd;

    private String userName;

    private String sex;

    private String birth;

    private String city;

    private String phone;
}