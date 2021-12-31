package me.suhyuk.springjpa;

import me.suhyuk.springjpa.domain.Member;
import me.suhyuk.springjpa.strategy.IdentityType;
import me.suhyuk.springjpa.strategy.SequenceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import me.suhyuk.springjpa.strategy.TableType;

import javax.persistence.*;

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

			/** // -- 6. 엔티티 변경 감지 (Dirty Checking)
			Member member = em.find(Member.class, 0L);
			Assert.isTrue(member.getName().equalsIgnoreCase("마지막의수혁"), "오류1");
			System.out.println(member.getName());

			member.setName("처음의수혁");
			em.persist(member);
			em.flush();
			Assert.isTrue(member.getName().equalsIgnoreCase("처음의수혁"), "오류2");

			member.setName("마지막의수혁");
			tx.commit();
			Assert.isTrue(member.getName().equalsIgnoreCase("마지막의수혁"), "오류3");

			System.out.println("성공");
			 */

			/** // -- 7. Entity 테이블 생성
			Member member = Member.builder()
					.id(0L).userName("박수혁").roleType(Member.RoleType.ADMIN).build();
			em.persist(member);
			 */

			/** // -- 8. Column 맵핑
			Account account = new Account();
			account.setName("박수혁");
			em.persist(account);
			 */

			/** // -- 9. 타입 별 동작방식 디버깅
			IdentityType identity = new IdentityType("아이덴티티");
			em.persist(identity); // 데이터베이스와 무관하게 id 값이 생성됨 컨텍스트 내에서의 값으로 반영됨
			System.out.println(identity);

			TableType table = new TableType("테이블");
			em.persist(table); // 내가 유지하는 시퀀스 테이블에 직접 조회해야만 id 값을 알 수 있어 무조건 조회가 일어나며 심지어 실제 값도 추가되어 외부 조회 시에도 값이 변경되어 있음
			System.out.println(table);

			SequenceType sequence = new SequenceType("시퀀스");
			em.persist(sequence); // 마찬가지로 데이터베이스 시퀀스를 통해 조회가 되어야 id 값 생성이 가능하여 조회가 발생하지만 시퀀스가 컨텍스트 내에서만 증가함
			System.out.println(sequence);
			 */

			/** // -- 10. 테이블 시퀀스가 롤백이 되어도 SEQ_TABLE 값은 롤백되지 않음
			TableType table = new TableType("테이블");
			em.persist(table);
			System.out.println(table);
			if (table.getName().equals("테이블"))
				 throw new RuntimeException("오류 발생");
			 */
			em.find(Member.class, 0L);

			// -- 11. 예제 실습

			// -- flush entity manager
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
		} finally {
			emf.close();
		}
	}

}
