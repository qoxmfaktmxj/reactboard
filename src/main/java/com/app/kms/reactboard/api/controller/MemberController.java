package com.app.kms.reactboard.api.controller;

import com.app.kms.reactboard.api.dto.MemberDTO;
import com.app.kms.reactboard.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService; //@RequiredArgsConstructor

    // 회원가입 페이지 출력 요청 (모든 요청은 GET)
    @GetMapping("/api/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/api/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        //RequestParam에 담겨온 값을 String memberEmail에 옮겨 담는다.
        System.out.println("MemberController.save");//soutm
        System.out.println("memberDTO = " + memberDTO);//soutp
        //MemberService memberService = new MemberService(); //spring 이전 버전
        memberService.save(memberDTO);
        return "login";
    }

    @GetMapping("/api/member/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/api/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/api/member/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        //어떠한 html로 가져갈 데이터가 있다면 model사용 (js에서 직접 가져오기도 함)
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    @GetMapping("/api/member/{id}")
    public String findById(@PathVariable Long id, Model model){
        memberService.findById(id);
        //1명 회원 목록 DTO 타입으로 받아준다.
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/api/member/update")
    public String updateForm(HttpSession session, Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/api/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        //controller메소드가 처리가 끝나고 다시 다른 컨트롤러 메소드 요청 : redirect
        return "redirect:/member/" + memberDTO.getId(); //내 정보 수정 후 수정 완료된 상세 페이지 띄우기
    }

    @GetMapping("/api/member/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);

        return "redirect:/member/";
    }

    @GetMapping("/api/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }

    @PostMapping("/api/member/email-check")
    //Ajax 사용 시 ResponseBody 사용
    public @ResponseBody
    String emailCheck(@RequestParam("memberEmail") String memberEmail){
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
    }
}
