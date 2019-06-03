package com.unionman.shiro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unionman.shiro.common.domain.Base;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @description: 权限管理，角色实体类
 * @author: Rong.Jia
 * @date: 2019/04/17 12:34:22
 */
@Getter
@Setter
@Entity
@Table(name = "um_t_roles")
public class Role extends Base implements Serializable {

    private static final long serialVersionUID = -2060340050037731679L;

    /**
     * id，编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的:
     */
    private String role;

    /**
     * 角色 -- 权限关系：多对多关系
     */
    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "um_t_role_permission",joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private Set<Permission> permissions;

    /**
     * 角色- 用户 关系定: 一个角色对应多个用户
     *
     */
    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name="um_t_user_role",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="userId")})
    private Set<UserInfo> userInfos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) &&
                Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", id=" + id +
                ", updatedUser='" + updatedUser + '\'' +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", createdUser='" + createdUser + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
