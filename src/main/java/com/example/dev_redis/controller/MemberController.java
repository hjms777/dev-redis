package com.example.dev_redis.controller;

import com.example.dev_redis.entity.member.Member;
import com.example.dev_redis.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 생성
     * @param name
     * @return
     */
    @PostMapping
    public Member create(@RequestParam String name) {
        return memberService.createMember(name);
    }

    /**
     * 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Member get(@PathVariable Long id) {
        return memberService.getMember(id);
    }
}
