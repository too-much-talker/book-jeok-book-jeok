package com.ssafy.bjbj.api.booklog.service;

public interface LikeService {

    void like(Long booklogSeq, Long memberSeq);

    void unLike(Long booklogSeq, Long memberSeq);

}
