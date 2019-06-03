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
 * @description: 权限实体类
 * @author: Rong.Jia
 * @date: 2019/04/17 12:34:22
 */
@Getter
@Setter
@Entity
@Table(name = "um_t_permissions")
public class Permission extends Base implements Serializable {

    private static final long serialVersionUID = -8657060387835915950L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;

    /**
     *  权限 -- 角色： 多对多
     */
    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="um_t_role_permission", joinColumns={@JoinColumn(name="permissionId")}, inverseJoinColumns={@JoinColumn(name="roleId")})
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, permission);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", id=" + id +
                ", updatedUser='" + updatedUser + '\'' +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", createdUser='" + createdUser + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
