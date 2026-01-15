package springstudy.member.application.required;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.persistence.EntityManager;
import springstudy.member.domain.Member;
import springstudy.member.domain.MemberRegisterRequest;
import springstudy.member.domain.MemberStatus;

import static springstudy.member.domain.MemberFixture.createPasswordEncoder;
import static springstudy.member.domain.MemberFixture.createMemberRegisterRequest;



@DataJpaTest
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	EntityManager entityManager;
	
	
	@Test
	void createMember()
	{
		Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
		
		Assertions.assertThat(member.getId()).isNull();
		
		memberRepository.save(member);
		
		Assertions.assertThat(member.getId()).isNotNull();
		
		entityManager.flush();
	}
	
	
	@Test
	void duplicateEmailFail()
	{
		Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
		memberRepository.save(member);
		
		Member member2 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
		Assertions.assertThatThrownBy(()->{
			memberRepository.save(member2);
		}).isInstanceOf(DataIntegrityViolationException.class);
	}
	
	
}
