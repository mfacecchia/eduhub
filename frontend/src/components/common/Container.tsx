import { cn } from "@/lib/utils";
import { ComponentProps } from "react";

const Container = ({
    className,
    children,
    ...props
}: ComponentProps<"div">) => {
    return (
        <div
            className={cn(
                "text-center flex flex-col items-center justify-center w-full min-h-[--full-screen]",
                className
            )}
            {...props}
        >
            {children}
        </div>
    );
};

export default Container;
