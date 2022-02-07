import Navigation from "./Navigation";
import styled from "styled-components";
import logo from "../../../res/img/logo.png";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";

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
