import React, { useState, useEffect } from "react";
import userList from "./asset/data";
import { UserTable, EditUserForm } from "./UserInfoPresenter";
import profile from "./asset/ProfilePicture.png";
import styled from "styled-components";
import axios from 'axios'
import {
  checkId,
  checkEmail,
  checkNickname,
  checkPassword,
  checkNameLength,
  checkPhoneNumber,
  checkPasswordConfim,
} from "../../validCheck/ValidCheck";
const url = "https://77e1dca6-cd01-4930-ae25-870e7444cc55.mock.pstmn.io";

const Profile = styled.img`
  width: 150px;
  height: 150px;
  border-radius: 100%;
`;
function UserInfoContainer() {
  const [users, setUsers] = useState(userList);
  const [enteredEmail, setEnterdEmail] = useState(userList[0].email);
  const [enteredName, setEnterdnName] = useState(userList[0].name);
  console.log(enteredName);
  const [enteredPassword, setEnteredPassword] = useState(userList[0].password);
  const [enteredPasswordConfirm, setEnteredPasswordConfirm] = useState(
    userList[0].password
  );
  const [enteredNickName, setEnteredNickName] = useState(userList[0].nickname);
  const [enteredPhone, setEnteredPhone] = useState(userList[0].phoneNumber);

  const emailChangeHandler = (event) => {
    setEnterdEmail(event.target.value);
  };
  const nameChangeHandler = (event) => {
    setEnterdnName(event.target.value);
  };
  const passwordChangeHandler = (event) => {
    setEnteredPassword(event.target.value);
  };
  const passwordChangeConfirmHandler = (event) => {
    setEnteredPasswordConfirm(event.target.value);
  };

  const nicknameChangeHandler = (event) => {
    setEnteredNickName(event.target.value);
  };
  const phoneChangeHandler = (event) => {
    setEnteredPhone(event.target.value);
  };

  const deleteUser = (id) => {
    window.location.replace("/");
    setUsers(users.filter((user) => user.id !== id));
  };

  const [editing, setEditing] = useState(false);

  const initialUser = {
    id: null,
    name: "",
    password: "",
    email: "",
    nickname: "",
  };

  const [currentUser, setCurrentUser] = useState(initialUser);

  const editUser = (id, user) => {
    setEditing(true);
    setCurrentUser(user);
  };

  const updateUser = (newUser) => {
    setUsers(
      users.map((user) => (user.id === currentUser.id ? newUser : user))
    );
    setCurrentUser(initialUser);
    setEditing(false);
  };
  function validId() {
    return checkId(enteredEmail, url);
  }
  function validEmail() {
    return checkEmail(enteredEmail);
  }
  function validNickname() {
    return checkNickname(enteredNickName, url);
  }
  function validPassword() {
    return checkPassword(
      enteredPassword,
      enteredEmail,
      enteredNickName,
      enteredName
    );
  }
  function validPasswordConfirm() {
    return checkPasswordConfim(enteredPassword, enteredPasswordConfirm);
  }
  function validNameLength() {
    return checkNameLength(enteredName);
  }
  function validPhoneNumber() {
    return checkPhoneNumber(enteredPhone);
  }

  const onModify = () => {
    axios
      .put(url + `/api/v1/members`, {
        email: enteredEmail,
        password: enteredPassword,
        name: enteredName,
        nickname: enteredNickName,
        phoneNumber: enteredPhone,
      })
      .then(function (response) {
        if (response.status === 201) {
          alert(response.data.data.msg);//modified
        } else {
          alert(response.data.data.msg);
        }
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <div className="container">
      <div className="title">
        <h2>나의 정보수정</h2>
        <Profile src={profile}></Profile>
      </div>
      <div className="usertable">
        {!editing ? (
          <div>
            <UserTable
              users={users}
              deleteUser={deleteUser}
              editUser={editUser}
            />
          </div>
        ) : (
          <div></div>
        )}
      </div>
      <div className="row">
        <div className="eight columns">
          {editing ? (
            <div className="edituserform">
              <EditUserForm
                checkId={validId}
                checkEmail={validEmail}
                checkNickname={validNickname}
                checkPassword={validPassword}
                checkPhoneNumber={validPhoneNumber}
                checkNameLength={validNameLength}
                checkPasswordConfirm={validPasswordConfirm}
                onModify={onModify}
                currentUser={currentUser}
                setEditing={setEditing}
                updateUser={updateUser}
                emailChange={emailChangeHandler}
                passwordChange={passwordChangeHandler}
                passwordConfirmChange={passwordChangeConfirmHandler}
                nameChange={nameChangeHandler}
                nicknameChange={nicknameChangeHandler}
                phoneChange={phoneChangeHandler}
                name={enteredName}
                email={enteredEmail}
                password={enteredPassword}
                passwordConfirm={enteredPasswordConfirm}
                nickname={enteredNickName}
                phoneNumber={enteredPhone}
              />
            </div>
          ) : (
            <div></div>
          )}
        </div>
      </div>
    </div>
  );
}

export default UserInfoContainer;