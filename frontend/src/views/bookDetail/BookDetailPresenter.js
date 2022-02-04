
import styled from "styled-components";
import ReviewItem from "./ReviewItem";
import Pagination from "react-js-pagination";
import '../bookInfo/bookSearch/Paging.css';
const Block= styled.div`
position:absolute;
display:flex;
`;
const BookInfoBlock= styled.div`
background:yellow;
width:50%;
height:100vh;
margin:20px;
`;
const ReviewBookLog= styled.div`
width:50%;
height:100vh;
margin:20px;
`;

const BookImage= styled.div`
text-align:center;
margin-top:30px;
`;
const BookInfo= styled.div`
background:blue;
margin-top:40px;
margin-left:13%;
width:600px;
`;
const BookInfoDetail= styled.div`
`;


const BookReview= styled.div`
height:50%;
background:red;
`;


const ReviewHeader = styled.div``;//책리뷰
const ReviewContents= styled.div``;

const BookLog= styled.div`
background:blue;
height:50%;

`;

function BookDetailPresenter({
    reviews,reviewPage,reviewTotalCnt,reviewPageHandler,
    image,title, author,publisher, publicationDate}){
    return(
        <Block>
        <BookInfoBlock> 
            <BookImage>
                <img src={image} height="400" width="300"></img>
            </BookImage>
                <BookInfo>
                <BookInfoDetail>제목:{title} </BookInfoDetail>
                <BookInfoDetail>작가:{author} </BookInfoDetail>
                <BookInfoDetail>출판사:{publisher} </BookInfoDetail>
                <BookInfoDetail>출판일:{publicationDate}</BookInfoDetail>
            </BookInfo>
        </BookInfoBlock>
        <ReviewBookLog>
            <BookReview>
                <ReviewHeader>책리뷰</ReviewHeader>
                <ReviewContents>
                    {/* {reviews.map(review=>(
                        <ReviewItem
                            summary={review.summary}
                            reviewStarRating={review.starRating}
                            reviewDate={review.createdDate}
                        >
                        </ReviewItem>
                    ))} */}
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
        </Block>
    );

}

export default BookDetailPresenter;