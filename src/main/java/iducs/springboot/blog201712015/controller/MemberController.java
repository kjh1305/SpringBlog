package iducs.springboot.blog201712015.controller;


import iducs.springboot.blog201712015.domain.Member;
import iducs.springboot.blog201712015.domain.Pagination;
import iducs.springboot.blog201712015.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/member")
public class MemberController {
    HttpSession session = null;
    HttpCookie cookie = null;
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signin-form")
    public String signinForm(HttpServletRequest request, Model model){

        Cookie[] cookie1 = request.getCookies();

            Optional<Cookie> cookie_id = Arrays.stream(cookie1).filter((s)->s.getName().equals("cookie_id")).findAny();
            if(cookie_id.isPresent()){
                String s1 = cookie_id.map((s) -> s.getValue()).get();
                model.addAttribute("cookie_id", s1);
            }


        return "member/signin-form";
    }
    @GetMapping("/signup-form")
    public String signupForm(){
        return "member/signup-form";
    }
    @PostMapping("/signup") //정보를 추가 PostMapping(보안에 좋음), 수정은 PutMapping, 삭제는 DeleteMapping
    public String signup(@Valid Member member, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("message", errors.getFieldValue("email"));
            return "errors/message";
        }
        if(memberService.postMember(member) > 0) {
            model.addAttribute("member", member);
            return "member/signin-form";
        } else {
            model.addAttribute("message", "등록을 실패하였습니다.");
            return "errors/message";
        }
    }
    @PostMapping("/signin")
    public String signin(HttpServletRequest request, Model model, HttpServletResponse response, @RequestParam(defaultValue = "no") String checked){
        session = request.getSession();

        String email = request.getParameter("email");
        String pw = request.getParameter("pw");
        Member memberByEmail = memberService.getMemberByEmail(email);
        if(memberByEmail != null && memberByEmail.getPw().equals(pw)){
            if(checked.equals("yes")){
                Cookie cookie = new Cookie("cookie_id", email);
                response.addCookie(cookie);
            }
            if(checked.equals("no")){
                Cookie cookie = new Cookie("cookie_id", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            session.setAttribute("name", memberByEmail.getName());
            session.setAttribute("id", memberByEmail.getId());
            session.setAttribute("email", memberByEmail.getEmail());
            return "main/index";
        }else{
            model.addAttribute("message", "로그인 실패");
            return "errors/message";
        }
    }
    @GetMapping("/{id}")
    public String getMember(@PathVariable("id") Long id, Model model) {
        Member member = memberService.getMember(id);
        model.addAttribute("member", member);
        return "member/detail-form";
    }
    @PutMapping("/{id}")
    public String putMember(@PathVariable("id") Long id, @Valid Member member, Model model) {
        if(memberService.putMember(member) > 0) {
            model.addAttribute("member", member);
            return "redirect:/member/" + id; // GetMapping 호출
        } else {
            model.addAttribute("message", "업데이트를 실패하였습니다.");
            return "errors/message";
        }
    }
    @GetMapping("/list")
    public String getMembers(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1") int page) {
        if(session != null && ((String) session.getAttribute("email")).equals("root@induk.ac.kr")) {
            int totalListCnt = memberService.getMemberCnt();
            Pagination pagination = new Pagination(totalListCnt, page);
            int startIndex = pagination.getStartIndex();
            int pageSize = pagination.getPageSize();
            List<Member> memberList = memberService.getMembersByPage(startIndex, pageSize);
            model.addAttribute("pagination", pagination);
            model.addAttribute("memberList", memberList);
            return "member/list";
        } else {
            model.addAttribute("message", "관리자로 로그인하시오.");
            return "errors/message";
        }
    }
    @DeleteMapping("/{id}")
    public String deleteMember(@Valid Member member, Model model) {
        if(memberService.deleteMember(member) > 0) {
            return "redirect:/member/signout";
        } else {
            model.addAttribute("message", "탈퇴를 실패하였습니다.");
            return "errors/message";
        }
    }
    @GetMapping("/signout")
    public String signout(HttpServletRequest request){
            session = request.getSession();
            session.invalidate(); //현재 session 객체를 무효화
        return "main/index";
    }

}
