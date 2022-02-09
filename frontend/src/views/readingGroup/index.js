import { Route, Routes } from "react-router-dom";
import MeetingContainer from "./meeting/MeetingContainer";

function ReadingGroup() {
  return (
    <Routes>
      <Route index path="/meeting/:meetingSeq" element={<MeetingContainer />} />
      {/* <Route path="detail/:booklogSeq" element={<BooklogDetailContainer />} /> */}
    </Routes>
  );
}

export default ReadingGroup;
