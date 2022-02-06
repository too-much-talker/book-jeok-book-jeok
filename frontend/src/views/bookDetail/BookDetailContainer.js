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
        const [starRating, setStarRating]= useState();
        const [reviews, setReviews]= useState();

        const [reviewPage, setReviewPage]= useState(1);
        const [reviewOrder, setReviewOrder]= useState("recent");
        const [booklogPage, setBooklogPage]= useState(1);
        const [booklogOrder, setBooklogOrder]= useState("recent");
        /////로그인한 사용자 정보 땡겨오는거부터 하자!!!!!!!

        

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
                setStarRating(response.data.data.bookInfo.starRating);
            })
            .catch(function (error) {
                console.log(error);
              }); 
        }
        
        
        function getBookReview(){
            //책 리뷰 가져오기
            axios.get(url+`/api/v1/bookreviews/bookInfos/${useParam.seq}`)
            .then(function (response){
                console.log(response.data.data.myBookReviews);
                setReviews(response.data.data.myBookReviews);
            })
            .catch(function (error) {
                console.log(error);
              }); 
        }

        function writeBookReview(){

        }

        // function modifyBookReview(){
        //     axios.put(url+`/api/v1/bookreviews/${bookReviewSeq}`,{
        //         bookInfoSeq:bookInfoSeq, 
        //         memberSeq:memberSeq, 
        //         starRating: starRating,  ////이거 바궈야함. 이 리뷰의 starrating으로
        //         summary: summary
        //     })
        //     .then(function (response){
        //         console.log(response.data.data.modifiedBookReview);
        //     })
        //     .catch(function (error) {
        //         console.log(error);
        //       }); 
        // }

        // function deleteBookReview(){
        //     axios.delete(url+`/api/v1/bookreviews/${bookReviewSeq}`)
        //     .then(function (response) {
        //         console.log(response);
        //     })
        //     .catch(function (error) {
        //         console.log(error);
        //     });
        // }

        function reviewPageHandler(event){
            setReviewPage(event);
        }
        
        function booklogPageHandler(event){
            setBooklogPage(event);
        }
        
        function reviewOrderHandler(event){
            setReviewOrder(event);
        }
        
        function booklogOrderHandler(event){
            setBooklogOrder(event);
        }
        
        function getBookLog(){
            //북로그 가져오기
            axios.get(url+`/api/v1/booklogs`,{
                page:reviewPage,
                size:5,
                sort:reviewOrder,
            })
            .then(function (response){
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
              }); 
        
        }

    return(
        <BookDetailPresenter booklogOrderHandler={booklogOrderHandler} reviewOrderHandler={reviewOrderHandler} reviewPageHandler={reviewPageHandler} booklogPageHandler={booklogPageHandler} reviews={reviews}image={image} title ={title} author={author} publisher={publisher} publicationDate={publicationDate} starRating={starRating}></BookDetailPresenter>
    );
}
export default BookDetailContainer;