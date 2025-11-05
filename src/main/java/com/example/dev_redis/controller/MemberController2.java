package com.example.dev_redis.controller;

import com.example.dev_redis.entity.member.Member;
import com.example.dev_redis.service.MemberService2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members2")
public class MemberController2 {

    private final MemberService2 memberService2;

    public MemberController2(MemberService2 memberService2) {
        this.memberService2 = memberService2;
    }

    @PostMapping
    public Member create(@RequestParam String name) {
        return memberService2.createMember(name);
    }

    @GetMapping("/{id}")
    public Member get(@PathVariable Long id) {
        return memberService2.getMember(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        memberService2.deleteMember(id);
    }
}
