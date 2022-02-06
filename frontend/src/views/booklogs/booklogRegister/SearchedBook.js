import styled from "styled-components";

const SearchedBook = (props) => {
  const Bookinfo = styled.div`
    display: flex;
    border: 1px solid #cccccc;
    height: 13rem;
    margin: 0 10rem;
    cursor: pointer;
    p {
      margin: 0;
    }
    margin-bottom: 1rem;
    align-items: center;
    padding: 1rem;
    padding-left: 5rem;
    border-radius: 0.5rem;
    transition: all 0.1s;
    &:hover {
      transform: scale(1.03);
    }
    &:active {
      transform: scale(1.01);
    }
  `;
  const Wrapper = styled.div`
    display: inline-block;
    margin-left: 5rem;
    justify-content: center;
    align-items: center;
    font-size: 1.5rem;
    .title {
      font-weight: bold;
    }
    .author {
      font-size: 1.3rem;
    }
  `;
  const bookSelectHandler = () => {
    props.onClick(props.book);
  };
  let tmpUrl;

  tmpUrl = props.book.largeImgUrl.substring(
    0,
    props.book.largeImgUrl.length - 5
  );
  tmpUrl = tmpUrl + "h.jpg";

  return (
    <Bookinfo onClick={bookSelectHandler}>
      <img src={tmpUrl} alt={props.book.title}></img>
      <Wrapper>
        <p className="title">{props.book.title}</p>
        <p className="author">{props.book.author}</p>
      </Wrapper>
    </Bookinfo>
  );
};
export default SearchedBook;
