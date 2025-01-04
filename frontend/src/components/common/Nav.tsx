import { useAccountAuthContext } from "@/context/AuthContext";
import { getAccountInitials } from "@/lib/accountInitials";
import { Link } from "react-router";
import { Avatar, AvatarFallback } from "./Avatar";
import { Button } from "./Button";

const Nav = () => {
    const { account } = useAccountAuthContext();

    return (
        <nav className="w-full bg-background/85 border border-border rounded-[--radius] px-4 py-3 backdrop-blur-md flex items-center justify-between mb-7">
            <div className="flex items-center gap-3">
                <Link to="/" aria-description="Back to homepage">
                    <h3>EduHub</h3>
                </Link>
            </div>
            <div>
                <Link to="/login">
                    {account ? (
                        <Avatar className="size-8">
                            <AvatarFallback className="bg-muted-foreground text-muted text-xs">
                                {getAccountInitials(
                                    account.firstName,
                                    account.lastName
                                )}
                            </AvatarFallback>
                        </Avatar>
                    ) : (
                        <Button variant="link">Login</Button>
                    )}
                </Link>
            </div>
        </nav>
    );
};

export default Nav;
