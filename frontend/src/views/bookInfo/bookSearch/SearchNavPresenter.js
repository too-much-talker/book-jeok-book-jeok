import styled from "styled-components";
import icon from  "../../../res/img/search.png";

const SearchBlock = styled.div`
position:absolute;
left:50%;
top:20%;
width:1200px;
height:60px;
text-align:center;
transform: translate(-50%, -50%);
`;

const SelectBox = styled.select`
position :relative;
width:80px;
height:50px;
margin-right:10px;
`;

const SearchInput= styled.input`
position:relative;
border-left-width:0;
border-right-width:0;
border-top-width:0;
border-bottom-width:1;
width:766px;
height:50px;
margin-right:10px;
:focus {outline:none;}
font-size:20px;
padding-left:20px;
`;



const SearchIcon = styled.img.attrs({
    src: icon,
  })`
position:absolute;
width:30px;
height:30px;
top:20%;
left:82%;
  `;


function SearchNavPresenter( {KEYWORD,keyword,searchKeyword,onSubmit,onSearchHandler,onSearchCategoryHandler}){
    return(    
    <SearchBlock>
        <SelectBox defaultValue="total" onClick={onSearchCategoryHandler}>
                <option value="total" >전체</option>
                <option value="title">책 제목</option>
                <option value="author">저자</option>
                <option value="publisher">출판사</option>
            </SelectBox>

        <SearchInput value={KEYWORD} onChange={onSearchHandler} onKeyPress={onSubmit}></SearchInput>

        <SearchIcon></SearchIcon>
    </SearchBlock>);

}

export default SearchNavPresenter;