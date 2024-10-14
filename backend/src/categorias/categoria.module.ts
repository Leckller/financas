import { Module } from "@nestjs/common";
import CategoriaService from "./categoria.service";
import CategoriaContorller from "./categoria.controller";
import { TypeOrmModule } from "@nestjs/typeorm";
import CategoriaEntity from "./categoria.entity";

@Module({

    providers: [CategoriaService],
    controllers: [CategoriaContorller],
    exports: [CategoriaService],
    imports: [
        TypeOrmModule.forFeature([CategoriaEntity])
    ],

})
export default class CategoriaModule {}