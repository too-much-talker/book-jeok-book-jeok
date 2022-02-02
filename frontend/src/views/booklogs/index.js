import BooklogListContainer from "./booklogList/BooklogListContainer";
import { Route, Routes } from "react-router-dom";

function Booklogs() {
  return (
    <Routes>
      <Route index path="/list/:order" element={<BooklogListContainer />} />
    </Routes>
  );
}

export default Booklogs;
