package com.MedGuard.task.Controller;

import com.MedGuard.Response.ApiResponseClass;
import com.MedGuard.task.Enum.TaskState;
import com.MedGuard.task.Request.TaskRequest;
import com.MedGuard.task.Response.TaskResponse;
import com.MedGuard.task.Service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@Tag(name = "task")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("")
    @Operation(
            description = "This endpoint build to Get All tasks which is in our system",
            summary = "Get All tasks",
            responses = {
                    @ApiResponse(
                            description ="Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getNearbyTasks(@RequestParam Double longtitude, @RequestParam Double latitude) {
        return ResponseEntity.ok(taskService.getNearbyTasks(longtitude,latitude));
    }

    @GetMapping("{id}")
    @Operation(
            description = "This endpoint build to Get task by id which is in our system",
            summary = "Get task by id",
            responses = {
                    @ApiResponse(
                            description ="Get task by idl done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> gettaskById(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    @Operation(
            description = "This endpoint build to Create task which is in our system",
            summary = "Create new task",
            responses = {
                    @ApiResponse(
                            description ="Create done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> addNewTask(@RequestBody List< TaskRequest> taskRequests) {
        List<TaskResponse> responseList = new ArrayList<>();
        for(TaskRequest request : taskRequests){
           responseList.add( taskService.createTask(request));
        }
        return ResponseEntity.ok( new ApiResponseClass("Create new hub successfully" , HttpStatus.CREATED , LocalDateTime.now(), responseList));
    }

    @PutMapping("{id}")
    @Operation(
            description = "This endpoint build to update task by id which is in our system",
            summary = "Edit task by id",
            responses = {
                    @ApiResponse(
                            description ="Edit task by id done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> updateTaskState(@PathVariable Integer id, @RequestBody TaskRequest request) {
        return ResponseEntity.status(202).body(taskService.updateTask(id, request));
    }

    @PutMapping("state/{id}")
    @Operation(
            description = "This endpoint build to update task state by id which is in our system",
            summary = "Edit state by id (take task)",
            responses = {
                    @ApiResponse(
                            description ="Edit task by id done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> updateTaskState(@PathVariable Integer id, @RequestBody TaskState request) {
        return ResponseEntity.status(202).body(taskService.updateStatus(id, request));
    }


    @DeleteMapping("{id}")
    @Operation(
            description = "This endpoint build to Delete task by id which is in our system",
            summary = "Delete task by id",
            responses = {
                    @ApiResponse(
                            description ="Delete by id done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> deletetask(@PathVariable Integer id) {
        return ResponseEntity.status(204).body(taskService.deleteTask(id));
    }

}
