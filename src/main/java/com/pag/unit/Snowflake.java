package com.pag.unit;

/**
 * 生成雪花ID
 */
public class Snowflake {

    /** 开始时间戳 (2021-01-01) */
    private final long START_TIMESTAMP = 1609430400000L;

    /** 机器ID所占的位数 */
    private final long WORKER_ID_BITS = 5L;

    /** 数据标识ID所占的位数 */
    private final long DATA_CENTER_ID_BITS = 5L;

    /** 支持的最大机器ID，结果是31 (0B11111) */
    private final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /** 支持的最大数据标识ID，结果是31 (0B11111) */
    private final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    /** 序列在ID中占的位数 */
    private final long SEQUENCE_BITS = 12L;

    /** 机器ID向左移12位 */
    private final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /** 数据标识ID向左移17位(12+5) */
    private final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /** 时间戳向左移22位(5+5+12) */
    private final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /** 支持的最大序列号，结果是4095 (0B111111111111) */
    private final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    /** 工作机器ID */
    private final long workerId;

    /** 数据中心ID */
    private final long dataCenterId;

    /** 毫秒内序列号 */
    private long sequence = 0L;

    /** 上次生成ID的时间戳 */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     * @param workerId 工作机器ID
     * @param dataCenterId 数据中心ID
     */
    public Snowflake(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("WorkerID不能超过%d且不能小于0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("DataCenterID不能超过%d且不能小于0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 生成ID
     * @return long类型的ID
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        // 如果当前时间小于上次生成ID的时间戳，说明系统时钟回退过，抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("系统时钟回退，拒绝生成ID，上次生成ID的时间戳：%d，当前时间戳：%d",
                    lastTimestamp, timestamp));
        }

        // 如果当前时间等于上次生成ID的时间戳（同一毫秒内），则序列号加1
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // 如果序列号已经超过最大值，需要等待到下一毫秒再继续生成ID
                timestamp = waitNextMillis(timestamp);
            }
        } else {
            sequence = 0L;
        }

        // 更新上次生成ID的时间戳
        lastTimestamp = timestamp;

        // 生成ID
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT) |
                (dataCenterId << DATA_CENTER_ID_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence;
    }

    /**
     * 等待下一毫秒
     * @param timestamp 上次生成ID的时间戳
     * @return 下一毫秒的时间戳
     */
    private long waitNextMillis(long timestamp) {
        long nextTimestamp = System.currentTimeMillis();
        while (nextTimestamp <= timestamp) {
            nextTimestamp = System.currentTimeMillis();
        }
        return nextTimestamp;
    }

    // 示例
    public static void main(String[] args) {
        Snowflake snowflake = new Snowflake(1, 1);
        System.out.println(snowflake.nextId());

        TencentCosUtil util=new TencentCosUtil();
        util.uploadObject("");
    }
}