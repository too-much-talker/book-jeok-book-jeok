import ModifyArticlePresenter from "./ModifyArticlePresenter"
import axios from "axios";
import React, { useState,useEffect, useReducer } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
function ModifyArticleContainer(){
    // const user=useSelector(state => state.authReducer);
    // const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
    // const url = "https://i6a305.p.ssafy.io:8443";
    // let useParam=useParams();
    // const [readingGroupSeq, setReadingGroupSeq]= useState(useParam.seq);
    // const [articleSeq, setArticleSe]
    // const [title, setTitle]= useState();
    // const [content,setContent]= useState();
    // const [files, setFiles]= useState();
    // function getArticle(){ 
    //     //이거ㅣ url 수정해야함
    //     axios.get(url+`/api/v1/reading-groups/${useParam.seq}`)
    //     .then(function (response){
    //       console.log(response.data.data);

    //     })
    //     .catch(function (error) {
    //         console.log(error);
    //       }); 
          
    // }
    return(<ModifyArticlePresenter></ModifyArticlePresenter>);
}
export default ModifyArticleContainer;