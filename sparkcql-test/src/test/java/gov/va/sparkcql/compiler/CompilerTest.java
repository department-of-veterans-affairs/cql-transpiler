// package gov.va.sparkcql.compiler;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.List;

// import org.cqframework.cql.cql2elm.CqlTranslator;
// import org.cqframework.cql.cql2elm.LibraryManager;
// import org.cqframework.cql.cql2elm.ModelManager;
// import org.hl7.cql_annotations.r1.CqlToElmError;
// import org.hl7.elm.r1.Library;
// import org.hl7.elm.r1.VersionedIdentifier;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.opencds.cqf.cql.engine.execution.CqlEngine;

// import gov.va.sparkcql.di.ComponentFactory;

// public class CompilerTest {

//     protected Compiler compiler;

//     @BeforeEach
//     public void setup() {
//         compiler = new Compiler();
//     }

//     @Test
//     public void should_compile_a_literal_cql() {
//         assertEquals("MyLibrary", compiler.compile("library MyLibrary version '1'").get(0).getIdentifier().getId());
//     }

//     @Test
//     public void should_support_multiple_independent_modules() {
//         var output = compiler.compile(
//             "library MyLibrary version '1'",
//             "library MyOtherLibrary version '2'",
//             "library OurLibrary version '3'"
//         );
//         assertLibraries(3, output);
//     }

//     @Test
//     public void should_support_multiple_dependent_modules() {
//         var output = compiler.compile(
//             "library MyLibrary version '1'\ninclude MyDependency version '2'",
//             "library MyDependency version '2'"
//         );
//         assertLibraries(2, output);
//     }

//     @Test
//     public void should_support_anonymous_modules() {
//         var output = compiler.compile(
//             "define myconst: 123", "define myconst: 456", "define myconst: 789"
//         );
//         assertLibraries(3, output);
//     }

//     @Test
//     public void should_handle_duplicates_with_differing_versions() {
//         var output = compiler.compile(
//             "library MyLibrary version '1'", "library MyLibrary version '2'"
//         );
//         assertLibraries(2, output);
//     }

//     @Test
//     public void should_allow_file_repository_loading() {
//         var fileCompiler = new Compiler(new CqlSourceFileRepository("./src/test/resources/cql"));
//         var output = fileCompiler.compile(List.of(new VersionedIdentifier().withId("ComplexLiteral").withVersion("2.1")));
//         assertLibraries(1, output);
//     }

//     @Test
//     public void should_not_break_spark() {
//         var spark = ComponentFactory.createSpark();
//         var ds = spark.sql("select 12345 foo");
//     }

//     @Test
//     public void should_allow_library_manager_usage() {
//         ModelManager modelManager = new ModelManager();
//         var libraryManager = new LibraryManager(modelManager);
//         libraryManager.getLibrarySourceLoader().registerProvider(compiler);
//         var loaders = libraryManager.getLibrarySourceLoader();
//         libraryManager.beginCompilation("ComplexLiteral");
//         libraryManager.endCompilation("ComplexLiteral");
//         var z = new CqlEngine(null)
//         // var x = libraryManager.resolveLibrary(new VersionedIdentifier().withId("ComplexLiteral").withVersion("2.1"), CqlCompilerOptions.defaultOptions(), null);
//         // var y = x;
//     }

//     private void assertLibraries(int expectedCount, List<Library> output) {
//       assertEquals(expectedCount, output.size());
//       output.stream().forEach(l -> {
//         var a = l.getAnnotation();
//         if (a.size() > 1) {
//             CqlToElmError e = (CqlToElmError)a.get(1);
//             throw new RuntimeException("CQL compilation error: " + e.toString());
//         }
//       });
//     }
// }