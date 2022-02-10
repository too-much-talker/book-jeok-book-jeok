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
    const [file, setFile]= useState();

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
          setFile("https://i6a305.p.ssafy.io:8443/"+response.data.data.imagePaths);
        })
        .catch(function (error) {
            console.log(error);
          }); 
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
        <ModifyArticlePresenter title={title} content={content} file={file}handleTitle={handleTitle} handleContent={handleContent} handleFile={handleFile} modifySubmit={modifySubmit}></ModifyArticlePresenter>
    );
}
export default ModifyArticleContainer;