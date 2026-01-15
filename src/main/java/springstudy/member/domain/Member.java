package springstudy.member.domain;

import java.util.Objects;
import java.util.function.IntPredicate;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudy.member.domain.MemberStatus;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	@NaturalId
	private Email email;
	
	private String nickname;
	
	private String passwordHash;
	
	@Enumerated(EnumType.STRING)
	private MemberStatus status; 
	
	
	public static Member register(MemberRegisterRequest registerRequest, PasswordEncoder passwordEncoder)
	{
		var member = new Member();
		
		member.email = new Email(registerRequest.email());
		member.nickname = Objects.requireNonNull(registerRequest.nickname());
		member.passwordHash = Objects.requireNonNull(passwordEncoder.encode(registerRequest.password()));
		
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
