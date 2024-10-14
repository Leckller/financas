import { IsEmail, IsStrongPassword } from "class-validator";

export default class LoginRequestDto {

    @IsEmail()
    email: string;

    @IsStrongPassword({})
    senha: string;
    
}