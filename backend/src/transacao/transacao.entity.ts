import CategoriaEntity from "src/categorias/categoria.entity";
import UsuarioEntity from "src/usuario/usuario.entity";
import { Column, CreateDateColumn, Entity, ManyToOne, OneToOne, PrimaryGeneratedColumn } from "typeorm";

@Entity()
export default class TransacaoEntity {

    @PrimaryGeneratedColumn({unsigned: true})
    id: number;

    @Column({default: 'despesa',})
    tipo: string;

    @Column()
    valor: number;

    @OneToOne(() => CategoriaEntity)
    categoria: CategoriaEntity;
    
    @CreateDateColumn({})
    data: Date;

    @Column({})
    descricao: string;

    @ManyToOne(() => UsuarioEntity, (usuarioEntity) => usuarioEntity)
    user: UsuarioEntity;

}