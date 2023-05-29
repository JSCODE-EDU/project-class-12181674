package com.jscode.boardService.service;

import com.jscode.boardService.token.JwtProvider;
import com.jscode.boardService.domain.Member;
import com.jscode.boardService.domain.dto.MemberDto;
import com.jscode.boardService.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.jscode.boardService.domain.ExceptionMessageConst.NOT_EXIST_EMAIL;
import static com.jscode.boardService.domain.ExceptionMessageConst.WRONG_PASSWORD;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public void save(MemberDto memberDto){

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .createdAt(LocalDateTime.now())
                .build();
        memberRepository.save(member);
    }

    public String login(MemberDto memberDto) {

        Member member = checkEmail(memberDto.getEmail());

        if(!member.getPassword().equals(memberDto.getPassword())){
            throw new IllegalArgumentException(WRONG_PASSWORD.getMessage());
        }

        String token = jwtProvider.createToken(member.getEmail());

        return token;
    }

    public Member checkEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(email);

        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            return member;
        }else{
            throw new IllegalArgumentException(NOT_EXIST_EMAIL.getMessage());
        }
    }
}
