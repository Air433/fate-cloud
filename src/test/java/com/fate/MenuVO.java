package com.fate;

/**
 * @Author oyg
 * @Date 2018/9/19/23:05
 */
public class MenuVO {

    private String code;

    private String name;

    private String type;

    private String permissionCode;

    private MenuVO[] menus;

    public MenuVO[] getMenus() {
        return menus;
    }

    public void setMenus(MenuVO[] menus) {
        this.menus = menus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
}
