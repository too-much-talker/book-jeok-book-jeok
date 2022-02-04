import { apiInstance } from "./index";

const api = apiInstance();

async function booklogList(info, success, fail) {
  await api.get(`/api/v1/booklogs`, { params: info }).then(success).catch(fail);
}

export { booklogList };
