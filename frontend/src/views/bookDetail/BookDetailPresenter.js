
import styled from "styled-components";
import ReviewItem from "./ReviewItem";
import BooklogItem from "./BooklogItem";
import Pagination from "react-js-pagination";
import '../bookInfo/bookSearch/Paging.css';
import MyModalContainer from "./MyModalContainer";
// import WriteModalContainer from "./WriteModalContainer";
const Block= styled.div`
// margin-top:3    
// text-align:center;
// position:absolute;
// margin-left:-200px;
// width:1430px;
// height:2000px;
// background:green;
// display:flex;
`;
const Line = styled.div`
position:absolute;
width:1230px;
margin-left:-100px;
justify-content:center; 
border-top: 1px solid black;
`;
const Contents = styled.div`
position:absolute;
display: flex;
width:1230px;
margin-left:-100px;
margin-top:20px;
`;




const BookInfoBlock= styled.div`
 position:relative;
width:35%;
height:100vh;
`;



const ReviewBookLog= styled.div`
position:relative;
width:50%;
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
width:100vh;
height:50%;
`;
const ReviewHeader = styled.div`
position:relative;
text-align:left;
font-size:23px;
margin-top:10px;
margin-left:10px;
margin-bottom:5px;
`;
const Blank=styled.div`
width:50%;
height:8%;
`;
const MyReviewButton = styled.button`
position:relative;
`;
const WriteReviewButton = styled.button`
position:relative
`;
const ReviewContents= styled.div`
position:relative;
height:80%;
border-radius:20px;
box-shadow: 4px 5px 7px 2px lightgrey;
margin-bottom:-55px;
`;

///북로그
const BookLog= styled.div`
position:relative;
height:80%;
width:100vh;
`;

const BooklogHeader = styled.div`
position:relative;
text-align:left;
font-size:23px;
margin-top:10px;
margin-left:10px;
margin-bottom:5px;
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
margin-bottom:-55px;
//background:red;


`;

const SelectBox = styled.select`
top:0%;
left:35%;
position :absolute;
width:14%;
height:6%;
`;

const Page= styled.div`
position:absolute;
left:36%;
`;





function BookDetailPresenter({
    reviews,reviewPage,reviewTotalCnt,reviewPageHandler,starRating,
    booklogs,booklogPage,booklogTotalCnt,booklogPageHandler,booklogOrderHandler,
    image,title, author,publisher, publicationDate,
    MyModalOpen,WriteModalOpen,handleMyModalClose,handleWriteModalClose
    ,setMyModalOpen,setWriteModalOpen
    ,handleMyModalOpen,handleWriteModalOpen,userReview,bookInfoSeq
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
                    <ReviewHeader>이 책의 책리뷰</ReviewHeader>
                    <MyReviewButton onClick={handleMyModalOpen}>내 책리뷰 {MyModalOpen}</MyReviewButton>
                    <MyModalContainer isOpen={MyModalOpen} onCancel={handleMyModalClose} userReview={userReview} bookInfoSeq={bookInfoSeq}></MyModalContainer>
                    <WriteReviewButton onClick={setWriteModalOpen(true)}>책리뷰 작성하기</WriteReviewButton>
                    {/* <WriteModalContainer></WriteModalContainer> */}
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
                    <BooklogHeader>이 책을 주제로 쓴 북로그</BooklogHeader>
                        <SelectBox defaultValue="recent" onClick={booklogOrderHandler}>
                            <option value="recent" >최신순</option>
                            <option value="like">좋아요순</option>
                        </SelectBox>
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