import styled from "styled-components";
import SearchNavContainer from "./SearchNavContainer";
import BookItem from "./BookItem";
import Pagination from "react-js-pagination";
import './Paging.css';
import {useEffect, useState } from "react";
import { findAllInRenderedTree } from "react-dom/cjs/react-dom-test-utils.production.min";
const ResultBlock = styled.div`
position:absolute;
display:flex;
left:50%;
top:25%;
transform: translate(-50%, -50%);
background:white;
width:1400px;
height:50px;
border-bottom: 2px solid black;
background:yellow


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
`
const SelectBox = styled.select`
position :relative;
width:80px;
height:50px;
left:63%;
`;

const BookListBlock = styled.div`
position:relative;
display: flex;
justify-content: space-between;
align-items: flex-start;
flex-wrap: wrap;
text-align:center;
width: 70%;
margin:auto;
margin-top:250px;
`;


//onSearchHandler,onSearch,onSearchCategoryHandler,books,booksPerPage, totalBooks, paginat



function SearchResultPresenter ({page, handlePageChange,url,totalCnt,orderHandler,useParam,books }){

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

        {/* resultcontainer가 레더링 되자마자 얘도 실행이 되는데 그 때 얘는 빈 공간이어서 error가 뜨는데 
        books 데이터가 들어오기 전에 이걸 어떻게 처리해야할까요 ㅜㅜ */}
        {/* 질문!!!!!!!!!!!!!!!!!!!!!!!!!!!! */}
          {books.map(book=>(
            <div>
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
            </div>
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