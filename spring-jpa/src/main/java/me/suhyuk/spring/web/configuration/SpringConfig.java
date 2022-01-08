package me.suhyuk.spring.web.configuration;

import lombok.AllArgsConstructor;
import me.suhyuk.spring.web.aop.TimeTraceAop;
import me.suhyuk.spring.web.repository.BarRepository;
import me.suhyuk.spring.web.repository.MemberRepository;
import me.suhyuk.spring.web.service.FooService;
import me.suhyuk.spring.web.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

/*
@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
}
*/

@Configuration
@AllArgsConstructor
public class SpringConfig {

//    private DataSource dataSource;
//    @PersistenceContext
//    private EntityManager em;

    private final MemberRepository memberRepository;

//    public SpringConfig(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    @Bean
    public FooService fooService() {
        return new FooService(barRepository());
    }

    @Bean
    public BarRepository barRepository() {
        return new BarRepository();
    }

//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository(memberService());
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
}