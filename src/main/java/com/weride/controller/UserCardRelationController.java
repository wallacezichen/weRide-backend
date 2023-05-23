package com.weride.controller;
import com.weride.model.Card;
import com.weride.model.UserCardRelation;
import com.weride.repository.UserCardRelationRepository;
import com.weride.service.UserCardRelationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userCardRelation-service")
public class UserCardRelationController {
    private final UserCardRelationService userCardRelationService;
    private final UserCardRelationRepository userCardRelationRepository;
    public UserCardRelationController(UserCardRelationService userCardRelationService, UserCardRelationRepository userCardRelationRepository) {
        this.userCardRelationService = userCardRelationService;
        this.userCardRelationRepository = userCardRelationRepository;
    }
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody UserCardRelation userCardRelation){
        if (userCardRelationService.addCardToUser(userCardRelation)){
            return ResponseEntity.ok("Card added to the user successfully");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserCardRelation already exists");
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> remove(@RequestBody UserCardRelation userCardRelation){
        if(userCardRelationService.removeCardFromUser(userCardRelation)){
            return ResponseEntity.ok("Card removed from the user successfully");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card does not exist");
        }
    }
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserCardRelation userCardRelation, Card card){
        if(userCardRelationService.updateCard(userCardRelation, card)){
            return ResponseEntity.ok("Card updated successfully");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card does not exist");
        }
    }

//    //有点问题
//    @GetMapping("{userId}")
//    public ResponseEntity<StudentCourse> getStudentCourseById(@PathVariable("userId") long studentCourseid){
//        return new ResponseEntity<StudentCourse>(studentCourseService.getStudentCourseById(studentCourseid),HttpStatus.OK);
//    }
}
