import styled from 'styled-components';
import icon from  "../../res/img/search.png";

const SearchBlock = styled.div`
text-align:center;
`;
const SearchIcon = styled.img.attrs({
    src: icon,
  })`
position:absolute;
width:30px;
height:30px;
margin-left:30px;
margin-top:62px;
  `;
const SearchBar = styled.input`
placeholder:dd;
width:800px;
height:60px;
display: inline-block;
margin-right:200px;
margin-top:45px;
border:0px;
outline:0px;
border-radius:2em;
box-shadow: 2px 2px 20px 1px rgba(0, 0, 0, 0.2);
text-align:center;
font-size:20px;
::placeholder{
  text-align:center;
  font-size: 20px;
}
`;

const Welcome= styled.div`
position:absolute;
top:260px;
right:450px;
`;

function SearchPresenter(){
    return(
      <>
        <SearchBlock>
            <SearchIcon></SearchIcon>
            <SearchBar placeholder='당신의 책을 검색해보세요!'></SearchBar>
        </SearchBlock>
        <Welcome>북적북적이 처음이신가요?</Welcome>
        </> 
    );
}

export default SearchPresenter;