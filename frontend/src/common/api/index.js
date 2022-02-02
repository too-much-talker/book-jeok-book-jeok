import axios from "axios";
import { URL } from "../config";

function apiInstance() {
  const instance = axios.create({
    baseURL: URL,
    headers: {
      "Content-type": "application/json",
    },
  });

  return instance;
}

export { apiInstance };
