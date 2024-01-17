package gov.va.transpiler.jinja.printing.printitem2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PrintSegment extends PrintItem {

    @Override
    protected FileOutputStream getOutputStream() {
        return getParent().getOutputStream();
    }

    @Override
    protected void start() throws IOException {
        getOutputStream().write(getContent().getBytes());
    }

    @Override
    protected void end() throws IOException {
        // do nothing
    }

    @Override
    protected File getPrintFile() {
        return getParent().getPrintFile();
    }
}
