import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import UsuarioEntity from './usuario/usuario.entity';
import TransacaoEntity from './transacao/transacao.entity';
import TransacaoModule from './transacao/transacao.module';
import UsuarioModule from './usuario/usuario.module';

@Module({
  imports: [
    TransacaoModule,
    UsuarioModule,
    TypeOrmModule.forRoot({
      entities: [
        UsuarioEntity,
        TransacaoEntity
      ],
      synchronize: true,
    })
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
