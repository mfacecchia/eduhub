import { backendAddress } from "@/lib/constants";
import axios from "axios";

export async function fetchClasses() {
    return axios
        .get(`${backendAddress}/classes`, {
            withCredentials: true,
        })
        .then(({ data: { data } }) => {
            return data;
        });
}
