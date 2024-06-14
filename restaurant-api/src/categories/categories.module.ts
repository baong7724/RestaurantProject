import { Module } from '@nestjs/common';
import { CategoriesController } from './categories.controller';
import { Type } from '@angular-devkit/build-angular';
import { Category } from './entity/category.entity';
import { TypeOrmModule } from '@nestjs/typeorm';
import { CategoriesService } from './categories.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([Category]),
],
  controllers: [CategoriesController],
  providers: [CategoriesService],
  exports: [CategoriesService], 
})
export class CategoriesModule {}
