package com.group.nice.restaurantapi.models;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE images SET deleted_at = CURRENT_TIMESTAMP() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@EqualsAndHashCode(callSuper = false)
public class Image extends BaseEntity {
    @Column(name = "url")
    protected String url;
    @Column(name = "is_main")
    protected boolean isMain;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    protected Food food;
}
