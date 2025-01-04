import { backendAddress } from "@/lib/constants";
import axios from "axios";

export async function fetchAccount() {
    return axios
        .get(`${backendAddress}/api/v1/accounts`, {
            withCredentials: true,
            validateStatus: (status) => {
                return (status >= 200 && status < 300) || status === 401;
            },
        })
        .then(({ data: { data } }) => {
            return data;
        });
}
