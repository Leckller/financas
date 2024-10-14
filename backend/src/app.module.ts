import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import UsuarioEntity from './usuario/usuario.entity';

@Module({
  imports: [
    TypeOrmModule.forRoot({
      entities: [
        UsuarioEntity
      ],
      synchronize: true,
    })
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
