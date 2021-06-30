package iducs.springboot.blog201712015.service;

import iducs.springboot.blog201712015.domain.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BlogService {
    Optional<Blog> getBlog(long id); //블로그 id로 가져오기
    List<Blog> getBlogByTitle(String title, int index, int size); //블로그 title로 가져오기
    List<Blog> getBlogsByMember(String name, int index, int size); // blogger으로 조회
    List<Blog> getBlogs(); //블로그 리스트
    List<Blog> getBlogsByPage(int index, int size); //페이지
    int postBlog(Blog blog); //블로그 생성
    int patchBlog(Blog blog); //블로그 수정
    int deleteBlog(Long id); //블로그 삭제
    void upload(MultipartFile file) throws IOException;
    int getBlogCnt();
    int getBlogTitleCnt(String title);
    int getBlogNameCnt(String name);
}
