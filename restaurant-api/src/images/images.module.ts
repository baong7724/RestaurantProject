import { Module } from '@nestjs/common';
import { ImagesController } from './images.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Image } from './entity/image.entity';
import { ImagesService } from './images.service';
import { Food } from 'src/foods/entity/food.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([Image, Food]),
  ],
  controllers: [ImagesController],
  providers: [ImagesService],
  exports: [ImagesService],
})
export class ImagesModule {}
