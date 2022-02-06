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
position :absolute;
width: 82px;
height: 35px;
left:20%;
top:16%;
background: url('https://freepikpsd.com/media/2019/10/down-arrow-icon-png-7-Transparent-Images.png') calc(100% - 5px) center no-repeat;
background-size: 20px;
padding: 5px 10px 5px 5px;
border-radius: 4px;
`;


const SearchInput= styled.input`
position:relative;
border-left-width:0;
border-right-width:0;
border-top-width:0;
border-bottom-width:1;
width:600px;
height:50px;
margin-right:10px;
:focus {outline:none;}
font-size:20px;
padding-left:110px;
`;



const SearchIcon = styled.img.attrs({
    src: icon,
  })`
position:absolute;
width:30px;
height:30px;
top:20%;
left:75%;
  `;


function SearchNavPresenter( {KEYWORD,keyword,searchKeyword,onSubmit,onSearchHandler,onSearchCategoryHandler}){
    return(    
    <SearchBlock>
        

        <SearchInput value={KEYWORD} onChange={onSearchHandler} onKeyPress={onSubmit}></SearchInput>
        <SelectBox defaultValue="total" onClick={onSearchCategoryHandler}>
                <option value="total" >전체</option>
                <option value="title">책 제목</option>
                <option value="author">저자</option>
                <option value="publisher">출판사</option>
            </SelectBox>
        <SearchIcon></SearchIcon>
    </SearchBlock>);

}

export default SearchNavPresenter;