package gov.va.sparkcql.core.adapter

import gov.va.sparkcql.core.adapter.model.CompositeModelAdapter
import gov.va.sparkcql.core.adapter.source.CompositeSourceAdapter

case class AdapterSet(model: CompositeModelAdapter, source: CompositeSourceAdapter)