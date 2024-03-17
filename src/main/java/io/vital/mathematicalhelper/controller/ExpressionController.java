package io.vital.mathematicalhelper.controller;

import io.vital.mathematicalhelper.Util;
import io.vital.mathematicalhelper.repository.ExpressionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/calc/expression")
@AllArgsConstructor
public class ExpressionController {

    private ExpressionRepository expressionRepository;

    @PutMapping
    public ResponseEntity<String> saveExpression(@RequestBody String expression, HttpSession session){
        System.out.println(expression);
//        if(Util.isExpressionBadlyFormatted(expression)) {
//            return ResponseEntity.status(400).build();
//        }
        long id = expressionRepository.createExpression(expression);
        ResponseEntity<String> responseEntity;
        if(session.getAttribute("expression") == null) responseEntity = ResponseEntity.status(201).build();
        else responseEntity = ResponseEntity.ok().build();
        session.setAttribute("expression", expression);
        session.setAttribute("expressionId", id);
        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<String> unsetExpression(HttpSession session){
        session.setAttribute("expression", null);
        return ResponseEntity.status(204).build();
    }
}
