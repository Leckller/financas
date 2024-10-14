import { IsString } from "class-validator";

export default class AddCategoryRequestDto {

    @IsString()
    nome: string;

}