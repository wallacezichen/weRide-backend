package com.weride.controller;
import com.weride.model.Card;
import com.weride.model.UserCardRelation;
import com.weride.repository.UserCardRelationRepository;
import com.weride.service.UserCardRelationService;
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
        return userCardRelationService.addCardToUser(userCardRelation);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> remove(@RequestBody UserCardRelation userCardRelation){
        return userCardRelationService.removeCardFromUser(userCardRelation);
    }
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserCardRelation userCardRelation, Card card){
        return userCardRelationService.updateCard(userCardRelation, card);
    }

//    //有点问题
//    @GetMapping("{userId}")
//    public ResponseEntity<StudentCourse> getStudentCourseById(@PathVariable("userId") long studentCourseid){
//        return new ResponseEntity<StudentCourse>(studentCourseService.getStudentCourseById(studentCourseid),HttpStatus.OK);
//    }
}
