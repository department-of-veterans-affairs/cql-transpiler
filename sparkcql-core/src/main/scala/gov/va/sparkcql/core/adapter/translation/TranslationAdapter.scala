package gov.va.sparkcql.core.adapter.model

import gov.va.sparkcql.core.adapter.Adapter

trait TranslationAdapter extends Adapter {
  /**
    * TODO: As CQL and ELM evolve, new versions beyond R1 will be released. Need a design to
    * handle multiple concurrent versions. Consider using the same adapter framework for translation
    * as is done with data, model, and sources. Will require a robust and backwards compatble
    * canonical model for core concepts. Since this is a major undertaking and is dependent on the
    * future of CQL design, deferring this design until R2 is developed.
    */
}