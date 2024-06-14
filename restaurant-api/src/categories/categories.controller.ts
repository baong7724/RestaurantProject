import { Body, Controller, Delete, Get, HttpException, Param, Post, Put, Req, Scope } from '@nestjs/common';
import { CategoriesService } from './categories.service';
import { ApiBearerAuth, ApiTags } from '@nestjs/swagger';
import { HttpMessage, HttpStatusCode } from 'src/common/enum/httpstatus.enum';
import { ResponseData } from 'src/common/global/responde.data';
import { Role } from 'src/common/enum/roles.enum';
import { Roles } from 'src/common/decorator/roles.decorator';
import { CategoryDTO } from './dto/category.dto';

@Controller({
    path: 'categories',
    scope: Scope.REQUEST,
})
@ApiTags('categories')
export class CategoriesController {
    constructor(private readonly categoryService: CategoriesService){}

    @Post()
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.ADMIN)
    async createCategory(@Body() createCategoryDTO: CategoryDTO){
        try {
            const result = await this.categoryService.createCategory(createCategoryDTO)
            if (result){
                return new ResponseData<CategoryDTO>(result, HttpStatusCode.OK, HttpMessage.OK);
            }
            return new HttpException(HttpMessage.BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
        } catch (err) {
            throw new HttpException(err.message, err.status);
        }
    }

    @Put(':id')
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.ADMIN)
    async updateCategory(
        @Param('id') id: number,
        @Body() updateCategoryDTO: CategoryDTO){
        try {
            if (updateCategoryDTO.id){
                return new HttpException(HttpMessage.BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
            } 
            const result = await this.categoryService.updateCategory(id, updateCategoryDTO)
            if (result){
                return new ResponseData<CategoryDTO>(result, HttpStatusCode.OK, HttpMessage.OK);
            }
            return new HttpException(HttpMessage.BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
        } catch (err) {
            throw new HttpException(err.message, err.status);
        }
    }

    @Get('all')
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.ADMIN)
    async getAllCategories(){
        try {
            const result = await this.categoryService.getAllCategories();
            if (result){
                return new ResponseData<CategoryDTO[]>(result, HttpStatusCode.OK, HttpMessage.OK);
            }
            return new HttpException(HttpMessage.BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
        } catch (err) {
            throw new HttpException(err.message, err.status);
        }
    }

    @Get(':id')
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.ADMIN)
    async getCategoryById(@Param('id') id: number){
        try {
            const result = await this.categoryService.getCategoryById(id);
            if (result){
                return new ResponseData<CategoryDTO>(result, HttpStatusCode.OK, HttpMessage.OK);
            }
            return new HttpException(HttpMessage.BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
        } catch (err) {
            throw new HttpException(err.message, err.status);
        }
    }

    @Delete(':id')
    @ApiBearerAuth('JWT-auth')
    @Roles(Role.ADMIN)
    async deleteCategory(@Param('id') id: number){
        try {
            const result = await this.categoryService.deleteCategory(id);
            if (result){
                return new ResponseData<boolean>(result, HttpStatusCode.OK, HttpMessage.OK);
            }
            return new HttpException(HttpMessage.BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
        } catch (err) {
            throw new HttpException(err.message, err.status);
        }
    }
}
