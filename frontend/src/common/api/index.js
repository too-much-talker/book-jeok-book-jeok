import axios from "axios";
import { MOCK_URL, URL } from "../config";

function apiInstance() {
  const instance = axios.create({
    baseURL: URL,
    headers: {
      "Content-type": "application/json",
    },
  });

  return instance;
}

function mockInstance() {
  const instance = axios.create({
    baseURL: MOCK_URL,
    headers: {
      "Content-type": "application/json",
    },
  });

  return instance;
}

export { apiInstance, mockInstance };
