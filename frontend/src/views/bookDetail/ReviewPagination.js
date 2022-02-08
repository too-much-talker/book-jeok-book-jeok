import React from "react";
import { useState } from "react";
import styled from "styled-components";
const Wrapper = styled.div`
display: flex; 
justify-content: center;  } 
`;
const PageLists = styled.ul`
display: flex; 
list-style: none; padding: 0;
`;
const PageNumber = styled.li`
display: inline-block; 
width: 30px; 
height: 30px; 
border: 1px solid #e2e2e2; 
display: flex; 
justify-content: center; 
align-items: center; 
font-size: 1rem;
&: first-child {
  border-radius: 5px 0 0 5px;
}
&: last-child {
  border-radius: 0 5px 5px 0;
}

&.active{
  background-color: #337ab7;
}

`;
const PageButton = styled.button`
  cursor: pointer;
  color: ${props => props.theme.uiColorOrange};
  margin: 0 0.3rem;
  padding: 0;
  border: none;
  text-decoration: none; 
  color: #337ab7; 
  &:hover{
    color: blue;
  }
  &.active{
    color: blue;
  }
`;


// function changeColor(){
//   alert(PageButton.background);
//   if(PageButton.background==="white"){
//     alert("아님");
//   }
// }
function ReviewPagination({ postPerPage, totalPosts, paginate }) {
  const pageNumbers = [];
  // 페이지 넘버를 설정하기 위해 페이지당 포스트 개수와 총 포스트 개수를 가져온다.
  // index 를 1로 설정하고, index 가 (총 포스트개수 / 페이지당 포스트 개수) 보다 크지 않을때까지 i값을 올린다.
  // 그리고 그 값을 pageNumber 에 넣어서 설장한다.
  for (let i = 1; i <= Math.ceil(totalPosts / postPerPage); i++) {
    pageNumbers.push(i);
  }
  return (
    <Wrapper>
      <PageLists>
        {pageNumbers.map(number => (
          <PageNumber key={number}>
            <PageButton onClick={() => paginate(number)}>{number}</PageButton>
            {/* <PageButton onClick={() => {paginate(number);changeColor();
    }}>{number}</PageButton> */}

          </PageNumber>
        ))}
      </PageLists>
    </Wrapper>
  );
}
export default ReviewPagination;