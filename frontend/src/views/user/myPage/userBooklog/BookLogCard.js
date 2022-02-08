import styled from "styled-components";
import { Link } from "react-router-dom";
import { useState } from "react";

const Card = styled.div`
  font-size: 1.2rem;
  width: 120px;
  height: 300px;
  float: left;
  margin-left: 75px;
`;

const Img = styled.img`
  width: 120px;
`;

// const ContentLink = styled(Link)`
//   text-decoration: none;
//   color: black;
// `;

const Date = styled.div`
  width: 100%;
  font-size: 0.8rem;
  text-align: right;
  border-top: solid 1px;
  top: 32rem;
`;

const Title = styled.h6`
    color: black;
`;

const Private = styled.div`
  width: 1rem;
  height: 1rem;
  border-radius: 10rem;
  /* position: absolute;
  /* bottom: 1.5rem; */
  top: 6rem;
  right: 8rem;
  background-color: #3b4cf6;

  ${({ active }) =>
    active &&
    `
    background: #ED544A;
  `}
  .description {
    color: #777;
    position: absolute;
    top: -2.5rem;
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
  const { imgUrl, title, createdDate, open } = props.book;
  const description = open ? (
    <div className="description">공개</div>
  ) : (
    <div className="description">비공개</div>
  );
  return (
    <Card>
        <Title>{title}</Title>
        <Img src={imgUrl} alt="book img"></Img>
        <Date>{createdDate}</Date>
        <Private
          active={!open}
          onMouseEnter={mouseEnterHandler}
          onMouseLeave={mouseLeaveHandler}
        >
          {isEntered && description}
        </Private>
    </Card>
  );
}

export default BooklogCard;
