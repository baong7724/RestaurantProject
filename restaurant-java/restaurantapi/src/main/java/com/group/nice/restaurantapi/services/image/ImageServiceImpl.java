package com.group.nice.restaurantapi.services.image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.nice.restaurantapi.dtos.ImageDTO;
import com.group.nice.restaurantapi.models.Food;
import com.group.nice.restaurantapi.models.Image;
import com.group.nice.restaurantapi.repositories.FoodRepository;
import com.group.nice.restaurantapi.repositories.ImageReopsitory;

@Service
public class ImageServiceImpl implements IImageService {
    @Autowired
    private ImageReopsitory imageRepository;
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public void addFoodImages(long foodId, Collection<ImageDTO> images) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodId));
        Collection<Image> imagesList = new ArrayList<>();
        for (ImageDTO imageDTO : images) {
            Image image = new Image();
            image.setUrl(imageDTO.getUrl());
            image.setMain(imageDTO.isMain());
            image.setFood(food);
            imagesList.add(image);
        }
        imageRepository.saveAll(imagesList);
        food.setImages(imagesList);
        foodRepository.save(food);
    }
    @Override
    public void deleteFoodImage(long foodId, long imageId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodId));
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
        food.getImages().remove(image);
        foodRepository.save(food);
        image.setDeletedAt(new Date());
        imageRepository.save(image);
    }
    @Override
    public List<String> getFoodImages(long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodId));
        List<String> images = new ArrayList<>();
        for (Image image : food.getImages()) {
            images.add(image.getUrl());
        }
        return images;
    }
    @Override
    public String getMainFoodImage(long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodId));
        for (Image image : food.getImages()) {
            if (image.isMain()) {
                return image.getUrl();
            }
        }
        return null;
    }
    @Override
    public void setMainFoodImage(long foodId, long imageId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodId));
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
        for (Image img : food.getImages()) {
            if (img.isMain()) {
                img.setMain(false);
                imageRepository.save(img);
            }
        }
        image.setMain(true);
        imageRepository.save(image);
    }
    @Override
    public void deleteAllImages(long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodId));
        for (Image image : food.getImages()) {
            image.setDeletedAt(new Date());
            imageRepository.save(image);
        }
    }
}
