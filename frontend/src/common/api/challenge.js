import { apiInstance } from "./index";

const api = apiInstance();

async function getChallengeList(info, header, success, fail) {
  await api
    .get(
      `/api/v1/challenges/me?end=${info.end}&size=${info.size}&page=${info.page}`,
      header
    )
    .then(success)
    .catch(fail);
}

async function getChallengeDetail(challengeSeq, header, success, fail) {
  await api
    .get(`/api/v1/challenges/me/${challengeSeq}`, header)
    .then(success)
    .catch(fail);
}

export { getChallengeList, getChallengeDetail };
