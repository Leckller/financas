import { Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import TransacaoEntity from "./transacao.entity";
import { Repository } from "typeorm";

@Injectable()
export default class TransacaoService {

    constructor(
        @InjectRepository(TransacaoEntity) private readonly transacaoRepository: Repository<TransacaoEntity>
    ) {}

    public async createReceita () {

        return 

    }

    public async createDespesa () {

        return 

    }
    
}