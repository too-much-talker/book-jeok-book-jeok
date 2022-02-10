import axios from "axios";
import React, { useState,useEffect, useReducer } from "react";
import { useSelector } from "react-redux";
import ArticleDetailPresenter from "./ArticleDetailPresenter"
import { useParams } from "react-router-dom";
function ArticleDetailContainer(){
    const user= useSelector(state=> state.authReducer);
    const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
    const url = "https://i6a305.p.ssafy.io:8443";
    let useParam=useParams(); ////게시글 번호 useParam으로 받아올거임
    const [title, setTitle]= useState();
    const [content, setContent]= useState();
    const [nickname, setNickname]= useState();
    const [createdDate, setCreatedDate]= useState();
    const [views, setViews]= useState();
    const [comments, setComments]= useState();
    const [file, setFile]= useState();
    useEffect(() => {
        getArticle();
        console.log('컴포넌트가 화면에 나타남');
        return () => {
          console.log('컴포넌트가 화면에서 사라짐');
        };
      }, []);

  
    
    function getArticle(){ ////useParam.seq에서 seq 글자 이거 수정되면 꼭 같이 수정해야함
        ///그니깐 게시글 목록할 때 맞춰줘야함
        console.log(jwtToken);
        axios.get(url+`/api/v1/reading-groups/boards/${useParam.seq}`, {
            headers: {
                Authorization: `Bearer `+jwtToken
            }
        }    
        )
        .then(function (response){
            console.log(response);
            setTitle(response.data.data.readingGroupArticle.title);
            setContent(response.data.data.readingGroupArticle.content);
            setNickname(response.data.data.readingGroupArticle.nickname);
            setCreatedDate(response.data.data.readingGroupArticle.createDate);
            setViews(response.data.data.readingGroupArticle.views);
            setComments(response.data.data.readingGroupArticle.comments);
            setFile("https://i6a305.p.ssafy.io:8443/"+response.data.data.imagePaths);
          })
          .catch(function (error) {
              console.log(error);
            }); 
    }
    return(
        <>
            <ArticleDetailPresenter file={file} comments={comments} title={title} content={content} nickname={nickname} createdDate={createdDate} views={views}></ArticleDetailPresenter>
        </>
    );
}

export default ArticleDetailContainer;