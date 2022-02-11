import axios from "axios";
import OpenViduSession from "openvidu-react";
import "./meeting.css";
import { useState } from "react";
import React from "react";
import {
  OPENVIDU_SECRET,
  OPENVIDU_URL,
} from "../../../common/config/key/reading_group";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";

function MeetingPresenter() {
  const OPENVIDU_SERVER_URL = OPENVIDU_URL;
  const OPENVIDU_SERVER_SECRET = OPENVIDU_SECRET;
  const params = useParams();
  const nickname = useSelector(
    (state) => state.authReducer.memberInfo.nickname
  );

  const [state, setState] = useState({
    mySessionId: "Session" + params.meetingSeq,
    myUserName: nickname,
    // mySessionId: "SessionA",
    // myUserName: "OpenVidu_User_" + Math.floor(Math.random() * 100),
    token: undefined,
    session: undefined,
  });

  function handlerJoinSessionEvent() {
    console.log(state);
    console.log("Join session");
  }

  function handlerLeaveSessionEvent() {
    console.log("Leave session");
    setState({
      ...state,
      session: undefined,
    });
  }

  function handlerErrorEvent() {
    console.log("Leave session");
  }

  function handleChangeSessionId(e) {
    setState({
      ...state,
      mySessionId: e.target.value,
    });
  }

  function handleChangeUserName(e) {
    setState({
      ...state,
      myUserName: e.target.value,
    });
  }

  function joinSession(event) {
    if (state.mySessionId && state.myUserName) {
      getToken().then((token) => {
        setState((prevState) => {
          return {
            ...prevState,
            token: token,
            session: true,
          };
        });
      });
      event.preventDefault();
    }
  }

  function getToken() {
    return createSession(state.mySessionId)
      .then((sessionId) => createToken(sessionId))
      .catch((Err) => console.error(Err));
  }

  function createSession(sessionId) {
    return new Promise((resolve, reject) => {
      var data = JSON.stringify({ customSessionId: sessionId });
      axios
        .post(OPENVIDU_SERVER_URL + "/openvidu/api/sessions", data, {
          headers: {
            Authorization:
              "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          console.log("CREATE SESION", response);
          resolve(response.data.id);
        })
        .catch((response) => {
          // console.log("createSession error");
          var error = Object.assign({}, response);
          if (error.response && error.response.status === 409) {
            resolve(sessionId);
            // console.log("createSession 409 error");
          } else {
            console.log(error);
            console.warn(
              "No connection to OpenVidu Server. This may be a certificate error at " +
                OPENVIDU_SERVER_URL
            );
            if (
              window.confirm(
                'No connection to OpenVidu Server. This may be a certificate error at "' +
                  OPENVIDU_SERVER_URL +
                  '"\n\nClick OK to navigate and accept it. ' +
                  'If no certificate warning is shown, then check that your OpenVidu Server is up and running at "' +
                  OPENVIDU_SERVER_URL +
                  '"'
              )
            ) {
              window.location.assign(
                OPENVIDU_SERVER_URL + "/accept-certificate"
              );
            }
          }
        });
    });
  }

  function createToken(sessionId) {
    return new Promise((resolve, reject) => {
      var data = JSON.stringify({});
      axios
        .post(
          OPENVIDU_SERVER_URL +
            "/openvidu/api/sessions/" +
            sessionId +
            "/connection",
          data,
          {
            headers: {
              Authorization:
                "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
              "Content-Type": "application/json",
            },
          }
        )
        .then((response) => {
          console.log("TOKEN", response);
          resolve(response.data.token);
        })
        .catch((error) => reject(error));
    });
  }

  return (
    <div>
      {state.session === undefined ? (
        <div id="join">
          <div id="join-dialog">
            <h1> 독서모임 화상 미팅방 입장 </h1>
            <form onSubmit={joinSession}>
              <p>
                <label>Nickname: </label>
                <input
                  type="text"
                  id="userName"
                  value={state.myUserName}
                  onChange={handleChangeUserName}
                  required
                />
              </p>
              <p>
                {/* <label> Session: </label> */}
                <input
                  type="hidden"
                  id="sessionId"
                  value={state.mySessionId}
                  onChange={handleChangeSessionId}
                  required
                />
              </p>
              <p>
                <input name="commit" type="submit" value="JOIN" />
              </p>
            </form>
          </div>
        </div>
      ) : (
        <div id="session">
          <OpenViduSession
            id="opv-session"
            sessionName={state.mySessionId}
            user={state.myUserName}
            token={state.token}
            joinSession={handlerJoinSessionEvent}
            leaveSession={handlerLeaveSessionEvent}
            error={handlerErrorEvent}
          />
        </div>
      )}
    </div>
  );
}

export default MeetingPresenter;
