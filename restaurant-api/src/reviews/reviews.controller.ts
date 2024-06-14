import { Body, Controller, Delete, Get, HttpException, Param, Post, Put, Scope } from '@nestjs/common';
import { ReviewsService } from './reviews.service';
import { ApiBearerAuth } from '@nestjs/swagger';
import { Roles } from 'src/common/decorator/roles.decorator';
import { Role } from 'src/common/enum/roles.enum';
import { ReviewDto } from './dto/review.dto';
import { ResponseData } from 'src/common/global/responde.data';
import { HttpMessage, HttpStatusCode } from 'src/common/enum/httpstatus.enum';

@Controller({
    path: 'reviews',
    scope: Scope.REQUEST,
})
export class ReviewsController {
    constructor(
        private readonly reviewsService: ReviewsService,
    ) {}

    @Post(':foodId')
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.USER)
    async createReview(
        @Param('foodId') foodId: number,
        @Body() reviewDto: ReviewDto,): Promise<ResponseData<ReviewDto>> {
        try{
            const review = await this.reviewsService.createReview(foodId, reviewDto);
            if(review){
                return new ResponseData<ReviewDto>(review, HttpStatusCode.CREATED, HttpMessage.OK);
            }
            return new ResponseData<ReviewDto>(null, HttpStatusCode.BAD_REQUEST, HttpMessage.BAD_REQUEST);
        } catch(e){
            throw new HttpException(e.message, e.status);
        }
    }

    @Put(':foodId/:reviewId')
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.USER)
    async updateReview(
        @Param('foodId') foodId: number,
        @Param('reviewId') reviewId: number,
        @Body() reviewDto: ReviewDto,): Promise<ResponseData<ReviewDto>> {
        try{
            const review = await this.reviewsService.updateReview(foodId, reviewId, reviewDto);
            if(review){
                return new ResponseData<ReviewDto>(review, HttpStatusCode.OK, HttpMessage.OK);
            }
            return new ResponseData<ReviewDto>(null, HttpStatusCode.BAD_REQUEST, HttpMessage.BAD_REQUEST);
        } catch(e){
            throw new HttpException(e.message, e.status);
        }
    }

    @Delete(':foodId/:reviewId')
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.USER, Role.ADMIN)
    async deleteReview(
        @Param('foodId') foodId: number,
        @Param('reviewId') reviewId: number): Promise<ResponseData<boolean>> {
        try{
            const result = await this.reviewsService.deleteReview(foodId, reviewId);
            if (result){
                return new ResponseData<boolean>(result, HttpStatusCode.OK, HttpMessage.OK);
            }
            return new ResponseData<boolean>(false, HttpStatusCode.BAD_REQUEST, HttpMessage.BAD_REQUEST);
        } catch(e){
            throw new HttpException(e.message, e.status);
        }
    }

    @Get(':reviewId')
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.USER, Role.ADMIN)
    async getReview(
        @Param('reviewId') reviewId: number): Promise<ResponseData<ReviewDto>> {
        try{
            const review = await this.reviewsService.getReview(reviewId);
            if (review){
                return new ResponseData<ReviewDto>(review, HttpStatusCode.OK, HttpMessage.OK);
            }
            return new ResponseData<ReviewDto>(null, HttpStatusCode.NOT_FOUND, HttpMessage.NOT_FOUND);
        } catch(e){
            throw new HttpException(e.message, e.status);
        }
    }
}