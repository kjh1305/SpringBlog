package iducs.springboot.blog201712015.service;

import iducs.springboot.blog201712015.domain.Blog;
import iducs.springboot.blog201712015.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService{
    private BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public Optional<Blog> getBlog(long id) {
        return blogRepository.readById(id);
    }

    @Override
    public List<Blog> getBlogByTitle(String title, int index, int size) {
        return blogRepository.readByTitle(title).stream().sorted(Comparator.comparing(Blog::getId).reversed()).skip(index).limit(size).collect(Collectors.toList());
    }

    @Override
    public List<Blog> getBlogsByMember(String name, int index, int size) {
        return blogRepository.readByMember(name).stream().sorted(Comparator.comparing(Blog::getId).reversed()).skip(index).limit(size).collect(Collectors.toList());
    }

    @Override
    public List<Blog> getBlogs() {
        return blogRepository.readBlogs().stream().sorted(Comparator.comparing(Blog::getId).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<Blog> getBlogsByPage(int index, int size) {
        return blogRepository.readBlogs().stream().sorted(Comparator.comparing(Blog::getId).reversed()).skip(index).limit(size).collect(Collectors.toList());
    }

    @Override
    public int postBlog(Blog blog) {
        return blogRepository.create(blog);
    }

    @Override
    public int patchBlog(Blog blog) {
        return blogRepository.update(blog);
    }

    @Override
    public int deleteBlog(Long id) {
        return blogRepository.delete(id);
    }

    @Override
    public void upload(MultipartFile file) throws IOException {
        file.transferTo(new File(uploadPath, file.getOriginalFilename()));
    }

    @Override
    public int getBlogCnt() {
        return blogRepository.rowCnt();
    }

    @Override
    public int getBlogTitleCnt(String title) {
        return blogRepository.rowCntTitle(title);
    }

    @Override
    public int getBlogNameCnt(String name) {
        return blogRepository.rowCntName(name);
    }
}
