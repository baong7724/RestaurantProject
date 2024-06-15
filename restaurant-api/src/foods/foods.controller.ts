import { Body, Controller, Get, HttpException, Param, Post, Put, Scope } from '@nestjs/common';
import { FoodsService } from './foods.service';
import { ImagesService } from 'src/images/images.service';
import { ReviewsService } from 'src/reviews/reviews.service';
import { ApiBearerAuth, ApiTags } from '@nestjs/swagger';
import { Roles } from 'src/common/decorator/roles.decorator';
import { Role } from 'src/common/enum/roles.enum';
import { FoodDto } from './dto/food.dto';
import { ResponseData } from 'src/common/global/responde.data';
import { HttpMessage, HttpStatusCode } from 'src/common/enum/httpstatus.enum';
import { ImageDTO } from 'src/images/dto/image.dto';
import { Public } from 'src/common/decorator/public.decorator';

@Controller({
    path: 'foods',
    scope: Scope.REQUEST
})
@ApiTags('foods')
export class FoodsController {
    constructor(
        private readonly foodsService: FoodsService,
        private readonly imagesService: ImagesService,
        private readonly reviewsService: ReviewsService,
    ){}

    @Post()
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.ADMIN)
    async createFood(@Body() createFoodDTO: FoodDto): Promise<ResponseData<FoodDto>>{
        try{
            const food = await this.foodsService.createFood(createFoodDTO);
            if(food){
                return new ResponseData<FoodDto>(food, HttpStatusCode.CREATED, HttpMessage.CREATED);
            }
            return new ResponseData<FoodDto>(null, HttpStatusCode.BAD_REQUEST, HttpMessage.BAD_REQUEST);
        }catch(e){
            throw new HttpException(e.message, e.status);
        }
    }

    @Put(':id')
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.ADMIN)
    async updateFood(
        @Param('id') id: number,
        @Body() updateFoodDTO: FoodDto): Promise<ResponseData<FoodDto>>{
        try{
            const food = await this.foodsService.updateFood(id,updateFoodDTO);
            if(food){
                return new ResponseData<FoodDto>(food, HttpStatusCode.OK, HttpMessage.OK);
            }
            return new ResponseData<FoodDto>(null, HttpStatusCode.BAD_REQUEST, HttpMessage.BAD_REQUEST);
        }catch(e){
            throw new HttpException(e.message, e.status);
        }
    }

    @Put(':foodId/images/:imageId')
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.ADMIN)
    async updateImage(
        @Param('foodId') foodId: number,
        @Param('imageId') imageId: number,
        @Body() ImageDTO: ImageDTO){
        try{
            await this.imagesService.updateImage(imageId, foodId, ImageDTO);
        }catch(e){
            throw new HttpException(e.message, e.status);
        }
    }

    @Get(':foodId/reviews')
    @ApiBearerAuth('JWT-auth')
    @Public()
    async getReviews(@Param('foodId') foodId: number){
        try{
            const reviews = await this.reviewsService.getReviews(foodId);
            if(reviews){
                return new ResponseData(reviews, HttpStatusCode.OK, HttpMessage.OK);
            }
            return new ResponseData(null, HttpStatusCode.BAD_REQUEST, HttpMessage.BAD_REQUEST);
        }catch(e){
            throw new HttpException(e.message, e.status);
        }
    }

    
}
