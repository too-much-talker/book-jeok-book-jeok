package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupMember;

public interface ReadingGroupMemberService {

    ReadingGroupMember join(Long readingGroupSeq, Long memberSeq);

    void unJoin(Long readingGroupSeq, Long memberSeq);

}
