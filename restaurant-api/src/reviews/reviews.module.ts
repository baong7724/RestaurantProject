import { Module } from '@nestjs/common';
import { ReviewsController } from './reviews.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Review } from './entity/review.entity';
import { ReviewsService } from './reviews.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([Review]),
  ],
  controllers: [ReviewsController],
  providers: [ReviewsService],
})
export class ReviewsModule {}
