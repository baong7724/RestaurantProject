package com.group.nice.restaurantapi.services.food;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.nice.restaurantapi.dtos.*;
import com.group.nice.restaurantapi.models.*;
import com.group.nice.restaurantapi.repositories.*;
import com.group.nice.restaurantapi.services.image.IImageService;

@Service
public class FoodServiceImpl implements IFoodService{
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private IImageService imageService;
    @Autowired
    private ImageReopsitory imageRepository;
    

    @Override
    public FoodDTO addFood(PostFoodsAdd foodDTO) {
        if (foodDTO == null) {
            throw new IllegalArgumentException("FoodDTO is null");
        }
            if (foodDTO.getName() == null || foodDTO.getName().isEmpty() ||
            foodDTO.getDescription() == null || foodDTO.getDescription().isEmpty() ||
            foodDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("Name, description, and price are required fields.");
        }
        Food food = new Food();
        food.setName(foodDTO.getName());
        food.setDescription(foodDTO.getDescription());
        food.setPrice(foodDTO.getPrice());
        
        CategoryDTO origin = null;
        Collection<CategoryDTO> ingredients = new ArrayList<>();
        Collection<Category> categoriesForFood = new ArrayList<>();
        if (foodDTO.getCategories() != null && !foodDTO.getCategories().isEmpty()) {
            for (CategoryDTO categoryDTO : foodDTO.getCategories()) {            
                if ("origin".equalsIgnoreCase(categoryDTO.getTag())) {
                    origin = categoryDTO; 
                } else if ("ingredient".equalsIgnoreCase(categoryDTO.getTag()) || 
                        "ingredients".equalsIgnoreCase(categoryDTO.getTag())) {
                    ingredients.add(categoryDTO);
                } 
                Category category = categoryRepository.findById(categoryDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryDTO.getId()));
                categoriesForFood.add(category);
            }

            food.setCategories(categoriesForFood);
        }
        foodRepository.save(food);
        if (foodDTO.getImages() != null && !foodDTO.getImages().isEmpty()) {
            imageService.addFoodImages(food.getId(), foodDTO.getImages());
        }
        Collection<ImageDTO> imgs = imageService.getFoodImages(food.getId());
        List<Image> images = new ArrayList<>();
        for (ImageDTO img : imgs) {
            Image image = new Image();
            image.setUrl(img.getUrl());
            image.setMain(img.isMain());
            image.setFood(food);
            images.add(image);
        }
        food.setImages(images);
        foodRepository.save(food);
        return new FoodDTO(
            food.getId(),
            food.getName(),
            food.getDescription(),
            food.getPrice(),
            (categoriesForFood != null) ? foodDTO.getCategories() : null,
            (food.getImages() != null) ?  imgs: null,
            0.0,
            origin,
            ingredients,
            null
        );
    }
    
    @Override
    public FoodDTO updateFood(long id, FoodDTO foodDTO) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
    
        boolean isUpdated = false;
    
        if (!food.getName().equals(foodDTO.getName())) {
            food.setName(foodDTO.getName());
            isUpdated = true;
        }
    
        if (!food.getDescription().equals(foodDTO.getDescription())) {
            food.setDescription(foodDTO.getDescription());
            isUpdated = true;
        }
    
        if (food.getPrice() != foodDTO.getPrice()) {
            food.setPrice(foodDTO.getPrice());
            isUpdated = true;
        }
    
        if (foodDTO.getCategories() != null) {
            Collection<Category> newCategories = new ArrayList<>();
            for (CategoryDTO categoryDTO : foodDTO.getCategories()) {
                Category category = categoryRepository.findById(categoryDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryDTO.getId()));
                newCategories.add(category);
            }
            if (!food.getCategories().equals(newCategories)) {
                food.setCategories(newCategories);
                isUpdated = true;
            }
        }
    
        if (foodDTO.getImages() != null && !foodDTO.getImages().isEmpty()) {
            imageService.addFoodImages(food.getId(), foodDTO.getImages());
            List<String> imageUrls = imageService.getURLFoodImages(food.getId());
            Collection<Image> newImages = new ArrayList<>();
            for (String url : imageUrls) {
                Image image = new Image();
                image.setUrl(url);
                image.setMain(image.isMain());
                image.setFood(food);
                newImages.add(image);
            }
            if (!food.getImages().equals(newImages)) {
                food.setImages(newImages);
                isUpdated = true;
            }
        }
        if (isUpdated) {
            foodRepository.save(food);
        }
        foodDTO.setId(food.getId());
        return foodDTO;
    }
    @Override
    public boolean deleteFood(long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
        imageService.deleteAllImages(id);
        food.setDeletedAt(new Date());
        foodRepository.save(food);
        return true;
    }
    @Override
    public FoodDTO getFood(long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
        
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(food.getId());
        foodDTO.setName(food.getName());
        foodDTO.setDescription(food.getDescription());
        foodDTO.setPrice(food.getPrice());
    
        List<CategoryDTO> categories = new ArrayList<>();
        CategoryDTO origin = null;
        List<CategoryDTO> ingredients = new ArrayList<>();
        
        for (Category category : food.getCategories()) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setTag(category.getTag());
            categoryDTO.setColorCode(category.getColorCode());
            categories.add(categoryDTO);
            
            if ("origin".equals(category.getTag())) {
                origin = categoryDTO;
            } else if ("ingredient".equals(category.getTag())) {
                ingredients.add(categoryDTO);
            }
        }
        
        foodDTO.setCategories(categories);
        foodDTO.setOrigin(origin);
        foodDTO.setIngredients(ingredients);
        
        Collection<ImageDTO> imgs = imageService.getFoodImages(food.getId());
        foodDTO.setImages(imgs);
    
        double averageRating = food.getReviews().stream().mapToInt(Review::getRating).average().orElse(0.0);
        foodDTO.setRating(averageRating);
        
        List<ReviewDTO> reviews = new ArrayList<>();
        for (Review review : food.getReviews()) {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId(review.getId());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setComment(review.getComment());
            
            UserBaseDTO userBaseDTO = new UserBaseDTO();
            userBaseDTO.setId(review.getUser().getId());
            userBaseDTO.setUsername(review.getUser().getUsername());
            userBaseDTO.setEmail(review.getUser().getEmail());
            userBaseDTO.setRole(review.getUser().getRole());
            
            reviewDTO.setUser(userBaseDTO);
            reviews.add(reviewDTO);
        }
        foodDTO.setReviews(reviews);
    
        return foodDTO;
    }
    @Override
    public List<FoodDTO> getAllFoods() {
        List<Food> foods = foodRepository.findAll();
        List<FoodDTO> foodDTOs = new ArrayList<>();
        for (Food food : foods) {
            FoodDTO foodDTO = new FoodDTO();
            foodDTO.setId(food.getId());
            foodDTO.setName(food.getName());
            foodDTO.setDescription(food.getDescription());
            foodDTO.setPrice(food.getPrice());
    
            List<CategoryDTO> categories = new ArrayList<>();
            CategoryDTO origin = null;
            List<CategoryDTO> ingredients = new ArrayList<>();
            
            for (Category category : food.getCategories()) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
                categoryDTO.setTag(category.getTag());
                categoryDTO.setColorCode(category.getColorCode());
                categories.add(categoryDTO);
                
                if ("origin".equals(category.getTag())) {
                    origin = categoryDTO;
                } else if ("ingredient".equals(category.getTag())) {
                    ingredients.add(categoryDTO);
                }
            }
            
            foodDTO.setCategories(categories);
            foodDTO.setOrigin(origin);
            foodDTO.setIngredients(ingredients);
            
            Collection<ImageDTO> imgs = imageService.getFoodImages(food.getId());
            foodDTO.setImages(imgs);
        
            double averageRating = food.getReviews().stream().mapToInt(Review::getRating).average().orElse(0.0);
            foodDTO.setRating(averageRating);
            
            List<ReviewDTO> reviews = new ArrayList<>();
            for (Review review : food.getReviews()) {
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setId(review.getId());
                reviewDTO.setRating(review.getRating());
                reviewDTO.setComment(review.getComment());
                
                UserBaseDTO userBaseDTO = new UserBaseDTO();
                userBaseDTO.setId(review.getUser().getId());
                userBaseDTO.setUsername(review.getUser().getUsername());
                userBaseDTO.setEmail(review.getUser().getEmail());
                userBaseDTO.setRole(review.getUser().getRole());
                
                reviewDTO.setUser(userBaseDTO);
                reviews.add(reviewDTO);
            }
            foodDTO.setReviews(reviews);
            
            foodDTOs.add(foodDTO);
        }
        return foodDTOs;
    }
    @Override
    public List<PopularFoodDTO> getPopularFoods(long limit) {
        List<Food> foods = foodRepository.findAll();
        List<PopularFoodDTO> popularFoodDTOs = new ArrayList<>();

        for (Food food : foods) {
            double averageRating = food.getReviews().stream().mapToInt(Review::getRating).average().orElse(0.0);

            PopularFoodDTO popularFoodDTO = new PopularFoodDTO();
            popularFoodDTO.setFoodId(food.getId());
            popularFoodDTO.setFoodName(food.getName());
            popularFoodDTO.setFoodDescription(food.getDescription());
            popularFoodDTO.setFoodPrice(food.getPrice());
            popularFoodDTO.setFoodRating(averageRating);

            List<String> imageUrls = imageService.getURLFoodImages(food.getId());
            if (!imageUrls.isEmpty()) {
                popularFoodDTO.setFoodImage(imageUrls.get(0));
            } else {
                popularFoodDTO.setFoodImage(""); 
            }

            popularFoodDTOs.add(popularFoodDTO);
        }
        popularFoodDTOs.sort(Comparator.comparingDouble(PopularFoodDTO::getFoodRating).reversed());
        return popularFoodDTOs.stream().limit(limit).collect(Collectors.toList());
    }

}
