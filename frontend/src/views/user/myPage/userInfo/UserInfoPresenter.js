import React, { useState } from "react";
import userList from "./asset/data";
import {UserTable, EditUserForm} from "./UserInfoContainer";
import profile from "./asset/ProfilePicture.png";
import styled from "styled-components";
import "./asset/UserInfoPresenter.css"

const Profile = styled.img`
  width: 150px;
  height: 150px;
  border-radius: 100%;
`
const UserInfoPresenter = () => {
  const [users, setUsers] = useState(userList);

  const deleteUser = (id) => {
    window.location.href = "/";
    setUsers(users.filter((user) => user.id !== id));
  };

  const [editing, setEditing] = useState(false);

  const initialUser = { id: null, name: "", password:"", email:"", nickname: "" };

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

export default UserInfoPresenter;