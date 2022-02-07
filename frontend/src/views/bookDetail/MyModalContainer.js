
import { useState,useEffect } from "react";
import MyModalPresenter from "./MyModalPresenter";
import axios from "axios";
import { useSelector } from 'react-redux';
import { useParams } from "react-router-dom";
function MyModalContainer({ isOpen, onCancel,userReview ,bookInfoSeq}){
    const url = "http://i6a305.p.ssafy.io:8080";
    const user=useSelector(state => state.authReducer);
    const token=user.jwtToken;
    console.log(userReview);

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

    // const [starRating, setStarRating]= useState(userReview.starRating);
    // const [summary, setSummary]= useState(userReview.summary);
    // const [bookReviewSeq, setBookReviewSeq]= useState();
    const [starRating, setStarRating]= useState();
    const [summary, setSummary]= useState();
    const [bookReviewSeq, setBookReviewSeq]= useState();
    const [memberInfo, setMemberInfo]= useState();

    function handleStarRating(event){
        setStarRating(event.target.value);
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
                Authorization:`Bearer `+token
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
    }


    return(
    <MyModalPresenter modifyReview={modifyReview} handleStarRating={handleStarRating} handleSummary={handleSummary} deleteReview={deleteReview}starRating={starRating} summary={summary} isOpen={isOpen} onCancel={onCancel}></MyModalPresenter>
    );

}

export default MyModalContainer;