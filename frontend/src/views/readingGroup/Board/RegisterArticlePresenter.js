import React from "react";
import styled from "styled-components";

// const Form= styled.form.attrs(props => ({
//   method:"post",
//    enctype:"multipart/form-data"
// }))``;
const Title= styled.input``;
const Content = styled.input``;
const Image= styled.input.attrs(props => ({
    type: "file",
    id:"file-id"
  }))``;
const SubmitBtn = styled.button``;
function RegisterArticlePresenter({onCreate,handleTitle,handleContent,handleImage}){
 
    return(
    // <Form>
      <>
    <Title onChange={handleTitle} placeholder="제목을 입력해주세요"></Title>
    <Content onChange={handleContent} placeholder="내용을 입력해주세요"></Content>
    <Image onChange={handleImage}></Image>
    <SubmitBtn onClick={onCreate}>작성 완료</SubmitBtn>
    </>
    // </Form>);
    )}
export default RegisterArticlePresenter;