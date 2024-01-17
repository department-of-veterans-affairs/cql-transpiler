package gov.va.transpiler.jinja.printing.printitem2;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import gov.va.transpiler.jinja.printing.Segment;

public class PrintItemFactory {

    public enum PrintType {
        DIRECTORY,
        FILE,
        BLOCK,
        SEGMENT
    }

    private Queue<PrintDirectory> printDirectoryQueue = new ArrayDeque<>();
    private PrintItem head;

    protected void moveHeadToOpenDirectory() {
        if (head != null && !(head instanceof PrintDirectory && !head.isLocked())) {
            lockCurrentFile();
            head = head.getParent();
            moveHeadToOpenDirectory();
        }
    }

    protected void moveHeadToOpenFile() {
        if(head instanceof PrintDirectory) {
            // stop here
        } else if (!(head instanceof PrintFile) || head.isLocked()) {
            lockCurrentBlock();
            head = head.getParent();
            moveHeadToOpenFile();
        }
    }

    protected void moveHeadToOpenBlock() {
        if(head instanceof PrintFile) {
            // stop here
        } else if (!(head instanceof PrintBlock) || head.isLocked()) {
            lockCurrentSegment();
            head = head.getParent();
            moveHeadToOpenBlock();
        }
    }

    protected void moveHeadToOpenSegment() {
        if(head instanceof PrintBlock) {
            // stop here
        } else if(!(head instanceof PrintSegment)) {
            throw new RuntimeException("Invalid print state");
        } else if (head.isLocked()) {
            // we're inside a locked segment
            head = head.getParent();
            moveHeadToOpenSegment();
        }
    }

    protected void lockCurrentDirectory() {
        if (head != null) {
            boolean isHeadLockedToBeginWith = head.isLocked();
            head.lock();
            if (isHeadLockedToBeginWith || !(head instanceof PrintDirectory)) {
                head = head.getParent();
                lockCurrentDirectory();
            }
        }
    }

    protected void lockCurrentFile() {
        if (head != null && !(head instanceof PrintDirectory)) {
            boolean isHeadLockedToBeginWith = head.isLocked();
            head.lock();
            if (isHeadLockedToBeginWith || !(head instanceof PrintFile)) {
                head = head.getParent();
                lockCurrentFile();
            }
        }
    }

    protected void lockCurrentBlock() {
        if (head != null && !(head instanceof PrintDirectory || head instanceof PrintFile)) {
            boolean isHeadLockedToBeginWith = head.isLocked();
            head.lock();
            if (isHeadLockedToBeginWith || !(head instanceof PrintBlock)) {
                head = head.getParent();
                lockCurrentBlock();
            }
        }
    }

    protected void lockCurrentSegment() {
        if (head != null && !(head instanceof PrintDirectory || head instanceof PrintFile || head instanceof PrintBlock)) {
            boolean isHeadLockedToBeginWith = head.isLocked();
            head.lock();
            if (isHeadLockedToBeginWith || !(head instanceof PrintSegment)) {
                head = head.getParent();
                lockCurrentSegment();
            }
        }
    }

    public void createNewPrintItem(PrintType type, String content, boolean lockPrevious, boolean lockAfterInsert) {
        PrintItem newItem;
        switch (type) {
            case DIRECTORY:
                moveHeadToOpenDirectory();
                if (lockPrevious && head != null) {
                    head.lock();
                    moveHeadToOpenDirectory();
                }
                newItem = new PrintDirectory();
                newItem.setContent(content);
                if (head == null) {
                    head = newItem;
                    printDirectoryQueue.add((PrintDirectory) head);
                }
                break;
            case FILE:
                if (!lockPrevious) {
                    throw new RuntimeException("Cannot add file to file");
                }
                moveHeadToOpenDirectory();
                newItem = new PrintFile();
                newItem.setContent(content);
                head.addChild(newItem);
                break;
            case BLOCK:
                if (lockPrevious) {
                    while (head instanceof PrintSegment) {
                        moveHeadToOpenSegment();
                    }
                    if (head instanceof PrintBlock) {
                        head.lock();
                    }
                    moveHeadToOpenBlock();
                } else {
                    moveHeadToOpenBlock();
                }
                newItem = new PrintBlock();
                newItem.setContent(content);
                head.addChild(newItem);
                break;
            case SEGMENT:
            if (lockPrevious) {
                
            }
                newItem = new PrintSegment();
                newItem.setContent(content);
                head.addChild(newItem);
                break;
            default:
                throw new RuntimeException("unsupported print type");
        }

        if (lockAfterInsert) {
            newItem.lock();
        }
    }

    public void resolvePrinting() throws IOException {
        for (var directory: printDirectoryQueue) {
            directory.resolve();
        }
    }
}
