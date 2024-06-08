package com.group.nice.restaurantapi.models;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "created_at")
    @CreationTimestamp
    protected Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    protected Date updatedAt;

    @Column(name = "deleted_at")
    protected Date deletedAt;
}
