package com.ssafy.bjbj.common.auth;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@RequiredArgsConstructor
@Component
public class SsafyUserDetailService implements UserDetailsService{
//	@Autowired
	private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    		Member member = memberService.findMemberByEmail(username);
    		if(member != null) {
    			SsafyUserDetails userDetails = new SsafyUserDetails(member);
    			return userDetails;
    		}
    		return null;
    }
}
