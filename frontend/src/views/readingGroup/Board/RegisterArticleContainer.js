import RegisterArticlePresenter from "./RegisterArticlePresenter"
import React, { useState,useRef } from "react";
import { useParams } from "react-router-dom";
import { useSelector } from 'react-redux';
import axios from "axios";
import { getSpaceUntilMaxLength } from "@testing-library/user-event/dist/utils";
function RegisterArticleContainer(){
    let useParam=useParams();
    const url = "https://i6a305.p.ssafy.io:8443";
    const user=useSelector(state => state.authReducer);
    const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
    const [readingGroupSeq, setReadingGroupSeq]= useState(useParam.seq);
    const [title, setTitle]= useState();
    const [content,setContent]= useState();
    const [files, setFiles]= useState();

    function handleTitle(event){
        setTitle(event.target.value);
    }
    function handleContent(event){
        setContent(event.target.value);
    }
    function handleImage(event){
        setFiles(event.target.files[0]);
    }
    
    function onCreate(){
        let formData = new FormData();
        formData.append('files', files);
        console.log(files);
        
        let reqReadingGroupBoard = {
            readingGroupSeq:1208,
            title: title,
            content: content
        }; 
      
        formData.append("reqReadingGroupBoard", new Blob([JSON.stringify(reqReadingGroupBoard)], {type: "application/json"}))

        // formData.append('reqReadingGroupBoard', reqReadingGroupBoard);
        // formData.append('readingGroupSeq',readingGroupSeq);
        // formData.append('title',title);
        // formData.append('content',content);

        axios.post(url + `/api/v1/reading-groups/boards`, formData, {
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'multipart/form-data',
                Authorization: `Bearer ${jwtToken}` 
            },
        }).then(function (response) {
            if(response.data.status===201){
                alert("게시글이 작성되었습니다.")
                document.location.href = `/board/${readingGroupSeq}`;
            }
            console.log(response);
        })  
        .catch(function (error) {
            console.log(error);
        });
    }
    return(<RegisterArticlePresenter onCreate={onCreate} handleTitle={handleTitle} handleContent={handleContent} handleImage={handleImage}></RegisterArticlePresenter>);
}
export default RegisterArticleContainer;