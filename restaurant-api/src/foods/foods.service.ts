import { HttpException, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Food } from './entity/food.entity';
import { Category } from 'src/categories/entity/category.entity';
import { Image } from 'src/images/entity/image.entity';
import { Review } from 'src/reviews/entity/review.entity';
import { FoodDto } from './dto/food.dto';
import { HttpStatusCode } from 'src/common/enum/httpstatus.enum';
import { ImagesService } from 'src/images/images.service';
import { FoodBaseDto } from './dto/food-base.dto';
import { ReviewsService } from 'src/reviews/reviews.service';
import { CategoryDTO } from 'src/categories/dto/category.dto';
import { plainToClass } from 'class-transformer';
import { ImageDTO } from 'src/images/dto/image.dto';
import { ReviewDto } from 'src/reviews/dto/review.dto';
import { OriginDto } from '../categories/dto/origin.dto';
import { IngredientDTO } from 'src/categories/dto/ingredient.dto';

@Injectable()
export class FoodsService {
    constructor(
        @InjectRepository(Food) private readonly foodRepository: Repository<Food>,
        @InjectRepository(Category) private readonly categoryRepository: Repository<Category>,
        @InjectRepository(Image) private readonly imageRepository: Repository<Image>,
        @InjectRepository(Review) private readonly reviewRepository: Repository<Review>,
        private readonly imagesService: ImagesService,
        private readonly reviewsService: ReviewsService
        
    ){}
    async createFood(foodDto: FoodDto): Promise<FoodDto>{
        if (!foodDto){
            throw new HttpException('No data provided', HttpStatusCode.BAD_REQUEST);
        }
        let food = new Food();
        food.name = foodDto.name;
        food.description = foodDto.description;
        food.price = foodDto.price;
        if(foodDto.categories){
            const categories = await this.categoryRepository.find(
                {
                    where: foodDto.categories.map(category => ({id: category.id}))
                }
            );
            if (!categories){
                throw new HttpException('Categories not found', HttpStatusCode.NOT_FOUND);
            }
            food.categories = categories;
        }
        food = await this.foodRepository.save(food);
        if(foodDto.images){
            await this.imagesService.createImages(food.id, foodDto.images);
        }
        food = await this.foodRepository.save(food);
        foodDto.id = food.id;
        return foodDto;
    }

    async updateFood(foodId: number, foodDto: FoodDto): Promise<FoodDto>{
        if (!foodDto){
            throw new HttpException('No data provided', HttpStatusCode.BAD_REQUEST);
        }
        let food = await this.foodRepository.findOne({
            where: {id: foodId},
            relations: ['categories']
        });
        if (!food){
            throw new HttpException('Food not found', HttpStatusCode.NOT_FOUND);
        }
        if (foodDto.name){
            food.name = foodDto.name;
        }
        if (foodDto.description){
            food.description = foodDto.description;
        }
        if (foodDto.price){
            food.price = foodDto.price;
        }
        if(foodDto.categories){
            const categories = await this.categoryRepository.find(
                {
                    where: foodDto.categories.map(category => ({id: category.id}))
                }
            );
            if (!categories){
                throw new HttpException('Categories not found', HttpStatusCode.NOT_FOUND);
            }
            food.categories = categories;
        }
        if(foodDto.images){
            await this.imagesService.createImages(food.id, foodDto.images);
        }
        food = await this.foodRepository.save(food);
        foodDto.id = food.id;
        return foodDto;
    }

    async deleteFood(id: number): Promise<boolean>{
        const food = await this.foodRepository.findOne({
            where: {id},
            relations: ['categories']
        });
        if (!food){
            throw new HttpException('Food not found', HttpStatusCode.NOT_FOUND);
        }
        food.categories.map(async category => category.deletedAt = new Date());
        food.images.map(async image => image.deletedAt = new Date());
        food.deletedAt = new Date();
        await this.foodRepository.save(food);
        return true;
    }

    foodBase(food: Food): FoodBaseDto {
        const foodDto = new FoodBaseDto();
        const categories: CategoryDTO[] = [];
        let origin: OriginDto | undefined = undefined;
        const ingredients: IngredientDTO[] = [];
        foodDto.id = food.id;
        foodDto.name = food.name;
        foodDto.description = food.description;
        foodDto.price = food.price;
        food.categories.map(category => {
            if (category.tag === 'origin'){
                origin = {
                    id: category.id,
                    originalName: category.name
                };
            } else if (category.tag === 'ingredient'){
                ingredients.push({
                    id: category.id,
                    ingredientName: category.name
                });
            } else {
                categories.push({
                    id: category.id,
                    name: category.name
                });
            }
        });
        foodDto.categories = categories;
        foodDto.origin = origin;
        foodDto.ingredients = ingredients;
        foodDto.images = food.images.map(image => plainToClass(ImageDTO, image));
        foodDto.reviews = food.reviews.map(review => plainToClass(ReviewDto, review));
        return foodDto;
    }

    async getFoods(): Promise<FoodBaseDto[]>{
        const foods = await this.foodRepository.find({
            where: {deletedAt: null},
            relations: ['categories', 'images']
        });

        return foods.map(food => {
            let foodDto = this.foodBase(food);
            return foodDto;
        });
    }

    async getFood(id: number): Promise<FoodBaseDto>{
        const food = await this.foodRepository.findOne({
            where: {id, deletedAt: null},
            relations: ['categories', 'images', 'reviews']
        });
        if (!food){
            throw new HttpException('Food not found', HttpStatusCode.NOT_FOUND);
        }
        let foodDto = this.foodBase(food);
        return foodDto;
    }

    async getPopularFoods(limit: number): Promise<FoodBaseDto[]>{
        const foods = await this.getFoods();
        foods.sort((a, b) => b.rating - a.rating);
        return foods.slice(0, limit-1);
    }

    async getFoodsByCategory(categoryId: number): Promise<FoodBaseDto[]>{
        const foods = await this.foodRepository.find({
            where: {deletedAt: null, categories: {id: categoryId}},
            relations: ['categories', 'images']
        });
        return foods.map(food => {
            let foodDto = this.foodBase(food);
            return foodDto;
        });
    }
}
