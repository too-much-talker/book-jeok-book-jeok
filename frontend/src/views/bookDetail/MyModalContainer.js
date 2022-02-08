
import { useState,useEffect } from "react";
import MyModalPresenter from "./MyModalPresenter";
import axios from "axios";
import { useParams } from "react-router-dom";

function MyModalContainer({ isOpen, onCancel,userReview ,user,bookInfoSeq,jwtToken,url}){
    //console.log(userReview);

    let useParam = useParams();
    //console.log(user.memberInfo.seq);
    const handleClose = () => {
        onCancel();
      };

      useEffect(() => {
        setStarRating(userReview.starRating)
        setSummary(userReview.summary);
        setBookReviewSeq(userReview.bookReviewSeq);
        setMemberInfo(userReview.memberInfo);
        console.log(userReview);
      },[userReview]);


    const [starRating, setStarRating]= useState();
    const [summary, setSummary]= useState();
    const [bookReviewSeq, setBookReviewSeq]= useState();
    const [memberInfo, setMemberInfo]= useState();

    function handleStarRating(param){
        setStarRating(param);
    }

    function handleSummary(event){
        setSummary(event.target.value);
    }

    function modifyReview(){
        console.log(user.memberInfo.seq);
        console.log(bookInfoSeq,memberInfo,starRating,summary)
        axios.put(url+`/api/v1/bookreviews/${bookReviewSeq}`,{
            bookInfoSeq: bookInfoSeq,
            memberSeq:user.memberInfo.seq,
            starRating:starRating,
            summary:summary
        },
        {
            headers:{
                Authorization:`Bearer `+jwtToken
            }
        }
        
        )
        .then(function (response){
          console.log(response);
          alert(response.data.data.msg);
          document.location.href = `/detail/${useParam.seq}`; 
          })
        .catch(function (error) {
            console.log(error);
            alert("수정 중 문제가 발생하였습니다.")
          }); 
    }

    function deleteReview(){
        axios.delete(url+`/api/v1/bookreviews/${bookReviewSeq}`,
        {
            headers:{
                Authorization:`Bearer `+jwtToken
            }
        })
        .then(function (response){
          console.log(response);
          alert(response.data.data.msg);
          document.location.href = `/detail/${useParam.seq}`; 
          })
        .catch(function (error) {
            console.log(error);
            alert("삭제 중 문제가 발생하였습니다.")
          }); 

    }



    return(
    <MyModalPresenter modifyReview={modifyReview} handleStarRating={handleStarRating} handleSummary={handleSummary} deleteReview={deleteReview}starRating={starRating} summary={summary} isOpen={isOpen} onCancel={onCancel}></MyModalPresenter>
    );

}

export default MyModalContainer;