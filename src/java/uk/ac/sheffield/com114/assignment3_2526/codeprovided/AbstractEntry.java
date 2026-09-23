package java.uk.ac.sheffield.com114.assignment3_2526.codeprovided;

import java.util.List;

// This abstract class provides a basic implementation to represent the entries in an EntryCatalog.
// You need to implement the abstract methods in this class by overriding them.
// You can not change the implementation of this class as it is part of a codeprovided package

public abstract class AbstractEntry {
    private final int id;

    public AbstractEntry(int id, List<String> entryLine) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
