import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { useDispatch } from "react-redux";
import { setUserInfo } from "../../../common/reducers/modules/auth";
import { useNavigate } from "react-router-dom";

const NaviBlock = styled.nav`
  height: 70px;
  // background: yellow;
  font-size: 1.8rem; // 메뉴 크기
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


const links = ["/booklogs/list/like", "/", "/search", "/", "/","/mypage"];

function Navigation({ isLogin }) {
  const menus = isLogin ? ["북로그", "독서모임", "책정보", "챌린지", "공지", "마이페이지"] : ["북로그", "독서모임", "책정보", "챌린지", "공지"];
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const logout = () => {
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
    window.location.replace("/");
  };

  return (
    <NaviBlock>
      <UL>
        {menus.map((menu, index) => (
          <LI key={index}>
            <StyledLink to={links[index]}>{menu}</StyledLink>
          </LI>
        ))}
        <LI>
          {isLogin ? (
            <LoginButton onClick={logout}>로그아웃</LoginButton>
          ) : (
            <Link to="/login">
              <LoginButton>로그인</LoginButton>
            </Link>
          )}
        </LI>
      </UL>
    </NaviBlock>
  );
}

export default Navigation;
