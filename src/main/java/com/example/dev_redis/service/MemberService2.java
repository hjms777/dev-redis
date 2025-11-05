package com.example.dev_redis.service;

import com.example.dev_redis.entity.member.Member;
import com.example.dev_redis.entity.member.MemberRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MemberService2 {

    private final MemberRepository memberRepository;
    Logger logger = Logger.getLogger(MemberService2.class.getName());

    public MemberService2(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Cacheable(cacheNames = "members", key = "#id")
    public Member getMember(Long id) {
        // 캐시에 없을 때만 이 코드가 실행되고,
        // 실행 결과가 자동으로 Redis에 들어간다.
        logger.info("DB에서 멤버 조회: " + id);
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("member not found: " + id));
    }

    // 생성/수정할 때 캐시도 같이 갱신
    @CachePut(cacheNames = "members", key = "#result.id")
    public Member createMember(String name) {
        Member saved = memberRepository.save(new Member(name));
        return saved;
    }

    @CacheEvict(cacheNames = "members", key = "#id")
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
    /*우리가 직접 redisTemplate.opsForValue().set(...) 안 써도 됨
    key는 members::<id>같은 형태로 Redis에 박힘 (스프링이 정해준 규칙으로)
    DB에서 한 번만 읽고 그 다음부터는 Redis에서 빠르게 꺼내옴
    @CacheEvict로 DB랑 캐시를 같이 맞춰줄 수 있음
    공식 Spring Data Redis 문서에서도 “Spring 캐시 추상화 위에 RedisCacheManager를 얹어라”는 식으로 되어 있어서 이게 스프링스러운 방식*/
}
