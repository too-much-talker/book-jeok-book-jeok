import React, { useState, useEffect } from "react";
import userList from "./asset/data";
import {UserTable, EditUserForm} from "./UserInfoPresenter";
import profile from "./asset/ProfilePicture.png";
import styled from "styled-components";
import "./asset/UserInfoPresenter.css"
import axios from 'axios'
import {checkId, checkEmail, checkNickname, checkPassword, checkNameLength, checkPhoneNumber} from "../../validCheck/ValidCheck";
const url= "https://77e1dca6-cd01-4930-ae25-870e7444cc55.mock.pstmn.io";

const Profile = styled.img`
  width: 150px;
  height: 150px;
  border-radius: 100%;
`
function UserInfoContainer() {
  const [users, setUsers] = useState(userList);

  const deleteUser = (id) => {
    window.location.href = "/";
    // setUsers(users.filter((user) => user.id !== id));
  };

  const [editing, setEditing] = useState(false);

  const initialUser = { id: null, name: "", password:"", email:"", nickname: ""};

  const [currentUser, setCurrentUser] = useState(initialUser);

  const editUser = (user) => {
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
  function CheckId(){
    checkId(users.email, url);
  }
  function CheckEmail(){
    checkEmail(users.email);
  }
  function CheckNickname(){
    checkNickname(users.nickname, url);
  }
  function CheckPassword(){
    checkPassword(users.password,users.email,users.nickname,users.name);
  }
  function CheckNameLength(){
    checkNameLength(users.name);
  }
  function CheckPhoneNumber(){
    checkPhoneNumber(users.phoneNumber);
  }

  const onModify= ()=>{
    if(users.email===''||users.password===''|| users.name==='' || users.nickname===''|| users.phoneNumber===''){
        alert("입력하지 않은 정보가 있습니다. 확인해주세요.")
    }
    else{
        if(checkPassword(users.password, users.email, users.nickname, users.name)===true){
            axios.post(url+`/api/v1/members`,{
                email: users.email,
                password: users.password,
                name: users.name,
                nickname: users.nickname,
                phoneNumber: users.phoneNumber
    
            }).then(function(response){
                if(response.status===201){
                    alert(response.data.data.msg);
                    // document.location.href = '/login'
                }else{
                    alert(response.data.data.msg);
                }
            }).catch(function(error){
                console.log(error);
            });
        }  
    }
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
                editUser={editUser} />
            </div>
          ) : (
            <div>
            </div>
          )}
        </div>
        <div className="row">
          <div className="eight columns">
            {editing ? (
              <div className="edituserform">
                <EditUserForm
                  checkId={CheckId}
                  checkEmail={CheckEmail}
                  checkNameLength={CheckNameLength}
                  checkNickname={CheckNickname}
                  checkPassword={CheckPassword}
                  checkPhoneNumber={CheckPhoneNumber} 
                  onModify={onModify}
                  currentUser={currentUser}
                  setEditing={setEditing}
                  updateUser={updateUser} />
              </div>
            ) : (
              <div>
              </div>
            )}
          </div>
        </div>
      </div>
  );
};

export default UserInfoContainer;