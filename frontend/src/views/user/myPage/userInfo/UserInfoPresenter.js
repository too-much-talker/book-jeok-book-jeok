import React, { useState, useEffect } from "react";
import styled from "styled-components";
import {
  checkId,
  checkEmail,
  checkNickname,
  checkPassword,
  checkNameLength,
  checkPhoneNumber,
} from "../../validCheck/ValidCheck";

const Form2 = styled.div`
  margin-left: 15rem;
  text-align: left;
`;

const Form1 = styled.div`
  margin-left: 15rem;
  padding-top: 5rem;
  padding-bottom: 5rem;
  border-radius: 3rem;
  border: 1px solid #cccccc;
  width: 550px;
`;
const EditUserForm = (props) => {
  // useEffect(() => {
  //     setUser(props.currentUser)
  // }, [props])
  const [emailIsValid, setEmailIsValid] = useState(true);
  const [nameIsValid, setNameIsValid] = useState(true);
  const [passwordIsValid, setPasswordIsValid] = useState(true);
  const [passwordConfirmIsValid, setPasswordConfirmIsValid] = useState(true);
  // const [phoneIsValid, setPhoneIsValid] = useState(true);
  const [nicknameIsValid, setNicknameIsValid] = useState(true);

  const emailBlurHandler = () => {
    const isValid = props.checkEmail();
    setEmailIsValid(isValid);
  };
  const nameBlurHandler = () => {
    const isValid = props.checkNameLength();
    setNameIsValid(isValid);
  };
  const passwordBlurHandler = () => {
    const isValid = props.checkPassword();
    setPasswordIsValid(isValid);
  };
  const passwordConfirmBlurHandler = () => {
    const isValid = props.checkPasswordConfirm();
    setPasswordConfirmIsValid(isValid);
  };
  // const phoneBlurHandler = () => {
  //   const isValid = props.checkPhoneNumber();
  //   setPhoneIsValid(isValid);
  // };
  const nicknameBlurHandler = () => {
    const isValid = props.checkPhoneNumber();
    setNicknameIsValid(isValid);
  };
  let formIsValid;
  if (
    nameIsValid &&
    passwordIsValid &&
    passwordConfirmIsValid &&
    nicknameIsValid &&
    emailIsValid
    // phoneIsValid
  )
    formIsValid = true;
  else formIsValid = false;

  const handleSubmit = (e) => {
    e.preventDefault();
    if (props.name && props.password && props.email && props.nickname)
      props.updateUser();
    props.onModify();
  };
  const buttonStyle = formIsValid ? "button-primary" : "";
  // const nameBlurHandler = () => {
  //     checkNameLength(user.name);
  // };

  return (
    <Form1>
      <Form2>
        <label>이름</label>
        <input
          type="text"
          value={props.name}
          name="name"
          onChange={props.nameChange}
          onBlur={nameBlurHandler}
        />
        <label>비밀번호</label>
        <input
          type="password"
          // value={props.password}
          name="password"
          onChange={props.passwordChange}
          onBlur={passwordBlurHandler}
        />
        <label>비밀번호확인</label>
        <input
          type="password"
          // value={props.passwordConfirm}
          name="password"
          onChange={props.passwordConfirmChange}
          onBlur={passwordConfirmBlurHandler}
        />
        <label>이메일</label>
        <input
          type="text"
          value={props.email}
          name="email"
          onChange={props.emailChange}
          onBlur={emailBlurHandler}
        />
        {/* <input className="u-full-width" type="text" value={user.email} name="email" onChange={handleChange} onBlur={props.checkEmail}/> */}
        <button onClick={props.checkId}>중복확인</button>
        <label>닉네임</label>
        <input
          type="text"
          value={props.nickname}
          name="nickname"
          onChange={props.nicknameChange}
          onBlur={nicknameBlurHandler}
        />
        <button onClick={props.checkNickname}>중복확인</button>
        {/* <label>핸드폰번호</label>
      <input
        type="text"
        value={props.phoneNumber}
        name="phoneNumber"
        onChange={props.phoneChange}
        onBlur={phoneBlurHandler}
      /> */}
        {/* <input className="u-full-width" type="text" value={user.phoneNumber} name="phoneNumber" onChange={handleChange} onBlur={props.checkPhoneNumber}/> */}
        <br></br>
        <br></br>
        <div>
          <button
            className={buttonStyle}
            disabled={!formIsValid}
            type="submit"
            onClick={handleSubmit}
          >
            수정하기
          </button>
          <button type="submit" onClick={() => props.setEditing(false)}>
            취소하기
          </button>
        </div>
      </Form2>
    </Form1>
  );
};

const UserTable = (props) => {
  const { name, password, email, nickname } = props.user;
  return (
    <Form1>
      <Form2>
        <label>이름</label>
        <p>{name}</p>
        <label>비밀번호</label>
        <p>*********</p>
        <label>이메일</label>
        <p>{email}</p>
        <label>닉네임</label>
        <p>{nickname}</p>
        {/* <label>핸드폰번호</label>
        <p>010-5023-9161</p> */}
        <br></br>
        <br></br>
        <button onClick={props.editUser}>수정하기</button>
        <button>탈퇴하기</button>
      </Form2>
    </Form1>
  );
};

const UserExpPoint = (props) => {
  const ExpPoint = styled.div`
    font-size: 0.5rem;
  `;
  const Point = styled.div`
    display: inline-block;
    margin-left: 70px;
  `;
  const Exp = styled.div`
    display: inline-block;
  `;
  return (
      <ExpPoint>
        <Exp>
          경험치 {props.exp}
        </Exp>
        <Point>
          포인트 {props.point}
        </Point>
      </ExpPoint>
  );
};

export { EditUserForm, UserTable, UserExpPoint };
