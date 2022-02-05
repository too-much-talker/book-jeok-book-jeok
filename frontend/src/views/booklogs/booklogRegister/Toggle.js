import React, { useState } from 'react';
import styled from 'styled-components';

// 클래스를 설정하며 .toggle--checked 클래스가 활성화 되었을 경우의 CSS도 구현
const ToggleContainer = styled.div`
  position: absolute;
  display: inline-block;
  left: 25%;
  cursor: pointer;

  > .toggle-container {
    width: 50px;
    height: 24px;
    border-radius: 30px;
    background-color: red;
    transition: all .2s ease;
    &.toggle--checked {
        background-color: blue;
    }
  }
  
  >.toggle--checked {
      background-color: #ac88c2;
    }

  > .toggle-circle {
    position: absolute;
    top: 1px;
    left: 1px;
    width: 22px;
    height: 22px;
    border-radius: 50%;
    background-color: #ffffff;
    transition: all .25s ease;
    &.toggle--checked {
        left: 27px;
    }
  }
  
  >.toggle--checked {
    left: 23px;
  }
`;

// 설명 부분의 CSS를 구현
const Desc = styled.div`
  text-align: left;
  font-size: 1.5rem;
`;

// State를 바꿔줄 이벤트 함수 구현
export const Toggle = (props) => {
  const [isOn, setisOn] = useState(false);

  const toggleHandler = () => {
    setisOn(!isOn)
    props.toggle(isOn);
  };

  return (
    <>
      <ToggleContainer onClick={toggleHandler}>
        {/* 조건부 스타일링 : Toggle이 ON일 경우 toggle--checked 클래스를 추가 */}
        <div className = {`toggle-container ${isOn ? "toggle--checked" : ""}`}/>
        <div className = {`toggle-circle ${isOn ? "toggle--checked" : ""}`}/>
        <Desc> {isOn ? "공개" : "비공개"} </Desc>
      </ToggleContainer>

    </>
  );
};
export default Toggle;
