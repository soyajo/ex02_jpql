package soya;


import soya.domain.*;

import javax.persistence.*;
import java.util.List;


public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");


        EntityManager em = emf.createEntityManager();
        // 트렌젝션 생성
        EntityTransaction ts = em.getTransaction();
        // 트렌젝션 시작
        ts.begin();

        try {
            /**
             * jpql 문법
             * - select m from Member as m where m.age > 18
             * - 엔티티와 속성은 대소문자 구분 o (Member, age)
             * - jpql 키워드는 대소문자 구분 x (SELECT, FROM, where)
             * - 엔티티 이름 사용, 테이블 이름이 아님(Member)
             * - 별칭은 필수(m) (as는 생략가능)
             *
             * TypeQuery : 반환 타입이 명확할 때 사용
             * Query : 반환 타입이 명확하지 않을 때 사용
             *
             * query.getResultList(); : 결과가 하나 이상일 때, 리스트 반환
             * 결과가 없으면 빈 리스트 반환
             *
             * query.getSingleResult(); : 결과가 정확히 하나, 단일 객체 반환
             * 결과가 없거나 둘 이상이면 에러 반환
             */

//            Member member = new Member();
//            member.setUsername("member1");
//            member.setAge(10);
//            em.persist(member);
//
//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            List<Member> resultList = query.getResultList();
//
//            for (Member member1 : resultList) {
//                System.out.println("member1 = " + member1);
//            }
//
//            // 값이 없으면 에러, 여러개가 있어도 에러
////            Member singleResult = em.createQuery("select m from Member m where m.id = 10L", Member.class).getSingleResult();
////            System.out.println("singleResult = " + singleResult);
//
//            Member singleResultMember1 = em.createQuery("select m from Member m where m.username = :username", Member.class).setParameter("username", "member1").getSingleResult();
//            System.out.println("singleResultMember1 = " + singleResultMember1.getUsername());

            /**
             * 프로젝션
             * - select 절에 조회할 대상을 지정하는 것
             * - 프로젝션 대상 : 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자 등 기본 데이터 타입)
             * - select m from Member m -> 엔티티 프로젝션
             * - select m.team from Member m -> 엔티티 프로젝션
             * - select m.address from Member m -> 임베디드 프로젝션
             * - select m.username, m.age from Member m -> 스칼라 타입 프로젝션
             * - distinct 로 중복 제거
             *
             */
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setAge(10);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            // 조인 쿼리가 나감.
//            List<Team> teams = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();
//            System.out.println("teams = " + teams);
//
//            List<Address> addresses = em.createQuery("select o.address from Order o", Address.class).getResultList();
//            System.out.println("addresses = " + addresses);

            /**
             * 프로젝션 - 여러 값 조회 방법 3가지
             * select m.username ,m.age from Member m
             * 1. Query 타입으로 조회
             * 2. Object[] 타입으로 조회
             * 3. new 명령어로 조회
             *  - 단순 값을 DTO로 바로 조회
             *  - select new soya.domain.UserDTO(m.username, m.age) from Member m
             *  - 패키지 명을 포함한 전체 클래스 명 입력
             *  - 순서와 타입이 일치하는 생성자 필요
             *
             */

//            List<MemberDTO> resultList = em.createQuery("select new soya.domain.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
//            MemberDTO memberDTO = resultList.get(0);
//
//            System.out.println("memberDTO.getName() = " + memberDTO.getName());
//            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());

            /**
             * 페이징 API
             * - jpa는 페이징을 다음 두 api로 추상화
             * setFirstResult(int startPosition) : 조회 시작 위치
             * setMaxResults(int maxResult) : 조회할 데이터 수
             */

//            for (int i = 0; i < 100; i++) {
//                Member member = new Member();
//                member.setUsername("member"+ i);
//                member.setAge(i);
//                em.persist(member);
//            }
//
//            em.flush();
//            em.clear();
//
//            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            System.out.println("resultList.size() = " + resultList.size());
//            for (Member member1 : resultList) {
//                System.out.println("member1 = " + member1);
//            }

            /**
             * 조인
             *
             * 세타조인 - 연관관계가 없는 조인
             * ex) select count(m) from Member m , Team t where m.username = t.name
             *
             * on 절을 활용한 조인(jpa 2.1부터 지원)
             * 1. 조인 대상 필터링
             * 2. 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)
             *
             *
             */
            Team team = new Team("teamA");
            em.persist(team);

            Member member = new Member("member1", 10);
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 내부 조인
            String query1 = "select m from Member m inner join m.team t";
            // 외부 조인
            String query2 = "select m from Member m left join m.team t";
            // 세타 조인
            String query3 = "select m from Member m, Team t where m.username = t.name";
            // on절 - 조인 대상 필터링
            String query4 = "select m from Member m left join m.team t on t.name = 'A' ";
            // on절 - 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)
            String query5 = "select m from Member m, Team t on m.username = t.name";

            List<Member> resultList = em.createQuery(query3, Member.class)
                    .getResultList();

            ts.commit();

        } catch (Exception e) {
            e.printStackTrace();
            ts.rollback();
        }

        em.close();

        emf.close();

    }


}
