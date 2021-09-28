package com.tong.common.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池使用监控
 * 
 * @author: chentianjin
 * @date: 2021年4月6日 下午6:00:13
 */
@Component
@Slf4j
public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

	private static final long serialVersionUID = -6655746154293201866L;

	private void showThreadPoolInfo(String prefix) {
		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

		if (null == threadPoolExecutor) {
			return;
		}

		log.info("===============================================================threadPoolExecutor===========" + threadPoolExecutor);
		log.info("{}, {},任务总数 [{}], 已完成数 [{}], 活跃线程数 [{}], 队列大小 [{}]", this.getThreadNamePrefix(), prefix, threadPoolExecutor.getTaskCount(), threadPoolExecutor.getCompletedTaskCount(),
				threadPoolExecutor.getActiveCount(), threadPoolExecutor.getQueue().size());
		log.info("===============================================================threadPoolExecutor===========" + this);
	}

	@Override
	public void execute(Runnable task) {
		showThreadPoolInfo("1. do execute");
		super.execute(task);
	}

	@Override
	public void execute(Runnable task, long startTimeout) {
		showThreadPoolInfo("2. do execute");
		super.execute(task, startTimeout);
	}

	@Override
	public Future<?> submit(Runnable task) {
		showThreadPoolInfo("1. do submit");
		return super.submit(task);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		showThreadPoolInfo("2. do submit");
		return super.submit(task);
	}

	@Override
	public ListenableFuture<?> submitListenable(Runnable task) {
		showThreadPoolInfo("1. do submitListenable");
		return super.submitListenable(task);
	}

	@Override
	public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
		showThreadPoolInfo("2. do submitListenable");
		return super.submitListenable(task);
	}
}