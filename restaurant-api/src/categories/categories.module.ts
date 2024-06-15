import { Module } from '@nestjs/common';
import { CategoriesController } from './categories.controller';
import { Type } from '@angular-devkit/build-angular';
import { Category } from './entity/category.entity';
import { TypeOrmModule } from '@nestjs/typeorm';
import { CategoriesService } from './categories.service';
import { Food } from 'src/foods/entity/food.entity';
import { Review } from 'src/reviews/entity/review.entity';
import { User } from 'src/users/entity/user.entity';
import { Image } from 'src/images/entity/image.entity';
import { FoodsService } from 'src/foods/foods.service';
import { ImagesService } from 'src/images/images.service';
import { ReviewsService } from 'src/reviews/reviews.service';
import { UsersService } from 'src/users/users.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([Food, Category, Image, Review, User]),
],
  controllers: [CategoriesController],
  providers: [FoodsService, ImagesService, ReviewsService, CategoriesService, UsersService],
  exports: [FoodsService, ImagesService, ReviewsService, CategoriesService, UsersService],
})
export class CategoriesModule {}
