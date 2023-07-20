package gov.va.sparkcql.adapter.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.xml.namespace.QName;

import org.junit.jupiter.api.Test;

public class ModelAdapterTest {

    @Test
    public void should_support_empty_aggregations() {
        var adapter = new ModelAdapterAggregator(List.of());
        assertTrue(adapter.supportedDataTypes().size() == 0);
        assertThrows(
            RuntimeException.class,
            () -> {
                adapter.schemaOf(new QName("", "", ""));
            }
        );
    }
}