import { apiInstance } from "./index";

const api = apiInstance();

async function booklogList(info, success, fail) {
  await api.get(`/api/v1/booklogs`, { params: info }).then(success).catch(fail);
}

async function findBooklog(info, success, fail) {
  await api
    .get(`/api/v1/booklogs/search`, { params: info })
    .then(success)
    .catch(fail);
}

async function booklogDetail(booklogSeq, header, success, fail) {
  await api
    .get(`/api/v1/booklogs/${booklogSeq}`, header)
    .then(success)
    .catch(fail);
}

async function isLikeBooklog(booklogSeq, header, success, fail) {
  await api
    .get(`/api/v1/booklogs/${booklogSeq}/like`, header)
    .then(success)
    .catch(fail);
}

async function setLikeBooklog(booklogSeq, header, isLike, success, fail) {
  if (isLike) {
    console.log("delete 요청");

    await api
      .delete(`/api/v1/booklogs/${booklogSeq}/like`, header)
      .then(success)
      .catch(fail);
  } else {
    console.log("post 요청");
    await api
      .post(`/api/v1/booklogs/${booklogSeq}/like`, {}, header)
      .then(success)
      .catch(fail);
  }
}

export {
  booklogList,
  findBooklog,
  booklogDetail,
  isLikeBooklog,
  setLikeBooklog,
};
