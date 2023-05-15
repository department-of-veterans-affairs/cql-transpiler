// package gov.va.fast.evaluator;

// import org.apache.spark.sql.Dataset;
// import org.apache.spark.sql.Row;
// import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
// import org.hl7.elm.r1.*;

// class SparkTranslatorVisitor extends ElmBaseLibraryVisitor<Object, EvaluationContext> {

//     @Override
//     public Object visitExpression(Expression elm, EvaluationContext context) {
//         //System.out.println(elm.getResultType().toString());
//         //System.out.println(elm.getResultTypeSpecifier().get);
//         System.out.println(elm.getClass().getSimpleName());
//         return super.visitExpression(elm, context);
//     }

//     @Override
//     public Object visitRetrieve(Retrieve elm, EvaluationContext context) {
//         System.out.println(elm.getClass().getSimpleName());
//         return super.visitRetrieve(elm, context);
//     }

//     // @Override
//     // public Object visitCodeFilterElement(CodeFilterElement elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCodeFilterElement'");
//     // }

//     // @Override
//     // public Object visitDateFilterElement(DateFilterElement elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitDateFilterElement'");
//     // }

//     // @Override
//     // public Object visitOtherFilterElement(OtherFilterElement elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitOtherFilterElement'");
//     // }

//     // @Override
//     // public Object visitIncludeElement(IncludeElement elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIncludeElement'");
//     // }

//     // @Override
//     // public Object visitSearch(Search elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSearch'");
//     // }

//     // @Override
//     // public Object visitCodeSystemDef(CodeSystemDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCodeSystemDef'");
//     // }

//     // @Override
//     // public Object visitValueSetDef(ValueSetDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitValueSetDef'");
//     // }

//     // @Override
//     // public Object visitCodeDef(CodeDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCodeDef'");
//     // }

//     // @Override
//     // public Object visitConceptDef(ConceptDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConceptDef'");
//     // }

//     // @Override
//     // public Object visitCodeSystemRef(CodeSystemRef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCodeSystemRef'");
//     // }

//     // @Override
//     // public Object visitValueSetRef(ValueSetRef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitValueSetRef'");
//     // }

//     // @Override
//     // public Object visitCodeRef(CodeRef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCodeRef'");
//     // }

//     // @Override
//     // public Object visitConceptRef(ConceptRef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConceptRef'");
//     // }

//     // @Override
//     // public Object visitCode(Code elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCode'");
//     // }

//     // @Override
//     // public Object visitConcept(Concept elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConcept'");
//     // }

//     // @Override
//     // public Object visitInCodeSystem(InCodeSystem elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitInCodeSystem'");
//     // }

//     // @Override
//     // public Object visitAnyInCodeSystem(AnyInCodeSystem elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAnyInCodeSystem'");
//     // }

//     // @Override
//     // public Object visitInValueSet(InValueSet elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitInValueSet'");
//     // }

//     // @Override
//     // public Object visitAnyInValueSet(AnyInValueSet elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAnyInValueSet'");
//     // }

//     // @Override
//     // public Object visitSubsumes(Subsumes elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSubsumes'");
//     // }

//     // @Override
//     // public Object visitSubsumedBy(SubsumedBy elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSubsumedBy'");
//     // }

//     // @Override
//     // public Object visitQuantity(Quantity elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitQuantity'");
//     // }

//     // @Override
//     // public Object visitRatio(Ratio elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitRatio'");
//     // }

//     // @Override
//     // public Object visitCalculateAge(CalculateAge elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCalculateAge'");
//     // }

//     // @Override
//     // public Object visitCalculateAgeAt(CalculateAgeAt elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCalculateAgeAt'");
//     // }

//     // @Override
//     // public Object visitElement(Element elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitElement'");
//     // }

//     // @Override
//     // public Object visitTypeSpecifier(TypeSpecifier elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTypeSpecifier'");
//     // }

//     // @Override
//     // public Object visitNamedTypeSpecifier(NamedTypeSpecifier elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitNamedTypeSpecifier'");
//     // }

//     // @Override
//     // public Object visitIntervalTypeSpecifier(IntervalTypeSpecifier elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIntervalTypeSpecifier'");
//     // }

//     // @Override
//     // public Object visitListTypeSpecifier(ListTypeSpecifier elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitListTypeSpecifier'");
//     // }

//     // @Override
//     // public Object visitTupleElementDefinition(TupleElementDefinition elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTupleElementDefinition'");
//     // }

//     // @Override
//     // public Object visitTupleTypeSpecifier(TupleTypeSpecifier elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTupleTypeSpecifier'");
//     // }

//     // @Override
//     // public Object visitChoiceTypeSpecifier(ChoiceTypeSpecifier elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitChoiceTypeSpecifier'");
//     // }

//     // @Override
//     // public Object visitUnaryExpression(UnaryExpression elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitUnaryExpression'");
//     // }

//     // @Override
//     // public Object visitOperatorExpression(OperatorExpression elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitOperatorExpression'");
//     // }

//     // @Override
//     // public Object visitBinaryExpression(BinaryExpression elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitBinaryExpression'");
//     // }

//     // @Override
//     // public Object visitTernaryExpression(TernaryExpression elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTernaryExpression'");
//     // }

//     // @Override
//     // public Object visitNaryExpression(NaryExpression elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitNaryExpression'");
//     // }

//     // @Override
//     // public Object visitExpressionDef(ExpressionDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitExpressionDef'");
//     // }

//     // @Override
//     // public Object visitFunctionDef(FunctionDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitFunctionDef'");
//     // }

//     // @Override
//     // public Object visitExpressionRef(ExpressionRef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitExpressionRef'");
//     // }

//     // @Override
//     // public Object visitFunctionRef(FunctionRef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitFunctionRef'");
//     // }

//     // @Override
//     // public Object visitParameterDef(ParameterDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitParameterDef'");
//     // }

//     // @Override
//     // public Object visitParameterRef(ParameterRef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitParameterRef'");
//     // }

//     // @Override
//     // public Object visitOperandDef(OperandDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitOperandDef'");
//     // }

//     // @Override
//     // public Object visitOperandRef(OperandRef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitOperandRef'");
//     // }

//     // @Override
//     // public Object visitIdentifierRef(IdentifierRef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIdentifierRef'");
//     // }

//     // @Override
//     // public Object visitLiteral(Literal elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLiteral'");
//     // }

//     // @Override
//     // public Object visitTupleElement(TupleElement elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTupleElement'");
//     // }

//     // @Override
//     // public Object visitTuple(Tuple elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTuple'");
//     // }

//     // @Override
//     // public Object visitInstanceElement(InstanceElement elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitInstanceElement'");
//     // }

//     // @Override
//     // public Object visitInstance(Instance elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitInstance'");
//     // }

//     // @Override
//     // public Object visitInterval(Interval elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitInterval'");
//     // }

//     // @Override
//     // public Object visitList(org.hl7.elm.r1.List elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     //throw new UnsupportedOperationException("Unimplemented method 'visitList'");
//     // }

//     // @Override
//     // public Object visitAnd(And elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAnd'");
//     // }

//     // @Override
//     // public Object visitOr(Or elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitOr'");
//     // }

//     // @Override
//     // public Object visitXor(Xor elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitXor'");
//     // }

//     // @Override
//     // public Object visitImplies(Implies elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitImplies'");
//     // }

//     // @Override
//     // public Object visitNot(Not elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitNot'");
//     // }

//     // @Override
//     // public Object visitIf(If elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIf'");
//     // }

//     // @Override
//     // public Object visitCaseItem(CaseItem elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCaseItem'");
//     // }

//     // @Override
//     // public Object visitCase(Case elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCase'");
//     // }

//     // @Override
//     // public Object visitNull(Null elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitNull'");
//     // }

//     // @Override
//     // public Object visitIsNull(IsNull elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIsNull'");
//     // }

//     // @Override
//     // public Object visitIsTrue(IsTrue elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIsTrue'");
//     // }

//     // @Override
//     // public Object visitIsFalse(IsFalse elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIsFalse'");
//     // }

//     // @Override
//     // public Object visitCoalesce(Coalesce elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCoalesce'");
//     // }

//     // @Override
//     // public Object visitIs(Is elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIs'");
//     // }

//     // @Override
//     // public Object visitAs(As elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAs'");
//     // }

//     // @Override
//     // public Object visitConvert(Convert elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvert'");
//     // }

//     // @Override
//     // public Object visitCanConvert(CanConvert elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCanConvert'");
//     // }

//     // @Override
//     // public Object visitConvertsToBoolean(ConvertsToBoolean elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertsToBoolean'");
//     // }

//     // @Override
//     // public Object visitToBoolean(ToBoolean elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToBoolean'");
//     // }

//     // @Override
//     // public Object visitToChars(ToChars elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToChars'");
//     // }

//     // @Override
//     // public Object visitToConcept(ToConcept elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToConcept'");
//     // }

//     // @Override
//     // public Object visitConvertsToDate(ConvertsToDate elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertsToDate'");
//     // }

//     // @Override
//     // public Object visitToDate(ToDate elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToDate'");
//     // }

//     // @Override
//     // public Object visitConvertsToDateTime(ConvertsToDateTime elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertsToDateTime'");
//     // }

//     // @Override
//     // public Object visitToDateTime(ToDateTime elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToDateTime'");
//     // }

//     // @Override
//     // public Object visitConvertsToLong(ConvertsToLong elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertsToLong'");
//     // }

//     // @Override
//     // public Object visitToLong(ToLong elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToLong'");
//     // }

//     // @Override
//     // public Object visitConvertsToDecimal(ConvertsToDecimal elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertsToDecimal'");
//     // }

//     // @Override
//     // public Object visitToDecimal(ToDecimal elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToDecimal'");
//     // }

//     // @Override
//     // public Object visitConvertsToInteger(ConvertsToInteger elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertsToInteger'");
//     // }

//     // @Override
//     // public Object visitToInteger(ToInteger elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToInteger'");
//     // }

//     // @Override
//     // public Object visitToList(ToList elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToList'");
//     // }

//     // @Override
//     // public Object visitConvertQuantity(ConvertQuantity elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertQuantity'");
//     // }

//     // @Override
//     // public Object visitCanConvertQuantity(CanConvertQuantity elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCanConvertQuantity'");
//     // }

//     // @Override
//     // public Object visitConvertsToQuantity(ConvertsToQuantity elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertsToQuantity'");
//     // }

//     // @Override
//     // public Object visitToQuantity(ToQuantity elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToQuantity'");
//     // }

//     // @Override
//     // public Object visitConvertsToRatio(ConvertsToRatio elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertsToRatio'");
//     // }

//     // @Override
//     // public Object visitToRatio(ToRatio elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToRatio'");
//     // }

//     // @Override
//     // public Object visitConvertsToString(ConvertsToString elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertsToString'");
//     // }

//     // @Override
//     // public Object visitToString(ToString elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToString'");
//     // }

//     // @Override
//     // public Object visitConvertsToTime(ConvertsToTime elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConvertsToTime'");
//     // }

//     // @Override
//     // public Object visitToTime(ToTime elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToTime'");
//     // }

//     // @Override
//     // public Object visitEqual(Equal elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitEqual'");
//     // }

//     // @Override
//     // public Object visitEquivalent(Equivalent elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitEquivalent'");
//     // }

//     // @Override
//     // public Object visitNotEqual(NotEqual elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitNotEqual'");
//     // }

//     // @Override
//     // public Object visitLess(Less elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLess'");
//     // }

//     // @Override
//     // public Object visitGreater(Greater elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitGreater'");
//     // }

//     // @Override
//     // public Object visitLessOrEqual(LessOrEqual elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLessOrEqual'");
//     // }

//     // @Override
//     // public Object visitGreaterOrEqual(GreaterOrEqual elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitGreaterOrEqual'");
//     // }

//     // @Override
//     // public Object visitAdd(Add elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAdd'");
//     // }

//     // @Override
//     // public Object visitSubtract(Subtract elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSubtract'");
//     // }

//     // @Override
//     // public Object visitMultiply(Multiply elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMultiply'");
//     // }

//     // @Override
//     // public Object visitDivide(Divide elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitDivide'");
//     // }

//     // @Override
//     // public Object visitTruncatedDivide(TruncatedDivide elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTruncatedDivide'");
//     // }

//     // @Override
//     // public Object visitModulo(Modulo elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitModulo'");
//     // }

//     // @Override
//     // public Object visitCeiling(Ceiling elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCeiling'");
//     // }

//     // @Override
//     // public Object visitFloor(Floor elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitFloor'");
//     // }

//     // @Override
//     // public Object visitTruncate(Truncate elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTruncate'");
//     // }

//     // @Override
//     // public Object visitAbs(Abs elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAbs'");
//     // }

//     // @Override
//     // public Object visitNegate(Negate elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitNegate'");
//     // }

//     // @Override
//     // public Object visitRound(Round elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitRound'");
//     // }

//     // @Override
//     // public Object visitLn(Ln elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLn'");
//     // }

//     // @Override
//     // public Object visitExp(Exp elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitExp'");
//     // }

//     // @Override
//     // public Object visitLog(Log elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLog'");
//     // }

//     // @Override
//     // public Object visitPower(Power elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitPower'");
//     // }

//     // @Override
//     // public Object visitSuccessor(Successor elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSuccessor'");
//     // }

//     // @Override
//     // public Object visitPredecessor(Predecessor elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitPredecessor'");
//     // }

//     // @Override
//     // public Object visitMinValue(MinValue elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMinValue'");
//     // }

//     // @Override
//     // public Object visitMaxValue(MaxValue elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMaxValue'");
//     // }

//     // @Override
//     // public Object visitPrecision(Precision elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitPrecision'");
//     // }

//     // @Override
//     // public Object visitLowBoundary(LowBoundary elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLowBoundary'");
//     // }

//     // @Override
//     // public Object visitHighBoundary(HighBoundary elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitHighBoundary'");
//     // }

//     // @Override
//     // public Object visitConcatenate(Concatenate elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitConcatenate'");
//     // }

//     // @Override
//     // public Object visitCombine(Combine elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCombine'");
//     // }

//     // @Override
//     // public Object visitSplit(Split elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSplit'");
//     // }

//     // @Override
//     // public Object visitSplitOnMatches(SplitOnMatches elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSplitOnMatches'");
//     // }

//     // @Override
//     // public Object visitLength(Length elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLength'");
//     // }

//     // @Override
//     // public Object visitUpper(Upper elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitUpper'");
//     // }

//     // @Override
//     // public Object visitLower(Lower elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLower'");
//     // }

//     // @Override
//     // public Object visitIndexer(Indexer elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIndexer'");
//     // }

//     // @Override
//     // public Object visitPositionOf(PositionOf elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitPositionOf'");
//     // }

//     // @Override
//     // public Object visitLastPositionOf(LastPositionOf elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLastPositionOf'");
//     // }

//     // @Override
//     // public Object visitSubstring(Substring elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSubstring'");
//     // }

//     // @Override
//     // public Object visitStartsWith(StartsWith elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitStartsWith'");
//     // }

//     // @Override
//     // public Object visitEndsWith(EndsWith elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitEndsWith'");
//     // }

//     // @Override
//     // public Object visitMatches(Matches elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMatches'");
//     // }

//     // @Override
//     // public Object visitReplaceMatches(ReplaceMatches elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitReplaceMatches'");
//     // }

//     // @Override
//     // public Object visitDurationBetween(DurationBetween elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitDurationBetween'");
//     // }

//     // @Override
//     // public Object visitDifferenceBetween(DifferenceBetween elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitDifferenceBetween'");
//     // }

//     // @Override
//     // public Object visitDateFrom(DateFrom elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitDateFrom'");
//     // }

//     // @Override
//     // public Object visitTimeFrom(TimeFrom elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTimeFrom'");
//     // }

//     // @Override
//     // public Object visitTimezoneFrom(TimezoneFrom elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTimezoneFrom'");
//     // }

//     // @Override
//     // public Object visitTimezoneOffsetFrom(TimezoneOffsetFrom elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTimezoneOffsetFrom'");
//     // }

//     // @Override
//     // public Object visitDateTimeComponentFrom(DateTimeComponentFrom elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitDateTimeComponentFrom'");
//     // }

//     // @Override
//     // public Object visitTimeOfDay(TimeOfDay elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTimeOfDay'");
//     // }

//     // @Override
//     // public Object visitToday(Today elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitToday'");
//     // }

//     // @Override
//     // public Object visitNow(Now elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitNow'");
//     // }

//     // @Override
//     // public Object visitDateTime(DateTime elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitDateTime'");
//     // }

//     // @Override
//     // public Object visitDate(Date elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitDate'");
//     // }

//     // @Override
//     // public Object visitTime(Time elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTime'");
//     // }

//     // @Override
//     // public Object visitSameAs(SameAs elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSameAs'");
//     // }

//     // @Override
//     // public Object visitSameOrBefore(SameOrBefore elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSameOrBefore'");
//     // }

//     // @Override
//     // public Object visitSameOrAfter(SameOrAfter elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSameOrAfter'");
//     // }

//     // @Override
//     // public Object visitWidth(Width elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitWidth'");
//     // }

//     // @Override
//     // public Object visitSize(Size elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSize'");
//     // }

//     // @Override
//     // public Object visitPointFrom(PointFrom elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitPointFrom'");
//     // }

//     // @Override
//     // public Object visitStart(Start elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitStart'");
//     // }

//     // @Override
//     // public Object visitEnd(End elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitEnd'");
//     // }

//     // @Override
//     // public Object visitContains(Contains elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitContains'");
//     // }

//     // @Override
//     // public Object visitProperContains(ProperContains elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitProperContains'");
//     // }

//     // @Override
//     // public Object visitIn(In elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIn'");
//     // }

//     // @Override
//     // public Object visitProperIn(ProperIn elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitProperIn'");
//     // }

//     // @Override
//     // public Object visitIncludes(Includes elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIncludes'");
//     // }

//     // @Override
//     // public Object visitIncludedIn(IncludedIn elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIncludedIn'");
//     // }

//     // @Override
//     // public Object visitProperIncludes(ProperIncludes elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitProperIncludes'");
//     // }

//     // @Override
//     // public Object visitProperIncludedIn(ProperIncludedIn elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitProperIncludedIn'");
//     // }

//     // @Override
//     // public Object visitBefore(Before elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitBefore'");
//     // }

//     // @Override
//     // public Object visitAfter(After elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAfter'");
//     // }

//     // @Override
//     // public Object visitMeets(Meets elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMeets'");
//     // }

//     // @Override
//     // public Object visitMeetsBefore(MeetsBefore elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMeetsBefore'");
//     // }

//     // @Override
//     // public Object visitMeetsAfter(MeetsAfter elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMeetsAfter'");
//     // }

//     // @Override
//     // public Object visitOverlaps(Overlaps elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitOverlaps'");
//     // }

//     // @Override
//     // public Object visitOverlapsBefore(OverlapsBefore elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitOverlapsBefore'");
//     // }

//     // @Override
//     // public Object visitOverlapsAfter(OverlapsAfter elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitOverlapsAfter'");
//     // }

//     // @Override
//     // public Object visitStarts(Starts elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitStarts'");
//     // }

//     // @Override
//     // public Object visitEnds(Ends elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitEnds'");
//     // }

//     // @Override
//     // public Object visitCollapse(Collapse elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCollapse'");
//     // }

//     // @Override
//     // public Object visitExpand(Expand elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitExpand'");
//     // }

//     // @Override
//     // public Object visitUnion(Union elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitUnion'");
//     // }

//     // @Override
//     // public Object visitIntersect(Intersect elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIntersect'");
//     // }

//     // @Override
//     // public Object visitExcept(Except elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitExcept'");
//     // }

//     // @Override
//     // public Object visitExists(Exists elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitExists'");
//     // }

//     // @Override
//     // public Object visitTimes(Times elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTimes'");
//     // }

//     // @Override
//     // public Object visitFilter(Filter elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitFilter'");
//     // }

//     // @Override
//     // public Object visitFirst(First elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitFirst'");
//     // }

//     // @Override
//     // public Object visitLast(Last elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLast'");
//     // }

//     // @Override
//     // public Object visitSlice(Slice elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSlice'");
//     // }

//     // @Override
//     // public Object visitChildren(Children elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitChildren'");
//     // }

//     // @Override
//     // public Object visitDescendents(Descendents elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitDescendents'");
//     // }

//     // @Override
//     // public Object visitMessage(Message elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMessage'");
//     // }

//     // @Override
//     // public Object visitIndexOf(IndexOf elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIndexOf'");
//     // }

//     // @Override
//     // public Object visitFlatten(Flatten elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitFlatten'");
//     // }

//     // @Override
//     // public Object visitSort(Sort elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSort'");
//     // }

//     // @Override
//     // public Object visitForEach(ForEach elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitForEach'");
//     // }

//     // @Override
//     // public Object visitRepeat(Repeat elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitRepeat'");
//     // }

//     // @Override
//     // public Object visitDistinct(Distinct elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitDistinct'");
//     // }

//     // @Override
//     // public Object visitCurrent(Current elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCurrent'");
//     // }

//     // @Override
//     // public Object visitIteration(Iteration elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIteration'");
//     // }

//     // @Override
//     // public Object visitTotal(Total elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitTotal'");
//     // }

//     // @Override
//     // public Object visitSingletonFrom(SingletonFrom elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSingletonFrom'");
//     // }

//     // @Override
//     // public Object visitAggregateExpression(AggregateExpression elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAggregateExpression'");
//     // }

//     // @Override
//     // public Object visitAggregate(Aggregate elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAggregate'");
//     // }

//     // @Override
//     // public Object visitCount(Count elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitCount'");
//     // }

//     // @Override
//     // public Object visitSum(Sum elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSum'");
//     // }

//     // @Override
//     // public Object visitProduct(Product elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitProduct'");
//     // }

//     // @Override
//     // public Object visitGeometricMean(GeometricMean elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitGeometricMean'");
//     // }

//     // @Override
//     // public Object visitMin(Min elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMin'");
//     // }

//     // @Override
//     // public Object visitMax(Max elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMax'");
//     // }

//     // @Override
//     // public Object visitAvg(Avg elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAvg'");
//     // }

//     // @Override
//     // public Object visitMedian(Median elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMedian'");
//     // }

//     // @Override
//     // public Object visitMode(Mode elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitMode'");
//     // }

//     // @Override
//     // public Object visitVariance(Variance elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitVariance'");
//     // }

//     // @Override
//     // public Object visitPopulationVariance(PopulationVariance elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitPopulationVariance'");
//     // }

//     // @Override
//     // public Object visitStdDev(StdDev elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitStdDev'");
//     // }

//     // @Override
//     // public Object visitPopulationStdDev(PopulationStdDev elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitPopulationStdDev'");
//     // }

//     // @Override
//     // public Object visitAllTrue(AllTrue elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAllTrue'");
//     // }

//     // @Override
//     // public Object visitAnyTrue(AnyTrue elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAnyTrue'");
//     // }

//     // @Override
//     // public Object visitProperty(Property elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitProperty'");
//     // }

//     // @Override
//     // public Object visitAliasedQuerySource(AliasedQuerySource elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAliasedQuerySource'");
//     // }

//     // @Override
//     // public Object visitLetClause(LetClause elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitLetClause'");
//     // }

//     // @Override
//     // public Object visitRelationshipClause(RelationshipClause elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitRelationshipClause'");
//     // }

//     // @Override
//     // public Object visitWith(With elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitWith'");
//     // }

//     // @Override
//     // public Object visitWithout(Without elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitWithout'");
//     // }

//     // @Override
//     // public Object visitSortByItem(SortByItem elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSortByItem'");
//     // }

//     // @Override
//     // public Object visitByDirection(ByDirection elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitByDirection'");
//     // }

//     // @Override
//     // public Object visitByColumn(ByColumn elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitByColumn'");
//     // }

//     // @Override
//     // public Object visitByExpression(ByExpression elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitByExpression'");
//     // }

//     // @Override
//     // public Object visitSortClause(SortClause elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitSortClause'");
//     // }

//     // @Override
//     // public Object visitAggregateClause(AggregateClause elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAggregateClause'");
//     // }

//     // @Override
//     // public Object visitReturnClause(ReturnClause elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitReturnClause'");
//     // }

//     // @Override
//     // public Object visitQuery(Query elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitQuery'");
//     // }

//     // @Override
//     // public Object visitAliasRef(AliasRef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitAliasRef'");
//     // }

//     // @Override
//     // public Object visitQueryLetRef(QueryLetRef elm, EvaluationContext context) {
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitQueryLetRef'");
//     // }

//     // @Override
//     // public Object visitLibrary(Library elm, EvaluationContext context) {
//     //     return super.visitLibrary(elm, context);
//     //     //throw new UnsupportedOperationException("Unimplemented method 'visitLibrary'");
//     // }

//     // @Override
//     // public Object visitUsingDef(UsingDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitUsingDef'");
//     // }

//     // @Override
//     // public Object visitIncludeDef(IncludeDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitIncludeDef'");
//     // }

//     // @Override
//     // public Object visitContextDef(ContextDef elm, EvaluationContext context) {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'visitContextDef'");
//     // }
    
// }
