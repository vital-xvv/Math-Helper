package io.vital.mathematicalhelper.controller;

import io.vital.mathematicalhelper.ExpressionResolver;
import io.vital.mathematicalhelper.Util;
import io.vital.mathematicalhelper.repository.ExpressionRepository;
import io.vital.mathematicalhelper.repository.RootRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("calc/result")
@AllArgsConstructor
public class EvaluationController {
    RootRepository rootRepository;
    ExpressionRepository expressionRepository;

    @GetMapping
    public ResponseEntity<String> getResult(HttpSession session){

        String readyExpression = (String)session.getAttribute("expression");
        if(readyExpression == null || readyExpression.isBlank()){
            return ResponseEntity.status(409).build();
        }else {
            List<String> operands = Stream.
                    of(readyExpression.split("[*+-/]"))
                    .filter(s -> s.matches("[a-z]"))
                    .toList();

            for(String attr: operands) {
                if(session.getAttribute(attr) == null) return ResponseEntity.status(409).build();
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        String expression = (String) session.getAttribute("expression");
        List<String> expressionParsed = Arrays.asList(expression.split(""));

        List<String> exes = expressionParsed.stream().map(e -> {
            if(e.matches("[a-z]")) return getDeepValue(session, e);
            return e;
        }).filter(e -> !e.isBlank()).collect(Collectors.toList());


        String exesString = String.join("", exes);
        System.out.println(exesString);


        while(true) {
            ExpressionResolver exp = new ExpressionResolver(exesString);
            exesString = exp.getNewExpression();
            System.out.println(exesString);
            if(exp.isSolved()) break;
        }

        List<Double> sides = Arrays.stream(exesString.split("=")).map(Double::parseDouble).toList();

        if(Math.abs(sides.get(0)-sides.get(1)) < 0.000000001) {
            long rootId = rootRepository.createRoot(Double.parseDouble((String) session.getAttribute("x")));
            expressionRepository.setExpressionRoot((Long) session.getAttribute("expressionId"), rootId);
            return ResponseEntity.ok().body(exesString);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exesString);
    }

    private String getDeepValue(HttpSession session, String attribute) {
        String value = (String) session.getAttribute(attribute);
        if(Util.isStringNumber(value)) return value;
        else return getDeepValue(session, value);
    }
}
