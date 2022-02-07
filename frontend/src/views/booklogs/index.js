import BooklogListContainer from "./booklogList/BooklogListContainer";
import { Route, Routes } from "react-router-dom";
import BooklogDetailContainer from "./booklogList/booklogDetail/BooklogDetailContainer";

function Booklogs() {
  return (
    <Routes>
      <Route index path="/list/:order" element={<BooklogListContainer />} />
      <Route path="detail/:booklogSeq" element={<BooklogDetailContainer />} />
    </Routes>
  );
}

export default Booklogs;
