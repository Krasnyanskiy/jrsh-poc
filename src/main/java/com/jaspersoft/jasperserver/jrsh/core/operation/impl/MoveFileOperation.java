package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.google.common.io.Files;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.FileNameToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import lombok.Data;

import java.io.File;
import java.io.IOException;

@Data
@Master(name = "move", description = "Moves file.")
public class MoveFileOperation implements Operation {

    @Parameter(mandatory = true, dependsOn = "move", values =
    @Value(tokenAlias = "F", tokenClass = FileNameToken.class))
    private String first;

    @Parameter(mandatory = true, dependsOn = "F", values = {
            @Value(tokenAlias = "DF", tokenValue = "->", tokenClass = StringToken.class),
            @Value(tokenAlias = "DB", tokenValue = "<-", tokenClass = StringToken.class)})
    private String direction;

    @Parameter(mandatory = true, dependsOn = {"DF", "DB"}, values =
    @Value(tokenAlias = "S", tokenClass = FileNameToken.class, tail = true))
    private String second;

    @Override
    public OperationResult eval(Session session) {
        OperationResult result;
        try {
            switch (direction) {
                case "->":
                    Files.move(new File(first), new File(second));
                    break;
                case "<-":
                    Files.move(new File(second), new File(first));
                    break;
            }
            result = new OperationResult("Moved.", ResultCode.SUCCESS, this, null);
        } catch (IOException ignored) {
            result = new OperationResult("File moving failed.", ResultCode.FAILED, this, null);
        }
        return result;
    }
}
