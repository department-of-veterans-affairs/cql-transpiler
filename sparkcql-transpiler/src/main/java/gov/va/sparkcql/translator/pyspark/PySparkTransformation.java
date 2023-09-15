package gov.va.sparkcql.translator.pyspark;

import org.hl7.elm.r1.Element;

import gov.va.sparkcql.translator.Transformation;

public abstract class PySparkTransformation<T extends Element> extends Transformation {

    public abstract Class<T> transformsClass();
}
