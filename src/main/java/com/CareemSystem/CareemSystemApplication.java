package com.CareemSystem;

import com.CareemSystem.object.Enum.BicycleCategory;
import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.object.Model.ModelPrice;
import com.CareemSystem.object.Repository.BicycleRepository;
import com.CareemSystem.policy.Policy;
import com.CareemSystem.policy.PolicyRepository;
import com.CareemSystem.wallet.Model.MoneyCode;
import com.CareemSystem.wallet.Repository.MoneyCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    private BicycleRepository bicycleRepository;
	@Autowired
	private ServerProperties serverProperties;

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


//		if(bicycleRepository.findAll().isEmpty()){
//			Bicycle bicycle1 = Bicycle.builder()
//					.model_price(ModelPrice.builder()
//							.model("SASR23")
//							.price(342.0).build())
//					.size(17)
//					.hasOffer(false)
//					.type(BicycleCategory.Road_bikes)
////					.photoPath("upload-dir/-5893485657953777788_120.jpg")
//					.build();
//			Bicycle bicycle2 = Bicycle.builder()
//					.model_price(ModelPrice.builder()
//							.model("KASAR43")
//							.price(1000.0).build())
//					.size(18)
//					.hasOffer(false)
//					.type(BicycleCategory.Road_bikes)
////					.photoPath("upload-dir/DSC01930.JPG")
//					.build();
//			Bicycle bicycle3 = Bicycle.builder()
//					.model_price(ModelPrice.builder()
//							.model("XMLO88")
//							.price(1500.0).build())
//					.size(17)
//					.hasOffer(false)
//					.type(BicycleCategory.Road_bikes)
////					.photoPath("upload-dir/FB_IMG_1625816069245.jpg")
//					.build();
//
//			String  server = serverProperties.getAddress().toString();
//			String port = serverProperties.getPort().toString();
////			System.out.println(port + " " + server);
//
//			if (Objects.equals(server, "localhost/127.0.0.1") && Objects.equals(port,"3010")){
//				bicycle1.setPhotoPath("D:/BusinessProjects/SpringProjects/Careem-System/upload-dir/-5893485657953777788_120.jpg");
//				bicycle2.setPhotoPath("D:/BusinessProjects/SpringProjects/Careem-System/upload-dir/DSC01930.JPG");
//				bicycle3.setPhotoPath("D:/BusinessProjects/SpringProjects/Careem-System/upload-dir/FB_IMG_1625816069245.jpg");
//				System.out.println(server + "/"+port);
//			}
//			bicycleRepository.save(bicycle1);
//			bicycleRepository.save(bicycle2);
//			bicycleRepository.save(bicycle3);
//		}
	}
}

