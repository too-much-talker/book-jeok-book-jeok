import React from 'react';
import styled from 'styled-components';
import NaviContainer from './NaviContainer';
const HeaderBlock = styled.div`
width:100vw;
height: 143px;
background: white;
font-weight: bold;
display: flex;
justify-content: space-between;
align-items: center;
margin-left: -8px;
margin-top:-8px;
`;

function HeaderPresenter(){
    return(
        <HeaderBlock>
            <NaviContainer></NaviContainer>
        </HeaderBlock>
    );
}

export default HeaderPresenter;