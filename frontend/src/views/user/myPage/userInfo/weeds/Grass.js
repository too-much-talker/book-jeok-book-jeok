import { useState } from "react";
import style from "./Grass.module.css";
import styled from "styled-components";

const Grass = (props) => {
  const [isEntered, setIsEntered] = useState(false);
  const mouseEnterHandler = () => {
    setIsEntered(true);
  };
  const mouseLeaveHandler = () => {
    setIsEntered(false);
  };

  let color;
  const description = <div>{`${props.count} contributions on ${props.date}`}</div>;
  if(props.count===0){
    color = style.zero;
  }
  else if(props.count>=1 && props.count<=3){
    color = style.one;
  }
  else if(props.count<=6){
    color = style.two;
  }
  else if(props.count<=10){
    color = style.three;
  }
  else {
    color = style.four;
  }
  const floater = isEntered ? style.able : style.disable
  return <div className={style.wrapper} onMouseLeave={mouseLeaveHandler}>
    <div className={`${style.floater} ${floater}`}>{isEntered && description}</div>
  <p className={`${style.grass} ${color}`} onMouseEnter={mouseEnterHandler} ></p>
  </div>
};
export default Grass;
