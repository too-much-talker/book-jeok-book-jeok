import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";

const NaviBlock = styled.nav`
  height: 70px;
  background: yellow;
`;

const NaviList = styled.div`
  position: relative;
  bottom: -25px;
  background: white;
  float: right;
  width: 64%;
  height: 45px;
  margin-right: 150px;
`;

const NaviItem = styled.div`
  width: 100px;
  height: 20px;
  backgroud: white;
  float: left;
  margin-left: 80px;
  margin-top: 10px;
  text-align: center;
  vertical-align: middle;
  font-size: 20px;
`;
const LoginButton = styled.button`
  width: 120px;
  height: 45px;
  position: absolute;
  right: 0px;
  border: 0px;
  outline: 0px;
  backgroud-color: #f8f4f0;
  border-radius: 0.7em;
  font-size: 20px;
  text-decoration: none;
`;

const UL = styled.ul`
  list-style-type: none;
  margin: 0;
  padding: 0;
`;

const LI = styled.li`
  float: right;
  display: block;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
`;

function Navigation() {
  return (
    <NaviBlock>
      <UL>
        <LI>북로그</LI>
        <LI>독서모임</LI>
        <LI>챌린지</LI>
        <LI>책정보</LI>
        <LI>공지</LI>
        <LI>
          <Link to="/login">
            <LoginButton>로그인</LoginButton>
          </Link>
        </LI>
      </UL>
    </NaviBlock>
  );
}

export default Navigation;
