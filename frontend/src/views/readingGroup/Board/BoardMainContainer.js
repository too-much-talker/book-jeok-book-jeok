import BoardMainPresenter from "./BoardMainPresenter"
import React, { useState } from "react";
import { useParams } from "react-router-dom";
function BoardMainContainer(){
    let useParam=useParams();
    //독서모임 게시판 번호
    const [readingGroupSeq,setReadingGroupSeq]= useState(useParam.seq);
    
    function gotoRegister(){
        
        ///변수명 확인 후 수정
        document.location.href = `/article/write/${readingGroupSeq}`;
    }
    return(
        <BoardMainPresenter gotoRegister={gotoRegister}></BoardMainPresenter>
    );
}
export default BoardMainContainer;