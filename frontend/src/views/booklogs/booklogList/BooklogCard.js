import styled from "styled-components";
import { Link } from "react-router-dom";
import heart from "../../../res/img/heart_black.png";

const Card = styled.div`
  width: 30rem;
  height: 18rem;
  float: left;
  margin: 0.5rem;
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

const Like = styled.span`
  margin-right: 1rem;
  text-align: left;
`;

const Heart = styled.img`
  width: 1rem;
  text-align: left;
`;

const Author = styled.span`
  display: inline-block;
  text-align: right;
`;

const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 0.5rem;
`;

const Title = styled.h6`
  margin: 0.2rem;
  margin-top: 0.6rem;
`;

const Content = styled.div`
  height: 48%;
  text-align: left;
  font-size: 1.3rem;
`;

function BooklogCard({ booklog }) {
  let content = booklog.content;

  if (content !== null && content.length > 40) {
    content = content.substr(0, 39);
  }

  return (
    <Card>
      <ImgGrid>
        <Img src={booklog.imgUrl} alt="book img"></Img>
      </ImgGrid>
      <InnerGrid>
        <ContentLink to="/">
          <Title>{booklog.title}</Title>
          <Content>{booklog.content}...</Content>
        </ContentLink>
        <Date>{booklog.createdDate}</Date>
        <Wrapper>
          <Like>
            <Heart src={heart} /> {booklog.likes}
          </Like>
          <Author>{booklog.nickname}</Author>
        </Wrapper>
      </InnerGrid>
    </Card>
  );
}

export default BooklogCard;
