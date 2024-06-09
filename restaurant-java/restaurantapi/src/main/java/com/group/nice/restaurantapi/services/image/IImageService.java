package com.group.nice.restaurantapi.services.image;

import java.util.Collection;
import java.util.List;

import com.group.nice.restaurantapi.dtos.ImageDTO;

public interface IImageService {
        void addFoodImages(long foodId, Collection<ImageDTO> images);
        void deleteFoodImage(long foodId, long imageId);
        List<String> getURLFoodImages(long foodId);
        String getMainFoodImage(long foodId);
        void setMainFoodImage(long foodId, long imageId);
        void deleteAllImages(long foodId);
        Collection<ImageDTO> getFoodImages(long foodId);
}
