import updatePageTitle from "@/lib/titleUpdater";

const LandingPage = () => {
    updatePageTitle("Home");

    return (
        <section>
            <h1>Landing page</h1>
        </section>
    );
};

export default LandingPage;
