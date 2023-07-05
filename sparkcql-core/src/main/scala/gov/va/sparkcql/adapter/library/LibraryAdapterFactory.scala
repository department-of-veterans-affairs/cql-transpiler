package gov.va.sparkcql.adapter.library

import gov.va.sparkcql.di.ComponentConfiguration

trait LibraryAdapterFactory {

  def create(configuration: ComponentConfiguration): LibraryAdapter
}