package com.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 用户实体
 *
 * @author jerome
 * @date 2017/2/24 15:35
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // null不序列化
public class User {
    private Long id;
    private String login_name;
    private String pwd;
    // 平台用户唯一标识(md5(id))
    private String openid;
    // 用户头像
    private String head_pic_key;
    private Integer age;

    public String getHead_pic_key() {
        return head_pic_key;
    }

    public void setHead_pic_key(String head_pic_key) {
        this.head_pic_key = head_pic_key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
