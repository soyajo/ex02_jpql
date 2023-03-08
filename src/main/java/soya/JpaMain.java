package soya;


import soya.domain.*;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.Collection;
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
//            Team team = new Team("teamA");
//            em.persist(team);
//
//            Member member = new Member("member1", 10);
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            // 내부 조인
//            String query1 = "select m from Member m inner join m.team t";
//            // 외부 조인
//            String query2 = "select m from Member m left join m.team t";
//            // 세타 조인
//            String query3 = "select m from Member m, Team t where m.username = t.name";
//            // on절 - 조인 대상 필터링
//            String query4 = "select m from Member m left join m.team t on t.name = 'A' ";
//            // on절 - 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)
//            String query5 = "select m from Member m join Team t on m.username = t.name";
//
//            List<Member> resultList = em.createQuery(query5, Member.class)
//                    .getResultList();
            /**
             * 서브쿼리
             *
             * 지원함수
             * - [NOT] EXISTS : 서브쿼리에 결과가 존재하면 참
             * - ALL 모두 만족하면 참
             * - ANY, SOME : 같은 의미, 조건을 하나라도 만족하면 참
             * - [NOT] IN : 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참
             *
             * 팀A 소속인 회원
             * select m from Member m
             * where exists (select t from m.team t where t.name = '팀A')
             *
             * 전체 상품 가각의 재고보다 주문량이 많은 주문들
             * select o from order o
             * where o.orderAmount > ALL (select p.stockAmount from Product p)
             *
             * 어떤 팀이든 팀에 소속된 회원
             * select m from Member m
             * where m.team = ANY (select t from Team t)
             *
             * jpa 서브 쿼리 한계
             * - jpa는 where, having 절에서만 서브 쿼리 가능
             * - select 절도 가능(하이버네이트에서 지원)
             * - from 절의 서브 쿼리는 현재 jpql에서 불가능!! (중요)
             * - 조인으로 풀 수 있으면 풀어서 해결
             *
             */

//            Team team = new Team("teamA");
//            em.persist(team);
//
//            Member member = new Member("member1", 10);
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            // select 절에 서브 쿼리
//            String query1 = "select (select avg(m1.age) from Member m1) as avgAge from Member m join Team t on m.username = t.name";
//
//            List<Member> resultList = em.createQuery(query1, Member.class)
//                    .getResultList();

            /**
             * jpql 타입 표현
             * - 문자 : 'hello', 'she'
             * - 숫자 : 10L(Long), 10D(Double), 10F(Float)
             * - Boolean : TRUE, FALSE
             * - ENUM : jpabook.MemberType.Admin(패키지명 포함) - setParameter()로 처리하면 됨.
             * - 엔티티 타입 : TYPE(m) = Member (상속 관계에서 사용)
             */

//            Team team = new Team("teamA");
//            em.persist(team);
//
//            Member member = new Member("member1", 10);
//            member.setType(MemberType.ADMIN);
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            // select 절에 서브 쿼리
//            String query1 = "select m.username, 'Hello', TRUE from Member m where m.type = :userType";
//
//            List<Object[]> resultList = em.createQuery(query1)
//                    .setParameter("userType", MemberType.ADMIN)
//                    .getResultList();
//            for (Object[] objects : resultList) {
//                System.out.println("objects[0] = " + objects[0]);
//                System.out.println("objects[1] = " + objects[1]);
//                System.out.println("objects[2] = " + objects[2]);
//            }

            /**
             * 조건식 - case 식
             *
             */
//            Team team = new Team("teamA");
//            em.persist(team);
//
//            Member member = new Member("관리자", 10);
//            member.setType(MemberType.ADMIN);
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            String query1 = "select  " +
//                                "case when m.age <= 10 then '학생요금'" +
//                                "     when m.age >= 60 then '경로요금'" +
//                                "     else '학생요금' " +
//                                "end " +
//                            "from Member m ";
//            // 이름이 널이면 이름 없는 회원 반환
//            String query2 = "select coalesce(m.username, '이름 없는 회원') as username from Member m";
//            // 관리자면 null 을 반환
//            String query3 = "select nullif(m.username, '관리자') as username from Member m";
//
//            List<String> resultList = em.createQuery(query3, String.class).getResultList();
//            for (String s : resultList) {
//                System.out.println("s = " + s);
//            }

            /**
             * jpql 기본 함수
             * - concat
             * - substring
             * - trim
             * - lower, upper
             * - length
             * - locate
             * - abs, sqrt,mod
             * - size, index(jpa 용도)
             *
             * 사용자 정의 함수
             * - 하이버네이트는 사용전 방언에 추가해야 한다.
             * - 사용하는 db방언을 상속받고, 사용자 정의 함수를 등록한다.
             * select function('group_concat', i.name) from Item i
             */
//            Team team = new Team("teamA");
//            em.persist(team);
//
//            Member member = new Member("관리자1", 10);
//            member.setType(MemberType.ADMIN);
//            member.setTeam(team);
//            em.persist(member);
//
//            Member member1 = new Member("관리자2", 10);
//            member1.setType(MemberType.ADMIN);
//            member1.setTeam(team);
//            em.persist(member1);
//
//            em.flush();
//            em.clear();
//
//            String query1 = "select concat('a' ,'b') as username from Member m";
//            // 문자길이수
//            String query2 = "select locate('de' ,'abcdeqf') from Member m";
//            // 사용자 정의 함수
//            // dialect 파일 생성 후 설정 -> persistence.xml 파일 수정
//            String query3 = "select function('group_concat', m.username) from Member m";
//
//            List<String> resultList = em.createQuery(query3, String.class).getResultList();
//            for (String s : resultList) {
//                System.out.println("s = " + s);
//            }

            /**
             * 경로 표현식 용어 정리
             *
             * 상태 필드 : 단순히 값을 저장하기 위한 필드
             * ex) m.username
             *
             * 연관 필드 : 연관관계를 위한 필드
             * - 단일 값 연관 필드 :
             * @ManyToOne, @OneToOne, 대상이 엔티티(ex : m.team)
             * - 컬렉션 값 연관 필드 :
             * @OneToMany, @ManyToMany, 대상이 컬렉션(ex : m.orders)
             *
             * 경로 표현식 특징
             * - 상태 필드 : 경로 탐색의 끝, 탐색 x
             * - 단일 값 연관 경로 : 묵시적 내부 조인(inner join) 발생, 탐색 o
             * - 컬렉션 값 연관 경로 : 묵시적 내부 조인 발생, 탐색 x
             *   - FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
             *
             * 경로 탐색을 사용한 묵시적 조인 시 주의사항
             * - 항상 내부 조인
             * - 컬렉션은 경로 탐색의 끝, 명시적 조인을 통해 별칭을 얻어야함
             * - 경로 탐색은 주로 select, where 절에서 사용하지만 묵시적 조인으로 인해 sql의 from (join) 절에 영향을 줌
             *
             * 실무 조언
             * - 명시적 조인을 무조건 사용해야함 !!! (중요)
             * - 묵시적인 내부조인이 발생하지 않도록 query 를 짜야함.
             * - 조인은 sql 튜닝에 중요 포인트
             *
             */
//            Team team = new Team();
//            team.setName("team1");
//            em.persist(team);
//
//            Member member1 = new Member();
//            member1.setUsername("관리자1");
//            member1.setTeam(team);
//            em.persist(member1);
//            Member member2 = new Member();
//            member2.setUsername("관리자2");
//            member2.setTeam(team);
//            em.persist(member2);
//
//            em.flush();
//            em.clear();
//            // 상태 필드
//            String query1 = "select m.username from Member m";
//            // 단일 값 연관 경로 - 묵시적인 내부 조인 발생
//            String query2 = "select m.team from Member m";
//            // 컬렉션 값 연관 경로 - 묵시적인 내부 조인 발생, 경로 탐색 x
//            String query3 = "select t.members from Team t";
//            // 명시적 조인 - 경로 탐색 o
//            String query4 = "select m.username from Team t join t.members m";
//
//            List<String> resultList = em.createQuery(query4, String.class).getResultList();
//            System.out.println("resultList = " + resultList);

            /**
             * 페치 조인
             * - sql 조인 종류 x
             * - jpql에서 성능 최적화를 위해 제공하는 기능
             * - 연관된 엔티티나 컬렉션을 sql 한 번에 함께 조회하는 기능
             * - join fetch 명령어 사용
             * - 페치 조인 ::= [ left[outer | inner] join fetch 조인경로
             */
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);
            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            /**
             * entity fetch join
             *
             */

//            // fetch join 사용 전 - sql 세번 연결
//            String query1 = "select m from Member m";
//            // 회원1, 팀A (sql)
//            // 회원2, 팀A (1차캐시)
//            // 회원3, 팀B (sql)
//            // 회원 100명 -> n + 1 발생
//
//            // fetch join 사용 후 - sql 한번에 끝
//            String query2 = "select m from Member m join fetch m.team";
//            List<Member> result = em.createQuery(query2, Member.class).getResultList();
//
//            for (Member member : result) {
//                System.out.println("member = " + member);
//                System.out.println("member.getTeam() = " + member.getTeam());
//            }


            /**
             * collection fetch join
             *
             * 페치 조인과 distinct
             * - sql의 distinct 는 중복된 결과를 제거하는 명령
             * - jpql의 distinct 2가지 기능 제공
             *  1. sql 에 distinct 를 추가
             *  2. 애플리케이션에서 엔티티 중복 제거
             *
             * 페치 조인의 특징과 한계
             * - 페치 조인 대상에는 별칭을 줄 수 없다.
             *  - 아이버네이트는 가능, 가급적 사용 x
             * - 둘 이상의 컬렉션은 페치 조인 할 수 없다. -> 데이터 펑튀기
             * - 컬렉션을 페치 조인하면 페이징 api를 사용할 수 없다.
             *  - 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능
             *  - 하이버네이트는 경고 로그를 남기고 메모리에서 페이징(매우 위험)
              */

//            // fetch join 사용 전 -> sql 세번 연결
//            String query3 = "select t from Team t";
//            // fetch join 사용 후 -> sql 한번에 끝 -> 중복되는 entity 존재
//            String query4 = "select t from Team t join fetch t.members ";
//            // fetch join + distinct -> entity 중복 제거
//            String query5 = "select distinct t from Team t join fetch t.members ";
//            // 페치 조인 대상에는 별칭을 줄 수 없다. -> 주면 복잡해지는 문제가 있음. - 데이터의 적합서 이슈
//            String query6 = "select distinct t from Team t join fetch t.members";
//            // 페이징 처리를 하고 싶으면 다대일로 바궈야함.
//            String query7 = "select m from Member m join fetch m.team";
//            // @BatchSize() or persistence.xml에서 <property name="hibernate.default_batch_fetch_size" value="100"/> 를 작성하면 n+1 문제 해결함.
//            // 왠만하면 persistence.xml에 설정해야함.
//            String query8 = "select t from Team t";
//
//            List<Team> resultList = em.createQuery(query8, Team.class)
//                    .getResultList();
//
//            for (Team team : resultList) {
//                System.out.println("team = " + team);
//                for (Member member : team.getMembers()) {
//                    System.out.println("member = " + member);
//                }
//            }
//            List<Member> members = em.createQuery(query7, Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();
//            for (Member member : members) {
//                System.out.println("member = " + member);
//            }

            /**
             * 다형성 쿼리
             * type
             * - 조회 대상을 특정 자식으로 한정
             * ex) item 중에 Book, Movie를 조회해라
             *
             * select i from Item i where type(i) in (Book, Movie);
             *
             * treat
             * ex) 부모인 item 과 Book이 있다.
             *
             * select i from Item i where treat(i as Book).auther = 'kim';
             */

            /**
             * 엔티티 직접 사용 - 기본 키 값
             *
             * jpql에서 엔티티를 직접 사용하면 sql에서 해당 엔티티의 기본 키 값을 사용
             *
             * select count(m.id) from Member m // 엔티티의 아이디를 사용
             * select count(m) from Member m // 엔티티를 직접 사용
             *
             */

            /**
             * named 쿼리 - 정적 쿼리
             * - 미리 정의해서 이름을 부여해두고 사용하는 jpql
             * - 정적 쿼리
             * - 어노테이션, xml에 정의
             * - 애플리케이션 로딩 시점에 초기화 후 재사용
             * - 애플리케이션 로딩 시점에 쿼리를 검증 (중요 !!!)
             *
             */
//            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
//                    .setParameter("username", "회원1")
//                    .getResultList();
//            for (Member member : resultList) {
//                System.out.println("member = " + member);
//            }

            /**
             * 벌크 연산
             * - 재고가 10개 미만인 모든 상품의 가격을 10% 상승하려면?
             * - jpa 변경 감지 기능으로 실행하려면 너무 많은 sql실행
             *  1. 재고가 10개 미만인 상품을 리스트로 조회한다.
             *  2. 상품 엔티티의 가격을 10% 증가한다.
             *  3. 트랜잭션 커밋 시점에 변경감지가 동작한다.
             * - 변경된 데이터가 100건이라면 100번의 update sql 실행
             */

            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            System.out.println("resultCount = " + resultCount);

            ts.commit();

        } catch (Exception e) {
            e.printStackTrace();
            ts.rollback();
        }


        em.close();

        emf.close();

    }


}
