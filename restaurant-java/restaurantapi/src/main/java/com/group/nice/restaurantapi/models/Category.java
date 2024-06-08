package com.group.nice.restaurantapi.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    @Column(name = "name")
    protected String name;
    @Column(name = "color_code")
    protected String colorCode;
}
