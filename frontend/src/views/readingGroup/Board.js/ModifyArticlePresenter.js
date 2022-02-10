import React from "react";
import styled from "styled-components";

const Title= styled.input``;
const Content = styled.input``;
const Image= styled.input.attrs(props => ({
    type: "file",
    id:"files",
  }))``;

const Image2 = styled.div`
position:relative;
`;
const Message= styled.div``;

const SubmitBtn = styled.button``;
function RegisterArticlePresenter({imagePath,title,content,file,handleTitle,handleContent,handleFile,modifySubmit}){
 
    return(
    // <Form>
      <>
    <Title onChange={handleTitle} value={title}></Title>
    <Content onChange={handleContent} value={content}></Content>
    <Message>바꿀 사진을 선택해주세요.</Message>
    <Image onChange={handleFile}></Image>
    <Image2>
        <img src={imagePath} height="50%" width="50%"></img>
      </Image2>

    <SubmitBtn onClick={modifySubmit}>수정 완료</SubmitBtn>
    </>
    // </Form>);
    )}
export default RegisterArticlePresenter;