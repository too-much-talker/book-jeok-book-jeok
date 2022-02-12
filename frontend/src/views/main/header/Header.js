import Navigation from "./Navigation";
import styled from "styled-components";
import logo from "../../../res/img/logo.png";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { setUserInfo } from "../../../common/reducers/modules/auth";

const HeaderWrapper = styled.div`
  width: 1170px;
  margin: 0 auto;
`;

const Logo = styled.img.attrs({
  src: logo,
})`
  float: left;
  width: 240px;
  height: 100px;
  // background: red;
  line-height: 80px;
`;

const HeaderRoot = styled.header`
  // background: green;
  width: 100%;
  display: inline-block;
  margin: 0 auto;
`;

function Header() {
  // const { jwtToken, memberInfo } = useSelector((state) => state.authReducer);
  // console.log(jwtToken, memberInfo);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const dispatch = useDispatch();

  const memberInfo = useSelector((state) => state.authReducer.memberInfo);

  useEffect(() => {
    if (
      (jwtToken === null || jwtToken === undefined || jwtToken === "") &&
      (memberInfo !== null || memberInfo !== undefined || memberInfo !== "")
    ) {
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
    }
  }, []);

  return (
    <HeaderRoot>
      <HeaderWrapper>
        <Link to="/">
          <Logo />
        </Link>
        <Navigation
          isLogin={
            jwtToken === null || jwtToken === undefined || jwtToken === ""
              ? false
              : true
          }
        />
      </HeaderWrapper>
    </HeaderRoot>
  );
}

export default Header;
