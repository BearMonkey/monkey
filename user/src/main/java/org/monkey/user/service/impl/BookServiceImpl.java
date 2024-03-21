package org.monkey.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.monkey.user.conponent.MybatisBatchUtils;
import org.monkey.user.factory.BatchThreadFactory;
import org.monkey.user.mapper.BookMapper;
import org.monkey.user.pojo.Book;
import org.monkey.user.service.BatchInsertThread;
import org.monkey.user.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private MybatisBatchUtils mybatisBatchUtils;

    private static final int BATCH_SIZE = 10;

    @Override
    public void addBook(String name) throws Exception {
        List<List<Book>> data = getData(name);

        System.out.println("start insert");
        long start = System.currentTimeMillis();

        // 网上copy代码 ，效果差，代码没读懂
        //batch0(list);

        // 全部数据交给mybatis处理
        //batch1(list);

        // 线程池并发，存在并发问题
        threadPool(data, name);

        // 分段提交，每段1000条数据
        //segmentCommit(data);

        System.out.println("end insert, time=" + (System.currentTimeMillis() - start) + "ms");

    }

    @Override
    public void del(Integer id) {
        bookMapper.delete(id);
    }

    private void segmentCommit(List<List<Book>> data) {
        for (List<Book> item : data) {
            batch1(item);
        }
    }

    private void threadPool(List<List<Book>> data, String name) {
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(150,150,10, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(1000),
                        new BatchThreadFactory("batch-insert-thread-%d"),
                        new ThreadPoolExecutor.DiscardPolicy());

        CountDownLatch downLatch = new CountDownLatch(data.size());
        //手动方式创建线程池
        for (List<Book> item : data) {
            executor.submit(new BatchInsertThread(bookMapper, item, downLatch));
        }

        try {
            downLatch.await();
            int all = bookMapper.findAll(name);
            log.info("batch insert: complete all batch tasks, totle insert={}", all);
        } catch (InterruptedException e) {
            log.error("CountDownLatch wait error, ", e);
        }
        /*// 开一个线程查看需要多久处理完，一秒查一次数据库，知道数据==100000
        Thread thread = new Thread(() -> {
            long l = System.currentTimeMillis();
            int cnt = bookMapper.findAll() - all;
            while (cnt < 1000000) {
                try {
                    System.out.println(cnt);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cnt = bookMapper.findAll() - all;
            }
            System.out.println("=======" + (System.currentTimeMillis() - l));
        });
        thread.start();*/
    }

    private void batch0(ArrayList<Book> list) throws Exception {
        mybatisBatchUtils.batchUpdateOrInsert(
                list,
                BookMapper.class,
                (item, bookMapper1) -> bookMapper1.insert(item));
    }

    private void batch1(List<Book> data) {
        bookMapper.batchInsert(data);
    }

    private List<List<Book>> getData(String name) {
        List<List<Book>> data = new ArrayList<>();
        List<Book> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Book book = new Book();
            book.setBookName("bookName-" + i);
            book.setCreatedBy(name);
            list.add(book);
            if (i % BATCH_SIZE == 0) {
                data.add(list);
                list = new ArrayList<>();
            }
        }
        return data;
    }
}
