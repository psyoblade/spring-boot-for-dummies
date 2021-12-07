package me.suhyuk.springjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;
import java.util.List;

@SpringBootApplication
public class SpringJpaApplication {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpashop");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {

			/** // -- 1. 조회 테스트
			Member member = em.find(Member.class, 1L);
			System.out.println("member.getName() = " + member.getName());
			member.setName("김영미");
			 */

			/** // -- 2. JPQL 테스트
			List<Member> members = em.createQuery("select m from Member as m", Member.class)
					.setFirstResult(0)
					.setMaxResults(10)
					.getResultList();
			for (Member member : members) {
				System.out.println(".member.getName() = " + member.getName());
			}
			 */

			/** // -- 3. 영속성 테스트
			Member member = new Member();
			member.setId(0L);
			member.setName("박수혁"); // 비영속 상태
			em.persist(member); // 영속 상태
			System.out.println("member = " + member);

			System.out.println("After persist");
			Member foundMember = em.find(Member.class, 0L);
			System.out.println("foundMember = " + foundMember);
			// 여기까지는 1차 캐시의 결과를 이용하여 출력되므로, 쿼리 수행이 이루어지지 않는다
			System.out.println(member == foundMember);
			 */

			/** // -- 4. 영속 엔티티의 동일성 보장
			Member first = em.find(Member.class, 0L);
			Member second = em.find(Member.class, 0L);
			System.out.println("first == second : " + (first == second));
			 */

			/** // -- 5. 영속성 컨텍스트 "쓰기 지연 SQL 저장소"
			Member a = Member.builder().id(3L).name("박소원").build();
			em.persist(a);
			System.out.println("persist a = " + a);
			Member b = Member.builder().id(4L).name("박시훈").build();
			em.persist(b);
			System.out.println("persist b = " + b);
			 */

			// -- 6. 엔티티 변경 감지 (Dirty Checking)
			Member member = em.find(Member.class, 0L);
			member.setName("마지막의수혁");
			member.setName("시작의수혁");

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			emf.close();
		}
	}

}
