package com.group.nice.restaurantapi.dtos;
import java.util.Collection;
import lombok.*;

@Data
@NoArgsConstructor
public class PostFoodsAdd {
    private long id;
    private String name;
    private String description;
    private double price;
    private Collection<CategoryDTO> categories;
    private Collection<ImageDTO> images;
}
