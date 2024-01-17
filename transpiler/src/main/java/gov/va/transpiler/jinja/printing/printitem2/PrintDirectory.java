package gov.va.transpiler.jinja.printing.printitem2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import gov.va.transpiler.jinja.standards.Standards;

public class PrintDirectory extends PrintItem {

    @Override
    protected void start() throws IOException {
        getPrintFile().mkdir();
    }

    @Override
    protected void end() throws IOException {
        // Do nothing
    }

    @Override
    protected File getPrintFile() {
        if (getParent() == null) {
            return new File(getContent() + Standards.FOLDER_SEPARATOR);
        } else {
            return new File(getParent().getPrintFile().getPath() + getContent() + Standards.FOLDER_SEPARATOR);
        }
    }

    @Override
    protected FileOutputStream getOutputStream() {
        throw new UnsupportedOperationException("Can't print to directory");
    }
}
