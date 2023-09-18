package gov.va.transpiler;

import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;

import org.hl7.elm.r1.Library;

public abstract class ElmConverter<O, S extends ElmConverterState> extends ElmBaseLibraryVisitor<String, S> {
    /**
     * @param toConvert The top-level parent of an ELM tree
     * @param state The state tracker for a given conversion process.
     * @return Converts the elm tree transversible from {@code toConvert} into an output of type {@link O}.
     */
    public abstract O convert(Library library, S state);
}
