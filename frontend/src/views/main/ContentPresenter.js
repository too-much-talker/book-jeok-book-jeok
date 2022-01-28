import React from 'react';
import styled from 'styled-components';
import banner from "../../res/img/banner.png";
// width:1150px;
// height:490px;
// left:260px;
// top:300px;

// width:1050px;
// height:450px;
const ContentImage = styled.img.attrs({
    src: banner,
  })`
position:absolute;
width:1150px;
height:490px;
left:260px;
top:300px;
border-radius:0.8em;
  `;
function ContentPresenter(){
    return(
            <ContentImage></ContentImage>
    );
}

export default ContentPresenter;