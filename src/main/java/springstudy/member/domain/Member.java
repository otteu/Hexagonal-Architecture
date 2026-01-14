package springstudy.member.domain;

import java.util.Objects;

import lombok.Getter;
import springstudy.member.domain.MemberStatus;

@Getter
public class Member {

	private String email;
	private String nickname;
	private String passwordHash;
	private MemberStatus status; 
	
	
	public Member(String email, String nickname, String passwordHash)
	{
		this.email = Objects.requireNonNull(email);
		this.nickname = Objects.requireNonNull(nickname);
		this.passwordHash = Objects.requireNonNull(passwordHash);
		
		this.status = MemberStatus.PENDING;
	}


	public void activate() 
	{
		if(this.status != MemberStatus.PENDING) 
		    throw new IllegalStateException("PENDING 상태가 아닙니다.");
		
		this.status = MemberStatus.ACTIVE;
	}


	public void deactivate() 
	{
		if(this.status != MemberStatus.ACTIVE)
			throw new IllegalStateException("ACTIVE 상태가 아닙니다.");
		
		this.status = MemberStatus.DEACTIVATED;
	}
	
}
