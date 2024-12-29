import { cn } from "@/lib/utils";
import { ChevronLeft } from "lucide-react";
import { ComponentProps } from "react";
import { Link, Outlet } from "react-router";
import { buttonVariants } from "./Button";

type TBackButtonProps = {
    urlTo?: string;
    textContent?: string;
} & ComponentProps<"a">;

export default function BackButton({
    urlTo,
    textContent,
    className,
    ...props
}: Omit<TBackButtonProps, "href">) {
    return (
        <>
            <div>
                <Link
                    to={urlTo ?? "../"}
                    className={cn(
                        buttonVariants({ variant: "outline" }),
                        "gap-1 pl-2 hover:animate-none",
                        className
                    )}
                    {...props}
                >
                    <ChevronLeft />
                    {textContent ?? "Back"}
                </Link>
            </div>
            <Outlet />
        </>
    );
}
