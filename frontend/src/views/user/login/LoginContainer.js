import LoginPresenter from "./LoginPresenter";
import { useSelector, useDispatch } from "react-redux";
import { setUserInfo } from "../../../common/reducers/modules/auth";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
import { login } from "../../../common/api/auth";
import { useEffect } from "react";

function LoginContainer() {
  const navigate = useNavigate();

  const { jwtToken } = useSelector((state) => state.authReducer);
  useEffect(() => {
    if (jwtToken !== "") {
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
          // let token = response.data.data["jwtToken"];
          // sessionStorage.setItem("access-token", JSON.stringify(token));
          dispatch(setUserInfo(response.data.data));
          console.log("로그인 성공");
          navigate("/");
        }
      },
      () => {
        alert("로그인 실패");
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
