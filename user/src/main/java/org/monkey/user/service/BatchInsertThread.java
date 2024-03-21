package org.monkey.user.service;

import lombok.extern.slf4j.Slf4j;
import org.monkey.user.mapper.BookMapper;
import org.monkey.user.pojo.Book;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class BatchInsertThread implements Runnable{

    private BookMapper bookMapper;

    private List<Book> data;

    private CountDownLatch countDownLatch;

    public BatchInsertThread(BookMapper bookMapper, List<Book> data, CountDownLatch countDownLatch) {
        this.bookMapper = bookMapper;
        this.data = data;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        boolean complete = false; // 是否完成
        int sleepCnt = 10;
        while (!complete) {
            try {
                bookMapper.batchInsert(data);
                complete = true;
                log.info("batch insert: complete batch, size={}", data.size());
            } catch (Throwable e) {
                log.error("batch insert: batch failed {}.", data.size(), e);
                if (sleepCnt > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        log.error("batch insert: thread sleep exception, ", ex);
                    }
                    sleepCnt--;
                } else {
                    insertFailedBatch(data);
                    complete = true;
                }
            } finally {
                if (complete) {
                    countDownLatch.countDown();
                }
            }
        }
    }

    private void insertFailedBatch(List<Book> data) {

        int cnt = 0;
        for (Book item : data) {
            try {
                bookMapper.insert(item);
            } catch (Throwable e) {
                log.error("batch insert: failed record: {}.", item, e);
                cnt++;
            }
        }
        log.info("batch insert: insertFailedBatch complete, failed record number={}", cnt);
    }
}
