import Signup from "./SignupPresenter";
import React, { useState } from "react";
import axios from "axios";
import {
  checkId,
  checkEmail,
  checkNickname,
  checkPassword,
  checkNameLength,
  checkPhoneNumber,
  checkPhoneDuplicate
} from "../validCheck/ValidCheck";

function SignupContainer() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [name, setName] = useState("");
  const [nickname, setNickname] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const url = "http://i6a305.p.ssafy.io:8080";

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  };

  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value);
  };

  const onPasswordConfirmHandler = (event) => {
    setPasswordConfirm(event.currentTarget.value);
  };

  const onNameHandler = (event) => {
    setName(event.currentTarget.value);
  };
  const onNicknameHandler = (event) => {
    setNickname(event.currentTarget.value);
  };
  const onPhoneNumberHandler = (event) => {
    setPhoneNumber(event.currentTarget.value);
  };

  function CheckId() {
    checkId(email, url);
  }
  function CheckEmail() {
    checkEmail(email);
  }
  function CheckNickname() {
    checkNickname(nickname, url);
  }
  function CheckPassword() {
    checkPassword(password, passwordConfirm, email, nickname, name);
  }
  function CheckNameLength() {
    checkNameLength(name);
  }
  function CheckPhoneNumber() {
    checkPhoneNumber(phoneNumber);
  }

  function CheckPhoneDuplicate(){
    checkPhoneDuplicate(phoneNumber,url);
  }
  
  const onCreate = () => {
    if (
      email === "" ||
      password === "" ||
      passwordConfirm === "" ||
      name === "" ||
      nickname === "" ||
      phoneNumber === ""
    ) {
      alert("입력하지 않은 정보가 있습니다. 확인해주세요.");
    } else {
      if (
        checkPassword(password, passwordConfirm, email, nickname, name) === true
      ) {
        console.log(email, password, name, nickname, phoneNumber);
        axios
          .post(url + `/api/v1/members`, 
          {
            email: email,
            password: password,
            name: name,
            nickname: nickname,
            phoneNumber: phoneNumber
          })
          .then(function (response) {
            console.log(response);
            if (response.data.status === 201) {
              alert(response.data.data.msg);
              setEmail("");
              setPassword("");
              setPasswordConfirm("");
              setName("");
              setNickname("");
              setPhoneNumber("");
              document.location.href = "/login";
            } else {
              alert(response.data.data.msg);
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    }
  };

  return ( 
    <>
      <Signup
        onEmailHandler={onEmailHandler}
        checkId={CheckId}
        checkEmail={CheckEmail}
        onPasswordHandler={onPasswordHandler}
        onPasswordConfirmHandler={onPasswordConfirmHandler}
        onNameHandler={onNameHandler}
        onPhoneNumberHandler={onPhoneNumberHandler}
        checkNameLength={CheckNameLength}
        onNicknameHandler={onNicknameHandler}
        checkNickname={CheckNickname}
        checkPassword={CheckPassword}
        checkEmail={CheckEmail}
        checkPhoneNumber={CheckPhoneNumber}
        checkPhoneDuplicate={CheckPhoneDuplicate}
        onCreate={onCreate}
      ></Signup>
    </>
  );
}
export default SignupContainer;
