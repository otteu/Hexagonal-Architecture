package springstudy.member.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static springstudy.member.domain.MemberFixture.createPasswordEncoder;
import static springstudy.member.domain.MemberFixture.createMemberRegisterRequest;

class MemberTest {

    private Member member;
    private PasswordEncoder passwordEncoder;
	
	@BeforeEach
	void setUp()
	{
		passwordEncoder = createPasswordEncoder();
		member = Member.register(createMemberRegisterRequest(), passwordEncoder);
	}
	
	
	
	@Test
	void registerMember() 
	{		
		Assertions.assertThat(member.getStatus().equals(MemberStatus.PENDING));
	}
	
	@Test
	void registerMemberNotnull()
	{
		Assertions.assertThatThrownBy(()->{
			Member.register(new MemberRegisterRequest("otteu@gmail.com", "otteu", "passwordHash"), null);			
		}).isInstanceOf(NullPointerException.class);
	}

	
	@Test
	void activate() 
	{
		member.activate();
		
		Assertions.assertThat(member.getStatus().equals(MemberStatus.ACTIVE));
	}
	
	@Test
	void activateFail()
	{
		member.activate();
		
		Assertions.assertThatThrownBy(() -> {
			member.activate();
		}).isInstanceOf(IllegalStateException.class);
	}
	
	@Test
	void deactivate()
	{
		member.activate();
	
		member.deactivate();
		
		Assertions.assertThat(member.getStatus().equals(MemberStatus.DEACTIVATED));
	
	}
	
	@Test
	void deactivateFail()
	{
		Assertions.assertThatThrownBy(() -> {
			member.deactivate();
		}).isInstanceOf(IllegalStateException.class);
	}
	
	@Test
	void verifyPassword()
	{
		Assertions.assertThat(member.verifyPassword("passwordHash", passwordEncoder)).isTrue();
	}
	
	@Test
	void changeNickname()
	{	
		Assertions.assertThat(member.getNickname()).isEqualTo("otteu");
		
		member.changeNickname("순둥이");
		
		Assertions.assertThat(member.getNickname()).isEqualTo("순둥이");		
	}
	
	@Test
	void changePassword()
	{	
		member.changePassword("change password", passwordEncoder);
		
		Assertions.assertThat(member.verifyPassword("change password", passwordEncoder)).isTrue();
	}
	
	@Test
	void isActive()
	{
		Assertions.assertThat(member.isActive()).isFalse();
		
		member.activate();
		
		Assertions.assertThat(member.isActive()).isTrue();
		
		member.deactivate();
		
		Assertions.assertThat(member.isActive()).isFalse();
	}
	
	@Test
	void invalidEmail()
	{
		Assertions.assertThatThrownBy(() -> {
			Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder);
		}).isInstanceOf(IllegalStateException.class);
		
		Member.register(createMemberRegisterRequest(), passwordEncoder);
	}
	
}
