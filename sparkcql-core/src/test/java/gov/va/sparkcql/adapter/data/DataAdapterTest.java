package gov.va.sparkcql.adapter.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.xml.namespace.QName;

import org.junit.jupiter.api.Test;

public class DataAdapterTest {

    @Test
    public void should_support_empty_aggregations() {
        var adapter = new DataAdapterAggregator(List.of());
        assertFalse(adapter.isDataTypeDefined(new QName("", "", "")));
        assertThrows(
            RuntimeException.class,
            () -> {
                adapter.acquireData(new QName("", "", ""));
            }
        );
    }
}