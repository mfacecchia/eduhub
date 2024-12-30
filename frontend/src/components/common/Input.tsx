import { cn } from "@/lib/utils";
import { TInputProps } from "@/types/inputProps";
import { forwardRef } from "react";
import Label from "./Label";

const Input = forwardRef<HTMLInputElement, TInputProps>(
    ({ className, type, id, label, errorLabel, ...props }, ref) => {
        return (
            <div className="w-full">
                {label && <Label htmlFor={id}>{label}</Label>}
                <input
                    id={id}
                    type={type}
                    className={cn(
                        "flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-base ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium file:text-foreground placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 md:text-sm",
                        className
                    )}
                    ref={ref}
                    {...props}
                />
                {errorLabel && (
                    <Label htmlFor={id} variant="error">
                        {errorLabel}
                    </Label>
                )}
            </div>
        );
    }
);
Input.displayName = "Input";

export default Input;
