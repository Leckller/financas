import { Module } from "@nestjs/common";
import UsuarioService from "./usuario.service";
import UsuarioController from "./usuario.controller";

@Module({
    providers: [UsuarioService],
    controllers: [UsuarioController],
    exports: [],
    imports: [],
})
export default class UsuarioModule {}