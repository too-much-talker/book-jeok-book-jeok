import { mockInstance } from "./index";

const api = mockInstance();

async function booklogList(info, success, fail) {
  await api
    .get(`/api/v1/booklog/list`, { params: info })
    .then(success)
    .catch(fail);
}

export { booklogList };
