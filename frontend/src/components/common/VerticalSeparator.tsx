import { cn } from "@/lib/utils";
import { ComponentProps } from "react";

const VerticalSeparator = ({ className, ...props }: ComponentProps<"hr">) => {
    return (
        <hr
            className={cn(
                "w-[1px] h-full border-none bg-foreground",
                className
            )}
            {...props}
        />
    );
};

export default VerticalSeparator;
