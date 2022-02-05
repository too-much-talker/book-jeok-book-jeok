import BooklogListContainer from "./booklogList/BooklogListContainer";
import BooklogSearchContainer from "./booklogSearch/BooklogSearchContainer";
import { Route, Routes } from "react-router-dom";

function Booklogs() {
  return (
    <Routes>
      <Route index path="/list/:order" element={<BooklogListContainer />} />
      <Route path="/search" exact element={<BooklogSearchContainer />} />
    </Routes>
  );
}

export default Booklogs;
