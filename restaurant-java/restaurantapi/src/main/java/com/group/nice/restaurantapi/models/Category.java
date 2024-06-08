package com.group.nice.restaurantapi.models;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@SQLDelete(sql = "UPDATE categories SET deleted_at = CURRENT_TIMESTAMP() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
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
    @Column(name = "name")
    protected String name;
    @Column(name = "color_code")
    protected String colorCode;
    @Column(name = "tag")
    protected String tag;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
    protected Collection<Food> foods;
}
