package me.suhyuk.spring.foo;

import me.suhyuk.spring.foo.domain.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * JPA Buddy 활용한 Entity 생성
 *
 * 1. 반드시 JPA Buddy 통해서만 Entity 생성을 합니다
 * 2.
 */
@SpringBootApplication
public class SpringFooApplication {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpademo");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 0. ManyToOne Fetch.Lazy 테스트
            /**
            User user0 = User.builder().name("user0").build();
            Team team0 = Team.builder().name("team0").build();
            user0.joinTeam(team0);
            em.persist(user0);
            em.persist(team0);

            User createdUser = em.find(User.class, 1L);
            System.out.println("createdUser = " + createdUser);

            System.out.println("##############BEFORE################");
            User foundUser = em.find(User.class, 1L);
            System.out.println("##############AFTER1################");
            System.out.println("foundUser = " + foundUser);
            System.out.println("##############AFTER2################");
            Team foundTeam = em.find(Team.class, 1L);
            System.out.println("##############AFTER3################");
            System.out.println("foundTeam = " + foundTeam);
            System.out.println("##############THEEND################");
             */

            // 1. 그냥 커넥터 객체 생성
            Connector connector = Connector.builder().createdBy("psyoblade").createdTime(LocalDateTime.now()).build();
            em.persist(connector);

            // 2. 상속만 받은 경우는 "단일테이블전략"으로 저장되며, @SuperBuilder 활용해서 @Builder 상속이 가능하다
            KafkaConnector kafkaConnector = KafkaConnector.builder().createdBy("kiki").createdTime(LocalDateTime.now()).broker("localhost").build();
            em.persist(kafkaConnector);

            // 3. @MappedSuperClass 사용한 "조인전략" 수행합니다
            KafkaBaseConnector kbc1 = KafkaBaseConnector.builder()
                    .createdBy("psyoblade")
                    .createdTime(LocalDateTime.now())
                    .build();
            em.persist(kbc1);
            em.clear();

            // 4. 콜렉션 필드는 @ElementCollection 활용합니다
            KafkaBaseConnector kbc2 = KafkaBaseConnector.builder()
                    .createdBy("kiki")
                    .createdTime(LocalDateTime.now())
                    .brokers(Arrays.asList("localhost:8080"))
                    .build();
            em.persist(kbc2);

            // 5. 1:1 관계 생성 - 이용자 상세정보를 입력, 반드시 명시적인 레퍼런스 추가 함수를 생성해두기로 합니다
            UserDetail userDetail = UserDetail.builder().name("DIT").build();
            User user = User.builder().name("psyoblade").build();
            user.addUserDetail(userDetail);
            em.persist(userDetail);
            em.persist(user);
            em.clear();

            // 6. 1:N 관계 생성 - 이용자가 커넥터를 추가하며, 반드시 양방향의 레퍼런스를 오너객체에서 관리합니다
            UserDetail userDetail1 = UserDetail.builder().name("developer").build();
            User user1 = User.builder().name("user1").build();
            user1.addUserDetail(userDetail1);
            KafkaBaseConnector connector1 = KafkaBaseConnector.builder()
                .createdBy("psyoblade")
                .createdTime(LocalDateTime.now())
                .brokers(Arrays.asList("localhost:1234"))
                .build();
            user1.createKafkaConnector(connector1);
            em.persist(userDetail1);
            em.persist(connector1);
            em.persist(user1);

            // 7. N:1 관계 생성 - 단방향: 이용자(N)가 팀(1)에 합류하다
            User user2 = User.builder().name("user2").build();
            User user3 = User.builder().name("user3").build();
            User user4 = User.builder().name("user4").build();
            Team team1 = Team.builder().name("DataIngestionTeam").build();
            Team team2 = Team.builder().name("DataServiceTeam").build();
            user2.joinTeam(team1);
            user3.joinTeam(team2);
            user4.joinTeam(team2);
            em.persist(user2);
            em.persist(user3);
            em.persist(user4);
            em.persist(team1);
            em.persist(team2);

            // 8. 1:N 관계 생성 - 양방향: 팀(1)에서 사람(N)을 채용하다
            Team team3 = Team.builder().name("DataClusterTeam").build();
            User user5 = User.builder().name("user5").build();
            User user6 = User.builder().name("user6").build();
            User user7 = User.builder().name("user7").build();
            team3.hireUsers(Arrays.asList(user5, user6, user7));
            em.persist(user5);
            em.persist(user6);
            em.persist(user7);
            em.persist(team3);

            // 9. N:M 관계 생성 - N:M 대신 N:1 + 1:N 방식으로 대체 - 하나의 팀은 여러 역할을 가질 수 있고, 하나의 역할에 여러 팀이 할당될 수 있다
            Tag dev = Tag.builder().name("개발").build();
            Tag op = Tag.builder().name("운영").build();
            em.persist(dev);
            em.persist(op);

            TeamTag devTag = new TeamTag();
            devTag.setTag(dev);
            TeamTag opTag = new TeamTag();
            opTag.setTag(op);
            em.persist(devTag);
            em.persist(opTag);

            Team devTeam = Team.builder().name("개발").build();
            Team opTeam = Team.builder().name("지원").build();
            Team devOpTeam = Team.builder().name("개발-운영").build();
            devTeam.setTeamTag(Arrays.asList(devTag));
            opTeam.setTeamTag(Arrays.asList(opTag));
            devOpTeam.setTeamTag(Arrays.asList(devTag, opTag));
            em.persist(devTeam);
            em.persist(opTeam);
            em.persist(devOpTeam);

            Team found = em.find(Team.class, 6L);
            System.out.println("found = " + found);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            emf.close();
        }
    }
}
