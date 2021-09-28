package com.tong.common.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @description  redis 工具类
 * @author oran
 * @date  2021/2/3 9:53
 **/
@Component
public class RedisUtil {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate redisTemplate;


	/**
	 * 设置key-value
	 */
	public String set(String key, String value) {
		try {
			stringRedisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			return "error";
		}
		return "ok";
	}

	/**
	 * 获得key对应value
	 */
	public String get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 设置key-value并在seconds秒后过期
	 */
	public boolean setex(final String key, final long seconds, final String value) {

		Object results = stringRedisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) {
				try {
					((StringRedisConnection) connection).setEx(key, seconds, value);
				} catch (Exception ex) {
					return "error";
				}
				return "ok";
			}
		});
		return "ok".equals(results);
	}

	public boolean expire(final String key, final long seconds) {

		Object results = stringRedisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) {
				try {
					return ((StringRedisConnection) connection).expire(key, seconds);
				} catch (Exception ex) {
					return false;
				}
			}
		});
		return (boolean) results;
	}

	/**
	 * 删除缓存
	 */
	public void delete(final String key) {
		stringRedisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) {
				try {
					((StringRedisConnection) connection).del(key);
					return true;
				} catch (Exception ex) {
					ex.printStackTrace();
					return false;
				}
			}
		});
//		return (boolean) results;
	}


	/**
	 * 批量删除指定前缀的所有key<br/>
	 * 注意：该方法会检出符合条件的所有key依次删除，必须在确定key的大约规模情况下使用，
	 * 否则容易造成redis库卡死，慎用！
	 *
	 * @param prefix
	 * @return void
	 * @Author Ajian
	 * @Date 2021-05-05 17:42
	 **/
	public void deleteBatch(final String prefix) {
		Set<String> keys = redisTemplate.keys(prefix+"*");
		redisTemplate.delete(keys);
	}



	/**
	 * 获取redis服务器时间戳
	 */
	public Long time() {
		Object results = stringRedisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) {
				try {
					return ((StringRedisConnection) connection).time();
				} catch (Exception ex) {
					return -1;
				}
			}
		});
		return (Long) results;
	}

	/**
	 * 判断KEY是否存在
	 */
	public boolean exists(final String key) {
		Object results = stringRedisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) {
				return ((StringRedisConnection) connection).exists(key);
			}
		});
		return (boolean) results;
	}

	/**
	 * key不存在时写入（原子操作）
	 */
	public boolean setNX(final String key, final String value) {
		Object results = stringRedisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) {
				return ((StringRedisConnection) connection).setNX(key, value);
			}
		});
		return (boolean) results;
	}

	/**
	 * 数字型key加1
	 */
	public String incr(final String key) {

		Object results = stringRedisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				return ((StringRedisConnection) connection).incr(key);
			}
		});
		return results + "";
	}

	/**
	 * 数字型key减1
	 */
	public String decr(final String key) {

		Object results = stringRedisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				return ((StringRedisConnection) connection).decr(key);
			}
		});
		return results + "";
	}

	/**
	 * 取值
	 */
	public List<String> lRange(String key, long start, long end) {
		List<String> results = stringRedisTemplate.execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
				return ((StringRedisConnection) connection).lRange(key, start, end);
			}
		});

		return results;
	}

	/**
	 * 删除
	 */
	public Long lRem(String key, long count, String value) {
		Long results = stringRedisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return ((StringRedisConnection) connection).lRem(key, count, value);
			}
		});

		return results;
	}

	/**
	 * 移除列表的最后一个元素，返回值为移除的元素。
	 */
	public String rPop(String key) {
		String results = stringRedisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				return ((StringRedisConnection) connection).rPop(key);
			}
		});

		return results;
	}

	/**
	 * 添加
	 */
	public Long lPush(String key, String value) {
		Long results = stringRedisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return ((StringRedisConnection) connection).lPush(key, value);
			}
		});

		return results;
	}

	/**
	 * 截取
	 */
	public void lTrim(String key, long start, long end) {
		stringRedisTemplate.execute(new RedisCallback<>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				((StringRedisConnection) connection).lTrim(key, start, end);

				return null;
			}
		});
	}

	/**
	 * Redis Llen 命令用于返回列表的长度。 如果列表 key 不存在，则 key 被解释为一个空列表，返回 0 。 如果 key 不是列表类型，返回一个错误。
	 */
	public Long lLen(String key) {
		Long results = stringRedisTemplate.execute(new RedisCallback<>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return ((StringRedisConnection) connection).lLen(key);
			}
		});

		return results;
	}

	/**
	 * 向redis中添加一个集合
	 *
	 * @param key
	 * @param list
	 * @return long
	 * @Author Ajian
	 * @Date 2021-03-11 13:55
	 **/
	public <T> long setList(String key, Collection<T> list){
		return redisTemplate.opsForList().rightPushAll(key,list);
	}
	/**
	 * 删除集合中所有指定的item
	 *
	 * @param key
	 * @param item
	 * @return long
	 * @Author Ajian
	 * @Date 2021-03-11 13:58
	 **/
	public long removeListItem(String key,Object item){
		return redisTemplate.opsForList().remove(key,0L,item);
	}
	/**
	 * 获取指定key的集合数据
	 *
	 * @param key
	 * @return java.util.List<T>
	 * @Author Ajian
	 * @Date 2021-03-11 14:04
	 **/
	public <T> List<T> getList(String key){
		return (List<T>) redisTemplate.opsForList().range(key,0,-1);
	}

}
