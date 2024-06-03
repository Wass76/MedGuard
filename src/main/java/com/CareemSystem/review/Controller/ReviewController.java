package com.CareemSystem.review.Controller;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.review.Model.Review;
import com.CareemSystem.review.Request.ReviewRequest;
import com.CareemSystem.review.Service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("byBicycleId/{id}")
    public ApiResponseClass getReview(@PathVariable Integer id) {
        return reviewService.getReviews(id);
    }

    @PostMapping
    public ApiResponseClass newReview(@RequestBody ReviewRequest request) {
        return reviewService.addReview(request);
    }


    @DeleteMapping("/{id}")
    public ApiResponseClass deleteReview(@PathVariable Integer id) {
        return reviewService.deleteReview(id);
    }

    @PutMapping("/{id}")
    public ApiResponseClass updateReview(@PathVariable Integer id,@RequestBody  ReviewRequest request) {
        return reviewService.updateReview(id, request);
    }

}
