package com.tvd12.ezyfox.testing.pattern;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.function.EzyVoid;
import com.tvd12.ezyfox.pattern.EzyObjectFactory;
import com.tvd12.ezyfox.pattern.EzyObjectPool;
import com.tvd12.test.base.BaseTest;

public class EzyObjectPool1Test extends BaseTest {

	@Test
	public void test() throws Exception {
		MyTestObjectPool pool = MyTestObjectPool.builder()
				.maxObjects(3)
				.minObjects(1)
				.validationInterval(30)
				.build();
		pool.start();
		MyTestObject o1 = pool.borrowOne();
		Thread.sleep(31);
		MyTestObject o2 = pool.borrowOne();
		Thread.sleep(31);
		MyTestObject o3 = pool.borrowOne();
		Thread.sleep(31);
		
		pool.returnOne(o1);
		Thread.sleep(31);
		pool.returnOne(o2);
		Thread.sleep(31);
		pool.returnOne(o3);
		
		Thread.sleep(2 * 1000);
		pool.destroy();
		
		pool.getRemainObjects();
	}
	
	@Test
	public void test1() throws Exception {
		MyTestObjectPool pool = MyTestObjectPool.builder()
				.maxObjects(3)
				.minObjects(1)
				.validationInterval(30)
				.pool(new ConcurrentLinkedQueue<>())
				.objectFactory(new EzyObjectFactory<MyTestObject>() {
					@Override
					public MyTestObject newProduct() {
						return new MyTestObject();
					}
				})
				.validationService(Executors.newSingleThreadScheduledExecutor())
				.build();
		pool.start();
		MyTestObject o1 = pool.borrowOne();
		Thread.sleep(31);
		MyTestObject o2 = pool.borrowOne();
		Thread.sleep(31);
		MyTestObject o3 = pool.borrowOne();
		Thread.sleep(31);
		
		pool.returnOne(o1);
		Thread.sleep(31);
		pool.returnOne(o2);
		Thread.sleep(31);
		pool.returnOne(o3);
		
		Thread.sleep(2 * 1000);
		
	}
	
	@Test
	public void test2() throws Exception {
		MyTestObjectPool pool = MyTestObjectPool.builder()
				.maxObjects(3)
				.minObjects(1)
				.validationInterval(30)
				.pool(new ConcurrentLinkedQueue<>())
				.objectFactory(new EzyObjectFactory<MyTestObject>() {
					@Override
					public MyTestObject newProduct() {
						return new MyTestObject();
					}
				})
				.validationService(Executors.newSingleThreadScheduledExecutor())
				.build();
		pool.start();
		MyTestObject o1 = pool.borrowOne();
		Thread.sleep(31);
		MyTestObject o2 = pool.borrowOne();
		Thread.sleep(31);
		MyTestObject o3 = pool.borrowOne();
		Thread.sleep(31);
		
		pool.returnOne(o1);
		Thread.sleep(31);
		pool.returnOne(o2);
		Thread.sleep(31);
		pool.returnOne(o3);
		
		try {
			Method tryReturnObject = EzyObjectPool.class.getDeclaredMethod("tryReturnObject", Object.class);
			tryReturnObject.setAccessible(true);
			Object invoke = tryReturnObject.invoke(pool, (String)null);
			assertEquals(invoke, false);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			Method runWithLock = EzyObjectPool.class.getDeclaredMethod("runWithLock", EzyVoid.class);
			runWithLock.setAccessible(true);
			runWithLock.invoke(pool, new EzyVoid() {
				
				@Override
				public void apply() {
					throw new RuntimeException();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			Method returnWithLock = EzyObjectPool.class.getDeclaredMethod("returnWithLock", Supplier.class);
			returnWithLock.setAccessible(true);
			returnWithLock.invoke(pool, new Supplier<String>() {
				@Override
				public String get() {
					throw new RuntimeException();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
