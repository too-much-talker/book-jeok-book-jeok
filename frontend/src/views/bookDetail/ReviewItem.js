import styled from "styled-components"
import React from 'react';

const Block= styled.div`
display:flex;
width:91%;
height:16%;
padding-top:2px;
margin-left:30px;
margin-bottom:7px;
`;
const ReviewStarRating= styled.div`
width:7.4%;
text-align:left;
//background:yellow;
`;//평점
const ReviewContents = styled.div`
padding-right:3px;
//background:red;
font-size:15px
text-align:left;
`; //내용
const ReviewDate= styled.div`
padding-top:4px;
margin-left:4px;
//background:yellow;
font-size:10px
`;//작성일

function ReviewItem({summary,reviewStarRating,reviewDate}){
    const date=reviewDate.substr(0,10);
    const time=reviewDate.substr(11,17);
    console.log(reviewStarRating);
    return(
        <Block>
            <ReviewStarRating>★ {reviewStarRating}</ReviewStarRating>
            <ReviewContents>{summary}</ReviewContents>
            <ReviewDate></ReviewDate>
        </Block>

    );
}

export default ReviewItem;