package springstudy.member.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

	private String nickname;
    private String email;
    private String passwordHash;
    private Member member;
	
	@BeforeEach
	void setUp()
	{
		String nickname = "김민성";
		String email = "otteu@gmail.com";
		String passwordHash = "passwordHash";
	
		member = new Member(email, nickname, passwordHash);
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
			new Member(null, email, passwordHash);			
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
	
}
