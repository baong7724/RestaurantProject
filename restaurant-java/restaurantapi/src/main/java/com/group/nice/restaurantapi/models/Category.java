package com.group.nice.restaurantapi.models;
import java.util.Collection;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@SQLDelete(sql = "UPDATE categories SET deleted_at = CURRENT_TIMESTAMP() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {
    @Column(name = "name")
    protected String name;
    @Column(name = "color_code")
    protected String colorCode;
    @Column(name = "tag")
    protected String tag;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
    protected Collection<Food> foods;
}
