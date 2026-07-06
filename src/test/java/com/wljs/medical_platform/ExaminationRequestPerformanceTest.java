package com.wljs.medical_platform;

import com.wljs.medical_platform.entity.ExaminationRequest;
import com.wljs.medical_platform.mapper.ExaminationRequestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class ExaminationRequestPerformanceTest {

    @Autowired
    private ExaminationRequestMapper examinationRequestMapper;

    @Test
    void testConcurrentAccess() throws Exception {
        int threadCount = 10; // 模拟10个并发用户
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                // 每个线程执行查询操作
                for (int j = 0; j < 100; j++) {
                    List<ExaminationRequest> requests = examinationRequestMapper.findAll();
                    System.out.println("线程 " + threadId + " 第 " + j + " 次查询，返回 " + requests.size() + " 条记录");
                }
            }, executor);
            futures.add(future);
        }

        // 等待所有线程完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("多用户并发测试完成");
        System.out.println("线程数: " + threadCount);
        System.out.println("总执行时间: " + duration + "ms");
        System.out.println("平均每个查询: " + (duration / (threadCount * 100.0)) + "ms");

        executor.shutdown();
    }
}