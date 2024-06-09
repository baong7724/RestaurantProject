package com.group.nice.restaurantapi.services.food;

import java.util.List;

import com.group.nice.restaurantapi.dtos.FoodDTO;
import com.group.nice.restaurantapi.dtos.PopularFoodDTO;
import com.group.nice.restaurantapi.dtos.PostFoodsAdd;

public interface IFoodService {
    public FoodDTO addFood(PostFoodsAdd food);
    public FoodDTO updateFood(long id, FoodDTO food);
    public boolean deleteFood(long id);
    public FoodDTO getFood(long id);
    public List<FoodDTO> getAllFoods();
    public List<PopularFoodDTO> getPopularFoods(long limit);
}
