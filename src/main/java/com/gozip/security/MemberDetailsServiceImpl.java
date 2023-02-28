package com.gozip.security;

import com.gozip.entity.Member;
import com.gozip.exception.CustomException;
import com.gozip.exception.ErrorCode;
import com.gozip.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByEmail(username)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return new MemberDetailsImpl(member , member.getEmail());
    }
}
