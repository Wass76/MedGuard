package com.CareemSystem;

import com.CareemSystem.policy.Policy;
import com.CareemSystem.policy.PolicyRepository;
import com.CareemSystem.wallet.Model.MoneyCode;
import com.CareemSystem.wallet.Repository.MoneyCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CareemSystemApplication implements CommandLineRunner {

	@Autowired
	private PolicyRepository policyRepository;
	@Autowired
	private MoneyCodeRepository moneyCodeRepository;

	public static void main(String[] args) {
		SpringApplication.run(CareemSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		policyRepository.deleteAll();
		List<Policy> policies = policyRepository.findAll();
		if (policies.isEmpty()) {
			Policy policy = Policy.builder()
					.title("Privacy Policy for Ride share")
					.description("At Rideshare, accessible from rideshare.com, one of our main priorities is the privacy of our visitors." +
							"This Privacy Policy document contains types of information that is collected and recorded by rideshare and how we use it" +
							"If you have additional questions or require more information about our Privacy Policy, do not hesitate to contact us." +
							"This Privacy Policy applies only to our online activities and is valid for visitors to our website with regards to the information that they shared and/or collect in rideshare." +
							" This policy is not applicable to any information collected offline or via channels other than this website." +
							" Our Privacy Policy was created with the help of the Free Privacy Policy Generator.")
					.build();
			policyRepository.save(policy);
		}
		MoneyCode moneyCode= MoneyCode.builder()
				.code("Wassem" + new Random().nextInt(10000))
				.balance(200000.0)
				.valid(true)
				.build();
		MoneyCode moneyCode2= MoneyCode.builder()
				.code("Abd-alaziz" + new Random().nextInt(10000))
				.balance(50000.0)
				.valid(true)
				.build();

		moneyCodeRepository.save(moneyCode);
		moneyCodeRepository.save(moneyCode2);
	}
}

