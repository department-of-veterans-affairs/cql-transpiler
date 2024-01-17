package gov.va.transpiler.jinja.printing.printitem2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PrintFile extends PrintItem {

    private FileOutputStream fileOutputStream = null;

    @Override
    protected void start() throws IOException {
        getPrintFile().createNewFile();
        fileOutputStream = new FileOutputStream(getPrintFile());
    }

    @Override
    protected void end() throws IOException {
       fileOutputStream.close();
       fileOutputStream = null;
    }

    @Override
    protected File getPrintFile() {
        return new File(getParent().getPrintFile().getPath() + getContent());
    }

    @Override
    protected FileOutputStream getOutputStream() {
        return fileOutputStream;
    }
}
