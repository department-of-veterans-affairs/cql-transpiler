package gov.va.sparkcql.dataprovider

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import org.hl7.elm.r1.Code

class ResourceLibraryDataProvider() extends LibraryDataProvider {

  override def retrieve(spark: SparkSession, dataBindableType: Code, filter: Option[List[Object]]): Option[DataFrame] = {
    ???
  }

  // def loadResources() = {
  //   ClassLoader loader = Thread.currentThread().getContextClassLoader();
  //   URL url = loader.getResource(folder);
  //   String path = url.getPath();
  //   return new File(path).listFiles();
  // }
}