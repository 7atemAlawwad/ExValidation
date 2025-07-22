package com.example.event.Controller;

import com.example.event.Model.EventModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    ArrayList<EventModel> events = new ArrayList<>();

    @GetMapping("/get")
    public ResponseEntity<?> getEvents() {
        return ResponseEntity.status(200).body(events);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@Valid @RequestBody EventModel eventModel, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        events.add(eventModel);
        return ResponseEntity.status(200).body("Event added successfully");
    }

    @PutMapping("/update/{index}")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody EventModel eventModel,Errors errors, @PathVariable int index) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        events.set(index, eventModel);
        return ResponseEntity.status(200).body("Event updated successfully");
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<?> deleteEvent(@PathVariable int index) {
        if(index >= events.size()){
            return ResponseEntity.status(400).body("Event not found");
        }
        events.remove(index);
        return ResponseEntity.status(200).body("Event deleted successfully");
    }

    @PutMapping("/changeCapacity/{index}")
    public ResponseEntity<?> changeCapacity(
            @RequestBody
            @Min(value = 25, message = "Capacity must be at least 25")
            @Positive(message = "Capacity must be a positive number")
            Integer capacity,
            Errors errors,
            @PathVariable int index){

        if (errors.hasErrors()) {
            String msg = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(msg);
        }

        if(index >= events.size() || index < 0){
            return ResponseEntity.status(400).body("Event not found");
        }
        events.get(index).setCapacity(capacity);
        return ResponseEntity.status(200).body("Event capacity updated successfully");
    }

    @GetMapping("/search/{ID}")
    public ResponseEntity<?> searchEvent(@PathVariable String ID){
        for(EventModel event : events){
            if(event.getID().equals(ID)){
                return ResponseEntity.status(200).body(event);
            }
        }
        return ResponseEntity.status(400).body("Event not found");

    }
}
