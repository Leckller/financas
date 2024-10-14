import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";

@Entity()
export default class UsuarioEntity {

    @PrimaryGeneratedColumn({unsigned: true})
    id: number;

    @Column({})
    nome: string;

    @Column({unique: true})
    email: string;

    @Column({})
    senha: string;

}