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
        let reqReadingGroupBoard = {
            readingGroupSeq:readingGroupSeq,
            title: title,
            content: content
        }; 

        formData.append('reqReadingGroupBoard', reqReadingGroupBoard);
        // formData.append('readingGroupSeq',readingGroupSeq);
        // formData.append('title',title);
        // formData.append('content',content);

        axios.post(url + `/api/v1/reading-groups/boards`, formData, {
            headers: { 
                'Content-Type': 'multipart/form-data', 
                Authorization: `Bearer ${jwtToken}` 
            },
        }).then(function (response) {
            console.log(response);
        })  
        .catch(function (error) {
            console.log(error);
        });
    }


    // http.post(`${url}/api/v1/reading-groups/boards`, form, {
    //       headers: {
    //         'Content-Type': 'multipart/form-data',
    //         Authorization: `Bearer ${jwtToken}`,
    //       },
    //     })
    return(<RegisterArticlePresenter onCreate={onCreate} handleTitle={handleTitle} handleContent={handleContent} handleImage={handleImage}></RegisterArticlePresenter>);
}
export default RegisterArticleContainer;