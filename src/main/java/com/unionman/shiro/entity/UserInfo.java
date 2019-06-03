package com.unionman.shiro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unionman.shiro.common.domain.Base;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @description: 用户实体类
 * @author Rong.Jia
 * @date: 2019/04/17 12:34:22
 */
@Getter
@Setter
@Entity
@Table(name = "um_t_users")
public class UserInfo extends Base implements Serializable {

    private static final long serialVersionUID = 3400870240691466646L;

    /**
     * 名称（昵称或者真实姓名）
     */
    private String name;

    /**
     * 账号
     */
    @Column(unique = true)
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户 -- 角色 ： 多对1
     * 立即从数据库中进行加载数据;
     */
    @JsonBackReference
    @ManyToOne( fetch = FetchType.EAGER)
    @JoinTable(name = "um_t_user_role", joinColumns = {@JoinColumn(name = "userId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(id, userInfo.id) &&
                Objects.equals(name, userInfo.name) &&
                Objects.equals(account, userInfo.account) &&
                Objects.equals(password, userInfo.password) &&
                Objects.equals(gender, userInfo.gender) &&
                Objects.equals(mobile, userInfo.mobile) &&
                Objects.equals(email, userInfo.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, account, password, gender, mobile, email);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", updatedUser='" + updatedUser + '\'' +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", createdUser='" + createdUser + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
