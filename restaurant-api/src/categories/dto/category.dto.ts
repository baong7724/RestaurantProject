import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsNotEmpty, IsNumber, IsOptional, IsString } from "class-validator";

export class CategoryDTO {
    @IsNumber()
    @IsOptional()
    @ApiPropertyOptional()
    id?: number;

    @IsString()
    @IsNotEmpty()
    @ApiProperty()
    name: string;

    @IsString()
    @IsNotEmpty()
    @ApiProperty()
    colorCode?: string;

    @IsString()
    @IsNotEmpty()
    @ApiProperty()
    tag?: string;
}