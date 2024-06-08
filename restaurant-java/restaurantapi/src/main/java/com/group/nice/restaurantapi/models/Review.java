package com.group.nice.restaurantapi.models;
import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    protected User user;
    @OneToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    protected Food food;
    @Column(name = "rating")
    protected int rating;
    @Column(name = "comment")
    protected String comment;
}
