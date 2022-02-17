import { apiInstance } from "./index";

const api = apiInstance();

async function getGroupDetail(readingGroupSeq, header, success, fail) {
  await api
    .get(`/api/v1/reading-groups/${readingGroupSeq}`, header)
    .then(success)
    .catch(fail);
}

async function checkGoMeeting(readingGroupSeq, header, success, fail) {
  await api
    .get(`/api/v1/reading-groups/${readingGroupSeq}/isMeetToday`, header)
    .then(success)
    .catch(fail);
}

async function deleteGroup(readingGroupSeq, header, success, fail) {
  await api
    .delete(`/api/v1/reading-groups/${readingGroupSeq}`, header)
    .then(success)
    .catch(fail);
}

async function getGroupList(info, header, success, fail) {
  await api
    .get(
      `/api/v1/reading-groups/me?size=${info.size}&page=${info.page}`,
      header
    )
    .then(success)
    .catch(fail);
}

async function submitReview(readingGroupSeq, params, header, success, fail) {
  await api
    .post(`/api/v1/reading-groups/${readingGroupSeq}/review`, params, header)
    .then(success)
    .catch(fail);
}

export {
  getGroupDetail,
  checkGoMeeting,
  deleteGroup,
  getGroupList,
  submitReview,
};
