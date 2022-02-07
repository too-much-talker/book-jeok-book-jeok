
import { useState } from "react";
import MyModalPresenter from "./MyModalPresenter";
import axios from "axios";


function MyModalContainer({ isOpen, onCancel,userReview ,url}){
    console.log(userReview);
    const handleClose = () => {
        onCancel();
      };
    
    const [starRating, setStarRating]= useState(userReview.starRating);
    const [summary, setSummary]= useState(userReview.summary);
    const [createdDate, setCreatedDate]= useState(userReview.createdDate);
    const [bookReviewSeq, setBookReviewSeq]= useState(userReview.bookReviewSeq);

    // const [starRating, setStarRating]= useState(userReview.starRating);
    // const [summary, setSummary]= useState(userReview.summary);
    // const [createdDate, setCreatedDate]= useState(userReview.createdDate);

    function handleStarRating(event){
        setStarRating(event.target.value);
    }
    function handleSummary(event){
        setSummary(event.target.value);
    }
    function handleCreatedDate(event){
        setCreatedDate(event.target.value);
    }


    function modifyReview(){
        axios.put(url+`/api/v1/bookreviews/${bookReviewSeq}`)
        .then(function (response){
          console.log(response);
          handleClose();
          })
        .catch(function (error) {
            console.log(error);
            alert("수정 중 문제가 발생하였습니다.")
          }); 
    }

    function deleteReview(){
        axios.delete(url+`/api/v1/bookreviews/${bookReviewSeq}`)
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
    <MyModalPresenter modifyReview={modifyReview} handleStarRating={handleStarRating} handleSummary={handleSummary} handleCreatedDate={handleCreatedDate}deleteReview={deleteReview}starRating={starRating} summary={summary} createdDate={createdDate} isOpen={isOpen} onCancel={onCancel}></MyModalPresenter>
    );

}

export default MyModalContainer;