package com.harish.quizapp.DataRepos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.EmailVerificationToken;

public interface EmailTokenRepo extends JpaRepository<EmailVerificationToken, Integer>
{
	Optional<EmailVerificationToken> findByOtpAndIsValidTrue(int otp);
	
	Optional<EmailVerificationToken> findByEmailAndIsVerifiedTrue(String email);
}
