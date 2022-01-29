package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class BookReviewRepositoryTests {

}
