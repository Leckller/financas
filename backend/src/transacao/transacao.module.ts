import { Module } from "@nestjs/common";
import TransacaoController from "./transacao.controller";
import TransacaoService from "./transacao.service";
import { TypeOrmModule } from "@nestjs/typeorm";
import TransacaoEntity from "./transacao.entity";

@Module({

    controllers: [TransacaoController],
    providers: [TransacaoService],
    exports: [TransacaoService],
    imports: [
        TypeOrmModule.forFeature([TransacaoEntity])
    ],

})
export default class TransacaoModule {}