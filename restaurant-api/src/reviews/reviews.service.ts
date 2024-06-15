import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Review } from './entity/review.entity';
import { Food } from 'src/foods/entity/food.entity';
import { ReviewDto } from './dto/review.dto';
import { plainToClass } from 'class-transformer';
import { User } from 'src/users/entity/user.entity';

@Injectable()
export class ReviewsService {
    constructor(
        @InjectRepository(Review) private readonly reviewRepository: Repository<Review>,
        @InjectRepository(Food) private readonly foodRepository: Repository<Food>,
        @InjectRepository(User) private readonly userRepository: Repository<User>
    ) {}

    async getReviews(foodId: number): Promise<ReviewDto[]> {
        const food = await this.foodRepository.findOne(
            {
                where: { id: foodId },
                relations: ['reviews'],
            }
        )
        if (!food) {
            throw new HttpException('Food not found', HttpStatus.NOT_FOUND);
        }
        if(!food.reviews) {
            return [];
        }
        const foodDtos = food.reviews.map(review => plainToClass(ReviewDto, review));
        return foodDtos;
    }

    async createReview(foodId: number, reviewDto: ReviewDto): Promise<ReviewDto> {
        const food = await this.foodRepository.findOne(
            {
                where: { id: foodId },
                relations: ['reviews'],
            }
        )
        if (!food) {
            throw new HttpException('Food not found', HttpStatus.NOT_FOUND);
        }
        const user = await this.userRepository.findOne({
            where: { id: reviewDto.userId },
        })
        if (!user) {
            throw new HttpException('User not found', HttpStatus.NOT_FOUND);
        }
        if (food.reviews.find(review => review.user.id === reviewDto.userId)){
            throw new HttpException('User has already reviewed this food', HttpStatus.BAD_REQUEST);
        }
        if (reviewDto.rating < 1 || reviewDto.rating > 5) {
            throw new HttpException('Rating must be between 1 and 5', HttpStatus.BAD_REQUEST);  
        }
        let review = plainToClass(Review, reviewDto);
        review.food = food;
        review = await this.reviewRepository.save(review);
        return plainToClass(ReviewDto, review);
    }

    async deleteReview(foodId: number, userId: number): Promise<boolean> {
        const food = await this.foodRepository.findOne(
            {
                where: { id: foodId },
                relations: ['reviews'],
            }
        )
        if (!food) {
            throw new HttpException('Food not found', HttpStatus.NOT_FOUND);
        }
        const review = food.reviews.find(review => review.user.id === userId);
        if (!review) {
            throw new HttpException('Review not found', HttpStatus.NOT_FOUND);
        }
        review.food = null;
        review.deletedAt = new Date();
        await this.reviewRepository.save(review);
        return true;
    }

    async updateReview(foodId: number, userId: number, reviewDto: ReviewDto): Promise<ReviewDto> {
        const food = await this.foodRepository.findOne(
            {
                where: { id: foodId },
                relations: ['reviews'],
            }
        )
        if (!food) {
            throw new HttpException('Food not found', HttpStatus.NOT_FOUND);
        }
        const review = food.reviews.find(review => review.user.id === userId);
        if (!review) {
            throw new HttpException('Review not found', HttpStatus.NOT_FOUND);
        }
        if (reviewDto.rating < 1 || reviewDto.rating > 5) {
            throw new HttpException('Rating must be between 1 and 5', HttpStatus.BAD_REQUEST);  
        }
        if (reviewDto.comment) {
            review.comment = reviewDto.comment;
        }
        if (reviewDto.rating) {
            review.rating = reviewDto.rating;
        }
        await this.reviewRepository.save(review);
        return plainToClass(ReviewDto, review);
    }

    async getReview(reviewId: number): Promise<ReviewDto> {
        const review = await this.reviewRepository.findOne(
            {
                where: { id: reviewId },
            }
        )
        if (!review) {
            throw new HttpException('Review not found', HttpStatus.NOT_FOUND);
        }
        return plainToClass(ReviewDto, review);
    }

    async getReviewsByUserId(userId: number): Promise<ReviewDto[]> {
        const user = await this.userRepository.findOne(
            {
                where: { id: userId },
                relations: ['reviews'],
            }
        )
        if (!user) {
            throw new HttpException('User not found', HttpStatus.NOT_FOUND);
        }
        if(!user.reviews) {
            return [];
        }
        const reviewDtos = user.reviews.map(review => plainToClass(ReviewDto, review));
        return reviewDtos;
    }

    async calculateAverageRating(foodId: number): Promise<number> {
        const food = await this.foodRepository.findOne(
            {
                where: { id: foodId },
                relations: ['reviews'],
            }
        )
        if (!food) {
            throw new HttpException('Food not found', HttpStatus.NOT_FOUND);
        }
        if(!food.reviews) {
            return 0;
        }
        const totalRating = food.reviews.reduce((acc, review) => acc + review.rating, 0);
        const averrageRating = food.reviews.length ? totalRating / food.reviews.length : 0;
        return averrageRating;
    }
}
