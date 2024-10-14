import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";

@Entity()
export default class CategoriaEntity {

    @PrimaryGeneratedColumn({unsigned: true})
    id: number;

    @Column({})
    nome: string;

}