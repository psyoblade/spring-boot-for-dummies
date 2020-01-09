package me.suhyuk.springcore.services;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FooBarArguements implements CommandLineRunner {

    // TODO 빈에 생성자가 1개이고, 생성자의 파라매터가 빈인 경우 스프링이 알아서 주입해 준다
    public FooBarArguements(ApplicationArguments args) {
        boolean foo = args.containsOption("foo"); // -Dfoo 옵션은 JVM 에서만 인지
        boolean bar = args.containsOption("bar"); // --bar 즉 프로그램 아규먼트만 인지
        System.out.println(foo + "," + bar);
//        assert(!args.containsOption("foo"));
//        assert(args.containsOption("bar"));
    }

    // TODO 자바 프로그램 내에서는 어떻게든 Program Arguments 만 받아낼 수 있다
    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(args).forEach(System.out::println);
    }
}
