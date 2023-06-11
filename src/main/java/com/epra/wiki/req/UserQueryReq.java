package com.epra.wiki.req;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/11 10:15 上午
 * @Description: TODO
 */
public class UserQueryReq extends PageReq {

    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("loginName=").append(loginName);
        sb.append("]");
        return sb.toString();
    }
}
