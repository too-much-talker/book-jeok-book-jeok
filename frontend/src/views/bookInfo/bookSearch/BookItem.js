import styled from "styled-components"
import axios from "axios";
import { useEffect, useState } from "react";
const Block= styled.div`
width:280px;
margin-bottom:50px;
`;
const Image=styled.div`
`;
const Content = styled.div``;
const Contents= styled.div`
width:220px;
margin:auto;
text-align:left;
`;

function BookItem({url,bookInfoSeq,title, author,largeImgUrl,publisher,publicationDate,starRating}){

    return (
        <>
            <Block>
                <Image>
                <img src={largeImgUrl} height="300" width="220"></img>
                </Image>
                <Contents>
                <Content>제목:{title}</Content>
                <Content>글쓴이 : {author}</Content>
                <Content>출판사: {publisher}</Content>
                <Content>출판일: {publicationDate}</Content>
                <Content>별점: {starRating}</Content>
                </Contents>
            </Block>
        </>
    );
}


export default BookItem;