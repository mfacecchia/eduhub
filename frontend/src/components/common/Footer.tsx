import { ExternalLink } from "lucide-react";
import { Link } from "react-router";
import VerticalSeparator from "./VerticalSeparator";

const Footer = () => {
    return (
        <footer className="w-full px-5 py-7 flex flex-wrap justify-between border border-t-border gap-4 rounded-[--radius]">
            <div className="flex flex-wrap items-center gap-2">
                <Link to="/about">About</Link>
                <VerticalSeparator className="h-3/4" />
                <Link to="/legal">Legal</Link>
                <VerticalSeparator className="h-3/4" />
                <Link
                    to="https://github.com/mfacecchia"
                    target="_blank"
                    className="flex items-center gap-1"
                >
                    Contacts <ExternalLink className="size-4" />
                </Link>
            </div>
            <div>
                <p className="muted">
                    ©{new Date().getFullYear()} Marco Facecchia. All Rights
                    Reserved.
                </p>
            </div>
        </footer>
    );
};

export default Footer;
