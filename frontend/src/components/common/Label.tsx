import * as LabelPrimitive from "@radix-ui/react-label";
import { cva, type VariantProps } from "class-variance-authority";

import { cn } from "@/lib/utils";
import {
    ComponentPropsWithoutRef,
    ElementRef,
    forwardRef,
    LabelHTMLAttributes,
} from "react";

const labelVariants = cva(
    "text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70",
    {
        variants: {
            variant: {
                error: "text-destructive",
            },
        },
    }
);

export interface LabelProps
    extends LabelHTMLAttributes<HTMLLabelElement>,
        VariantProps<typeof labelVariants> {
    asChild?: boolean;
}

const Label = forwardRef<
    ElementRef<typeof LabelPrimitive.Root>,
    ComponentPropsWithoutRef<typeof LabelPrimitive.Root> &
        VariantProps<typeof labelVariants>
>(({ className, variant, ...props }, ref) => (
    <LabelPrimitive.Root
        ref={ref}
        className={cn(labelVariants({ variant }), className)}
        {...props}
    />
));
Label.displayName = LabelPrimitive.Root.displayName;

export default Label;
