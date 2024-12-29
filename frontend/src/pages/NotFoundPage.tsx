import { Button } from "@/components/common/Button";
import Container from "@/components/common/Container";
import NotFound from "@/components/icons/NotFound";
import updatePageTitle from "@/lib/titleUpdater";
import { LucideHome } from "lucide-react";
import { NavLink } from "react-router";

const NotFoundPage = () => {
    updatePageTitle("Not found");
    return (
        <Container>
            <NotFound />
            <div>
                <h1>Uh oh!</h1>
                <p className="muted">
                    The page you're looking for seems to not exist.
                </p>
            </div>
            <NavLink to="/" className="hover:animate-pulse mt-5">
                <Button variant="link">
                    Go back to
                    <LucideHome className="inline align-text-bottom" />
                    home
                </Button>
            </NavLink>
        </Container>
    );
};

export default NotFoundPage;
