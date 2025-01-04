import { useAccountQuery } from "@/hooks/queries";
import { TDbAccount } from "@/types/account";
import { createContext, ReactNode, useContext } from "react";

type TAuthContext = {
    account: TDbAccount | undefined;
    isLoading: boolean;
};

const AccountAuthContext = createContext<TAuthContext>({} as TAuthContext);

const AuthContext = ({ children }: { children: ReactNode }) => {
    const { data: account, isLoading } = useAccountQuery();

    return (
        <AccountAuthContext.Provider value={{ account, isLoading }}>
            {children}
        </AccountAuthContext.Provider>
    );
};

function useAccountAuthContext() {
    return useContext(AccountAuthContext);
}

export default AuthContext;
export { useAccountAuthContext };
