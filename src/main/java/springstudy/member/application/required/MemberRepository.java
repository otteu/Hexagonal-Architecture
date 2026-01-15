package springstudy.member.application.required;

import org.springframework.data.repository.Repository;

import springstudy.member.domain.Member;

public interface MemberRepository extends Repository<Member, Long>{
	Member save(Member member);
}
