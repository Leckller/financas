import { Module } from "@nestjs/common";
import AuthGuard from "./auth.guard";
import AuthModule from "src/auth/auth.module";

@Module({

    providers: [AuthGuard],
    exports: [AuthGuard],
    imports: [AuthModule],

})
export default class GuardModule {}