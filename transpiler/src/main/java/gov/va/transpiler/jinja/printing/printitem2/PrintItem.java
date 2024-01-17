package gov.va.transpiler.jinja.printing.printitem2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public abstract class PrintItem {
    private PrintItem parent = null;
    private boolean locked = false;
    private Queue<PrintItem> children = new ArrayDeque<>();
    private String content = null;
    public void setParent(PrintItem parent) {
        this.parent = parent;
    }
    public PrintItem getParent() {
        return parent;
    }
    public void lock() {
        locked = true;
    }
    public boolean isLocked() {
        return locked;
    }
    public void addChild(PrintItem child) {
        if (!isLocked()) {
            children.add(child);
        } else {
            throw new RuntimeException("PrintItem already locked");
        }
    }
    protected Queue<PrintItem> getChildren() {
        return children;
    }
    public void setContent(String content) {
        this.content = content;
    }
    protected String getContent() {
        return content;
    }
    public void resolve() throws IOException {
        start();
        for (var child: children) {
            child.resolve();
        }
        end();
    }
    protected abstract void start() throws IOException;
    protected abstract void end() throws IOException;
    protected abstract File getPrintFile();
    protected abstract FileOutputStream getOutputStream();
}
