import MeetingPresenter from "./MeetingPresenter";
import "./index.css";
import React from "react";
import styled from "styled-components";

const MeetingBox = styled.div`
  all: none;
`;

function MeetingContainer() {
  return (
    <MeetingBox>
      <header className="App-header">
        <img className="App-logo" alt="logo" />
        <img className="React-logo" alt="logo" />
      </header>
      {/* <div id="title">
        <a
          href="http://www.openvidu.io/"
          target="_blank"
          rel="noopener noreferrer"
        >
          <img className="mainLogo" alt="logo" />
        </a>
      </div> */}
      <MeetingPresenter />{" "}
    </MeetingBox>
  );
}

export default MeetingContainer;
