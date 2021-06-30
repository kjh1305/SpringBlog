package iducs.springboot.blog201712015.service;

import iducs.springboot.blog201712015.domain.Blog;
import iducs.springboot.blog201712015.domain.Member;
import iducs.springboot.blog201712015.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService{
    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member getMember(long id) {
        Member member = new Member();
        member.setId(id);
        return memberRepository.readById(member);
    }

    @Override
    public Member getMemberByEmail(String email) {
        Member member = new Member();
        member.setEmail(email);
        return memberRepository.readByEmail(member);
    }

    @Override
    public List<Member> getMembers() {
        return memberRepository.readMembers();
    }

    @Override
    public List<Member> getMembersByPage(int index, int size) {
        return memberRepository.readMembers().stream().sorted(Comparator.comparing(Member::getId).reversed()).skip(index).limit(size).collect(Collectors.toList());
    }

    @Override
    public int postMember(Member member) {
        return memberRepository.create(member);
    }

    @Override
    public int putMember(Member member) {
        return memberRepository.update(member);
    }

    @Override
    public int deleteMember(Member member) {
        return memberRepository.delete(member);
    }

    @Override
    public int getMemberCnt() {
        return memberRepository.rowCnt();
    }
}
