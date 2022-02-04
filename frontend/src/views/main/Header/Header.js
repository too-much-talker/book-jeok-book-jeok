import Navigation from "./Navigation";
import styled from "styled-components";
import logo from "../../../res/img/logo.png";
import { Link } from "react-router-dom";

const HeaderWrapper = styled.div`
  width: 1170px;
  margin: 0 auto;
`;

const Logo = styled.img.attrs({
  src: logo,
})`
  float: left;
  width: 200px;
  height: 80px;
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
  return (
    <HeaderRoot>
      <HeaderWrapper>
        <Link to="/">
          <Logo />
        </Link>
        <Navigation />
      </HeaderWrapper>
    </HeaderRoot>
  );
}

export default Header;
