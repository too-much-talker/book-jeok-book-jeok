import styled from "styled-components"
import React from 'react';

const Block= styled.div`
display:flex;
width:91%;
height:10%;
padding-top:2px;
margin-left:30px;
margin-bottom:7px;
background:blue;
`;

const BooklogTitle= styled.div``;
const BooklogContents= styled.div``;
const BooklogDate= styled.div``;

function BooklogItem({title, content, createdDate}){
    return(
    <Block>
        <BooklogTitle>{title}</BooklogTitle>
        <BooklogContents>{content}</BooklogContents>
        <BooklogDate>{createdDate}</BooklogDate>
    </Block>


    );

}

export default BooklogItem;