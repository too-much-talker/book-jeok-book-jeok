const LOGIN = "auth/LOGIN";

export const setUserInfo = (info) => ({
  type: LOGIN,
  memberInfo: {
    seq: info.seq,
    email: info.email,
    password: info.password,
    name: info.name,
    nickname: info.nickname,
  },
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
};

export default function auth(state = initialState, action) {
  switch (action.type) {
    case LOGIN:
      return {
        ...state,
        memberInfo: action.memberInfo,
      };
    default:
      return state;
  }
}
