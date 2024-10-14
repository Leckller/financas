import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";

@Entity()
export default class TransacaoEntity {

    @PrimaryGeneratedColumn({unsigned: true})
    id: number;

    @Column({default: 'despesa',})
    tipo: string;



}