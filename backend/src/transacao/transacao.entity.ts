import { Column, CreateDateColumn, Entity, ManyToOne, OneToOne, PrimaryGeneratedColumn } from "typeorm";

@Entity()
export default class TransacaoEntity {

    @PrimaryGeneratedColumn({unsigned: true})
    id: number;

    @Column({default: 'despesa',})
    tipo: string;

    @Column()
    valor: number;

    // @OneToOne()
    // categoria: Categoria;
    
    @CreateDateColumn({})
    data: Date;

    @Column({})
    descricao: string;

    // @ManyToOne()
    // user: User;

}