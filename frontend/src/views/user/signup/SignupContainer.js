import Signup from "./SignupPresenter";
import React, { useState,useEffect } from "react";
import axios from "axios";
import {
  checkId,
  checkEmail,
  checkNickname,
  checkNameLength,
  checkPhoneNumber,
  checkPhoneDuplicate,
  checkPassword
} from "../validCheck/ValidCheck";

function SignupContainer() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [name, setName] = useState("");
  const [nickname, setNickname] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [checkValid, setCheckValid]= useState(true);
  const url = "https://i6a305.p.ssafy.io:8443";

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
    checkPassword(password, email, nickname, name,checkValidHandler);
    
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
  
  function checkPasswordConfim(password, passwordConfirm){
    if (password !== passwordConfirm) {
      return false;
    }
    return true;
  }

  function checkValidHandler(param){
    setCheckValid(param);
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
      console.log(checkValid);
      if (
        checkValid===true
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
