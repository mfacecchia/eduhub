import { cn } from "@/lib/utils";
import { ComponentProps } from "react";

export default function Form({
    className,
    onSubmit,
    children,
    ...props
}: ComponentProps<"form">) {
    return (
        <form
            onSubmit={onSubmit}
            className={cn(
                "w-full max-w-[600px] p-5 flex flex-col gap-5",
                className
            )}
            {...props}
        >
            {children}
        </form>
    );
}
