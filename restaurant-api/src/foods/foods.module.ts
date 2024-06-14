import { Module } from '@nestjs/common';
import { FoodsController } from './foods.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Food } from './entity/food.entity';
import { FoodsService } from './foods.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([Food]),
  ],
  controllers: [FoodsController],
  providers: [FoodsService],
})
export class FoodsModule {}
