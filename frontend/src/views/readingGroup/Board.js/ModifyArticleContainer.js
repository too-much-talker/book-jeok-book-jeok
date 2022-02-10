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
    const [title,setTitle]= useState();
    const [content,setContent]=useState();
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

    }
    return(
        <ModifyArticlePresenter handleTitle={handleTitle} handleContent={handleContent} handleFiles={handleFiles} modifySubmit={modifySubmit}></ModifyArticlePresenter>
    );
}
export default ModifyArticleContainer;