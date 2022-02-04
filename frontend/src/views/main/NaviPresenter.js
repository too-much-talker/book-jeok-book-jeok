import React from 'react';
import { Link  } from 'react-router-dom';
import styled from 'styled-components';
import logo from "../../res/img/logo2.PNG";

const NaviBlock= styled.div`
height:70px;
width:100%;
background:white;
margin-bottom:-50px;
`;
const Logo = styled.img.attrs({
    src: logo,
  })`
position:absolute;
width:190px;
height:60px;
margin-left:180px;
  `;
const NaviList = styled.div`
position:relative;
bottom:-25px;
background: white;
float:right;
width:64%;
height:45px;
margin-right:150px;

`;

const NaviItem= styled.div`
width:100px;
height:20px;
backgroud:white;
float:left;
margin-left:80px;
margin-top:10px;
text-align:center;
vertical-align: middle;
font-size: 20px;
`;
const LoginButton= styled.button`
width:120px;
height:45px;
position: absolute;
right:0px;
border:0px;
outline:0px;
backgroud-color:#F8F4F0;
border-radius:0.7em;
font-size: 20px;
text-decoration:none;
`;

function Navi(){
    return(
        <NaviBlock>
            <Logo></Logo>
            <NaviList>
                <NaviItem>북로그</NaviItem>
                <NaviItem>독서모임</NaviItem>
                <NaviItem>챌린지</NaviItem>
                <NaviItem>책정보</NaviItem>
                <NaviItem>공지</NaviItem>
                <Link to='/login'><LoginButton>로그인</LoginButton></Link>
            </NaviList>
        </NaviBlock>
    );
}

export default Navi;