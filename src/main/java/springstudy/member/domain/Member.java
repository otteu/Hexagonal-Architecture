package springstudy.member.domain;

import java.util.Objects;
import java.util.function.IntPredicate;

import lombok.Getter;
import springstudy.member.domain.MemberStatus;

@Getter
public class Member {

	private Email email;
	private String nickname;
	private String passwordHash;
	private MemberStatus status; 
	
	
	private Member() {
	}
	
	public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder)
	{
		var member = new Member();
		
		member.email = new Email(createRequest.email());
		member.nickname = Objects.requireNonNull(createRequest.nickname());
		member.passwordHash = Objects.requireNonNull(passwordEncoder.encode(createRequest.password()));
		
		member.status = MemberStatus.PENDING;
		
		return member;
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


	public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
		
		return passwordEncoder.matches(password, this.passwordHash);
	}


	public void changeNickname(String nickname) {
		
		this.nickname = Objects.requireNonNull(nickname);
	}


	public void changePassword(String password, PasswordEncoder passwordEncoder) {
		
		this.passwordHash = passwordEncoder.encode(Objects.requireNonNull(password));
	}


	public boolean isActive() {
		
		return this.status == MemberStatus.ACTIVE;
	}
	
}
