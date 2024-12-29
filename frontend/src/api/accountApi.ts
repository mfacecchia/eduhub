import { backendAddress } from "@/lib/constants";
import axios from "axios";

export async function fetchAccount() {
    return axios.get(`${backendAddress}/users/1`).then(({ data }) => {
        return data;
    });
}
