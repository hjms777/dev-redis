package com.example.dev_redis.service;

import com.example.dev_redis.entity.member.Member;
import com.example.dev_redis.entity.member.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.logging.Logger;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    Logger logger = Logger.getLogger(MemberService.class.getName());


    public MemberService(MemberRepository memberRepository, StringRedisTemplate redisTemplate) {
        this.memberRepository = memberRepository;
        this.redisTemplate = redisTemplate;
    }

    private String getMemberKey(Long id) {
        return "member:" + id;
    }

    public Member getMember(Long id) {

        String key = getMemberKey(id);

        // 1. Redis에서 먼저 시도
        String cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            try {
                logger.info("캐싱 반환");
                return objectMapper.readValue(cached, Member.class);
            } catch (JsonProcessingException e) {
                // 파싱 실패시 무시하고 DB에서 조회
                e.printStackTrace();
            }
        }

        // 2. 없으면 DB에서 조회
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("member not found: " + id));

        // 3. Redis에 캐싱
        try {
            String toCache = objectMapper.writeValueAsString(member);
            redisTemplate.opsForValue().set(key, toCache, Duration.ofMinutes(1));
            logger.info("멤버 캐싱");
        } catch (JsonProcessingException e) {
            // 캐시 저장 실패해도 로직은 계속
        }

        return member;
    }

    public Member createMember(String name) {
        Member saved = memberRepository.save(new Member(name));
        try {
            String key = getMemberKey(saved.getId());
            String toCache = objectMapper.writeValueAsString(saved);
            redisTemplate.opsForValue().set(key, toCache, Duration.ofMinutes(1));
        } catch (JsonProcessingException e) {
        }
        return saved;
    }
}
