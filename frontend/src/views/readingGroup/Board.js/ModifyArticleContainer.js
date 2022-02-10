import ModifyArticlePresenter from "./ModifyArticlePresenter"
import React, { useState,useEffect, useReducer } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { useSelector } from 'react-redux';
function ModifyArticleContainer(){
    const user=useSelector(state => state.authReducer);
    const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
    let useParam=useParams();
    const url = "https://i6a305.p.ssafy.io:8443";

    const [readingGoupSeq, setReadingGoupSeq]=useState(useParam.boardSeq);
    const [articleSeq, setArticleSeq]= useState(useParam.articleSeq);
    const [title,setTitle]= useState("dd");
    const [content,setContent]=useState("dd");
    const [files, setFiles]= useState();

    function handleTitle(e){
        setTitle(e.target.value);
    }
    function handleContent(e){
        setContent(e.target.value);
    }
    function handleFiles(e){
        setFiles(e.target.files[0]);
    }

    function modifySubmit(){
        axios
        .put(
            //url수정해야함
          url + `/api/v1/reading-group/${readingGoupSeq}/${articleSeq}`,
          {
              
          },
          {
            headers: {
              Authorization: `Bearer ` + jwtToken,
            },
          }
        )
        .then(function (response) {
          console.log(response);
          alert(response.data.data.msg);
          document.location.href = `/detail/${useParam.seq}`;
        })
        .catch(function (error) {
          console.log(error);
          alert("수정 중 문제가 발생하였습니다.");
        });

    }
    return(
        <ModifyArticlePresenter title={title} content={content} files={files}handleTitle={handleTitle} handleContent={handleContent} handleFiles={handleFiles} modifySubmit={modifySubmit}></ModifyArticlePresenter>
    );
}
export default ModifyArticleContainer;