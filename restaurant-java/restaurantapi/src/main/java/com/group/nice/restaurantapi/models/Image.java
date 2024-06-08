package com.group.nice.restaurantapi.models;
import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    @Column(name = "url")
    protected String url;
    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    protected Food food;
}
