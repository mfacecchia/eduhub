import { cn } from "@/lib/utils";
import { ChevronRight } from "lucide-react";
import { ComponentProps } from "react";
import { Link } from "react-router";

type THeaderLinkActionProps = {
    urlTo: string;
    text: string;
} & ComponentProps<"a">;

const Header = ({ className, ...props }: ComponentProps<"header">) => {
    return (
        <header
            className={cn("flex items-center justify-between py-3", className)}
            {...props}
        />
    );
};

const HeaderLinkAction = ({
    urlTo,
    text,
    className,
    ...props
}: THeaderLinkActionProps) => {
    return (
        <Link
            to={urlTo}
            className={cn(
                "flex items-center gap-1 text-muted-foreground",
                className
            )}
            {...props}
        >
            <span className="small">{text}</span>
            <ChevronRight className="size-4" />
        </Link>
    );
};

export { Header, HeaderLinkAction };
