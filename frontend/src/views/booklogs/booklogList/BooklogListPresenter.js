import BooklogCard from "./BooklogCard";
import BooklogNavi from "./BooklogNavi";

function BooklogListPresenter({ data, isPopular }) {
  // console.log(data);

  return (
    <div>
      <h2>BbooklogList</h2>
      <BooklogNavi isPopular={isPopular} />
      <div>
        {data.map((data, index) => (
          <BooklogCard booklog={data} key={index} />
        ))}
      </div>
    </div>
  );
}

export default BooklogListPresenter;
