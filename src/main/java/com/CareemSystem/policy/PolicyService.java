package com.CareemSystem.policy;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.Validator.ObjectsValidator;
import com.CareemSystem.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private ObjectsValidator<PolicyRequest> objectsValidator;

    public ApiResponseClass getAllPolicies(){
        List<Policy> policies = policyRepository.findAll();
        return  new ApiResponseClass("Get all policies" , HttpStatus.OK, LocalDateTime.now() , policies);
    }

    public ApiResponseClass getPolicy(Integer policyId){

        Policy policy = policyRepository.findById(policyId).orElseThrow(
                ()-> new ApiRequestException("There is no item with id: " + policyId)
        );
        return new ApiResponseClass("Get policy" , HttpStatus.OK, LocalDateTime.now() , policy);
    }
    public ApiResponseClass createPolicy(PolicyRequest request){
        objectsValidator.validate(request);

        Policy policy = Policy.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
        policyRepository.save(policy);

        return new ApiResponseClass("Create policy" , HttpStatus.CREATED, LocalDateTime.now() , policy);
    }

    public ApiResponseClass updatePolicy(Integer policyId, PolicyRequest request){
        objectsValidator.validate(request);
        Policy policy = policyRepository.findById(policyId).orElseThrow(
                ()-> new ApiRequestException("There is no item with id: " + policyId)
        );
        policy.setTitle(request.getTitle());
        policy.setDescription(request.getDescription());
        policyRepository.save(policy);
        return new ApiResponseClass("Update policy" , HttpStatus.OK, LocalDateTime.now() , policy);
    }

    public ApiResponseClass deletePolicy(Integer policyId){
        Policy policy = policyRepository.findById(policyId).orElseThrow(
                ()-> new ApiRequestException("There is no item with id: " + policyId)
        );
        policyRepository.delete(policy);
        return new ApiResponseClass("Delete policy" , HttpStatus.OK, LocalDateTime.now());
    }

}
