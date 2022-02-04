import BooklogCard from "./BooklogCard";
import BooklogNavi from "./BooklogNavi";
import styled from "styled-components";

const BooklogListWrapper = styled.div`
  width: 80vw;
`;

const BooklogListBody = styled.div`
  width: 100vw;
  display: flex;
  justify-content: center;
  text-align: center; // card 요소 가운데 정렬 -> 단, 그리드 이상해짐.
`;

const CardBox = styled.div`
  width: 100rem;
  height: 100%;
  background: green;
  margin: 0 auto;
`;

function BooklogListPresenter({ data, isPopular }) {
  // console.log(data);

  return (
    <div>
      <div>
        <h2>BooklogList</h2>
        <BooklogNavi isPopular={isPopular} />
        <CardBox>
          {data.map((data, index) => (
            <BooklogCard booklog={data} key={index} />
          ))}
        </CardBox>
      </div>
    </div>
  );
}

export default BooklogListPresenter;
