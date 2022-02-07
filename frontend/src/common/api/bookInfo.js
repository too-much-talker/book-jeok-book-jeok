import { apiInstance } from "./index";

const api = apiInstance();

async function bookInfoDetail(bookInfoSeq, header, success, fail) {
  await api
    .get(`/api/v1/bookinfos/${bookInfoSeq}`, header)
    .then(success)
    .catch(fail);
}

export { bookInfoDetail };
