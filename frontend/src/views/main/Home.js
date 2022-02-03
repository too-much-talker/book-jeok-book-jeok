import styled from "styled-components";

const HomeWrapper = styled.div`
  text-align: left;
  position: relative;
`;

const Circle = styled.div`
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background: black;
  display: inline-block;
  position: absolute;
`;

const Circle2 = styled(Circle)`
  right: 0;
`;

function Home() {
  return (
    <HomeWrapper>
      <h1>Home</h1>
      <div>this is Home view. please click menu.</div>
      <Circle />
      <Circle2 />
    </HomeWrapper>
  );
}

export default Home;
