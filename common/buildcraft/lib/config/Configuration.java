package buildcraft.lib.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class Configuration {
    private final ForgeConfigSpec.Builder builder;

    private final String fileName;

    private final Lock lock = new ReentrantLock();

    private final List<ConfigValue<?>> all = new CopyOnWriteArrayList<>();

    public Configuration(ForgeConfigSpec.Builder builder, String fileName) {
        this.builder = builder;
        this.fileName = fileName;
    }

    public Builder getBuilder() {
        return builder;
    }

    public List<ConfigValue<?>> getAll() {
        return all;
    }

    public String getFileName() {
        return fileName;
    }

    public synchronized BooleanValue define(String category, String comment, EnumRestartRequirement worldRestart, String subPath, boolean defaultValue) {
        lock.lock();
        comment = ensureCommentNotEmpty(comment, subPath);
        String fullPath = category + "." + subPath;
        builder
                .translation("config." + fullPath)
                .comment(comment);
        if (worldRestart == EnumRestartRequirement.WORLD) {
            builder.worldRestart();
        }
        BooleanValue ret = builder.define(fullPath, defaultValue);
        all.add(ret);
        lock.unlock();
        return ret;
    }

    public synchronized IntValue defineInRange(String category, String comment, EnumRestartRequirement worldRestart, String subPath, int defaultValue, int min, int max) {
        lock.lock();
        comment = ensureCommentNotEmpty(comment, subPath);
        String fullPath = category + "." + subPath;
        builder
                .translation("config." + fullPath)
                .comment(comment);
        if (worldRestart == EnumRestartRequirement.WORLD) {
            builder.worldRestart();
        }
        IntValue ret = builder.defineInRange(fullPath, defaultValue, min, max);
        all.add(ret);
        lock.unlock();
        return ret;
    }

    public synchronized IntValue defineInRange(String category, String comment, EnumRestartRequirement worldRestart, String subPath, int defaultValue, int min) {
        lock.lock();
        comment = ensureCommentNotEmpty(comment, subPath);
        String fullPath = category + "." + subPath;
        builder
                .translation("config." + fullPath)
                .comment(comment);
        if (worldRestart == EnumRestartRequirement.WORLD) {
            builder.worldRestart();
        }
        IntValue ret = builder.defineInRange(fullPath, defaultValue, min, Integer.MAX_VALUE);
        all.add(ret);
        lock.unlock();
        return ret;
    }

    public synchronized LongValue defineInRange(String category, String comment, EnumRestartRequirement worldRestart, String subPath, long defaultValue, long min) {
        lock.lock();
        comment = ensureCommentNotEmpty(comment, subPath);
        String fullPath = category + "." + subPath;
        builder
                .translation("config." + fullPath)
                .comment(comment);
        if (worldRestart == EnumRestartRequirement.WORLD) {
            builder.worldRestart();
        }
        LongValue ret = builder.defineInRange(fullPath, defaultValue, min, Integer.MAX_VALUE);
        all.add(ret);
        lock.unlock();
        return ret;
    }

    public synchronized IntValue defineInRange(String category, String comment, EnumRestartRequirement worldRestart, String subPath, int defaultValue) {
        lock.lock();
        comment = ensureCommentNotEmpty(comment, subPath);
        String fullPath = category + "." + subPath;
        builder
                .translation("config." + fullPath)
                .comment(comment);
        if (worldRestart == EnumRestartRequirement.WORLD) {
            builder.worldRestart();
        }
        IntValue ret = builder.defineInRange(fullPath, defaultValue, 0, Integer.MAX_VALUE);
        all.add(ret);
        lock.unlock();
        return ret;
    }

    public synchronized DoubleValue defineInRange(String category, String comment, EnumRestartRequirement worldRestart, String subPath, double defaultValue, double min, double max) {
        lock.lock();
        comment = ensureCommentNotEmpty(comment, subPath);
        String fullPath = category + "." + subPath;
        builder
                .translation("config." + fullPath)
                .comment(comment);
        if (worldRestart == EnumRestartRequirement.WORLD) {
            builder.worldRestart();
        }
        DoubleValue ret = builder.defineInRange(fullPath, defaultValue, min, max);
        all.add(ret);
        lock.unlock();
        return ret;
    }

    public synchronized <V extends Enum<V>> EnumValue<V> defineEnum(String category, String comment, EnumRestartRequirement worldRestart, String subPath, V defaultValue) {
        lock.lock();
        comment = ensureCommentNotEmpty(comment, subPath);
        String fullPath = category + "." + subPath;
        builder
                .translation("config." + fullPath)
                .comment(comment);
        if (worldRestart == EnumRestartRequirement.WORLD) {
            builder.worldRestart();
        }
        EnumValue<V> ret = builder.defineEnum(fullPath, defaultValue);
        all.add(ret);
        lock.unlock();
        return ret;
    }

    public synchronized <T> ConfigValue<List<? extends T>> defineList(String category, String comment, EnumRestartRequirement worldRestart, String subPath, List<? extends T> defaultValue, Predicate<Object> elementValidator) {
        lock.lock();
        comment = ensureCommentNotEmpty(comment, subPath);
        String fullPath = category + "." + subPath;
        builder
                .translation("config." + fullPath)
                .comment(comment);
        if (worldRestart == EnumRestartRequirement.WORLD) {
            builder.worldRestart();
        }
        ConfigValue<List<? extends T>> ret = builder.defineList(fullPath, defaultValue, elementValidator);
        all.add(ret);
        lock.unlock();
        return ret;
    }

    public synchronized ForgeConfigSpec build() {
        lock.lock();
        ForgeConfigSpec spec = builder.build();
        lock.unlock();
        return spec;
    }

    private String ensureCommentNotEmpty(String comment, String subPath) {
        if (comment.equals("")) {
            comment = subPath;
        }
        return comment;
    }
}
