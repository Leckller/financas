import { Module } from "@nestjs/common";
import TransacaoController from "./transacao.controller";
import TransacaoService from "./transacao.service";
import { TypeOrmModule } from "@nestjs/typeorm";
import TransacaoEntity from "./transacao.entity";
import AuthService from "src/auth/auth.service";
import AuthGuard from "src/guard/auth.guard";
import AuthModule from "src/auth/auth.module";

@Module({

    controllers: [TransacaoController],
    providers: [TransacaoService],
    exports: [TransacaoService],
    imports: [
        TypeOrmModule.forFeature([TransacaoEntity]),
        AuthModule,
    ],

})
export default class TransacaoModule {}