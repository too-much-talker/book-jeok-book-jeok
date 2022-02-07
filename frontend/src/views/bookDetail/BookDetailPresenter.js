
import styled from "styled-components";
import ReviewItem from "./ReviewItem";
import BooklogItem from "./BooklogItem";
import Pagination from "react-js-pagination";
import '../bookInfo/bookSearch/Paging.css';
import MyModalContainer from "./MyModalContainer";
import WriteModalContainer from "./WriteModalContainer";
const Block= styled.div`
`;
const Line = styled.div`
position:relative;
width:100%;
justify-content:center; 
border-top: 1px solid black;
`;
const Contents = styled.div`
position:relative;
display: flex;
width:100%;
margin-top:20px;
`;




const BookInfoBlock= styled.div`
 position:relative;
width:35%;
height:100vh;
`;



const ReviewBookLog= styled.div`
position:relative;
width:65%;
height:100vh;
`;


/////책정보
const BookTitle= styled.div`
margin-left:15%;
position:relative;
width:24vw;
text-align:left;
font-size:30px;
font-weight:bold;
`;
const BookStarRating= styled.div`
margin-left:15%;
position:relative;
width:24vw;
text-align:left;
font-size:20px;
`;
const BookImage= styled.div`
margin-top:15px;
`;
const BookInfo= styled.div`
margin-top:40px;
margin:auto;
width:300px;
text-align:left;
height:30%;
font-size:18px;
`;
const BookInfoDetail= styled.div`
`;

////책리뷰
const BookReview= styled.div`
position:relative;
width:95%;
height:50%;
`;
const ReviewHeader = styled.div`
position:relative;
display:flex;
`;

const ReviewTitle= styled.div`
position:relative;
text-align:left;
font-size:23px;
margin-left:10px;
margin-bottom:5px;
width:30%;
`;

const Blank=styled.div`
width:50%;
height:8%;
`;
const Buttons= styled.div`
position:relative;
width:70%;
height:2%;
margin-left:0%;
text-align:right;
margin-right:2%;
`;


const MyReviewButton = styled.button`
position:relative;
width:20%;
padding: 0px;
margin:0px;
margin-right:1%;
`;
const WriteReviewButton = styled.button`
position:relative;
width:30%;
padding: 0px;
margin:0px;
`;
const ReviewContents= styled.div`
position:relative;
height:80%;
border-radius:20px;
box-shadow: 4px 5px 7px 2px lightgrey;
margin-bottom:-8%;
`;

///북로그
const BookLog= styled.div`
position:relative;
height:80%;
width:95%;
`;

const BooklogHeader = styled.div`
position:relative;
display:flex;  
`;

const BooklogText= styled.div`
position:relative;
text-align:left;
font-size:23px;
margin-left:10px;
width:35%;
`;

const Blank2=styled.div`
width:50%;
height:5%;
`;

const BooklogContents= styled.div`
position:relative;
height:80%;
border-radius:20px;
box-shadow: 4px 5px 7px 2px lightgrey;
margin-bottom:-8%;
`;

const SelectBox = styled.select`
left:40%;
position :relative;
width:14%;
height:100%;
margin:0px;
`;

const Page= styled.div`
position:absolute;
left:36%;
`;





function BookDetailPresenter({
    reviews,reviewPage,reviewTotalCnt,reviewPageHandler,
    booklogs,booklogPage,booklogTotalCnt,booklogPageHandler,booklogOrderHandler,
    image,title, author,publisher, publicationDate,
    MyModalOpen,WriteModalOpen,handleMyModalClose,handleWriteModalClose
    ,handleMyModalOpen,handleWriteModalOpen,userReview,user,seq,starRating,jwtToken,
    url
}){
    return(

        <Block>
        <Line></Line>
        <Contents>
            <BookInfoBlock> 
                <BookTitle>{title}</BookTitle>
                <BookStarRating>평점 : ★{starRating}</BookStarRating>
                <BookImage>
                    <img src={image} height="400" width="300"></img>
                </BookImage>
                    <BookInfo>
                        <BookInfoDetail>작가 : {author} </BookInfoDetail>
                        <BookInfoDetail>출판사 : {publisher} </BookInfoDetail>
                        <BookInfoDetail>출판일 : {publicationDate}</BookInfoDetail>
                </BookInfo>
            </BookInfoBlock>
            <ReviewBookLog>

                <BookReview>
                    <ReviewHeader>
                        <ReviewTitle>이 책의 책리뷰</ReviewTitle>
                        <Buttons>
                            <MyReviewButton onClick={handleMyModalOpen}>내 책리뷰</MyReviewButton>
                            <MyModalContainer isOpen={MyModalOpen} onCancel={handleMyModalClose} userReview={userReview} url={url}></MyModalContainer>
                            <WriteReviewButton onClick={handleWriteModalOpen} >책리뷰 작성하기</WriteReviewButton>
                            <WriteModalContainer isOpen={WriteModalOpen}onCancel={handleWriteModalClose} user={user} jwtToken={jwtToken} seq={seq} url={url}></WriteModalContainer>
                        </Buttons>
                    </ReviewHeader>
       
                    
                    <ReviewContents> 
                        <Blank></Blank>
                        {reviews && reviews.map(review=>(
                            <ReviewItem
                                summary={review.summary}
                                reviewStarRating={review.starRating}
                                reviewDate={review.createdDate}
                            >
                            </ReviewItem>
                        ))}
                    </ReviewContents>
                    <Page>
                        <Pagination activePage={reviewPage} 
                            itemsCountPerPage={5} 
                            totalItemsCount={reviewTotalCnt} 
                            pageRangeDisplayed={5} 
                            prevPageText={"‹"} 
                            nextPageText={"›"} 
                            onChange={reviewPageHandler} />
                    </Page>
                </BookReview>

                <BookLog>
                    <BooklogHeader>
                        <BooklogText>이 책을 주제로 쓴 북로그</BooklogText>
                        <SelectBox defaultValue="recent" onClick={booklogOrderHandler}>
                            <option value="recent" >최신순</option>
                            <option value="like">좋아요순</option>
                        </SelectBox>

                    </BooklogHeader>

                    <BooklogContents>
                        <Blank2></Blank2>
                            {booklogs && booklogs.map(booklog=>(
                                <BooklogItem
                                    title={booklog.title}
                                    content={booklog.content}
                                    //test용
                                    //content="세이더네임세븐틴 안녕하세요 세븐틴입니다. 최승철 윤정한 홍지수 문준휘 권순영 전원우 이지훈 서명호 김민규 이석민 부승관 최한솔 이찬"
                                    createdDate={booklog.createdDate}
                                >
                                </BooklogItem>
                            ))}  
                    </BooklogContents>
                    <Page>
                        <Pagination
                        activePage={booklogPage} 
                                itemsCountPerPage={5} 
                                totalItemsCount={booklogTotalCnt} 
                                pageRangeDisplayed={5} 
                                prevPageText={"‹"} 
                                nextPageText={"›"} 
                                onChange={booklogPageHandler} />
                    </Page>

                </BookLog> 



            </ReviewBookLog>
        </Contents>
        </Block>
    );

}

export default BookDetailPresenter;