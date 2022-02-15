import React, { useState, useEffect } from "react";
import { UserTable, EditUserForm } from "./UserInfoPresenter";
import styled from "styled-components";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { setUserInfo } from "../../../../common/reducers/modules/auth";
import {
  checkId,
  checkEmail,
  checkNickname,
  checkPassword,
  checkNameLength,
  checkPhoneNumber,
  checkPasswordConfim,
} from "../../validCheck/ValidCheck";
import { useDispatch, useSelector } from "react-redux";
const url = "https://i6a305.p.ssafy.io:8443";

// const Profile = styled.img`
//   width: 150px;
//   height: 150px;
//   border-radius: 100%;
// `;
const Wrapper = styled.div`
  text-align: center;
  width: 100%;
`;
function UserInfoContainer() {
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const navigate = useNavigate();

  useEffect(() => {
    if (jwtToken === null || jwtToken === undefined || jwtToken === "") {
      navigate("/login");
      alert("로그인 후 이용 가능합니다.");
    }
  }, [jwtToken]);

  const fistuser = useSelector((state) => state.authReducer).memberInfo;
  const [user, setUser] = useState(fistuser);
  const [enteredEmail, setEnterdEmail] = useState(user.email);
  const [enteredName, setEnterdnName] = useState(user.name);
  // console.log(enteredName);
  const [enteredPassword, setEnteredPassword] = useState(user.password);
  const [enteredPasswordConfirm, setEnteredPasswordConfirm] = useState(
    user.password
  );
  const [enteredNickName, setEnteredNickName] = useState(user.nickname);
  const [enteredPhone, setEnteredPhone] = useState("010-1234-5678");

  const [modalOpen, setModalOpen] = useState(false);

  function handleModalOpen() {
    setModalOpen(true);
  }
  function handleModalClose() {
    setModalOpen(false);
  }

  const dispatch = useDispatch();
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

  const logOut = () => {
    dispatch(
      setUserInfo({
        memberInfo: {
          seq: "",
          email: "",
          password: "",
          name: "",
          nickname: "",
        },
        jwtToken: "",
      })
    );
    sessionStorage.removeItem("jwtToken");
    alert("다시 로그인 해주세요.");
    navigate("/login");
  };

  const deleteUser = (id) => {
    axios
      .delete(
        url + `/api/v1/members/resign`,
        {
          email: enteredEmail,
          password: enteredPassword,

          // phoneNumber: '010-1234-5678',
        },
        {
          headers: {
            Authorization: `Bearer ` + jwtToken,
          },
        }
      )
      .then(function (response) {})
      .catch(function (error) {
        console.log(error);
      });
    navigate("/");
    // setUsers(users.filter((user) => user.id !== id));
  };

  const [editing, setEditing] = useState(false);

  const editUser = () => {
    setEditing(true);
  };

  const updateUser = () => {
    const newUser = {
      email: enteredEmail,
      name: enteredName,
      password: enteredPassword,
      nickname: enteredNickName,
    };
    setUser(newUser);
    setEditing(false);
    logOut();
  };
  function validId(event) {
    event.preventDefault();
    return checkId(enteredEmail, url);
  }
  function validEmail() {
    return checkEmail(enteredEmail);
  }
  function validNickname(event) {
    event.preventDefault();
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
      .put(
        url + `/api/v1/members`,
        {
          email: enteredEmail,
          password: enteredPassword,
          name: enteredName,
          nickname: enteredNickName,
          // phoneNumber: '010-1234-5678',
        },
        {
          headers: {
            Authorization: `Bearer ` + jwtToken,
          },
        }
      )
      .then(function (response) {
        if (response.status === 201) {
          alert(response.data.data.msg); //modified
        } else {
          alert(response.data.data.msg);
        }
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  const Title = styled.div`
    margin-left: 150px;
  `;
  return (
    <div className="container">
      <Title>
        <h1>나의 정보수정</h1>
        {/* <hr></hr>
        <Profile src={profile}></Profile> */}
        <br></br>
        <br></br>
      </Title>
      <div className="usertable">
        {!editing ? (
          <div>
            <UserTable
              url={url}
              handleModalOpen={handleModalOpen}
              handleModalClose={handleModalClose}
              modalOpen={modalOpen}
              user={user}
              deleteUser={deleteUser}
              editUser={editUser}
            />
          </div>
        ) : (
          <div></div>
        )}
      </div>
      <Wrapper>
        <div>
          {editing ? (
            <div className="edituserform">
              <EditUserForm
                checkId={validId}
                checkEmail={validEmail}
                checkNickname={validNickname}
                checkPassword={validPassword}
                // checkPhoneNumber={validPhoneNumber}
                checkNameLength={validNameLength}
                checkPasswordConfirm={validPasswordConfirm}
                onModify={onModify}
                setEditing={setEditing}
                updateUser={updateUser}
                emailChange={emailChangeHandler}
                passwordChange={passwordChangeHandler}
                passwordConfirmChange={passwordChangeConfirmHandler}
                nameChange={nameChangeHandler}
                nicknameChange={nicknameChangeHandler}
                // phoneChange={phoneChangeHandler}
                name={enteredName}
                email={enteredEmail}
                password={enteredPassword}
                passwordConfirm={enteredPasswordConfirm}
                nickname={enteredNickName}
                // phoneNumber={enteredPhone}
              />
            </div>
          ) : (
            <div></div>
          )}
        </div>
      </Wrapper>
    </div>
  );
}

export default UserInfoContainer;
