
import styled from "styled-components";
import ReviewItem from "./ReviewItem";
import Pagination from "react-js-pagination";
import '../bookInfo/bookSearch/Paging.css';
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
width:330px;
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
// padding-left:10px;
`;
const BookInfoDetail= styled.div`
`;


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
`;//책리뷰

const Blank=styled.div`
width:50%;
height:8%;
// background:blue;

`;
const ReviewButton = styled.button`
position:relative;
`;
const ReviewContents= styled.div`
position:relative;
height:80%;
border-radius:20px;
box-shadow: 4px 5px 7px 2px lightgrey;
margin-bottom:-55px;
`;

const BookLog= styled.div`
position:relative;
background:blue;
height:80%;
width:100vh;

`;

function BookDetailPresenter({
    reviews,reviewPage,reviewTotalCnt,reviewPageHandler,starRating,
    image,title, author,publisher, publicationDate}){
    console.log(reviewTotalCnt);
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
                    <ReviewContents>
                        <Blank></Blank>
                        {reviews && reviews.map(review=>(
                            <ReviewItem
                                summary={review.summary}
                                reviewStarRating={(review.starRating)}
                                reviewDate={review.createdDate}
                            >
                            </ReviewItem>
                        ))}
                    </ReviewContents>
                    <Pagination activePage={reviewPage} 
                        itemsCountPerPage={5} 
                        totalItemsCount={reviewTotalCnt} 
                        pageRangeDisplayed={5} 
                        prevPageText={"‹"} 
                        nextPageText={"›"} 
                        onChange={reviewPageHandler} />
                </BookReview>

                <BookLog>
                    
                </BookLog> 
            </ReviewBookLog>
        </Contents>
        </Block>
    );

}

export default BookDetailPresenter;