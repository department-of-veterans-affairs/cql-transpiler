package gov.va.sparkcql.pipeline.evaluator;

import org.opencds.cqf.cql.engine.runtime.Code;
import org.opencds.cqf.cql.engine.terminology.CodeSystemInfo;
import org.opencds.cqf.cql.engine.terminology.TerminologyProvider;
import org.opencds.cqf.cql.engine.terminology.ValueSetInfo;

public class TerminologyProviderAdapter implements TerminologyProvider {

    @Override
    public boolean in(Code code, ValueSetInfo valueSet) {
        return false;
    }

    @Override
    public Iterable<Code> expand(ValueSetInfo valueSet) {
        return null;
    }

    @Override
    public Code lookup(Code code, CodeSystemInfo codeSystem) {
        return null;
    }
}
