import styled from "styled-components"
import React from 'react';

const Block= styled.div`
width:91%;
height:15%;
margin-left:30px;
margin-bottom:7px;
// background:blue;
`;
const BooklogHeader= styled.div`
display:flex;
`;
const BooklogTitle= styled.div`
position:relative;
font-weight:bold;
// background:red;
font-size:16px;
margin-right:5px;
`;
const BooklogDate= styled.div`
// background:green;
margin-top:2px;
font-size:13px;
`;

const BooklogContents= styled.div`
position:relative;
font-size:13px;
// background:yellow;
text-align:left;
`;


function BooklogItem({title, content, createdDate}){
    return(
    <Block>
        <BooklogHeader>
            <BooklogTitle>{title}</BooklogTitle>
            <BooklogDate>{createdDate}</BooklogDate>
        </BooklogHeader>
        <BooklogContents>{content}</BooklogContents>
    </Block>


    );

}

export default BooklogItem;