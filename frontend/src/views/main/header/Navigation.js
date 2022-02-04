import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";

const NaviBlock = styled.nav`
  height: 70px;
  // background: yellow;
  font-size: 1.6rem; // 메뉴 크기
`;

const LoginButton = styled.button`
  background: lightgrey;
  padding: 1rem;
  padding-top: 0;
  padding-bottom: 0;
  font-size: 1.5rem;
`;

const UL = styled.ul`
  list-style-type: none;
  margin: 0;
  padding: 0;
  float: right;
`;

const LI = styled.li`
  float: left;
  display: block;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  line-height: 80px;
`;

const StyledLink = styled(Link)`
  text-decoration: none;
  color: black;
`;

const menus = ["북로그", "독서모임", "챌린지", "공지"];
const links = ["/booklogs/list/like", "/", "/", "/"];

function Navigation() {
  return (
    <NaviBlock>
      <UL>
        {menus.map((menu, index) => (
          <LI>
            <StyledLink to={links[index]}>{menu}</StyledLink>
          </LI>
        ))}
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
