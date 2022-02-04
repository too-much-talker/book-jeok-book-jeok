import styled from "styled-components";
const SearchedBook = (props) => {
  const Bookinfo = styled.div`
  
    .wrapper{
      display : inline-block;
    }
    p{
      margin : 0;
    }
  `;
  const bookSelectHandler=()=>{
    props.onClick(props.book);
    
  };
  return (
    <Bookinfo onClick={bookSelectHandler}>
      <img src={props.book.smallImgUrl}></img>
      <div className="wrapper">
        <p>{props.book.title}</p>
        <p>{props.book.author}</p>
      </div>
    </Bookinfo>
  );
};
export default SearchedBook;
