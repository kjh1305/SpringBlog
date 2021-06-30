package iducs.springboot.blog201712015.repository;

import iducs.springboot.blog201712015.domain.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository("memberRepositoryImplMyBatis")
public class MemberRepositoryImplMyBatis implements MemberRepository{

    private SqlSession sqlSession;

    @Autowired
    public MemberRepositoryImplMyBatis(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private  static String namespace = "iducs.springboot.blog201712015.mapper.MemberMapper";

    @Override
    public int create(Member member) {
        return sqlSession.insert(namespace + ".create", member);
    }

    @Override
    public Member readById(Member member) {
        return sqlSession.selectOne(namespace + ".read", member.getId());
    }

    @Override
    public Member readByEmail(Member member) {
        return sqlSession.selectOne(namespace + ".readEmail", member.getEmail());
    }

    @Override
    public List<Member> readMembers() {
        return sqlSession.selectList(namespace + ".readList");
    }

    @Override
    public int update(Member member) {
        return sqlSession.update(namespace + ".update", member);
    }

    @Override
    public int delete(Member member) {
        return sqlSession.delete(namespace + ".delete", member.getId());
    }

    @Override
    public int rowCnt() {
        return sqlSession.selectOne(namespace + ".rowCnt");
    }
}
