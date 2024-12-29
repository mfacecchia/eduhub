import { Link } from "react-router";
import { Button } from "./Button";

const Nav = () => {
    return (
        <nav className="w-full bg-background/85 border border-border rounded-[--radius] px-4 py-3 backdrop-blur-md flex items-center justify-between">
            <div className="flex items-center gap-3">
                <h3>EduHub</h3>
            </div>
            <div>
                <Link to="/login">
                    <Button variant="link">Login</Button>
                </Link>
            </div>
        </nav>
    );
};

export default Nav;
