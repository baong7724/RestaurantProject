package com.group.nice.restaurantapi.models;
import java.util.Collection;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {
    @Column(name = "username")
    protected String username;
    @Column(name = "password")
    protected String password;
    @Column(name = "email")
    protected String email;
    @Column(name = "role")
    protected String role;
    @Column(name = "auth_id")
    protected String authId;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    protected Collection<Food> foods;
}
