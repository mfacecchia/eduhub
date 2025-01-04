import { backendAddress } from "@/lib/constants";
import axios from "axios";

export async function fetchAccount() {
    return axios
        .get(`${backendAddress}/api/v1/accounts`, {
            withCredentials: true,
        })
        .then(({ data }) => {
            return data;
        });
}
