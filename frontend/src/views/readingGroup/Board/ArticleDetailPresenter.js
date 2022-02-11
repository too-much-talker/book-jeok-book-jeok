import styled from "styled-components";
import Comment from "./Comment"
import React from "react";
const Block= styled.div`
height:100vh;
`;
const Article= styled.div`
position:relative;
width:100%;
height:100%;
border: 1px solid black;
border-radius:20px;
`;
const Head= styled.div`
position:relative;
height:12%;
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
const Contents= styled.div`
position:relative;
height:70%;
`;
const Content= styled.div`
position:relative;
height:55%;
// background:red;
`;
const Image = styled.div`
position:relative;
`;
const Buttons= styled.div``;
const Btn= styled.button``;

// const Comments= styled.div`
// position:relative;
// height:31%;
// background:blue;
// `;
function ArticleDetailPresenter({goDelete,goModify,file,comments,title, content,nickname, createdDate, views}){
   
    
    return(
    <Block>
        <Article>
            <Head>
                <Title>{title}</Title>
                <Writer>{nickname}</Writer>
                <Date>{createdDate}</Date>
                <Views> {views}</Views>
            </Head>
            <Line></Line>
            <Contents>
                <Image>
                    <img src={file} height="50%" width="50%"></img>
                </Image>
                <Content>{content}</Content>
            </Contents>
         
            
            <Comment>
                {comments && comments.map(comment=>(
                    <Comment
                    nickname={comment.nickname}
                    createdDate= {comment.createdDate}
                    content={comment.content}
                    >
                    </Comment>
                ))}
            </Comment>
        </Article>
        <Buttons>
                <Btn onClick={goModify}>수정</Btn>
                <Btn onClick={goDelete}>삭제</Btn>
            </Buttons>

    </Block>);
}

export default ArticleDetailPresenter;