import { z } from "zod";

export const requiredString = z
    .string()
    .trim()
    .min(1, "This field is required");

export const requiredStringNoTrim = z.string().min(1, "This field is required");

export const emailField = requiredString
    .toLowerCase()
    .email("This field must contain a valid Email address");
