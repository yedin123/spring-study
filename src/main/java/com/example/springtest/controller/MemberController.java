package com.example.springtest.controller;


import com.example.springtest.domain.Member;
import com.example.springtest.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members")
    public String list(Model model, @RequestParam(required = false, defaultValue = "") String searchText) {
        List<Member> members = memberService.findMembers(searchText);
        model.addAttribute("members", members);
            return "members/memberList";
    }

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(MemberForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setPassword(form.getPassword());

        try { //회원 중복 확인
            memberService.join(member);
        } catch (IllegalStateException e) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('이미 존재하는 회원입니다.'); history.go(-1);</script>");
            out.flush();
        }
        return "redirect:/";
    }

}

