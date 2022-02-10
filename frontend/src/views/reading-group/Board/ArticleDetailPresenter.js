import styled from "styled-components";
import Comment from "./Comment"
import React from "react";
const Block= styled.div`
height:100vh;
`;
const Contents= styled.div`
position:relative;
width:100%;
height:75%;
border: 1px solid black;
border-radius:20px;
`;
const Head= styled.div`
position:relative;
height:14%;
display: flex;
font-size:30px;
`;
const Title=styled.div`
margin-right:3%;
`;
const Writer= styled.div`
`;
const Date =styled.div``;
const Views= styled.div``;
const Line = styled.div`
position:relative;
width:95%;
margin:auto;
border-top: 1px solid black;
`;
const Content= styled.div`
position:relative;
height:55%;
// background:red;
`;
const Comments= styled.div`
position:relative;
height:31%;
background:blue;
`;
function ArticleDetailPresenter({comments,title, content,nickname, createdDate, views}){

    return(
    <Block>
        <Contents>
            <Head>
                <Title>{title}</Title>
                <Writer>{nickname}</Writer>
                <Date>{createdDate}</Date>
                <Views></Views>
            </Head>
            <Line></Line>
            <Content>{content}</Content>
            <Comments>
                {comments && comments.map(comment=>(
                    <Comment
                    nickname={comment.nickname}
                    createdDate= {comment.createdDate}
                    content={comment.content}
                    >
                    </Comment>
                ))}
            </Comments>
        </Contents>

    </Block>);
}

export default ArticleDetailPresenter;