import HeaderPresenter from "./HeaderPresenter";
import SearchContainer from "./SearchContainer";
import styled from 'styled-components';
import ContentPresenter from "./ContentPresenter";

const HomeBlock= styled.div`
background: #F7F7F7;
width:1900px;
height:1000px;
margin-left:-10px;
`;
function Home() {
  return (
    <HomeBlock>
      <HeaderPresenter></HeaderPresenter>
      <SearchContainer></SearchContainer>
      <ContentPresenter></ContentPresenter>
    </HomeBlock>
  );
}

export default Home;
