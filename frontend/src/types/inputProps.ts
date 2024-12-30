import { ComponentProps } from "react";

export type TInputProps = {
    label?: string;
    errorLabel?: string;
    id: string;
} & ComponentProps<"input">;
