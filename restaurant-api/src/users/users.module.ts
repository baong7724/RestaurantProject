import { Module } from '@nestjs/common';
import { UsersController } from './users.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { User } from './entity/user.entity';
import { UsersService } from './users.service';
import { Food } from 'src/foods/entity/food.entity';
import { Category } from 'src/categories/entity/category.entity';
import { Image } from 'src/images/entity/image.entity';
import { Review } from 'src/reviews/entity/review.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([Food, Category, Image, Review, User]),
  ],
  controllers: [UsersController],
  providers: [UsersService],
  exports: [UsersService],
})
export class UsersModule {}
