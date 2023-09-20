package gov.va.transpiler.bulk.pyspark;

import java.util.Stack;

import gov.va.transpiler.ElmConverterState;
import gov.va.transpiler.output.OutputNode;

public class BulkElmToPySparkConverterState extends ElmConverterState {
    public Stack<OutputNode> stack = new Stack<>();
}
