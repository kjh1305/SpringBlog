package iducs.springboot.blog201712015.service;

import iducs.springboot.blog201712015.domain.Member;

import java.util.List;

public interface MemberService {
    Member getMember(long id);
    Member getMemberByEmail(String email);
    List<Member> getMembers();
    List<Member> getMembersByPage(int index, int size);
    int postMember(Member member);
    int putMember(Member member);
    int deleteMember(Member member);
    int getMemberCnt();
}
