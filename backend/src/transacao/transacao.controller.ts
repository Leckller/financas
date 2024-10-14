import { Controller, Post, UseGuards } from "@nestjs/common";
import AuthGuard from "src/guard/auth.guard";
import TransacaoService from "./transacao.service";

@UseGuards(AuthGuard)
@Controller('transaction')
export default class TransacaoController {

    constructor(private readonly transacaoService: TransacaoService) {}

    @Post('/receita')
    public async Receita () {

        return this.transacaoService.createReceita();

    }

    @Post('/despesa')
    public async Despesa () {

        return this.transacaoService.createDespesa();

    }

}