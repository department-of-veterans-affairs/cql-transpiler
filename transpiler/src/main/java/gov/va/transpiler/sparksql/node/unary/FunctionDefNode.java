package gov.va.transpiler.sparksql.node.unary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Unary;
import gov.va.transpiler.sparksql.node.ary.TypeSpecifierNode;
import gov.va.transpiler.sparksql.node.leaf.AccessModifierNode;

// TODO: support overloaded functions
public class FunctionDefNode extends Unary {

    /** we don't support access modifiers */
    @SuppressWarnings("unused")
    private AccessModifierNode accessModifier;
    private FunctionDefNode scope;
    private List<OperandDefNode> operandDefList = new ArrayList<>();
    private Map<String, OperandDefNode> nameToOperandDefMap = new LinkedHashMap<>();
    /** Temporary state. Not thread safe. */
    private Map<OperandDefNode, AbstractCQLNode> operandDefToCurrentReplacementMap;
    private List<TypeSpecifierNode> typeSpecifierNodeList = new ArrayList<>();

    private void setAccessModifier(AccessModifierNode accessModifier) {
        this.accessModifier = accessModifier;
    }

    public void setScope(FunctionDefNode scope) {
        this.scope = scope;
    }

    public OperandDefNode getOperandDefForName(String name) {
        var operandDef = nameToOperandDefMap.get(name);
        if (operandDef == null && scope != null) {
            operandDef = getOperandDefForName(name);
        }
        return operandDef;
    }

    public AbstractCQLNode getParameterReplacementForOperandDef(OperandDefNode operandDef) {
        return operandDefToCurrentReplacementMap == null ? null : operandDefToCurrentReplacementMap.get(operandDef);
    }

    public String asInlineFunction(List<AbstractCQLNode> parameters) {
        if (parameters.size() != operandDefList.size()) {
            // TODO
            throw new UnsupportedOperationException("We don't support overloaded functions");
        } else {
            operandDefToCurrentReplacementMap = new LinkedHashMap<>();
            for (int i = 0; i < parameters.size(); i++) {
                operandDefToCurrentReplacementMap.put(operandDefList.get(i), parameters.get(i));
            }
        }
        String inline = getChild().asOneLine();
        operandDefToCurrentReplacementMap = null;
        return inline;
    }

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child instanceof AccessModifierNode){
            setAccessModifier((AccessModifierNode) child);
            return true;
        } else if (child instanceof OperandDefNode) {
            operandDefList.add((OperandDefNode) child);
            nameToOperandDefMap.put(child.getName(), (OperandDefNode) child);
            return true;
        } else if (child instanceof TypeSpecifierNode) {
            typeSpecifierNodeList.add((TypeSpecifierNode) child);
            return true;
        }
        return super.addChild(child);
    }

    @Override
    public String asOneLine() {
        String parameters = "";
        if (!operandDefList.isEmpty()) {
            parameters += operandDefList.get(0).asOneLine();
            for (int i = 1; i < operandDefList.size(); i++) {
                parameters += ", " + operandDefList.get(i).asOneLine();
            }
         }
        String text = "";
        for (var child : getChildren()) {
            text += "\n    " + child.asOneLine();
        }
        return "define " + getName() + "(" + parameters + "):" + text;
    }
}
