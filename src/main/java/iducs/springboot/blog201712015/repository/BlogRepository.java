package iducs.springboot.blog201712015.repository;

import iducs.springboot.blog201712015.domain.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    int create(Blog blog); // 레코드 생성
    Optional<Blog> readById(Long id); //하나 레코드 가져오기, 유일키 사용
    List<Blog> readByTitle(String title); //하나 레코드 가져오기
    List<Blog> readByMember(String name); //하나 레코드 가져오기
    List<Blog> readBlogs();  //다수의 레코드 가져오기
    int update(Blog blog); // 레코드 수정
    int delete(Long id); // 레코드 삭제
    int rowCnt();
    int rowCntTitle(String title);
    int rowCntName(String name);
}
