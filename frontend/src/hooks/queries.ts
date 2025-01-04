import { queryOptions } from "@/lib/constants";
import { TDbAccount } from "@/types/account";
import { useQuery } from "@tanstack/react-query";

function useAccountQuery() {
    return useQuery<TDbAccount>(queryOptions.account);
}

export { useAccountQuery };
