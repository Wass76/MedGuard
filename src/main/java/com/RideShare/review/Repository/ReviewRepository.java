package com.RideShare.review.Repository;

import com.RideShare.review.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    public Review findByReservationId(int reservationId);


}
