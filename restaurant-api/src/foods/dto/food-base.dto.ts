import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsArray, IsNumber, IsObject, IsOptional, IsString } from "class-validator";
import { CategoryDTO } from "src/categories/dto/category.dto";
import { IngredientDTO } from "src/categories/dto/ingredient.dto";
import { OriginDto } from "src/categories/dto/origin.dto";
import { ImageDTO } from "src/images/dto/image.dto";
import { ReviewDto } from "src/reviews/dto/review.dto";

export class FoodBaseDto {
    @IsNumber()
    @IsOptional()
    @ApiPropertyOptional()
    id: number;

    @IsString()
    @IsOptional()
    @ApiPropertyOptional()
    name: string;

    @IsString()
    @IsOptional()
    @ApiPropertyOptional()
    description: string;

    @IsNumber()
    @IsOptional()
    @ApiPropertyOptional()
    price: number;

    @IsNumber()
    @IsOptional()
    @ApiPropertyOptional()
    rating: number;
    
    @IsArray()
    @IsOptional()
    @ApiPropertyOptional()
    categories: CategoryDTO[];

    @IsArray()
    @IsOptional()
    @ApiPropertyOptional()
    images: ImageDTO[];

    @IsArray()
    @IsOptional()
    @ApiPropertyOptional()
    ingredients: IngredientDTO[];

    @IsObject()
    @IsOptional()
    @ApiPropertyOptional()
    origin: OriginDto;

    @IsArray()
    @IsOptional()
    @ApiPropertyOptional()
    reviews: ReviewDto[];
}