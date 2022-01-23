package com.ssafy.bjbj.api.challenge.entity;

import java.io.Serializable;
import java.util.Objects;

public class ChallengeMemberId implements Serializable {

    private Long challenge;
    private Long member;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChallengeMemberId that = (ChallengeMemberId) o;
        return Objects.equals(challenge, that.challenge) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(challenge, member);
    }

}
