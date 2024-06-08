package com.group.nice.restaurantapi.models;
import java.util.Collection;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "foods")
@Data
@SQLDelete(sql = "UPDATE foods SET deleted_at = CURRENT_TIMESTAMP() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class Food extends BaseEntity {
    @Column(name = "name")
    protected String name;
    @Column(name = "description")
    protected String description;
    @Column(name = "price")
    protected double price;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "foods_categories",
        joinColumns = @JoinColumn(name = "food_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    protected Collection<Category> categories;
    @OneToMany(mappedBy = "food")
    protected Collection<Image> images;
    @ManyToMany
    @JoinTable(
        name = "user_favorite_foods",
        joinColumns = @JoinColumn(name = "food_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    protected Collection<User> users;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    protected Collection<Review> reviews;
}
