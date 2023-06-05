// package gov.va.sparkcql.dataprovider.library

// import gov.va.sparkcql.TestBase
// import org.hl7.elm.r1.VersionedIdentifier

// class FileLibraryDataProviderTest extends TestBase {

//   lazy val provider = new FileLibraryDataProvider("./src/test/resources/")

//   "A FileLibraryDataProvider" should "fetch a CQL from a files" in {
//     assert(provider.fetchOne(new VersionedIdentifier().withId("BasicRetrieve").withVersion("1.0")) != None)
//   }

//   it should "inform when a key exists" in {
//     assert(provider.isDefined(new VersionedIdentifier().withId("LiteralDefinitions").withVersion("1.0")))
//   }

//   it should "inform when a key doesn't exist without erroring" in {
//     assert(!provider.isDefined(new VersionedIdentifier().withId("DoesNotExist").withVersion("1.0")))
//   }

//   it should "fetch version agnostically" in {
//     assert(provider.fetchOne(new VersionedIdentifier().withId("LiteralDefinitions")) != None)
//   }
// }