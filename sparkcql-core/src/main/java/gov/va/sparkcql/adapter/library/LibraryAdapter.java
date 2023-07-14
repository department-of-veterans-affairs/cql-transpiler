package gov.va.sparkcql.adapter.library;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.adapter.Adapter;

public interface LibraryAdapter extends Adapter {
    
    public String getLibrarySource(VersionedIdentifier identifier);

    public Boolean containsLibrary(VersionedIdentifier identifier);
}