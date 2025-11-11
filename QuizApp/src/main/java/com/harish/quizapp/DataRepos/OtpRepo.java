package com.harish.quizapp.DataRepos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.OtpLogs;
import com.harish.quizapp.Model.UserRegistration;

public interface OtpRepo extends JpaRepository<OtpLogs,Integer>
{
	Optional<OtpLogs> findByOtpAndIsValidTrue(int otp);
	
	Optional<OtpLogs> findByUserAndIsVerifiedTrue(UserRegistration user);

}
