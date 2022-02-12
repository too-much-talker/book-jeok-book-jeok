import { Route, Routes } from "react-router-dom";
import MeetingContainer from "./meeting/MeetingContainer";
import React from "react";
import GroupDetailContainer from "./groupDetail/GroupDetailContainer";

function ReadingGroup() {
  return (
    <Routes>
      <Route index path="/meeting/:meetingSeq" element={<MeetingContainer />} />
      <Route path="/detail/:meetingSeq" element={<GroupDetailContainer />} />
    </Routes>
  );
}

export default ReadingGroup;
