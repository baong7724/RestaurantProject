package com.group.nice.restaurantapi.models;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE reviews SET deleted_at = CURRENT_TIMESTAMP() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@EqualsAndHashCode(callSuper = false)
public class Review extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;
    @ManyToOne
    @JoinColumn(name = "food_id")
    protected Food food;
    @Column(name = "rating")
    protected int rating;
    @Column(name = "comment")
    protected String comment;
}
