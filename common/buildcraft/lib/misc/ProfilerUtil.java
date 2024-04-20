package buildcraft.lib.misc;


import buildcraft.api.core.BCLog;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;

/**
 * Provides a few methods for writing the results from a vanilla {@link ProfilerFiller} to a file or something else.
 */
public class ProfilerUtil {

    /**
     * Calls {@link #writeProfilerResults(ActiveProfiler, String, ILogAcceptor)} with {@link System#out} as the
     * {@link ILogAcceptor}.
     */
//    public static void printProfilerResults(Profiler profiler, String rootName)
    public static void printProfilerResults(ActiveProfiler profiler, String rootName) {
        printProfilerResults(profiler, rootName, -1);
    }

    /**
     * Calls {@link #writeProfilerResults(ActiveProfiler, String, ILogAcceptor)} with {@link System#out} as the
     * {@link ILogAcceptor}.
     */
//    public static void printProfilerResults(Profiler profiler, String rootName, long totalNanoseconds)
    public static void printProfilerResults(ActiveProfiler profiler, String rootName, long totalNanoseconds) {
        writeProfilerResults(profiler, rootName, totalNanoseconds, System.out::println);
    }

    /**
     * Calls {@link #writeProfilerResults(ActiveProfiler, String, ILogAcceptor)} with {@link BCLog#logger
     * BCLog.logger}::{@link org.apache.logging.log4j.Logger#info(CharSequence) info} as the {@link ILogAcceptor}.
     */
//    public static void logProfilerResults(Profiler profiler, String rootName)
    public static void logProfilerResults(ActiveProfiler profiler, String rootName) {
        logProfilerResults(profiler, rootName, -1);
    }

    /**
     * Calls {@link #writeProfilerResults(ActiveProfiler, String, ILogAcceptor)} with {@link BCLog#logger
     * BCLog.logger}::{@link org.apache.logging.log4j.Logger#info(CharSequence) info} as the {@link ILogAcceptor}.
     */
//    public static void logProfilerResults(Profiler profiler, String rootName, long totalNanoseconds)
    public static void logProfilerResults(ActiveProfiler profiler, String rootName, long totalNanoseconds) {
        writeProfilerResults(profiler, rootName, totalNanoseconds, BCLog.logger::info);
    }

    /**
     * Calls {@link #writeProfilerResults(ActiveProfiler, String, ILogAcceptor)} but saves the output to a file.
     *
     * @throws IOException if the file exists but is a directory rather than a regular file, does not exist but cannot
     *                     be created, or cannot be opened for any other reason, or if an I/O exception occurred while writing
     *                     the profiler results.
     */
//    public static void saveProfilerResults(Profiler profiler, String rootName, Path dest) throws IOException
    public static void saveProfilerResults(ActiveProfiler profiler, String rootName, Path dest) throws IOException {
        saveProfilerResults(profiler, rootName, -1, dest);
    }

    /**
     * Calls {@link #writeProfilerResults(ActiveProfiler, String, ILogAcceptor)} but saves the output to a file.
     *
     * @throws IOException if the file exists but is a directory rather than a regular file, does not exist but cannot
     *                     be created, or cannot be opened for any other reason, or if an I/O exception occurred while wrting
     *                     the profiler results.
     */
//    public static void saveProfilerResults(Profiler profiler, String rootName, File dest) throws IOException
    public static void saveProfilerResults(ActiveProfiler profiler, String rootName, File dest) throws IOException {
        dest = dest.getAbsoluteFile();
        dest.getParentFile().mkdirs();
        saveProfilerResults(profiler, rootName, -1, dest.toPath());
    }

    /**
     * Calls {@link #writeProfilerResults(ActiveProfiler, String, ILogAcceptor)} but saves the output to a file.
     *
     * @param totalNanoseconds The total amount of time that the profiler's root section took, or -1 if this isn't
     *                         known.
     * @throws IOException if the file exists but is a directory rather than a regular file, does not exist but cannot
     *                     be created, or cannot be opened for any other reason, or if an I/O exception occurred while writing
     *                     the profiler results.
     */
//    public static void saveProfilerResults(Profiler profiler, String rootName, long totalNanoseconds, Path dest)
    public static void saveProfilerResults(ActiveProfiler profiler, String rootName, long totalNanoseconds, Path dest)
            throws IOException {
        try (BufferedWriter br = Files.newBufferedWriter(dest, StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE))
        {
            writeProfilerResults(profiler, rootName, str ->
            {
                br.write(str);
                br.newLine();
            });
            br.flush();
        }
    }

    /**
     * @param rootName The base name to use. Most of the time you just want to use "root".
     * @param dest     The method to call with the finished lines.
     * @throws E if {@link ILogAcceptor#write(String)} throws an exception.
     */
//    public static <E extends Throwable> void writeProfilerResults(Profiler profiler, String rootName,
    public static <E extends Throwable> void writeProfilerResults(ActiveProfiler profiler, String rootName,
                                                                  ILogAcceptor<E> dest) throws E {
        writeProfilerResults(profiler, rootName, -1, dest);
    }

    /**
     * @param rootName         The base name to use. Most of the time you just want to use "root".
     * @param totalNanoseconds The total amount of time that the profiler's root section took, or -1 if this isn't
     *                         known.
     * @param dest             The method to call with the finished lines.
     * @throws E if {@link ILogAcceptor#write(String)} throws an exception.
     */
//    public static <E extends Throwable> void writeProfilerResults(Profiler profiler, String rootName,
    public static <E extends Throwable> void writeProfilerResults(ActiveProfiler profiler, String rootName,
                                                                  long totalNanoseconds, ILogAcceptor<E> dest) throws E {
        writeProfilerResults_Internal(profiler, rootName, totalNanoseconds, 0, dest);
    }

    //    private static <E extends Throwable> void writeProfilerResults_Internal(Profiler profiler, String sectionName,
    private static <E extends Throwable> void writeProfilerResults_Internal(ActiveProfiler profiler, String sectionName,
                                                                            long totalNanoseconds, int indent, ILogAcceptor<E> dest) throws E {

//        List<Profiler.Result> list = profiler.getProfilingData(sectionName);
        ActiveProfiler.PathEntry list = profiler.getEntry(sectionName);
//        Profiler.Result results = profiler.getResults();
//        Set<Pair<String, MetricCategory>> s = profiler.getChartedPaths();
//        List<Pair<String, MetricCategory>> list = s.stream().toList();

//        if (list != null && list.size() >= 3)
        if (list != null && list.getCount() >= 3) {
//            for (int i = 1; i < list.size(); ++i)
            for (String key : list.getCounters().keySet()) {
//                Pair<String, MetricCategory> p = list.get(i);

//                Profiler.Result result = list.get(i);
                long result = list.getCounters().getLong(key);
                StringBuilder builder = new StringBuilder();
                // Calen
//                builder.append(p.getValue().name());
//                builder.append(" - ");
//                builder.append(p.getValue().getDescription());

                builder.append(String.format("[%02d] ", indent));

                for (int j = 0; j < indent; ++j) {
                    builder.append("|   ");
                }

//                builder.append(result.profilerName);
                builder.append(key);
                builder.append(" - ");
//                builder.append(String.format("%.2f", result.usePercentage));
                builder.append(String.format("%.2f", result));
                builder.append("%/");
//                builder.append(String.format("%.2f", result.totalUsePercentage));
                builder.append(String.format("%.2f", list.getCount()));
                if (totalNanoseconds > 0) {
                    builder.append(" (");
//                    long nano = (long) (result.totalUsePercentage * totalNanoseconds / 100);
                    long nano = (long) (list.getMaxDuration() * totalNanoseconds / 100);
                    if (nano < 99_999) {
                        builder.append(NumberFormat.getInstance().format(nano));
                        builder.append("ns");
                    } else if (nano < 99_999_999) {
                        builder.append(NumberFormat.getInstance().format(nano / 1000));
                        builder.append("µs");
                    } else if (nano < 99_999_999_999L) {
                        builder.append(NumberFormat.getInstance().format(nano / 1_000_000));
                        builder.append("ms");
                    } else {
                        builder.append(NumberFormat.getInstance().format(nano / 1_000_000_000));
                        builder.append("s");
                    }
                    builder.append(")");
                }
                dest.write(builder.toString());

//                if (!"unspecified".equals(result.profilerName))
                if (!"unspecified".equals(key)) {
                    if (indent > 20) {
                        // Something probably went wrong
                        dest.write("[[ Too deep! ]]");
                        continue;
                    }
//                    writeProfilerResults_Internal(profiler, sectionName + "." + result.profilerName, totalNanoseconds,
                    writeProfilerResults_Internal(profiler, sectionName + "." + key, totalNanoseconds,
                            indent + 1, dest);
                }
            }
        }
    }

    /**
     * @param <E> The base exception type that {@link #write(String)} might throw. Used to allow writing to files to
     *            throw a (checked) exception, but {@link System#out} to never throw.
     */
    public interface ILogAcceptor<E extends Throwable> {
        void write(String line) throws E;
    }

    public interface ProfilerEntry {
        void startSection(String name);

        void endSection();

        default void endStartSection(String name) {
            endSection();
            startSection(name);
        }
    }

    //    public static ProfilerEntry createEntry(Profiler p1, Profiler p2)
    public static ProfilerEntry createEntry(ProfilerFiller p1, ProfilerFiller p2) {
//        if (p1.profilingEnabled)
        if (p1 instanceof ActiveProfiler) {
//            if (p2.profilingEnabled)
            if (p2 instanceof ActiveProfiler) {
                return new ProfilerEntry2(p1, p2);
            } else {
                return new ProfilerEntry1(p1);
            }
        } else {
//            if (p2.profilingEnabled)
            if (p2 instanceof ActiveProfiler) {
                return new ProfilerEntry1(p2);
            } else {
                return ProfilerEntry0.INSTANCE;
            }
        }
    }

    static enum ProfilerEntry0 implements ProfilerEntry {
        INSTANCE;

        @Override
        public void startSection(String name) {
            // NO-OP
        }

        @Override
        public void endSection() {
            // NO-OP
        }
    }

    static final class ProfilerEntry1 implements ProfilerEntry {
        final ProfilerFiller p;

        ProfilerEntry1(ProfilerFiller p) {
            this.p = p;
        }

        @Override
        public void startSection(String name) {
//            p.startSection(name);
            p.push(name);
        }

        @Override
        public void endSection() {
//            p.endSection();
            p.pop();
        }
    }

    static final class ProfilerEntry2 implements ProfilerEntry {
        //        final Profiler p1, p2;
        final ProfilerFiller p1, p2;

        //        ProfilerEntry2(Profiler p1, Profiler p2)
        ProfilerEntry2(ProfilerFiller p1, ProfilerFiller p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public void startSection(String name) {
//            p1.startSection(name);
//            p2.startSection(name);
            p1.push(name);
            p2.push(name);
        }

        @Override
        public void endSection() {
//            p1.endSection();
//            p2.endSection();
            p1.pop();
            p2.pop();
        }
    }

}
