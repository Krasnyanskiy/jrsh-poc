//package com.jaspersoft.jasperserver.jrsh.core;
//
//import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
//import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
//import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
//import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
//import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
//import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Prefix;
//import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
//import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
//import lombok.Data;
//
//public class OperationHolder {
//
//
//    @Data
//    @Master(name = "dummy", description = "Dummy operation.", tail = true)
//    public class DummyOperation implements Operation {
//
//        @Parameter(dependsOn = "dummy", values =
//        @Value(tokenAlias = "V", tail = true, tokenClass = StringToken.class, tokenValue = "--verbose"))
//        private String verbose;
//
//        @Override
//        public OperationResult eval(Session session) {
//            if (verbose == null) {
//                System.out.println("Hi!");
//            } else {
//                System.out.println("-v Hi!");
//            }
//            return new OperationResult("Dummy operation!", OperationResult.ResultCode.SUCCESS, this, null);
//        }
//    }
//
//
//    @Master(name = "t1", description = "T1 operation.", tail = true)
//    public class T1Operation implements Operation {
//
//        @Override
//        public OperationResult eval(Session session) {
//            return new OperationResult("T1", OperationResult.ResultCode.SUCCESS, this, null);
//        }
//    }
//
//
//    /**
//     * Grammar for PrefixOperation
//     * ---------------------------
//     *
//     * dummy -o X1
//     * dummy -o X2
//     * dummy -o X3
//     *
//     * dummy -> Abc
//     * dummy -o X2 -> Abc
//     * dummy -o X3 -> Abc
//     *
//     * dummy -> Abc -o X1
//     * dummy -> Abc -o X2
//     * dummy -> Abc -o X3
//     */
//    @Data
//    @Master(name = "dummy", description = "Dummy operation.")
//    public class PrefixOperation implements Operation {
//
//        @Prefix("-o")
//        @Parameter(dependsOn = {"dummy", "Abc"}, values = {
//                @Value(tokenAlias = "X1", tokenClass = StringToken.class, tokenValue = "first", tail = true),
//                @Value(tokenAlias = "X2", tokenClass = StringToken.class, tokenValue = "second", tail = true),
//                @Value(tokenAlias = "X3", tokenClass = StringToken.class, tokenValue = "third", tail = true)
//        })
//        private String order;
//
//        @Prefix("->")
//        @Parameter(dependsOn = {"X3", "dummy", "X2"}, values = @Value(tokenAlias = "Abc", tail = true))
//        private String abc;
//
//        @Override
//        public OperationResult eval(Session session) {
//            return new OperationResult("Test", OperationResult.ResultCode.SUCCESS, this, null);
//        }
//    }
//}
