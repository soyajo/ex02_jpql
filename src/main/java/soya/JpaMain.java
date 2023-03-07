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

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            List<Member> resultList = query.getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

            // 값이 없으면 에러, 여러개가 있어도 에러
//            Member singleResult = em.createQuery("select m from Member m where m.id = 10L", Member.class).getSingleResult();
//            System.out.println("singleResult = " + singleResult);

            Member singleResultMember1 = em.createQuery("select m from Member m where m.username = :username", Member.class).setParameter("username", "member1").getSingleResult();
            System.out.println("singleResultMember1 = " + singleResultMember1.getUsername());


            ts.commit();

        } catch (Exception e) {
            e.printStackTrace();
            ts.rollback();
        }

        em.close();

        emf.close();

    }


}
