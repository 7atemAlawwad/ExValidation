package com.example.trackersystem.Controller;

import com.example.trackersystem.API.ApiResponse;
import com.example.trackersystem.Model.Tracker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/tracker")
public class TrackerController {

    ArrayList<Tracker> trackerList = new ArrayList<>();

    @GetMapping("/get")
    public ResponseEntity<?> getTrackerList() {
        return ResponseEntity.status(200).body(trackerList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProject(@Valid @RequestBody Tracker tracker, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        trackerList.add(tracker);
        return ResponseEntity.status(200).body(new ApiResponse("Tracker Added Successfully"));
    }

    @PutMapping("/update/{index}")
    public ResponseEntity<?> updateProject(@Valid @RequestBody Tracker tracker, Errors errors, @PathVariable int index){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        trackerList.set(index, tracker);
        return ResponseEntity.status(200).body(new ApiResponse("Tracker Updated Successfully"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<?> deleteProject(@PathVariable int index){
        if(index >= trackerList.size()){
            return ResponseEntity.status(400).body(new ApiResponse("Tracker Not Found"));
        }
        trackerList.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("Tracker Deleted Successfully"));
    }

    @PutMapping("/changeStatus/{index}")
    public ResponseEntity<?> ChangeStatus(@Valid @ModelAttribute("status") @NotEmpty(message = "Status is required")
                                              @Pattern(regexp = "^(Not\\sStarted|In\\sProgress|Completed)$", message = "Status must be “Not Started”, “In Progress”, or “Completed”")
                                              String status, Errors errors, @PathVariable int index){


        if(index >= trackerList.size()){
            return ResponseEntity.status(400).body(new ApiResponse("Tracker Not Found"));
        }
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        trackerList.get(index).setStatus(status);
        return ResponseEntity.status(200).body(new ApiResponse("Status Changed Successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProject(@RequestParam String title){
        for(Tracker tracker : trackerList){
            if(tracker.getTitle().equals(title)){
                return ResponseEntity.status(200).body(new ApiResponse("Project found"));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Project Not Found"));
    }

    @GetMapping("/get/company")
    public ResponseEntity<?> getAllProjectByOneCompany(@RequestParam String companyName) {
        ArrayList<Tracker> trackerList = new ArrayList<>();
        for(Tracker tracker : this.trackerList){
            if(tracker.getCompanyName().equals(companyName)){
                trackerList.add(tracker);
            }
        }
        return ResponseEntity.status(200).body(trackerList);
    }


}
