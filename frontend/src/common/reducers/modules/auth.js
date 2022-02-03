const LOGIN = "auth/LOGIN";

export const setUserInfo = (info) => ({
  type: LOGIN,
  memberInfo: {
    seq: info.memberInfo.seq,
    email: info.memberInfo.email,
    password: info.memberInfo.password,
    name: info.memberInfo.name,
    nickname: info.memberInfo.nickname,
  },
  jwtToken: info.jwtToken,
});

const initialState = {
  type: "",
  memberInfo: {
    seq: "",
    email: "",
    password: "",
    name: "",
    nickname: "",
  },
  jwtToken: "",
};

export default function auth(state = initialState, action) {
  switch (action.type) {
    case LOGIN:
      return {
        ...state,
        memberInfo: action.memberInfo,
        jwtToken: action.jwtToken,
      };
    default:
      return state;
  }
}
