import axios from "axios";
import { AUTH_URL } from "../config";

function authInstance() {
  const instance = axios.create({
    baseURL: AUTH_URL,
    headers: {
      "Content-type": "application/json",
    },
  });

  return instance;
}

export { authInstance };
