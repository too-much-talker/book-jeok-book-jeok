import styled from "styled-components";
import React from "react";
const RegisterArticle=styled.button`

`;

function BoardMainPresenter({gotoRegister}){
    return(<>
    <RegisterArticle onClick={gotoRegister}>게시글 작성하기</RegisterArticle>
    </>);
}

export default BoardMainPresenter;