import styled from "styled-components";

const Block= styled.div`
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
const BookLog= styled.div`
background:blue;
height:50%;

`;

function BookDetailPresenter({reviews,image,title, author,publisher, publicationDate, starRating}){
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
                <BookInfoDetail>평점:{starRating}</BookInfoDetail>
            </BookInfo>
        </BookInfoBlock>
        <ReviewBookLog>
            <BookReview>리뷰</BookReview>
            <BookLog>북로그</BookLog> 
        </ReviewBookLog>
        </Block>
    );

}

export default BookDetailPresenter;