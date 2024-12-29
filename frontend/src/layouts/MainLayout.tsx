import Footer from "@/components/common/Footer";
import Nav from "@/components/common/Nav";
import { Outlet } from "react-router";

const MainLayout = () => {
    return (
        <>
            <Nav />
            <Outlet />
            <Footer />
        </>
    );
};

export default MainLayout;
