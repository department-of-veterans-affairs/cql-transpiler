package gov.va.sparkcql.core.transformer

trait Serializer {
  /**
    * TODO: As CQL and ELM evolve, new versions beyond R1 will be released. Need a design to
    * handle multiple concurrent versions. Consider using the same SPI framework for translation
    * as is done with data, model, and sources. Will require a robust and backwards compatble
    * canonical model for core concepts. Since this is a major undertaking and is dependent on the
    * future of CQL design, deferring this design until R2 is developed.
    */
}