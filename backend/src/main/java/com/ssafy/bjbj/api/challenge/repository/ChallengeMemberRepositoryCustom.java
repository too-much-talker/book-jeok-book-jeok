package com.ssafy.bjbj.api.challenge.repository;

public interface ChallengeMemberRepositoryCustom {

    Integer countAllByMemberSeq(boolean isEnd, Long memberSeq);

}
