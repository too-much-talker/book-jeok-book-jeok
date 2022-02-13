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

export { getGroupDetail, checkGoMeeting, deleteGroup };
