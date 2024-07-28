package com.CareemSystem.review.Controller;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.review.Model.Review;
import com.CareemSystem.review.Request.ReviewRequest;
import com.CareemSystem.review.Service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/v1/review")
@Tag(
        name = "Review a service"
)
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("byBicycleId/{id}")
    @Operation(
            description = "This endpoint build to Get review by id which is in our system",
            summary = "Get review by id",
            responses = {
                    @ApiResponse(
                            description = "Get review done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ApiResponseClass getReview(@PathVariable Integer id) {
        return reviewService.getReviews(id);
    }

    @PostMapping
    @Operation(
            description = "This endpoint build to Create new review in our system",
            summary = "Create new review",
            responses = {
                    @ApiResponse(
                            description = "Create done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ApiResponseClass newReview(@RequestBody ReviewRequest request) {
        return reviewService.addReview(request);
    }


    @DeleteMapping("/{id}")
    @Operation(
            description = "This endpoint build to Delete review by id from our system",
            summary = "Delete review by id",
            responses = {
                    @ApiResponse(
                            description = "Delete done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ApiResponseClass deleteReview(@PathVariable Integer id) {
        return reviewService.deleteReview(id);
    }

    @PutMapping("/{id}")
    @Operation(
            description = "This endpoint build to Edit review by id in our system",
            summary = "Edit review by id",
            responses = {
                    @ApiResponse(
                            description = "Edit by id done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ApiResponseClass updateReview(@PathVariable Integer id,@RequestBody  ReviewRequest request) {
        return reviewService.updateReview(id, request);
    }

}
