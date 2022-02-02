import styled from "styled-components";
import { Link } from "react-router-dom";
import heart from "../../../res/img/heart_black.png";

const Card = styled.div`
  width: 20rem;
  height: 12rem;
  // border: 1px solid black;
  // float: left;
  margin: 0.5rem;
  padding: 0.7rem;
  border-radius: 5%;
  box-shadow: 4px 5px 7px 2px lightgrey;
  display: inline-block;
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
  font-size: 0.9rem;
`;

const Date = styled.span`
  font-size: 0.8rem;
`;

const Like = styled.span`
  margin-right: 1rem;
  text-align: left;
`;

const Heart = styled.img`
  width: 0.7rem;
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

const Title = styled.h4`
  margin: 0.2rem;
  margin-top: 0.6rem;
`;

const Content = styled.div`
  height: 40%;
`;

function BooklogCard({ booklog }) {
  let content = booklog.content;

  if (content.length > 40) {
    content = content.substr(0, 39);
  }

  return (
    <Card>
      <ImgGrid>
        <Img src={booklog.imgUrl} alt="book img"></Img>
      </ImgGrid>
      <InnerGrid>
        <Title>{booklog.title}</Title>
        <Content>
          <ContentLink to="/">{content}...</ContentLink>
        </Content>

        <hr />

        <Date>{booklog.createDate}</Date>
        <Wrapper>
          <Like>
            <Heart src={heart} />
            {booklog.like}
          </Like>
          <Author>{booklog.author}</Author>
        </Wrapper>
      </InnerGrid>
    </Card>
  );
}

export default BooklogCard;
