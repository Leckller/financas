import TransacaoEntity from "src/transacao/transacao.entity";
import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";

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

    @OneToMany(() => TransacaoEntity, (transacaoEntity) => transacaoEntity)
    transactions: TransacaoEntity[];

}