package gov.va.sparkcql.dataprovider

object SyntheaDataLoader {

  def loadBundles(size: PopulationSize): Seq[String] = {
    size match {
      case PopulationSize.PopulationSize10 => load10Bundles
      case PopulationSize.PopulationSize1000 => load1000Bundles
      }
  }

  lazy val load10Bundles: Seq[String] = {
    val loader = Thread.currentThread().getContextClassLoader()
    import scala.io.Source
    Seq(
      Source.fromResource("embedded/Aaron697_Brekke496_2fa15bc7-8866-461a-9000-f739e425860a.json").mkString,
      Source.fromResource("embedded/Ali918_Stokes453_f5aa3408-57b3-4c05-a1d3-e4511b4be50e.json").mkString,
      Source.fromResource("embedded/Andreas188_Dare640_f7f63ca8-d282-4520-9a68-3177e2a5db6f.json").mkString,
      Source.fromResource("embedded/Despina962_Collier206_c3127664-ea4c-482c-953a-92f24da84bba.json").mkString,
      Source.fromResource("embedded/Erline657_Casper496_f8329bff-a048-4054-8a6b-208f54a2a330.json").mkString,
      Source.fromResource("embedded/Kent912_Ruecker817_f4b034c1-9e82-4e3a-8eda-96d1b938692f.json").mkString,
      Source.fromResource("embedded/Leopoldo762_Reynolds644_ef29d12a-a4a6-4b49-b100-66efa3a01987.json").mkString,
      Source.fromResource("embedded/Monserrate4_Carter549_aa465d13-1030-4fd4-a352-a1c615ea6df2.json").mkString,
      Source.fromResource("embedded/Sina65_Wolff180_582b89e2-30d8-44fb-bb96-03957b2ec7c2.json").mkString,
      Source.fromResource("embedded/Truman805_Durgan499_277bea41-b704-4be3-972a-4feee4e2712b.json").mkString
    )
  }

  lazy val load1000Bundles: Seq[String] = {
    ???
  }
}