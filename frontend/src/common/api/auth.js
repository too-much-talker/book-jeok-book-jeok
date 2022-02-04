import { apiInstance } from "./index";

const api = apiInstance();

async function login(user, success, fail) {
  await api
    .post(`/api/v1/members/login`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

export { login };
