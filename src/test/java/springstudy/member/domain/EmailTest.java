package springstudy.member.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

	@Test
	void equality() {
		var email1 = new Email("otteu@gmail.com");
		var email2 = new Email("otteu@gmail.com");
		
		
		Assertions.assertThat(email1).isEqualTo(email2);
	}

}
