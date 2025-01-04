import { accountSchema } from "@/schemas/accountSchema";
import { z } from "zod";

export type TAccount = z.infer<typeof accountSchema>;

export type TLoginAccount = Pick<TAccount, "email" | "password">;

export type TDbAccount = {
    id: number;
} & Omit<TAccount, "password" | "repeatPassword">;
