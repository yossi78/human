package com.example.human.api;

import com.example.human.dto.Human;
import com.example.human.services.HumanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping(value = "/v1/humans")
public class HumanController {


    private static final Logger logger = LoggerFactory.getLogger(HumanController.class);
    private HumanService humanService;


    @Autowired
    public HumanController(HumanService humanService){
        this.humanService=humanService;
    }


    @GetMapping(value = "/health")
    public ResponseEntity healthCheck() {
        try {
            return new ResponseEntity("Human Service Health is OK", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity addHuman(@RequestBody Human human) {
        try {
            humanService.addHuman(human);
            logger.info("Human has been added successfully");
            return new ResponseEntity(human,HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Failed to add new Human",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping
    public ResponseEntity getAllHumans() {
        try {
            List<Human> humans = humanService.getAllHumans();
            if (humans==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(humans);
        }catch (Exception e){
            logger.error("Failed to find all humans",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity updatePerson(@PathVariable Long id , @RequestBody Human human) {
        try {
            logger.info("Update Human : "+ human);
            Boolean isUpdated = humanService.updateHuman(human,id);
            if(!isUpdated){
                logger.warn("The human not exists and can not be updated");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to update human",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @DeleteMapping("/{id}")
    public ResponseEntity deleteHuman(@PathVariable Long id) {
        try {
            logger.info("Delete Human by Id: "+ id);
            Boolean isRemoved = humanService.removeHuman(id);
            if(!isRemoved){
                logger.warn("The human not exists and can not be deleted");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to delete human",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
