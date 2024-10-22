package com.MedGuard.task.Service;

import com.MedGuard.Response.ApiResponseClass;
import com.MedGuard.task.Entity.Task;
import com.MedGuard.task.Enum.TaskState;
import com.MedGuard.utils.Validator.ObjectsValidator;
import com.MedGuard.utils.exception.ApiRequestException;
import com.MedGuard.task.Repository.TaskRepository;
import com.MedGuard.task.Response.TaskResponse;
import com.MedGuard.task.Request.TaskRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectsValidator<TaskRequest> validator;

    public ApiResponseClass getNearbyTasks(Double longitude, Double latitude){

//        TODO: Radius of Earth is = 6371
        List<Task> taskList = taskRepository.findByLocation( latitude,longitude );

        List<TaskResponse> responseList = new ArrayList<>();
        for(Task task : taskList){
            responseList.add(TaskResponse.builder()
                    .id(task.getId())
                    .situationName(task.getSituationName())
                    .description(task.getDescription())
                    .latitude(task.getLatitude())
                    .longitude(task.getLongitude())
                    .taskState(task.getTaskState())
                    .dueDate(task.getDueDate())
                    .patientName(task.getPatientName())
                    .riskLevel(task.getRiskLevel())
                    .time(task.getTime())
                    .build());
        }
        return new ApiResponseClass("Get all places done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(),responseList);
    }

    public ApiResponseClass getTaskById(Integer id){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            TaskResponse response = TaskResponse.builder()
                    .id(task.get().getId())
                    .situationName(task.get().getSituationName())
                    .description(task.get().getDescription())
                    .latitude(task.get().getLatitude())
                    .longitude(task.get().getLongitude())
                    .taskState(task.get().getTaskState())
                    .dueDate(task.get().getDueDate())
                    .patientName(task.get().getPatientName())
                    .riskLevel(task.get().getRiskLevel())
                    .time(task.get().getTime())
                    .build();
             return new ApiResponseClass("Get all places done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(), response);
        }
        throw new ApiRequestException("Hub with id " + id + " not found");
//        return new ApiResponseClass("Hub not found" , HttpStatus.ACCEPTED , LocalDateTime.now());
    }

    @Transactional
    public TaskResponse createTask(TaskRequest request){
        validator.validate(request);
        Task task = Task.builder()
                .situationName(request.getSituationName())
                .description(request.getDescription())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .taskState(request.getTaskState())
                .dueDate(LocalDate.now())
                .time(LocalTime.now())
                .patientName(request.getPatientName())
                .riskLevel(request.getRiskLevel())
                .build();
        taskRepository.save(task);
//
//        HubContent hubContent = HubContent.builder()
//                .task(task)
//                .build();
//        hubContentRepository.save(hubContent);

        TaskResponse response = TaskResponse.builder()
                .id(task.getId())
                .situationName(task.getSituationName())
                .description(task.getDescription())
                .latitude(task.getLatitude())
                .longitude(task.getLongitude())
                .taskState(task.getTaskState())
                .dueDate(task.getDueDate())
                .patientName(task.getPatientName())
                .riskLevel(task.getRiskLevel())
                .time(task.getTime())
                .build();
        return response;
    }

    public ApiResponseClass updateTask(Integer id, TaskRequest request){
        Optional<Task> task = taskRepository.findById(id);
        if(!task.isPresent()){
            throw new ApiRequestException("Hub with id " + id + " not found");
        }
        validator.validate(request);
        task.get().setSituationName(request.getSituationName());
        task.get().setDescription(request.getDescription());
        task.get().setLatitude(request.getLatitude());
        task.get().setLongitude(request.getLongitude());
        task.get().setTaskState(request.getTaskState());
        task.get().setDueDate(request.getDueDate());
        task.get().setTime(request.getTime());
        task.get().setPatientName(request.getPatientName());
        task.get().setRiskLevel(request.getRiskLevel());
        taskRepository.save(task.get());

        TaskResponse response = TaskResponse.builder()
                .id(task.get().getId())
                .situationName(task.get().getSituationName())
                .description(task.get().getDescription())
                .latitude(task.get().getLatitude())
                .longitude(task.get().getLongitude())
                .taskState(task.get().getTaskState())
                .dueDate(task.get().getDueDate())
                .patientName(task.get().getPatientName())
                .riskLevel(task.get().getRiskLevel())
                .time(task.get().getTime())
                .build();

        return new ApiResponseClass("Update task successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    public ApiResponseClass updateStatus(Integer id, TaskState state){
        Optional<Task> task = taskRepository.findById(id);
        if(!task.isPresent()){
            throw new ApiRequestException("Hub with id " + id + " not found");
        }
        task.get().setTaskState(state);
        taskRepository.save(task.get());
        TaskResponse response = TaskResponse.builder()
                .id(task.get().getId())
                .situationName(task.get().getSituationName())
                .description(task.get().getDescription())
                .latitude(task.get().getLatitude())
                .longitude(task.get().getLongitude())
                .taskState(task.get().getTaskState())
                .dueDate(task.get().getDueDate())
                .patientName(task.get().getPatientName())
                .riskLevel(task.get().getRiskLevel())
                .time(task.get().getTime())
                .build();
        return new ApiResponseClass("Update task state successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }
    public ApiResponseClass deleteTask(Integer id){
        Optional<Task> hub = taskRepository.findById(id);
        if(!hub.isPresent()){
            throw new ApiRequestException("Hub with id " + id + " not found");
        }
        taskRepository.delete(hub.get());
        return new ApiResponseClass("Delete hub successfully" , HttpStatus.NO_CONTENT , LocalDateTime.now());
    }


}
