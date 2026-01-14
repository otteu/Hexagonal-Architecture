package springstudy.member.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MemberTest {

    private Member member;
    private PasswordEncoder passwordEncoder;
	
	@BeforeEach
	void setUp()
	{
		passwordEncoder = new PasswordEncoder() {
			@Override
			public String encode(String password)
			{
				return password.toUpperCase();
			}
			
			@Override
			public boolean matches(String password, String passwordHash)
			{
				return encode(password).equals(passwordHash);
			}
		};
	
		member = Member.create(new MemberCreateRequest("otteu@gmail.com", "otteu", "passwordHash"), passwordEncoder);
	}
	
	@Test
	void createMember() 
	{		
		Assertions.assertThat(member.getStatus().equals(MemberStatus.PENDING));
	}
	
	@Test
	void createMemberNotnull()
	{
		Assertions.assertThatThrownBy(()->{
			Member.create(new MemberCreateRequest("otteu@gmail.com", "otteu", "passwordHash"), null);			
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
			Member.create(new MemberCreateRequest("invalid email", "otteu", "passwordHash"), passwordEncoder);
		}).isInstanceOf(IllegalStateException.class);
		
		Member.create(new MemberCreateRequest("otteu@gmail.com", "otteu", "passwordHash"), passwordEncoder);
	}
	
}
