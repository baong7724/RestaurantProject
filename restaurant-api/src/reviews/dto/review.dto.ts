import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsNumber, IsOptional, IsString } from "class-validator";

export class ReviewDto{
    @IsNumber()
    @IsOptional()
    @ApiPropertyOptional()
    id: number

    @IsNumber()
    @ApiProperty()
    userId: number

    @IsNumber()
    @ApiProperty()
    foodId: number

    @IsNumber()
    @ApiProperty()
    rating: number

    @IsString()
    @ApiProperty()
    comment: string
}