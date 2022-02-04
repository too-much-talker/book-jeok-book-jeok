import React from "react";
import ReactDOM from "react-dom";
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
import {SearchMainContainer} from "./views/bookInfo/bookSearch/SearchMainContainer";
import SearchResultContainer from "./views/bookInfo/bookSearch/SearchResultContainer";
import SearchNavContainer from "./views/bookInfo/bookSearch/SearchNavContainer";
import BookDetailContainer from "./views/bookDetail/BookDetailContainer";
const store = createStore(rootReducer, composeWithDevTools()); // 스토어를 만듭니다.
const listener = () => {
  const state = store.getState();
  console.log(state);
};
const unsubscribe = store.subscribe(listener);

const persistor = persistStore(store);

const rootElement = document.getElementById("root");
render(
  <Provider store={store}>
    <PersistGate persistor={persistor}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<App />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/searchMain" element={<SearchMainContainer />} />
          <Route path="/search" element={<SearchMainContainer />} />
          <Route path="/search/:category/:keyword" element={<SearchResultContainer/>} />
          <Route path="/detail/:seq" element={<BookDetailContainer/>} />
        </Routes>
      </BrowserRouter>
    </PersistGate>
  </Provider>,
  rootElement
);
