import { Link } from "react-router-dom";
import styled from "styled-components";
import React from "react";

const TabWrapper = styled.div`
  margin: 1rem;
  margin-top: 5rem;
  text-align: left;
`;

const Tab = styled.span`
  border-radius: 10%;
  box-shadow: 2px 3px 5px 0px grey;
  padding: 0.3rem;
  margin: 0.2rem;
  &:hover {
    cursor: pointer;
  }
  background: ${(props) => (props.active ? "white" : "black")};
`;

const StyledLink = styled(Link)`
  text-decoration: none;
  color: ${(props) => (props.active ? "black" : "white")};
`;

const Button = styled.button`
  float: right;
  padding: 10px;
  padding-top: 5px;
  padding-buttom: 5px;
  background: grey;
  color: white;
  &:hover {
    cursor: pointer;
    background: black;
  }
  border-radius: 5px;
`;

function BooklogNavi({ isPopular }) {
  return (
    <TabWrapper>
      <Button>
        <StyledLink to="/booklogregister">북로그 작성</StyledLink>
      </Button>
      <Tab active={isPopular}>
        <StyledLink to="/booklogs/list/like" active={isPopular}>
          #인기순
        </StyledLink>
      </Tab>
      <Tab active={!isPopular}>
        <StyledLink to="/booklogs/list/recent" active={!isPopular}>
          #최신순
        </StyledLink>
      </Tab>
    </TabWrapper>
  );
}

export default BooklogNavi;
