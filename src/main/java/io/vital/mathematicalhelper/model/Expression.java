package io.vital.mathematicalhelper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Expression {
    private String expression;
    private Long rootId;
    private Long id;
}
