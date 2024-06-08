package com.group.nice.restaurantapi.models;
import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
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
