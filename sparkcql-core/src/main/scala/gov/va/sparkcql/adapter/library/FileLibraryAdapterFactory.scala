package gov.va.sparkcql.adapter.library

import gov.va.sparkcql.di.ComponentConfiguration

class FileLibraryAdapterFactory extends LibraryAdapterFactory {

  def create(configuration: ComponentConfiguration): LibraryAdapter = {
    new FileLibraryAdapter(configuration.read("sparkcql.filelibraryadapter.path", "./"))
  }
}