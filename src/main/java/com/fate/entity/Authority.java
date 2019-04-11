package com.fate.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author oyg
 * @since 2018-07-01
 */
@TableName("sys_authority")
public class Authority extends Model<Authority> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 连接路径或方法
     */
    @TableField("data_url")
    private String dataUrl;
    /**
     * 菜单样式
     */
    @TableField("menu_class")
    private String menuClass;
    /**
     * 菜单编码
     */
    @TableField("menu_code")
    private String menuCode;
    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;
    /**
     * 上级菜单编码
     */
    @TableField("parent_menucode")
    private String parentMenucode;
    /**
     * 排序
     */
    private Long sequence;
    /**
     * 菜单类型(1是左导航菜单 2是按钮权限)
     */
    @TableField("menu_type")
    private String menuType;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(String menuClass) {
        this.menuClass = menuClass;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getParentMenucode() {
        return parentMenucode;
    }

    public void setParentMenucode(String parentMenucode) {
        this.parentMenucode = parentMenucode;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Authority{" +
        "id=" + id +
        ", dataUrl=" + dataUrl +
        ", menuClass=" + menuClass +
        ", menuCode=" + menuCode +
        ", menuName=" + menuName +
        ", parentMenucode=" + parentMenucode +
        ", sequence=" + sequence +
        ", menuType=" + menuType +
        ", createTime=" + createTime +
        "}";
    }
}
