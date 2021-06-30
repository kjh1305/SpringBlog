package iducs.springboot.blog201712015.controller;

import iducs.springboot.blog201712015.domain.Blog;
import iducs.springboot.blog201712015.domain.Pagination;
import iducs.springboot.blog201712015.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @GetMapping("/list")
    public String getBlogs(Model model, @RequestParam(defaultValue = "1") int page, HttpServletRequest request){
        int totalListCnt = blogService.getBlogCnt();
        Pagination pagination = new Pagination(totalListCnt, page);
        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize();
        List<Blog> blogList = blogService.getBlogsByPage(startIndex, pageSize);
        model.addAttribute("blogList", blogList);
        model.addAttribute("pagination", pagination);

        return "blog/list-view";
    }

    @GetMapping("/list/search")
    public String getBlogSearch(Model model, @RequestParam(defaultValue = "1") int page, HttpServletRequest request){
        String type = request.getParameter("searchType");
        String search = request.getParameter("search");
        if(type.equals("title")){
            int totalListCnt = blogService.getBlogTitleCnt(search);
            if (totalListCnt == 0){
                totalListCnt = 1;
            }
            Pagination pagination = new Pagination(totalListCnt, page);
            int startIndex = pagination.getStartIndex();
            int pageSize = pagination.getPageSize();
            List<Blog> blogList = blogService.getBlogByTitle(search, startIndex, pageSize);
            model.addAttribute("blogList", blogList);
            model.addAttribute("pagination", pagination);
            model.addAttribute("searchType", type);
            model.addAttribute("search", search);
        }else if(type.equals("name")){
            int totalListCnt = blogService.getBlogNameCnt(search);
            if (totalListCnt == 0){
                totalListCnt = 1;
            }
            Pagination pagination = new Pagination(totalListCnt, page);
            int startIndex = pagination.getStartIndex();
            int pageSize = pagination.getPageSize();
            List<Blog> blogList = blogService.getBlogsByMember(search, startIndex, pageSize);
            model.addAttribute("blogList", blogList);
            model.addAttribute("pagination", pagination);
            model.addAttribute("searchType", type);
            model.addAttribute("search", search);
        }
        return "blog/list-view-search";
    }

    @GetMapping("/create-form")
    public String createBlogForm(){
        return "blog/create-form";
    }
    @PostMapping("/create")
    public String createBlog(@RequestParam("filepath") MultipartFile file , Blog blog, Errors errors, Model model, HttpServletRequest request) throws IOException {
        blogService.upload(file);
        blog.setFilepath(file.getOriginalFilename());
        if(blogService.postBlog(blog)>0){
            model.addAttribute("blog", blog);
            return "redirect:/blogs/list";
        }else{
            model.addAttribute("message", "등록에 실패하였습니다.");
            return "errors/message";
        }
    }
    @GetMapping("/{id}")
    public String readBlog(@PathVariable("id") Long id, Model model){
        Optional<Blog> optional = blogService.getBlog(id);
        if(optional.isPresent()){
            Blog blog = optional.get();
            model.addAttribute("blog", blog);
            return "blog/read-view";
        }else{
            model.addAttribute("message", "에러 입니다.");
            return "errors/message";
        }
    }
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model){
        Optional<Blog> optional = blogService.getBlog(id);
        Blog blog = optional.get();
        model.addAttribute("blog", blog);
        return "blog/update-form";
    }
    @PostMapping("/update/{id}")
    public String patchBlog(@PathVariable("id") Long id,
                            @RequestParam final String title,
                            @RequestParam final String content,
                            @RequestParam final String b201712015ger,
                            @RequestParam final String filepathname,
                            Model model, @RequestParam("filepath") MultipartFile file) throws IllegalStateException, IOException {
        Blog blog = new Blog();
        blog.setId(id);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setB201712015ger(b201712015ger);
        if (!file.getOriginalFilename().isEmpty()) {
            blogService.upload(file);
            blog.setFilepath(file.getOriginalFilename());
        } else {
            blog.setFilepath(filepathname);
        }
        blogService.patchBlog(blog);
        model.addAttribute("blog", blog);
        return "redirect:/blogs/" + id;
    }
    @DeleteMapping("/{id}")
    public String deleteBlog(@PathVariable("id") Long id){
        blogService.deleteBlog(id);
        return "redirect:/blogs/" + id;
    }
    @GetMapping("/getImage")
    public ResponseEntity<byte[]> getImage(@RequestParam("filepath") String fileName) {
        ResponseEntity<byte[]> result = null;
        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
