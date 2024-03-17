package io.vital.mathematicalhelper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/calc/{var}")
public class VariableController {

    @PutMapping
    public ResponseEntity<String> saveVariable(HttpSession session,
                                       @PathVariable("var") String variableName,
                                               @RequestBody String variableValue){
        ResponseEntity<String> responseEntity;
        if(session.getAttribute(variableName) == null) responseEntity = ResponseEntity.status(201).build();
        else responseEntity = ResponseEntity.ok().build();

        session.setAttribute(variableName, variableValue);
        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<String> unsetVariable(HttpSession session,
                             @PathVariable("var") String variableName){

        session.setAttribute(variableName, null);
        return ResponseEntity.status(204).build();
    }
}
