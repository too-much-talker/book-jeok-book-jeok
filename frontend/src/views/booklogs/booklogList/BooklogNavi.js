import { Link } from "react-router-dom";
import styled from "styled-components";

const TabWrapper = styled.div`
  margin: 1rem;
  margin-top: 2rem;
`;

const Tab = styled.span`
  border-radius: 10%;
  box-shadow: 2px 3px 5px 0px grey;
  padding: 0.3rem;
  margin: 0.2rem;
  &:hover {
    cursor: pointer;
  }
  background: ${(props) => (props.active ? "darkgrey" : "white")};
`;

const StyledLink = styled(Link)`
  text-decoration: none;
  color: ${(props) => (props.active ? "white" : "black")};
`;

function BooklogNavi({ isPopular }) {
  return (
    <TabWrapper>
      <Tab active={isPopular}>
        <StyledLink to="/booklogs/list/popular" active={isPopular}>
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
