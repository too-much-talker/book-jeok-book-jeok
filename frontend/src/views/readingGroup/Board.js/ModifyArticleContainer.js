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

    const [readingGroupSeq, setReadingGroupSeq]=useState(useParam.boardSeq);
    const [articleSeq, setArticleSeq]= useState(useParam.articleSeq);
    const [title,setTitle]= useState();
    const [content,setContent]=useState();
    const [file, setFile]= useState();
    const [imagePath, setImagePath]= useState();

    useEffect(() => {
      getArticle();
      console.log('컴포넌트가 화면에 나타남');
      return () => {
        console.log('컴포넌트가 화면에서 사라짐');
      };
    }, []);


    function handleTitle(e){
        setTitle(e.target.value);
    }
    function handleContent(e){
        setContent(e.target.value);
    }
    function handleFile(e){
        setFile(e.target.files[0]);
    }
    //////////file 넣어야 함.


    function getArticle(){ ////useParam.seq에서 seq 글자 이거 수정되면 꼭 같이 수정해야함
      ///그니깐 게시글 목록할 때 맞춰줘야함
      axios.get(url+`/api/v1/reading-groups/boards/${useParam.articleSeq}`, {
          headers: {
              Authorization: `Bearer `+jwtToken
          }
      }    
      )
      .then(function (response){
          console.log(response);
          setTitle(response.data.data.readingGroupArticle.title);
          setContent(response.data.data.readingGroupArticle.content);
          setImagePath("https://i6a305.p.ssafy.io:8443/"+response.data.data.imagePaths);
        })
        .catch(function (error) {
            console.log(error);
          }); 
  }


    function modifySubmit(){
      let formData = new FormData();
      formData.append('files', file);
      let reqReadingGroupBoard = {
          readingGroupSeq:readingGroupSeq,
          title: title,
          content: content
      }; 
      formData.append("reqReadingGroupBoard", new Blob([JSON.stringify(reqReadingGroupBoard)], {type: "application/json"}))

      axios.put(url + `/api/v1/reading-groups/boards/${articleSeq}`, formData, {
        headers: { 
            'Accept': 'application/json',
            'Content-Type': 'multipart/form-data',
            Authorization: `Bearer ${jwtToken}` 
        },
    }).then(function (response) {
            console.log(response.data.data);
            alert("수정이 완료되었습니다.")
            document.location.href = `/board/${readingGroupSeq}`;
    })  
    .catch(function (error) {
        console.log(error);
    });

    }
    return(
        <ModifyArticlePresenter imagePath={imagePath}title={title} content={content} file={file}handleTitle={handleTitle} handleContent={handleContent} handleFile={handleFile} modifySubmit={modifySubmit}></ModifyArticlePresenter>
    );
}
export default ModifyArticleContainer;