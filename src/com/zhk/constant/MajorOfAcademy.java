package com.zhk.constant;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 赵洪苛
 * @date 2019/12/21 21:55
 * @description 学院和专业的关联
 */
public class MajorOfAcademy {

    /**
     * 存储学院信息及其对应的专业信息
     */
    private static final Map<String, List<String>> MAJOR_OF_ACADEMY = new HashMap<>();

    /**
     * 静态块初始化学院及其对应的专业
     */
    static {
        initFirst();
        initSecond();
        initThird();
        initForth();
        initFifth();
        initSixth();
        initSeventh();
        initEighth();
        initNinth();
    }

    /**
     * 根据学院获取匹配的专业列表
     * @param academy 学院
     * @return 专业列表
     */
    public static List<String> getMajors(String academy) {
        return MAJOR_OF_ACADEMY.get(academy);
    }

    /**
     * 判断此专业是不是该学院下属的
     * @param academy 学院
     * @param major 专业
     * @return true：该专业是该学院里的  false：该专业不是该学院里的
     */
    public static boolean isMatched(String academy, String major) {
        return MAJOR_OF_ACADEMY.get(academy).stream().anyMatch(item -> item.equals(major));
    }

    private static void initFirst() {
        List<String> list = new LinkedList<>();
        list.add("化学工程与工艺");
        list.add("环境工程");
        list.add("能源化学工程");
        list.add("卓越工程师计划");
        list.add("国际教育合作");
        MAJOR_OF_ACADEMY.put(Config.ACADEMY_STRINGS[1], list);
    }

    private static void initSecond() {
        List<String> list = new LinkedList<>();
        list.add("高分子材料与工程");
        list.add("材料科学与工程");
        list.add("功能材料");
        MAJOR_OF_ACADEMY.put(Config.ACADEMY_STRINGS[2], list);
    }

    private static void initThird() {
        List<String> list = new LinkedList<>();
        list.add("安全工程");
        list.add("机械工程及其自动化");
        list.add("过程装备与控制工程");
        list.add("产品设计");
        MAJOR_OF_ACADEMY.put(Config.ACADEMY_STRINGS[3], list);
    }

    private static void initForth() {
        List<String> list = new LinkedList<>();
        list.add("计算机科学与技术");
        list.add("通信工程");
        list.add("自动化技术");
        list.add("测控技术");
        MAJOR_OF_ACADEMY.put(Config.ACADEMY_STRINGS[4], list);
    }

    private static void initFifth() {
        List<String> list = new LinkedList<>();
        list.add("信息管理与信息系统");
        list.add("物流管理");
        list.add("会计学");
        list.add("财务管理");
        list.add("国际经济与贸易");
        list.add("工商管理");
        MAJOR_OF_ACADEMY.put(Config.ACADEMY_STRINGS[5], list);
    }

    private static void initSixth() {
        List<String> list = new LinkedList<>();
        list.add("应用化学");
        list.add("化学");
        MAJOR_OF_ACADEMY.put(Config.ACADEMY_STRINGS[6], list);
    }

    private static void initSeventh() {
        List<String> list = new LinkedList<>();
        list.add("数学与应用数学");
        list.add("信息与计算科学");
        list.add("金融数学");
        list.add("电子科学与技术");
        MAJOR_OF_ACADEMY.put(Config.ACADEMY_STRINGS[7], list);
    }

    private static void initEighth() {
        List<String> list = new LinkedList<>();
        list.add("法律");
        list.add("外语");
        list.add("公共管理");
        list.add("社会体育");
        MAJOR_OF_ACADEMY.put(Config.ACADEMY_STRINGS[8], list);
    }

    private static void initNinth() {
        List<String> list = new LinkedList<>();
        list.add("制药工程");
        list.add("生物医学工程");
        list.add("生物技术");
        list.add("生物工程");
        MAJOR_OF_ACADEMY.put(Config.ACADEMY_STRINGS[9], list);
    }

}
