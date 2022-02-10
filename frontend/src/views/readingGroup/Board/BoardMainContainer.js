import BoardMainPresenter from "./BoardMainPresenter"
import React, { useState } from "react";
function BoardMainContainer(){

    //독서모임 게시판 번호
    const [readingGroupSeq,setReadingGroupSeq]= useState();
    
    function gotoRegister(){
        
        ///변수명 확인 후 수정
        document.location.href = `/article/write/${readingGroupSeq}`;
    }
    return(
        <BoardMainPresenter gotoRegister={gotoRegister}></BoardMainPresenter>
    );
}
export default BoardMainContainer;