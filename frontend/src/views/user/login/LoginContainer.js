import LoginPresenter from "./LoginPresenter";
import { useSelector, useDispatch } from "react-redux";
import { setUserInfo } from "../../../common/reducers/modules/auth";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
import { login } from "../../../common/api/auth";
import { useEffect } from "react";

function LoginContainer() {
  const navigate = useNavigate();

  // const { jwtToken } = useSelector((state) => state.authReducer);
  const jwtToken = sessionStorage.getItem("jwtToken");
  useEffect(() => {
    if (jwtToken !== "" && jwtToken !== undefined && jwtToken !== null) {
      alert("접근이 불가능한 페이지입니다.");
      navigate("/");
    }
  }, []);

  // const userInfo = useSelector((state) => state.authReducer.userInfo.id);
  const dispatch = useDispatch();

  const onLogin = (id, pw) => {
    const userInfo = {
      email: id,
      password: pw,
    };

    login(
      userInfo,
      (response) => {
        if (response.data.status === 200) {
          let token = response.data.data["jwtToken"];
          sessionStorage.setItem("jwtToken", JSON.stringify(token));
          dispatch(setUserInfo(response.data.data));
          console.log("로그인 성공");
          window.location.replace("/");
        } else {
          alert("계정이 없습니다.");
        }
      },
      (error) => {
        console.log("로그인 에러발생");
      }
    );
  };

  const [id, setId] = useState("");
  const [pw, setPw] = useState("");

  const onIdChange = (e) => {
    setId(e.currentTarget.value);
  };

  const onPwChange = (e) => {
    setPw(e.currentTarget.value);
  };

  const onSubmit = (e) => {
    onLogin(id, pw);
  };

  return (
    <>
      <LoginPresenter
        onIdChange={onIdChange}
        onPwChange={onPwChange}
        onSubmit={onSubmit}
      />
    </>
  );
}

export default LoginContainer;
