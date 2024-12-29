import { cn } from "@/lib/utils";
import { ComponentProps } from "react";

const Section = ({
    className,
    children,
    ...props
}: ComponentProps<"section">) => {
    return (
        <section className={cn("py-4", className)} {...props}>
            {children}
        </section>
    );
};

export default Section;
