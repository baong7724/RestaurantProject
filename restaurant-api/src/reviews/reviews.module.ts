import { Module } from '@nestjs/common';
import { ReviewsController } from './reviews.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Review } from './entity/review.entity';
import { ReviewsService } from './reviews.service';
import { Food } from 'src/foods/entity/food.entity';
import { FoodsService } from 'src/foods/foods.service';
import { User } from 'src/users/entity/user.entity';
import { UsersService } from 'src/users/users.service';
import { Category } from 'src/categories/entity/category.entity';
import { Image } from 'src/images/entity/image.entity';
import { ImagesService } from 'src/images/images.service';
import { CategoriesService } from 'src/categories/categories.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([Food, Category, Image, Review, User]),
  ],
  controllers: [ReviewsController],
  providers: [FoodsService, ImagesService, ReviewsService, CategoriesService, UsersService],
  exports: [FoodsService, ImagesService, ReviewsService, CategoriesService, UsersService],
})
export class ReviewsModule {}

