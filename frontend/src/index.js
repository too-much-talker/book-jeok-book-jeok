import React from "react";
import App from "./App";
import { Provider } from "react-redux";
import { createStore } from "redux";
import rootReducer from "./common/reducers/index";
import { composeWithDevTools } from "redux-devtools-extension"; // 리덕스 개발자 도구
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { render } from "react-dom";
import Login from "./views/user/login/LoginContainer";
import { persistStore } from "redux-persist";
import { PersistGate } from "redux-persist/integration/react";
import Signup from "./views/user/signup/SignupContainer";
import Booklogs from "./views/booklogs";
import BooklogListContainer from "./views/booklogs/booklogList/BooklogListContainer";
import MyPage from "./views/user/myPage/index";
import UserInfoContainer from "./views/user/myPage/userInfo/UserInfoContainer";
import styled from "styled-components";
import Header from "./views/main/header/Header";
import "./common/css/index.css";
import BooklogRegisterContainer from "./views/booklogs/booklogRegister/BooklogRegisterContainer";
import BooklogDetailContainer from "./views/booklogs/booklogDetail/BooklogDetailContainer";

const store = createStore(rootReducer, composeWithDevTools()); // 스토어를 만듭니다.
const listener = () => {
  const state = store.getState();
  console.log(state);
};
const unsubscribe = store.subscribe(listener);

const persistor = persistStore(store);

const Body = styled.body`
  // background: skyblue;
  text-align: center; // 수평 가운데 정렬
  width: 100vw;
  height: 100%;
  // margin: 0 auto;
`;

// box 2개를 감싸는 Wrapper를 하나 더 만듦.
const Wrapper = styled.div`
  // overflow: auto; // 크기를 줄이면 스크롤 생김.(텍스트나 박스 크기는 그대로 유지)
  // border: solid 1px;
  width: 1080px;
  // height: 100vh; // 이부분을 주석 풀면 아래까지 그리드가 내려옴
  // display: inline-block; // Body-ScrollWrapper 수평 가운데 정렬
  // margin-top: 10vh;
  margin: 0 auto;
`;

const rootElement = document.getElementById("root");
render(
  <Provider store={store}>
    <PersistGate persistor={persistor}>
      <BrowserRouter>
        <Body>
          <Header />
          <Wrapper>
            <Routes>
              <Route path="/" element={<App />} />
              <Route path="/login" element={<Login />} />
              <Route path="/signup" element={<Signup />} />
              <Route path="/booklogs/*" element={<Booklogs />}>
                <Route index element={<BooklogListContainer />} />
                {/* <Route path="detail"/> */}
              </Route>
              <Route path="/mypage" element={<MyPage />}>
                <Route index element={<UserInfoContainer />} />
                <Route path="mybooklog" />
                <Route path="mybookclub" />
                <Route path="mychallenge" />
              </Route>
              <Route path="/booklogregister" element={<BooklogRegisterContainer/>} />
              <Route path="/booklogdetail" element={<BooklogDetailContainer/>} />
            </Routes>
          </Wrapper>
        </Body>
      </BrowserRouter>
    </PersistGate>
  </Provider>,
  rootElement
);
