package org.monkey.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

public class FileUtil {

    public static boolean exist(String filePath) {
        return new File(filePath).exists();
    }

    public static boolean createFile(String filePath) {
        try {
            return new File(filePath).createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 读取文件内容以字符串形式输出
     * @param filePath 文件路径
     * @return String
     * @throws IOException 读取异常直接抛出
     */
    public static String read(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            int len;
            char[] buf = new char[512];
            StringBuilder sb = new StringBuilder();
            do {
                len = br.read(buf);
                sb.append(buf);
            } while (len == -1);
            return sb.toString();
        }
    }

    public static void write(String filePath, Object obj) throws IOException {
        write(filePath, JsonUtil.objToStr(obj));
    }

    public static void write(String filePath, String str) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(str);
        }
    }
}
