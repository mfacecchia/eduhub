import { useAccountQuery } from "@/hooks/queries";
import { TDbAccount } from "@/types/account";
import { createContext, ReactNode, useContext } from "react";
import { useNavigate } from "react-router";

const AccountAuthContext = createContext<TDbAccount | undefined>(undefined);

const AuthContext = ({ children }: { children: ReactNode }) => {
    const navigate = useNavigate();
    const { data: account, isLoading } = useAccountQuery();

    if (!isLoading && !account) {
        if (
            window.location.pathname !== "/login" &&
            window.location.pathname !== "/"
        ) {
            navigate("/login");
        }
    }
    if (account && window.location.pathname === "/login") {
        navigate("/dashboard");
    }

    return (
        <AccountAuthContext.Provider value={account}>
            {children}
        </AccountAuthContext.Provider>
    );
};

function useAccountAuthContext() {
    const context = useContext(AccountAuthContext);
    if (!context)
        throw new Error(
            "Context must be used inside a AccountAuthContext component"
        );
    return context;
}

export default AuthContext;
export { useAccountAuthContext };
