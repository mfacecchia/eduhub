import { Link } from "react-router";
import { Button } from "./Button";

const Nav = () => {
    return (
        <nav className="w-full bg-background/85 border border-border rounded-[--radius] px-4 py-3 backdrop-blur-md flex items-center justify-between mb-7">
            <div className="flex items-center gap-3">
                <Link to="/" aria-description="Back to homepage">
                    <h3>EduHub</h3>
                </Link>
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
