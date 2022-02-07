import BookDetailPresenter from "./BookDetailPresenter"; 
import React, { useState,useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
function BookDetailContainer(bookInfoSeq){
        let useParam=useParams();
        const url = "http://i6a305.p.ssafy.io:8080";
        const [title, setTitle]= useState();
        const [author, setAuthor]= useState();
        const [image, setImage]= useState();
        const [publisher, setPublisher]= useState();
        const [publicationDate, setPublicationDate]= useState();
        const [reviews, setReviews]= useState();
        const [starRating, setStarRating]= useState();
        const [reviewPage, setReviewPage]= useState(1);
        const [reviewTotalCnt, setReviewTotalCnt]= useState(1);

        const [booklogPage, setBooklogPage]= useState(1);
        const [booklogOrder, setBooklogOrder]= useState("recent");
        const [booklogTotalCnt, setBooklogTotalCnt]= useState();
        
        useEffect(() => {
            getBookInfo();
            getBookReview();
            console.log('컴포넌트가 화면에 나타남');
            return () => {
              console.log('컴포넌트가 화면에서 사라짐');
            };
          }, []);
        
        function getBookInfo(){
            //책정보 데려오기
            axios.get(url+`/api/v1/bookinfos/${useParam.seq}`)
            .then(function (response){
                console.log(response.data.data.bookInfo);
                setImage(response.data.data.bookInfo.largeImgUrl);
                setTitle(response.data.data.bookInfo.title);
                setAuthor(response.data.data.bookInfo.author);
                setPublisher(response.data.data.bookInfo.publisher);
                setPublicationDate(response.data.data.bookInfo.publicationDate);
                //setStarRating(response.data.data.bookInfo.starRating);
            })
            .catch(function (error) {
                console.log(error);
              }); 
        }
        
        
        function getBookReview(){
            //책 리뷰 가져오기
            axios.get(url+`/api/v1/bookreviews/bookinfos/${useParam.seq}`)
            .then(function (response){
                console.log(response.data.data);
                console.log(response.data.data.msg);
                setReviews(response.data.data.myBookReviews);
                setReviewTotalCnt(response.data.data.myBookReviews.totalCnt)
                console.log(response.data.data.myBookReviews.totalCnt);
            })
            .catch(function (error) {
                console.log(error);
              }); 
        }

        function reviewPageHandler(event){
            setReviewPage(event);
        }
        
        function booklogPageHandler(event){
            setBooklogPage(event);
        }
        
        
        function booklogOrderHandler(event){
            setBooklogOrder(event);
        }
        
        function getBookLog(){
            //북로그 가져오기
            axios.get(url+`/api/v1/booklogs`,{
                page:booklogPage,
                size:5,
                sort:booklogOrder,
            })
            .then(function (response){
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
              }); 
        
        }

    return(
        <BookDetailPresenter 
        reviewPage={reviewPage} reviewTotalCnt={reviewTotalCnt}reviews={reviews} reviewPageHandler={reviewPageHandler} 
        booklogPageHandler={booklogPageHandler} 
        starRating={starRating} image={image} title ={title} author={author} publisher={publisher} publicationDate={publicationDate}></BookDetailPresenter>
        
        );
}
export default BookDetailContainer;