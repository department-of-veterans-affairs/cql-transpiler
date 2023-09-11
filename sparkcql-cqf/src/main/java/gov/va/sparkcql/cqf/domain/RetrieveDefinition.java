package gov.va.sparkcql.cqf.domain;

import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ValueSetRef;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RetrieveDefinition implements Serializable {

    private QName dataType;
    private String templateId;

    private List<Filter> filters;

    public RetrieveDefinition() {
    }

    public static <T> RetrieveDefinition of(Retrieve retrieve) {
        var r = new RetrieveDefinition();
        r.setDataType(retrieve.getDataType());
        r.setTemplateId(retrieve.getTemplateId());
        r.filters = new ArrayList<>();

        if (retrieve.getCodes() instanceof ValueSetRef) {
            r.filters.add(new Filter()
                    .withProperty(retrieve.getCodeProperty())
                    .withComparator(Comparator.from(retrieve.getCodeComparator()))
                    .withValue(((ValueSetRef)retrieve.getCodes()).getName()));
        }

        return r;
    }

    public QName getDataType() {
        return dataType;
    }

    public void setDataType(QName dataType) {
        this.dataType = dataType;
    }

    public RetrieveDefinition withDataType(QName dataType) {
        this.dataType = dataType;
        return this;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public RetrieveDefinition withTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public RetrieveDefinition withFilters(List<Filter> filters) {
        this.filters = filters;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RetrieveDefinition retrieveDefinition = (RetrieveDefinition) o;

        // Verify type and template match.
        if (!Objects.equals(dataType, retrieveDefinition.dataType))
            return false;
        if (!Objects.equals(templateId, retrieveDefinition.templateId))
            return false;

        // Verify filters match by cross applying the two sets and counting the total that match.
        var matchedFilters = retrieveDefinition.filters.stream().filter(a -> ((RetrieveDefinition) o).filters.contains(a));
        return matchedFilters.count() == this.filters.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataType, templateId, filters);
    }

    public enum Comparator implements Serializable {
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        GREATER_THAN_EQUALS,
        LESS_THAN,
        LESS_THAN_EQUALS,
        IN,
        NOT_IN;

        static Comparator from(String elmCodeComparator) {
            switch (elmCodeComparator) {
                case "in": return Comparator.IN;
                case "equals": return Comparator.EQUALS;
                default:
                    throw new RuntimeException("Unexpected ELM code comparator" + elmCodeComparator + ".");
            }
        }
    }

    public static class Filter implements Serializable {

        private String property;
        private Comparator comparator;
        private Object value;

        public Filter() {
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public Filter withProperty(String property) {
            this.property = property;
            return this;
        }

        public Comparator getComparator() {
            return comparator;
        }

        public void setComparator(Comparator comparator) {
            this.comparator = comparator;
        }

        public Filter withComparator(Comparator comparator) {
            this.comparator = comparator;
            return this;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Filter withValue(Object value) {
            this.value = value;
            return this;
        }
    }
}