import styled from "styled-components";
import { NavLink } from "react-router-dom";
import { UserExpPoint } from "../user/myPage/userInfo/UserInfoContainer";
import React, { useState } from "react";
import userList from "../user/myPage/userInfo/asset/data";

const Side = styled.div`
  display: flex;
  border-right: 1px solid black;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 20%;
`
const Menu = styled.div`
  margin-top: 30px;
  width: 200px;
  display: flex;
  flex-direction: column;
  `
function Sidebar() {
  const [users] = useState(userList);
  const menus = [
    { name: "나의 정보수정", path: "/mypage" },
    { name: "나의 북로그", path: "/mybooklog" },
    { name: "나의 독서모임", path: "/mybookclub" },
    { name: "나의 챌린지", path: "/mychallenge" }
  ];

  return (
    <Side>
      <Menu>
        {menus.map((menu, index) => {
          return (
            <NavLink
              exact
              style={{color: "gray", textDecoration: "none"}}
              to={menu.path}
              key={index}
              activeStyle={{color: "black"}}
            >
              <div className="sidebar-item">
                <p>{menu.name}</p>
              </div>
            </NavLink>
          );
        })}
      </Menu>
      <UserExpPoint 
        users={users}
      />

    </Side>
  );
}

export default Sidebar;