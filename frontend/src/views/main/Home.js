import styled, { keyframes } from "styled-components";
import img1 from "../../res/img/img1.jpg";
import img2 from "../../res/img/img2.jpeg";
import img3 from "../../res/img/img3.jpg";
import img4 from "../../res/img/img4.jpg";
import img5 from "../../res/img/img5.jpg";
import img6 from "../../res/img/img6.jpg";
import img7 from "../../res/img/img7.png";

const pongAnimation = keyframes`
//  0% {
//     transform:rotate(0deg);
//     border-radius:0px;
//   }
//   50% {
//     border-radius:100px;
//   }
//   100%{
//     transform:rotate(360deg);
//     border-radius:0px;
//   }

  from {
    transform: translate(0, 100px); 
    opacity : 0;
  } 
  to {
    transform: translate(0px, 0px); 
    opacity : 1;
  }
`;

const Welcome = styled.h2`
  margin-top: 15rem;
  margin-left: 5rem;
  display: inline-block;
`;

const HomeWrapper = styled.div`
  text-align: left;
  position: relative;
`;

const Img = styled.img.attrs({
  src: img1,
})`
  width: 250px;
  height: 250px;
  top: 25rem;
  right: 8rem;
  border-radius: 50%;
  background: black;
  display: inline-block;
  position: absolute;
  box-shadow: 10px 10px 15px grey;
  animation: ${pongAnimation} 0.8s linear;
`;

const Img2 = styled(Img).attrs({
  src: img2,
})`
  width: 100px;
  height: 100px;
  top: 20rem;
  right: 35rem;
  animation: ${pongAnimation} 0.5s linear;
`;

const Img3 = styled(Img2).attrs({
  src: img3,
})`
  width: 140px;
  height: 140px;
  top: 5rem;
  right: 8rem;
  animation: ${pongAnimation} 1s linear;
`;

const Img4 = styled(Img2).attrs({
  src: img4,
})`
  width: 100px;
  height: 100px;
  top: 45rem;
  right: 40rem;
  animation: ${pongAnimation} 1s linear;
`;

const Img5 = styled(Img2).attrs({
  src: img5,
})`
  width: 100px;
  height: 100px;
  top: 15rem;
  right: 2rem;
  animation: ${pongAnimation} 0.6s linear;
`;

const Img6 = styled(Img2).attrs({
  src: img6,
})`
  width: 80px;
  height: 80px;
  top: 40rem;
  right: 0rem;
  animation: ${pongAnimation} 1s linear;
`;

const Img7 = styled(Img2).attrs({
  src: img7,
})`
  width: 80px;
  height: 80px;
  top: 38rem;
  right: 50rem;
  animation: ${pongAnimation} 0.5s linear;
`;

function Home() {
  return (
    <HomeWrapper>
      <Welcome>너와 나의 책 이야기, 북적북적</Welcome>
      <Img />
      <Img2 />
      <Img3 />
      <Img4 />
      <Img5 />
      <Img6 />
      <Img7 />
    </HomeWrapper>
  );
}

export default Home;
