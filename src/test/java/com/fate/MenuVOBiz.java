package com.fate;

import java.io.Serializable;

/**
 * @Author oyg
 * @Date 2018/9/19/23:04
 */
public class MenuVOBiz implements Serializable {

    private String clientType;

    private MenuVO menu;

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public MenuVO getMenu() {
        return menu;
    }

    public void setMenu(MenuVO menu) {
        this.menu = menu;
    }
}
