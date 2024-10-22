package com.MedGuard.task.Repository;

import com.MedGuard.task.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

//    List<Hub> findAllByLongitudeBetweenAndLatitudeBetween(Double longitude, Double latitude, Double longitude2, Double latitude2);

//    List<Hub> findByLocationWithinRadius(Location userLocation, double radius);


//    @Query("SELECT h FROM Hub h WHERE ST_DWithin(h.location, :userLocation, :radius) = true")
//    List<Hub> findByLocationWithinRadius(@Param("userLocation") Point userLocation, @Param("radius") double radius);

//    @Query(value = "select user_name, ST_Distance_Sphere(ST_GeomFromText(:geometry), coordinate) / 1000 as distance "
//            + "from task "
////            + "HAVING user_name != :userName and distance < 200 "
//            + "order by distance", nativeQuery = true)
//    List<Object> findAllHubs(@Param("geometry") String geometry);
@Query(value = """
             SELECT *,
                    ( 6371 * acos( cos( radians(:lati) ) * cos( radians( latitude ) )
                                  * cos( radians( longitude ) - radians(:longi) )
                                  + sin( radians(:lati) ) * sin( radians( latitude ) )
                            )
                            ) 
                        AS distance
             FROM task
             GROUP BY task.id
             HAVING ( 6371 * acos( cos( radians(:lati) ) * cos( radians( latitude ) )
                                  * cos( radians( longitude ) - radians(:longi) )
                                  + sin( radians(:lati) ) * sin( radians( latitude ) )
                            )
                            ) < 10  
             ORDER BY ( 6371 * acos( cos( radians(:lati) ) * cos( radians( latitude ) )
                                  * cos( radians( longitude ) - radians(:longi) )
                                  + sin( radians(:lati) ) * sin( radians( latitude ) )
                            )
                            )  
             LIMIT 20 OFFSET 0;
             \
            """
        , nativeQuery = true
)
List<Task> findByLocation(@Param("lati") Double lati, @Param("longi") Double longi);




}
