package gov.va.transpiler.jinja.printing.printitem2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import gov.va.transpiler.jinja.standards.Standards;

public class PrintBlock extends PrintItem {

    protected int getIndentLevel() {
        if (getParent() instanceof PrintBlock) {
            return ((PrintBlock) getParent()).getIndentLevel() + 1;
        } else {
            return 0;
        }
    }

    @Override
    protected FileOutputStream getOutputStream() {
        return getParent().getOutputStream();
    }

    @Override
    protected void start() throws IOException {
        for (int i = 0; i < getIndentLevel(); i++) {
            getOutputStream().write(Standards.INDENT.getBytes());
        }
    }

    @Override
    protected void end() throws IOException {
        getOutputStream().write(Standards.NEWLINE.getBytes());
    }

    @Override
    protected File getPrintFile() {
        return getParent().getPrintFile();
    }
}
