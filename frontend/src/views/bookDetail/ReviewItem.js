import styled from "styled-components"

const Block= styled.div`
width:280px;
margin-bottom:50px;
`;


const ReviewContents = styled.div``; //내용
const ReviewStarRating= styled.div``;//평점
const ReviewDate= styled.div``;//작성일

function ReviewItem({summary,reviewStarRating,reviewDate}){
    return(
        <Block>
            <ReviewContents>{summary}</ReviewContents>
            <ReviewStarRating>{reviewStarRating}</ReviewStarRating>
            <ReviewDate>{reviewDate}</ReviewDate>
        </Block>

    );
}

export default ReviewItem;