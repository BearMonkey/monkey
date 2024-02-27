package org.monkey.user.factory;

import java.util.concurrent.ThreadFactory;

public class BatchThreadFactory implements ThreadFactory {

    private static int no = 1;
    private String namePattern;

    public BatchThreadFactory(String namePattern) {
        this.namePattern = namePattern;
    }

    @Override
    public Thread newThread(Runnable th) {
        Thread thread = new Thread(th);
        thread.setName(String.format(namePattern, no++));
        return thread;
    }
}
