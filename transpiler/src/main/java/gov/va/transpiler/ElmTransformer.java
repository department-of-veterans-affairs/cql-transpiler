package gov.va.transpiler;

import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.Library;

public abstract class ElmTransformer extends ElmBaseLibraryVisitor<Integer, ElmTransformerState>{

    /**
     * @param toTransform The top-level parent of an ELM tree
     * @param state The state tracker for a given conversion process.
     * @return Transforms the ELM tree transversible from {@code toConvert} using the {@link Transformation}s provided to this tree.
     */
    public abstract void transform(Library toTransform, ElmTransformerState state);
}
