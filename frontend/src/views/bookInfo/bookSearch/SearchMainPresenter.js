import styled from "styled-components";
import icon from  "../../../res/img/search.png";
// import Pagination from "react-js-pagination";
import { Link } from "react-router-dom";
import BookItem from "./BookItem";
import SearchNavContainer from "./SearchNavContainer";

const BookResultBlock= styled.div`
width:100vw;
height:100vh;
background:yellow;
`;

const ResultBlock = styled.div`
position:absolute;
left:50%;
top:30%;
transform: translate(-50%, -50%);
background:white;
width:1250px;
height:60px;
border-top: 2px solid black;
margin-top:20px;
`;

const ResultText = styled.div`
position:absolute;
margin-left:20px;
margin-top:20px;
font-size:30px;
`;

const BestSellerBlock= styled.div`
display: flex;
// justify-content: space-between;
align-items: flex-start;
flex-wrap: wrap;
text-align:center;
width:1200px;
margin:auto;
margin-top:150px;
`;
function SearchMainPresenter ({goDetail, bestSellers}){
        return( 
        <>
        <SearchNavContainer keyword={""}></SearchNavContainer>
        <ResultBlock>
            <ResultText>현재 베스트셀러는?</ResultText>
        </ResultBlock>
        <BestSellerBlock>
            {/* 나중에 tempBestSeller를 BestSeller로 바꿔야함 */}
            {bestSellers.map(bestSeller=>(
                // <div onClick={goDetail(bestSeller.seq)}>
                <div>
                <BookItem 
                key={bestSeller.seq}
                //key 수정해야함 
                title={bestSeller.title} 
                author={bestSeller.author}
                largeImgUrl={bestSeller.largeImgUrl}
                publisher={bestSeller.publisher}
                publicationDate= {bestSeller.publicationDate}
                starRating={bestSeller.starRating}
                ></BookItem>
                </div>
            ))
            }
        </BestSellerBlock>
        </>
    );
}
export default SearchMainPresenter;