package com.cx.wms.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

public class User extends BaseEntity {

    private static final long serialVersionUID = 3L;

    private String user_id;
    private String nick_name;

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", nick_name='" + nick_name + '\'' +
                '}';
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
