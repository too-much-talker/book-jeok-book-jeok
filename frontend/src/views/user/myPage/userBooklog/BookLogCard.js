import styled from "styled-components";
import { Link } from "react-router-dom";
import { useState } from "react";
import Pagination from "react-js-pagination";
const Card = styled.div`
  width: 30rem;
  height: 18rem;
  /* float: left; */
  display: inline-block;
  margin: 1rem 3rem;
  padding: 0.7rem;
  border-radius: 5%;
  box-shadow: 4px 5px 7px 2px lightgrey;
  font-size: 1.2rem;
  // display: inline-block;
`;

const InnerGrid = styled.div`
  width: 60%;
  height: 100%;
  float: left;
  // text-align: center;
  position: relative;
`;

const ImgGrid = styled(InnerGrid)`
  width: 40%;
  position: relative;
`;

const Img = styled.img`
  width: 75%;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  left: 10%;
`;

const ContentLink = styled(Link)`
  text-decoration: none;
  color: black;
`;

const Date = styled.div`
  width: 100%;
  font-size: 0.8rem;
  text-align: left;
  border-top: solid 1px;
`;

const Title = styled.h6`
  margin: 0.2rem;
  margin-top: 0.6rem;
`;

const Private = styled.div`
  width: 1rem;
  height: 1rem;
  border-radius: 10rem;
  position: absolute;
  bottom: 1.5rem;
  right: 1.5rem;
  background-color: #3B4CF6;
  
  ${({ active }) => active && `
    background: #ED544A;
  `}
  .description{
    color: #777;
    position: absolute;
    top:-2.5rem;
    border: 1px solid #777;
    background-color: white;
    width: 4rem;
  }
`;


function BooklogCard(props) {
  const [isEntered, setIsEntered] = useState(false);
  const mouseEnterHandler = () => {
    setIsEntered(true);
  };
  const mouseLeaveHandler = () => {
    setIsEntered(false);
  };
  const {imgUrl, title, createdDate, open} = props.book;
  const description = open ? <div className="description">공개</div> : <div className="description">비공개</div>;
  return (
    <Card>
      <ImgGrid>
        <Img src={imgUrl} alt="book img"></Img>
      </ImgGrid>
      <InnerGrid>
        <ContentLink to="/">
          <Title>{title}</Title>
        </ContentLink>
        <Date>{createdDate}</Date>
        <Private active={!open} onMouseEnter={mouseEnterHandler} onMouseLeave={mouseLeaveHandler}>
          {isEntered&&description}
        </Private>
      </InnerGrid>
    </Card>
  );
}

export default BooklogCard;
