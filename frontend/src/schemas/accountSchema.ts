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
            15,
            "Minimum length is 15 characters"
        ),
        repeatPassword: requiredStringNoTrim,
    })
    .refine((fields) => fields.password === fields.repeatPassword, {
        message: "Passwords not matching",
        path: ["repeatPassword"],
    });
