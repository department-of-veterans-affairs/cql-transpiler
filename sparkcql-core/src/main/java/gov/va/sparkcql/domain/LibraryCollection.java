package gov.va.sparkcql.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonMapper;
import org.hl7.elm.r1.Library;

public class LibraryCollection implements List<Library>, Serializable {

    private ArrayList<Library> libraries;

    public LibraryCollection() {
        this.libraries = new ArrayList<>();
    }

    public LibraryCollection(List<Library> libraries) {
        this.libraries = new ArrayList<Library>(libraries);
    }
    
    // The ELM encounters serialization issues during Spark broadcasting so we implement serialization manually.
	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
        // Read the length written during serialization.
        var size = input.readInt();
        this.libraries = new ArrayList<>(size);
        // Use the ELM mapper to deserialize back into a list of Libraries.
        var mapper = ElmJsonMapper.getMapper();
        for (var i = 0; i < size; i++) {
            var library = mapper.readValue((String)input.readObject(), Library.class);
            libraries.add(library);
        }
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
        // Write the length so we know how many items to read during deserialization.
        output.writeInt(this.libraries.size());
        // Use the ELM mapper to serialize a list of Libraries.
        var mapper = ElmJsonMapper.getMapper();
        this.forEach(library -> {
            try {
                output.writeObject(mapper.writeValueAsString(library));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
	}

    @Override
    public boolean add(Library library) {
        return this.libraries.add(library);
    }

    @Override
    public void add(int index, Library library) {
        this.libraries.add(index, library);
    }

    @Override
    public boolean addAll(Collection<? extends Library> libraries) {
        return this.libraries.addAll(libraries);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Library> libraries) {
        return this.libraries.addAll(index, libraries);
    }

    @Override
    public void clear() {
        this.libraries.clear();
    }

    @Override
    public boolean contains(Object library) {
        return this.libraries.contains(library);
    }

    @Override
    public boolean containsAll(Collection<?> libraries) {
        return this.libraries.containsAll(libraries);
    }

    @Override
    public Library get(int index) {
        return this.libraries.get(index);
    }

    @Override
    public int indexOf(Object library) {
        return this.libraries.indexOf(library);
    }

    @Override
    public boolean isEmpty() {
        return this.libraries.isEmpty();
    }

    @Override
    public Iterator<Library> iterator() {
        return this.libraries.iterator();
    }

    @Override
    public int lastIndexOf(Object library) {
        return this.libraries.lastIndexOf(library);
    }

    @Override
    public ListIterator<Library> listIterator() {
        return this.libraries.listIterator();
    }

    @Override
    public ListIterator<Library> listIterator(int index) {
        return this.libraries.listIterator(index);
    }

    @Override
    public boolean remove(Object library) {
        return this.libraries.remove(library);
    }

    @Override
    public Library remove(int index) {
        return this.libraries.remove(index);
    }

    @Override
    public boolean removeAll(Collection<?> libraries) {
        return this.libraries.removeAll(libraries);
    }

    @Override
    public boolean retainAll(Collection<?> libraries) {
        return this.libraries.retainAll(libraries);
    }

    @Override
    public Library set(int index, Library library) {
        return this.libraries.set(index, library);
    }

    @Override
    public int size() {
        return this.libraries.size();
    }

    @Override
    public List<Library> subList(int fromIndex, int toIndex) {
        return this.libraries.subList(fromIndex, toIndex);
    }

    @Override
    public Object[] toArray() {
        return this.libraries.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.libraries.toArray(a);
    }
}