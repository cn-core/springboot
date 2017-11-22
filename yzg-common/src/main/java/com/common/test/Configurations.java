package com.common.test;


import com.common.test.pojo.CaloricLevel;
import com.common.test.pojo.Dish;
import com.common.test.pojo.Trader;
import com.common.test.pojo.Transaction;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author yangzhiguo
 */
@Data
public class Configurations {

    public static void main(String[] args) {
        /*List<Point> points = Arrays.asList(new Point(12, 2), null);
        Function<Point, Double> pointDoubleFunction = it -> Optional.ofNullable(it)
                .flatMap(x -> Optional.ofNullable(it.getX())).orElse(null);
        points.stream().map(pointDoubleFunction).forEach(System.out::println);*/

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> future = executorService.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return 0D;
            }
        });
        // 异步操作进行的同时,你可以做其他的事情
        parallelRangedSum(2);




    }

    public static int divideByZero(int n) {
        return n / 0;
    }


    @Test
    public void java8() {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);
        /*Dish dish1 = new Dish("pork", false, 800, Dish.Type.MEAT);
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH) );*/
        // =========================================================================================

        /*Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );*/

        // ================================构建流========================================
        // test1(menu);
        // test2(menu);
        // test3(transactions);
        // test4(menu);
        // test5();
        // test6();
        // test7(menu);
        // test8(menu);=========================================================================================================
        // test9(menu);
        // test10(numb1rs);
        // 新的日期和时间API


    }

    private void test10(List<Integer> numbers) {
        // 第七章 并行数据处理与性能
        // 7.1并行流
        // Integer sum = this.iterativeSum(5);
        // Integer reduce = Stream.iterate(1, data -> data + 1).limit(5).parallel().reduce(0, Integer::sum);

        // iterate操作时不宜使用parallel并行操作
        // System.out.println("Sequential sum done:" +
        //         measureSumPerf(Configurations::rangedSum,10000000) +
        //         " msecs");
        //  rangedSum(5ms)   parallelRangedSum(2ms)
        //  IntStream.rangeClosed(1,10).forEach(System.out::println);
        Map<String,Integer> map = new HashMap<>();
        map.put("1",1);
        map.put("2",2);
        Optional<Integer> integer = Optional.ofNullable(map.get("1"));


        List<Integer> result =
                numbers.stream()
                        .peek(x -> System.out.println("from stream: " + x))
                        .map(x -> x + 17)
                        .peek(x -> System.out.println("after map: " + x))
                        .filter(x -> x % 2 == 0)
                        .peek(x -> System.out.println("after filter: " + x))
                        .limit(3)
                        .peek(x -> System.out.println("after limit: " + x))
                        .collect(Collectors.toList());
    }


    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(0L, n).reduce(0, Long::sum);
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    private Integer iterativeSum(Integer tmp) {
        return Stream.iterate(1, data -> data + 1).limit(tmp).reduce(0, Integer::sum);

    }

    private void test9(List<Dish> menu) {
        // 6.4分区
        Map<Boolean, List<Dish>> collect = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));
        Map<Boolean, Map<Dish.Type, List<Dish>>> collect1 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        Map<Boolean, Dish> collect2 = menu.stream()
                .collect(groupingBy(Dish::isVegetarian,
                        collectingAndThen(maxBy(Comparator.comparing(Dish::getCalories)),
                                Optional::get)));

        Map<Boolean, Map<Boolean, List<Dish>>> collect3 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        groupingBy(data -> data.getCalories() > 500)));
        Map<Boolean, Map<Dish.Type, List<Dish>>> collect4 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        groupingBy(Dish::getType)));

        Map<Boolean, Long> collect5 = menu.stream().collect(partitioningBy(Dish::isVegetarian, counting()));
        boolean prime = this.isPrime(7);
    }

    public boolean isPrime(int canditdate) {
        int candidateRoot = (int) Math.sqrt((double) canditdate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(a -> canditdate % a == 0);
    }

    private void test8(List<Dish> menu) {
        // 6.3分组
        Map<Dish.Type, List<Dish>> collect = menu.stream()
                .collect(groupingBy(Dish::getType));

        // 自定义分类
        Map<CaloricLevel, List<Dish>> collect1 = menu.stream()
                .collect(groupingBy(data ->
                {
                    return getCaloricLevel(data);
                }));
        // 6.3.1多级分类
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> collect2 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        groupingBy(data ->
                        {
                            return getCaloricLevel(data);
                        })));
        // 6.3.2按子组收集数据
        Map<Dish.Type, Long> collect3 = menu.stream()
                .collect(groupingBy(Dish::getType, counting()));

        Map<Dish.Type, Optional<Dish>> collect4 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        maxBy(Comparator.comparing(Dish::getCalories))));
        // 把收集器的结果转换为另一种类型
        Map<Dish.Type, Dish> collect5 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(maxBy(Comparator.comparing(Dish::getCalories)),
                                Optional::get)));

        Map<Dish.Type, Integer> collect6 = menu.stream()
                .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        Map<Dish.Type, Set<CaloricLevel>> collect7 = menu.stream()
                .collect(groupingBy(Dish::getType, mapping(data ->
                {
                    return getCaloricLevel(data);
                }, toSet())));

        Map<Dish.Type, HashSet<CaloricLevel>> collect8 = menu.stream()
                .collect(groupingBy(Dish::getType, mapping(data ->
                {
                    return getCaloricLevel(data);
                }, toCollection(HashSet::new))));
    }

    private CaloricLevel getCaloricLevel(Dish data) {
        if (data.getCalories() <= 400) {
            return CaloricLevel.DIET;
        } else if (data.getCalories() <= 700) {
            return CaloricLevel.NORMAL;
        } else {
            return CaloricLevel.FAT;
        }
    }

    private void test7(List<Dish> menu) {
        // 用流收集数据
        // 6.2规约和汇总
        Long collect = menu.stream().collect(counting());
        Dish dish = menu.stream().collect(maxBy(Comparator.comparing(Dish::getCalories))).get();

        // 6.2.2汇总
        Integer collect1 = menu.stream().collect(summingInt(Dish::getCalories));
        Double collect2 = menu.stream().collect(averagingInt(Dish::getCalories));
        IntSummaryStatistics collect3 = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println("avg:" + collect3.getAverage());
        System.out.println("sum:" + collect3.getSum());
        System.out.println("max:" + collect3.getMax());
        System.out.println("min" + collect3.getMin());

        // 6.2.3连接字符串
        String collect4 = menu.stream().map(Dish::getName).collect(joining());
        String collect5 = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println(collect5);

        // 6.2.4广义的归约汇总
        Integer collect6 = menu.stream().collect(reducing(0, Dish::getCalories, (a, b) -> a + b));
        Dish dish2 = menu.stream().collect(reducing((a, b) -> a.getCalories() > b.getCalories() ? a : b)).get();
        System.out.println(dish2);
    }

    private void test6() {
        //        Stream.of("Java8","Lambdas","In","Action").map(String::toUpperCase).forEachOrdered(System.out::println);

        /*int[] arr = new int[]{1,2,3,4,5,6};
        Integer reduce = Stream.of(1, 2, 3, 4, 5, 6).reduce(0, Integer::max);
        System.out.println(reduce);

        long uniqueWords = 0;
        try(Stream<String> lines =
                    Files.lines(Paths.get("D;\\test.csv"), Charset.defaultCharset())){
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        }
        catch(IOException e){
        }
        System.out.println(uniqueWords);*/
        // 由函数生成流:创建无限流
//        Stream.iterate(0,n -> n + 3)
//                .limit(5)
//                .forEach(System.out::println);

        // iterrate
//        Stream.iterate(new int[]{0, 1},
//                t -> new int[]{t[1], t[0]+t[1]})
//                .limit(20)
//                .map(data -> data[0])
//                .forEach(System.out::println);

        // generate
//        Stream.generate(Math::random)
//                .limit(5)
//                .forEach(System.out::println);
        IntStream.generate(() -> 1).limit(10).forEach(System.out::println);
    }

    private void test5() {
        // ========================================数值流(数值范围)==============================================
        /*OptionalInt max = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max();

        long count = IntStream.rangeClosed(1, 100)
                .filter(data -> data % 2 == 0)
                .count();
        System.out.println("rangeClosed:" + count);

        IntStream count1 = IntStream.range(1, 100)
                .filter(d -> d % 2 == 0);
        long count2 = count1.count();
        System.out.println("range:" + count2);*/


        /*Stream<int[]> stream = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(1, 100)
                                .filter(b ->
                                        Math.sqrt(a * a + b * b) % 1 == 0)
                                .mapToObj(b ->
                                        new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );
        stream.limit(5).forEach(d -> {
            System.out.println(d[0] + ", " + d[1] + ", " + d[2]);
        });*/

        // ===============数值流勾============================================
        Stream<double[]> stream = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(data ->
                        IntStream.rangeClosed(data, 100)
                                .mapToObj(data2 ->
                                        new double[]{data, data2, Math.sqrt(data * data + data2 * data2)})
                                .filter(data3 -> data3[2] % 1 == 0));

        stream.limit(5).forEach(d -> System.out.println(d[0] + ", " + d[1] + ", " + d[2]));
    }

    private void test4(List<Dish> menu) {
        // ======================================数值流=============================================
        // 数据值流(将Strem转换为数值流[IntStream])
        Integer reduce = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println(reduce);
        // 将数值流(IntStream)转换为Stream
        Integer integer = menu.stream()
                .mapToInt(Dish::getCalories)
                .boxed()
                .max(Integer::max)
                .get();
        System.out.println(integer);
    }

    private void test3(List<Transaction> transactions) {
        // (1)找出2011年发生的所有交易，并按交易额排序（从低到高）
        transactions.stream()
                .filter(data -> data.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(toList())
                .forEach(System.out::println);
        System.out.println("1111111111111111111111111");
        // (2)交易员都在哪些不同的城市工作过？
        transactions.parallelStream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .collect(toList())
                .forEach(System.out::println);
        System.out.println("2222222222222222222222222");
        // (3)查找所有来自于剑桥的交易员，并按姓名排序
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(data -> data.getCity().equals("Cambridge"))
//                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(toList())
                .forEach(System.out::println);
        System.out.println("3333333333333333333333333");
        // (4)返回所有交易员的姓名字符串，按字母顺序排序
        String reduce = transactions.stream()
                .map(data -> data.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (a, b) -> a + b);
        System.out.println("reduce:" + reduce);
        System.out.println("4444444444444444444444444");
        // (5)有没有交易员是在米兰工作的
//        transactions.stream()
//                .map(Transaction::getTrader)
//                .filter(data -> data.getCity().equals("Milan"))
//                .map(Trader::getName)
//                .distinct()
//                .collect(toList())
//                .forEach(System.out::println);
        boolean mario1 = transactions.stream().allMatch(data -> data.getTrader().getCity().equals("Mario"));
        System.out.println(mario1);
        System.out.println("5555555555555555555555555");
        // (6)打印生活在剑桥的交易员的所有交易额
        transactions.parallelStream()
                .filter(data -> data.getTrader().getCity().equals("Cambridge"))
                .collect(toList())
                .forEach(System.out::println);
        System.out.println("6666666666666666666666666");
        // (7)所有交易中，最高的交易额是多少？
        Integer integer = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .get();
        /*Transaction transaction = transactions.stream()
                .max(Comparator.comparing(Transaction::getValue))
                .get();*/
        System.out.println(integer);
        System.out.println("7777777777777777777777777");
        // (8)找到交易额最小的交易
        Integer integer1 = transactions.stream()
                .map(data -> data.getValue())
                .reduce(Integer::min)
                .get();
        System.out.println(integer1);
    }

    private void test2(List<Dish> menu) {
        // 流中的元素是否都能匹配给定的谓词(allMatch)
//        boolean b = menu.parallelStream().allMatch(d -> d.getCalories() < 700);
        // 流中是否有一个元素能匹配给定的谓词(anyMatch)
//        boolean b = menu.parallelStream().anyMatch(data -> data.getCalories() < 701);
        // 确保流中没有任何元素与给定的谓词匹配(noneMatch)
//        boolean b = menu.parallelStream().noneMatch(data -> data.getCalories() >= 1000);
        // 返回当前流中的任意元素(findAny)
//        Optional<Dish> first = menu.parallelStream().filter(data -> data.isVegetarian()).findAny();
//        boolean present = first.isPresent();
//        System.out.println("isPresent:" + present);
//        Dish dish2 = first.orElse(dish1);
//        System.out.println("orElse:" + dish2.toString());
//        Dish dish = first.get();
//        System.out.println("get():" + dish.toString());
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        long count = menu.parallelStream().count();
        System.out.println(count);
    }

    private void test1(List<Dish> menu) {
        List<String> collect = menu.parallelStream()
                .filter(dish -> {
                    System.out.println("filtering:" + dish.getName());
                    return dish.getCalories() > 400;
                }).sorted(Comparator.comparing(a -> {
                    System.out.println("comparing:" + a.getCalories());
                    return a.getCalories();
                })).map(d -> {
                    System.out.println("mapping:" + d.getName());
                    return d.getName();
                }).limit(3).collect(toList());
        collect.forEach(data -> {
            System.out.println(data);
        });
    }
}