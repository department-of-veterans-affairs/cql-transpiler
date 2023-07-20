package gov.va.sparkcql.translator;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.Configuration;
import gov.va.sparkcql.TestBase;
import gov.va.sparkcql.adapter.AdapterFactoryProducer;
import gov.va.sparkcql.adapter.data.DataAdapter;
import gov.va.sparkcql.adapter.data.DataAdapterFactory;
import gov.va.sparkcql.adapter.library.LibraryAdapter;
import gov.va.sparkcql.adapter.library.LibraryAdapterFactory;
import gov.va.sparkcql.adapter.model.ModelAdapter;
import gov.va.sparkcql.adapter.model.ModelAdapterFactory;
import gov.va.sparkcql.compiler.Compiler;
import gov.va.sparkcql.common.LibraryWriter;
import gov.va.sparkcql.common.Resources;

public class TranslatorTest extends TestBase {

    private List<ModelAdapter> modelAdapters;
    private List<LibraryAdapter> libraryAdapters;
    private List<DataAdapter> dataAdapters;
    private Compiler compiler;
    private Translator translator;

    @BeforeEach
    public void setup() {
        var configuration = new Configuration();
        configuration.write("sparkcql.filelibraryadapter.path", "./src/test/resources/cql");
        this.modelAdapters = AdapterFactoryProducer.produceAdapters(ModelAdapterFactory.class, configuration, this.getSpark());
        this.libraryAdapters = AdapterFactoryProducer.produceAdapters(LibraryAdapterFactory.class, configuration, this.getSpark());
        this.dataAdapters = AdapterFactoryProducer.produceAdapters(DataAdapterFactory.class, configuration, this.getSpark());
        this.compiler = new Compiler();
        this.translator = new Translator(getSpark(), modelAdapters, libraryAdapters, dataAdapters);        
    }

    @Test
    public void should_translate_complex_literal() {
        var compilation = compiler.compile(Resources.read("cql/ComplexLiteral.cql"));
        LibraryWriter.write(compilation, OUTPUT_FOLDER);
        translator.visitLibrary(compilation.get(0), new Environment());
    }

    // @Test
    // public void should_test_performance_of_group_by() {
    //     List<String> data = new ArrayList<String>();
    //     for (var i = 0; i < 1000000; i++) {
    //         data.add("{groupid: 1, rowid: 1, val: 'here'}");
    //     }
    //     var ds = this.getSpark().createDataset(data, Encoders.STRING());
    //     ds.select(from_json(col("value")));
    //     Log.info(ds);
        
    //     var watch = new Stopwatch();
    //     var groupedDs = ds.groupBy("value").agg(collect_list(expr("*")));
    //     Log.info(groupedDs);

    //     Log.info(watch.toString());
    // }
}