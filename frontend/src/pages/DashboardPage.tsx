import { fetchAccount } from "@/api/accountApi";
import getGreeting from "@/lib/greetingSelector";
import { useQuery } from "@tanstack/react-query";

const DashboardPage = () => {
    const { data: accountData, isLoading: isAccountLoading } = useQuery({
        queryKey: ["account"],
        queryFn: fetchAccount,
    });
    const greeting = getGreeting();

    // TODO: Display an error in case of failed fetch
    return (
        <>
            <section>
                <p className="large">{greeting ?? "Hello"},</p>
                <h2>{isAccountLoading ? "Loading..." : accountData?.name}</h2>
            </section>
            <main>{/* TODO: Place next lesson and classes here */}</main>
        </>
    );
};

export default DashboardPage;
