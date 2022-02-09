import MeetingPresenter from "./MeetingPresenter";
import "./index.css";

function MeetingContainer() {
  return (
    <div>
      <header className="App-header">
        <img className="App-logo" alt="logo" />
        <img className="React-logo" alt="logo" />
      </header>
      <div id="title">
        <a
          href="http://www.openvidu.io/"
          target="_blank"
          rel="noopener noreferrer"
        >
          <img className="mainLogo" alt="logo" />
        </a>
      </div>
      <div>hi</div>
      <MeetingPresenter />{" "}
    </div>
  );
}

export default MeetingContainer;
