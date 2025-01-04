import {
    emailField,
    requiredString,
    requiredStringNoTrim,
} from "@/validators/defaultValidators";
import { z } from "zod";

export const accountSchema = z
    .object({
        firstName: requiredString,
        lastName: requiredString,
        email: emailField,
        password: requiredStringNoTrim.min(
            10,
            "Minimum length is 10 characters"
        ),
        repeatPassword: requiredStringNoTrim,
    })
    .refine((fields) => fields.password === fields.repeatPassword, {
        message: "Passwords not matching",
        path: ["repeatPassword"],
    });

export const loginAccountSchema = z.object({
    email: emailField,
    password: requiredStringNoTrim,
});
