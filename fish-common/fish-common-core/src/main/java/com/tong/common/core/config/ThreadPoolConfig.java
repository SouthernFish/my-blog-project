package com.tong.common.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author: chentianjin
 * @date: 2021年4月6日 下午6:00:30
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    /**
     * 线程池维护线程的最少数量,当ThreadPoolTaskExecutor初始化后不会创建thread到corePoolSize，当收到一个execute请求后就增加一个到corePoolSize至到达到配置数。
     **/
    @Value("${thread.corePoolSize:2}")
    private int corePoolSize;
    /**
     * 当corePoolSize和queueCapacity都满载时且池中线程数还小于maxPoolSize时，池会创建新的线程至maxPoolSize释放queueCapacity。
     **/
    @Value("${thread.maxPoolSize:20}")
    private int maxPoolSize;
    /**
     * 缓存队列，线程池维护线程的最大数量,当任务持续增加时corePoolSize达到上限，则放入queue中，上限为queueCapacity
     **/
    @Value("${thread.queueCapacity:5000}")
    private int queueCapacity;
    /**
     * 允许的空闲时间,单位s
     **/
    @Value("${thread.keepAliveSeconds:300}")
    private int keepAliveSeconds;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    /**
     * 全局公用的线程池任务对象
     *
     * @return
     * @author: chentianjin
     * @date: 2021年4月6日 下午6:00:39
     */
    @Bean
    public ThreadPoolTaskExecutor commonThreadPool() {

        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        // 配置队列大小
        executor.setQueueCapacity(queueCapacity);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 设置线程池中线程名称前缀
        executor.setThreadNamePrefix("-systemThreadPool-");
        // 设置拒绝策略,rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        return executor;
    }
}
