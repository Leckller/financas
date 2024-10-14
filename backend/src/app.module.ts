import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import UsuarioEntity from './usuario/usuario.entity';
import TransacaoEntity from './transacao/transacao.entity';
import TransacaoModule from './transacao/transacao.module';
import UsuarioModule from './usuario/usuario.module';
import CategoriaModule from './categorias/categoria.module';
import CategoriaEntity from './categorias/categoria.entity';

@Module({
  imports: [
    TransacaoModule,
    UsuarioModule,
    CategoriaModule,
    TypeOrmModule.forRoot({
      type: 'mysql',
      port: Number(process.env.DB_PORT) || 3307,
      host: process.env.DB_HOST || "localhost",
      username: process.env.DB_USERNAME || "root",
      password: process.env.DB_PASSWORD || "password",
      database: process.env.DB_DATABASE || "financass",
      entities: [
        UsuarioEntity,
        TransacaoEntity,
        CategoriaEntity
      ],
      synchronize: true,
    })
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
