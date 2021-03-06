package com.ssafy.bjbj.api.member.entity;

import java.io.Serializable;
import java.util.Objects;

public class SubscribeSeq implements Serializable {

    private Long fromMember;
    private Long toMember;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscribeSeq that = (SubscribeSeq) o;
        return Objects.equals(fromMember, that.fromMember) && Objects.equals(toMember, that.toMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromMember, toMember);
    }

}
