import styled from "styled-components";
import { NavLink } from "react-router-dom";
import { UserExpPoint } from "../user/myPage/userInfo/UserInfoPresenter";
import React, { useEffect, useState } from "react";
import Weeds from "../user/myPage/userInfo/weeds/Weeds";
import axios from "axios";

const url = "https://i6a305.p.ssafy.io:8443";
const Side = styled.div`
  display: flex;
  border-right: 1px solid #cccccc;
  flex-direction: column;
  width: 30%;
  height: 150%;
  font-size: 2rem;
  text-align: left;
`;
const Menu = styled.div`
  margin-top: 30px;
  width: 200px;
  display: flex;
  flex-direction: column;
  margin-left: 20px;
`;
function Sidebar() {
  const [activities, setActivities] = useState([]);
  const [exp, setExp] = useState(0);
  const [point, setPoint] = useState(0);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const menus = [
    { name: "나의 정보수정", path: "/mypage" },
    { name: "나의 북로그", path: "/mypage/mybooklog" },
    { name: "나의 독서모임", path: "/mypage/mybookclub" },
    { name: "나의 챌린지", path: "/mypage/mychallenge" },
  ];
  const getUserInfo = async () => {
    const response = await axios.get(url + `/api/v1/members/me`, {
      headers: {
        Authorization: `Bearer ` + jwtToken,
      },
    });
    setActivities(response.data.data.activityCountByDate);
    setExp(response.data.data.exp);
    setPoint(response.data.data.point);
    console.log(response);
  };
  useEffect(()=>{
    getUserInfo();
  },[]);
  return (
    <Side>
      <Menu>
        {menus.map((menu, index) => {
          return (
            <NavLink
              style={{ color: "gray", textDecoration: "none" }}
              to={menu.path}
              key={index}
            >
              <div className="sidebar-item">
                <p>{menu.name}</p>
              </div>
            </NavLink>
          );
        })}
      </Menu>
      <UserExpPoint exp={exp} point={point} />
      <Weeds datas={activities} />
    </Side>
  );
}

export default Sidebar;
