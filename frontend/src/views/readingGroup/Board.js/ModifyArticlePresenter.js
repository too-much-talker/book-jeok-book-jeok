import React from "react";
import styled from "styled-components";

const Title= styled.input``;
const Content = styled.input``;
const Image= styled.input.attrs(props => ({
    type: "file",
    id:"file-id"
  }))``;
const SubmitBtn = styled.button``;
function RegisterArticlePresenter({title, content,files,handleTitle,handleContent,handleFiles,modifySubmit}){
 
    return(
    // <Form>
      <>
    <Title onChange={handleTitle} value={title}></Title>
    <Content onChange={handleContent} value={content}></Content>
    <Image onChange={handleFiles}></Image>
    <SubmitBtn onClick={modifySubmit}>수정 완료</SubmitBtn>
    </>
    // </Form>);
    )}
export default RegisterArticlePresenter;