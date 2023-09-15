package gov.va.sparkcql.translator.pyspark;

import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.translator.CqlElmTranslator;
import gov.va.sparkcql.translator.TransformationState;
import gov.va.sparkcql.translator.Transformer;

public class CqlToPySparkElmTranslator implements CqlElmTranslator {

    private Transformer transformer;

    public CqlToPySparkElmTranslator(Transformer cqlToPySparkTranslator) {
        this.transformer = cqlToPySparkTranslator;
    }

    @Override
    public List<Library> translate(List<Library> cql) {

        for (Library library : cql) {
            // Create the state of the for the transformation transversal
            var transformationState = new TransformationState();
            // setting the depth to -1 means the TransformationState will transverse to the bottom of the tree
            transformationState.enterSubtreeSettingMaximumDepth(-1);
            // TODO: create a list with top-level parent nodes linking to each of the Libraries in cql.
            //  Transform from those top-level parent nodes.
            //  Collect the children of those top-level parents and return them as the output of this function.
            transformationState.putNode(null);
            transformer.visitElement(library, transformationState);
        }
        return cql;
    }
}