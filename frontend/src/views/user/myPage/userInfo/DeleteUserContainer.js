import DeleteUserPresenter from "./DeleteUserPresenter";
import React, { useState, useEffect } from "react";
import styled from "styled-components";
import axios from "axios";
import { useSelector } from "react-redux";

function DeleteUserContainer({ url, isOpen, onCancel }) {
  const user = useSelector((state) => state.authReducer);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [confirmPassword, setConfirmPassword] = useState();

  function handleEmail(event) {
    setEmail(event.target.value);
  }
  function handlePassword(event) {
    setPassword(event.target.value);
  }
  function handleConfirmPassword(event) {
    setConfirmPassword(event.target.value);
  }
  function onsubmit() {
    if (password !== confirmPassword) {
      alert("비밀번호와 비밀번호 확인이 다릅니다.");
    } else if (email !== user.memberInfo.email) {
      alert("이메일을 올바르게 작성해주세요.");
    } else {
      const headers = {
        Authorization: `Bearer ` + jwtToken,
      };
      const data = {
        email: email,
        password: password,
      };

      axios
        .delete(url + `/api/v1/members/resign`, { headers, data })
        .then(function (response) {
          console.log(jwtToken);
          if (response.data.status === 204) {
            sessionStorage.clear();
            document.location.href = `/`;
          }
          alert(response.data.data.msg);
        })
        .catch(function (error) {});
    }
  }
  return (
    <DeleteUserPresenter
      handleEmail={handleEmail}
      handlePassword={handlePassword}
      handleConfirmPassword={handleConfirmPassword}
      isOpen={isOpen}
      onCancel={onCancel}
      onsubmit={onsubmit}
    ></DeleteUserPresenter>
  );
}
export default DeleteUserContainer;
