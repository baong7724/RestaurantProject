package com.group.nice.restaurantapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.group.nice.restaurantapi.dtos.FoodDTO;
import com.group.nice.restaurantapi.dtos.PopularFoodDTO;
import com.group.nice.restaurantapi.dtos.PostFoodsAdd;
import com.group.nice.restaurantapi.services.food.IFoodService;

@RestController
@CrossOrigin
@RequestMapping("/foods")
public class FoodController {
    @Autowired
    private IFoodService iFoodService;
    
    @PostMapping("/add")
    public FoodDTO addFood(@RequestBody PostFoodsAdd food) {
        return iFoodService.addFood(food);
    }
    @PutMapping("/update/{id}")
    public FoodDTO updateFood(@PathVariable("id") Long id, @RequestBody FoodDTO food) {
        return iFoodService.updateFood(id, food);
    }
    @DeleteMapping("/delete/{id}")
    public boolean deleteFood(@PathVariable("id") Long id) {
        return iFoodService.deleteFood(id);
    }
    @GetMapping("/all")
    public List<FoodDTO> getAllFoods() {
        return iFoodService.getAllFoods();
    }
    @GetMapping("/{id}")
    public FoodDTO getFood(@PathVariable("id") Long id) {
        return iFoodService.getFood(id);
    }
    @GetMapping("/popular/{limit}")
    public List<PopularFoodDTO> getPopularFoods(@PathVariable("limit") Long limit){
        return iFoodService.getPopularFoods(limit);
    }
}
