package cn.org.tpeach.nosql.redis.command.list;

import cn.org.tpeach.nosql.enums.RedisVersion;
import cn.org.tpeach.nosql.redis.command.JedisDbCommand;
import cn.org.tpeach.nosql.redis.command.RedisLarkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author tyz
 * @Title: LpushList
 * @ProjectName RedisLark
 * @Description: TODO
 * @date 2019-07-03 0:36
 * @since 1.0.0
 */
public class RpushList extends JedisDbCommand<Long> {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RpushList.class);
    private byte[] key;
    private byte[][] values;

    /**
     * 命令：RPUSH key value [value ...]
     *
     * @param id
     * @param db
     * @param key
     * @param values
     */
    public RpushList(String id, int db, byte[] key, byte[]... values) {
        super(id, db);
        this.key = key;
        this.values = values;
    }

    @Override
    public String sendCommand() {
        return "RPUSH "+ byteToStr(key) +" "+ Arrays.stream(byteArrToStr(values)).collect(Collectors.joining(" "));
    }
    /**
     * 将一个或多个值value插入到列表key的表尾。
     * 如果有多个value值，那么各个value值按从左到右的顺序依次插入到表尾：
     * 比如对一个空列表(mylist)执行RPUSH mylist a b c，则结果列表为a b c，等同于执行命令RPUSH mylist a、RPUSH mylist b、RPUSH mylist c。
     * 如果key不存在，一个空列表会被创建并执行RPUSH操作。
     * 当key存在但不是列表类型时，返回一个错误。
     * 时间复杂度：O(1)
     *
     * @param redisLarkContext
     * @return 执行RPUSH操作后，表的长度。
     */
    @Override
    public Long concreteCommand(RedisLarkContext<byte[], byte[]> redisLarkContext) {
        super.concreteCommand(redisLarkContext);
        final Long response = redisLarkContext.rpush(key, values);
        return response;
    }

    @Override
    public RedisVersion getSupportVersion() {
        return RedisVersion.REDIS_1_0;
    }
}
