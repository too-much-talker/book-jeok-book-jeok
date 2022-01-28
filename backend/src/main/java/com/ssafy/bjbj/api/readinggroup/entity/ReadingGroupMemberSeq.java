package com.ssafy.bjbj.api.readinggroup.entity;

import java.io.Serializable;
import java.util.Objects;

public class ReadingGroupMemberSeq implements Serializable {

    private Long readingGroup;
    private Long member;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadingGroupMemberSeq that = (ReadingGroupMemberSeq) o;
        return Objects.equals(readingGroup, that.readingGroup) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readingGroup, member);
    }

}
