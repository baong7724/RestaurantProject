import { Delete } from "@nestjs/common";
import { Base } from "src/common/global/base.entity";
import { Food } from "src/foods/entity/food.entity";
import { Column, CreateDateColumn, DeleteDateColumn, Entity, JoinTable, ManyToMany, PrimaryGeneratedColumn, UpdateDateColumn } from "typeorm";

@Entity('categories')
export class Category extends Base{
    @Column()
    name: string;
    @Column({
        name: 'color_code',
        nullable: true
    })
    colorCode: string;
    @Column({
        nullable: true
    })
    tag: string;
    @ManyToMany(() => Food, (food) => food.categories)
    @JoinTable({
        name: 'food_categories',
        joinColumn: {
            name: 'category_id',
            referencedColumnName: 'id'
        },
        inverseJoinColumn: {
            name: 'food_id',
            referencedColumnName: 'id'
        }
    })
    foods: Food[];
}
