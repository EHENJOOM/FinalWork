package com.zhk.util;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/12/5 11:17
 * @description 序列化工具包
 */
public class SerializeUtil {

    /**
     * 序列化输出一个对象
     * @param object 序列化的对象
     * @param file 输出的文件位置
     * @throws IOException 抛出的IO异常
     */
    public static void write(Object object, File file) throws IOException {
        List<Object> list = new LinkedList<>();
        list.add(object);
        write(list, file);
    }

    /**
     * 序列化一组对象
     * @param objectList 序列化的一组对象
     * @param file 输出的文件位置
     * @throws IOException 抛出的IO异常
     */
    public static void write(List objectList, File file) throws IOException {
        FileOutputStream fout = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(objectList);
        fout.close();
        out.close();
    }

    /**
     * 反序列化一组对象
     * @param file 文件读取位置
     * @return 反序列化后的2文件
     * @throws IOException 抛出的IO异常
     * @throws ClassNotFoundException 抛出的找不到类的异常
     */
    public static List read(File file) throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fin);
        return (List) in.readObject();
    }

}
