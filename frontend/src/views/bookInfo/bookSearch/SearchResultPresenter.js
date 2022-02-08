import styled from "styled-components";
import SearchNavContainer from "./SearchNavContainer";
import BookItem from "./BookItem";
import Pagination from "react-js-pagination";
import './Paging.css';
const ResultBlock = styled.div`
position:relative;
display:flex;
left:50%;
transform: translate(-50%, -50%);
background:white;
width:100%;
height:50px;
border-bottom: 2px solid black;
// margin-top:3%;
`;
const ResultText = styled.div`
position:relative;
top:30%;
display: flex;
left:3%;
`;
const ResultBoldWords= styled.div`
font-weight:bold
`;
const Resultwords=styled.div`
margin-left:10px;
margin-right:10px;

`
const SelectBox = styled.select`
position :relative;
left:65%;
top:15%;
width: 82px;
height: 35px;
background: url('https://freepikpsd.com/media/2019/10/down-arrow-icon-png-7-Transparent-Images.png') calc(100% - 5px) center no-repeat;
background-size: 20px;
padding: 5px 10px 5px 5px;
border-radius: 4px;

`;

const BookListBlock = styled.div`
position:relative;
display: flex;
align-items: flex-start;
flex-wrap: wrap;
// background:red;
width:100%;
`;

const Book = styled.div`
width:25%;
`;

function SearchResultPresenter ({goDetail,page, handlePageChange,url,totalCnt,orderHandler,useParam,books }){

  return(
      <>
      <SearchNavContainer keyword={useParam.keyword}></SearchNavContainer>
      <ResultBlock>
        <ResultText>
          <ResultBoldWords>{useParam.keyword}</ResultBoldWords>
          <Resultwords> 에 대한 검색결과는</Resultwords>
          <ResultBoldWords>{totalCnt}</ResultBoldWords>
          <Resultwords> 건입니다.</Resultwords>
          </ResultText>
        <SelectBox defaultValue="latest" onClick={orderHandler}>
                <option value="latest" >최신순</option>
                <option value="star">별점순</option>
                <option value="review">리뷰순</option>
        </SelectBox>
      </ResultBlock>
      <BookListBlock> 
          
          {books&&books.map(book=>(
            <Book onClick={()=>goDetail(book.seq)} >
              <BookItem 
                url={url}
                key={book.seq}
                bookInfoSeq={book.seq}
                title={book.title} 
                author={book.author}
                largeImgUrl={book.largeImgUrl}
                publisher={book.publisher}
                publicationDate= {book.publicationDate}
                starRating={book.starRating}
              ></BookItem>
            </Book>
          ))
          }
      </BookListBlock>
      <Pagination activePage={page} 
      itemsCountPerPage={12} 
      totalItemsCount={totalCnt} 
      pageRangeDisplayed={5} 
      prevPageText={"‹"} 
      nextPageText={"›"} 
      onChange={handlePageChange} />
      </>

    );
}
export default SearchResultPresenter;