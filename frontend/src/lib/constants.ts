import { fetchAccount } from "@/api/accountApi";
import { fetchClasses } from "@/api/classesApi";

export const backendAddress = import.meta.env.VITE_BACKEND_ADDRESS;

// TODO: Add typing
const queryOptions = {
    account: {
        queryKey: ["account"],
        queryFn: fetchAccount,
        refetchOnWindowFocus: false,
    },
    classes: {
        queryKey: ["classes"],
        queryFn: fetchClasses,
        refetchOnWindowFocus: false,
    },
};

export { queryOptions };
