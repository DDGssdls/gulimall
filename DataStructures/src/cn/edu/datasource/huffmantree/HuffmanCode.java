package cn.edu.datasource.huffmantree;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: DDG
 * @Date: 2020/5/7 15:19
 * @Description:
 */
public class HuffmanCode {
    public static void main(String[] args) {
        /*String java = "i like like like java do you like a java";
        List<Node> nodes = getNodes(java.getBytes());
        Node huffmanTree = getHuffmanTree(nodes);
        System.out.println(huffmanTree.weight);
        previousOrder(huffmanTree);
        Map<Byte, String> huffmanCode = getHuffmanCode(huffmanTree);
        System.out.println("===================");
        System.out.println(huffmanCode);
        System.out.println(java.length());
        byte[] zip = zip(java.getBytes());
        System.out.println(Arrays.toString(zip));
        System.out.println(zip.length);
        System.out.println(decode(zip, huffmanCode));*/
        String srcFile = "E:\\ideaProjects\\DataStructures\\src\\cn\\edu\\datasource\\huffmantree\\1.txt";
        String srcFile1 = "E:\\ideaProjects\\DataStructures\\src\\cn\\edu\\datasource\\huffmantree\\2.zip";
        String srcFile2 = "E:\\ideaProjects\\DataStructures\\src\\cn\\edu\\datasource\\huffmantree\\3.txt";


        zipFile(srcFile, srcFile1);
        decodeFile(srcFile1, srcFile2);


    }
    // 进行文件的解压：
    public static void decodeFile(String srcFile, String desFile){
        try(ObjectInputStream inputStream = new  ObjectInputStream(new FileInputStream(srcFile));
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(desFile))){
            byte[] o = (byte[])inputStream.readObject();
            // 进行的是解压缩操作
                byte[] decode = decode(o, map);
                //先将zip进行添加 在 huffmanCode添加用于解压
                outputStream.write(decode);
                outputStream.flush();
            } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }


    // 使用赫夫曼进行文件的压缩：
    //传入的是文件的全路径
    public static void zipFile(String srcFile, String desFile)  {
        try(BufferedInputStream inputStream = new  BufferedInputStream(new FileInputStream(srcFile));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(desFile))){
            byte[] bytes = new byte[inputStream.available()];
            int length;
            while ((length = inputStream.read(bytes)) != -1){
                // 进行的是压缩操作
                byte[] zip = zip(bytes);
                //先将zip进行添加 在 huffmanCode添加用于解压
                objectOutputStream.writeObject(zip);
            }
            //objectOutputStream.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int strLength;

    // 使用编码进行字符串的压缩：
    public static byte[] zip( byte[] bytes) {

        List<Node> nodes = getNodes(bytes);
        Node huffmanTree = getHuffmanTree(nodes);
        Map<Byte, String> huffmanCode = getHuffmanCode(huffmanTree);

        StringBuilder stringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            stringBuilder.append(huffmanCode.get(aByte));
        }
       // System.out.println(stringBuilder);
        // 使用编码表进行映射
        byte[] bytes1 = new byte[(stringBuilder.length() + 7) / 8];
        for (int i = 0, j = 0; i < stringBuilder.length(); i += 8) {
            String substring = null;
            if (i + 8 < stringBuilder.length()) {
                substring = stringBuilder.substring(i, i + 8);

            } else {
                substring = stringBuilder.substring(i);
            }

            bytes1[j++] = (byte) Integer.parseInt(substring, 2);
        }
        //System.out.println(stringBuilder.length());
        strLength = stringBuilder.length();

        return bytes1;
    }

    // 数据的解压 就是decode
    public static byte[] decode(byte[] bytes, Map<Byte, String> huffmanCode) {
        int temp;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            temp = bytes[i];
             temp |= 256;
            String s = Integer.toBinaryString(temp);
            if (i < bytes.length - 1) {
                sb.append(s.substring(s.length() - 8));
            } else {
                sb.append(s.substring((s.length() - (strLength - (bytes.length - 1) * 8))));
            }
        }
        // 进行的是 解压缩：

        ArrayList<Byte> arrayList = new ArrayList<>();

        Map<String, Byte> collect = huffmanCode.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (v1, v2) -> v1));
        int start = 0;
        for (int i = 1; i <= sb.length(); i++) {
            String substring = sb.substring(start, i);
            Byte aByte = collect.get(substring);
            if ( aByte != null){
                start = i;
                arrayList.add(aByte);
            }
        }
        byte[] bytes1 = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            bytes1[i] = arrayList.get(i);
        }
        return bytes1;
    }

    // 创建赫夫曼树：
    public static Node getHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            nodes.sort((n1, n2) -> n1.weight - n2.weight);
            Node node = nodes.get(0);
            Node node1 = nodes.get(1);
            nodes.add(new Node((byte) -1, node.weight + node1.weight, node, node1));
            nodes.remove(0);
            nodes.remove(0);
        }
        return nodes.get(0);
    }

    static StringBuilder sb = new StringBuilder();
    static HashMap<Byte, String> map = new HashMap<>();

    // 将赫夫曼树获取赫夫曼编码表 存储到一个map中 实现规定一下 左边的是0 右边的是1
    public static Map<Byte, String> getHuffmanCode(Node node) {
        return getHuffmanCode(node, "", sb);
    }

    public static Map<Byte, String> getHuffmanCode(Node node, String code, StringBuilder sb) {
        if (node == null){
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder(sb);
        stringBuilder.append(code);
        // 进行递归处理
        if (node.data == -1) {
            // 左右递归
            getHuffmanCode(node.left, "0", stringBuilder);
            getHuffmanCode(node.right, "1", stringBuilder);
        } else {
            // 表示找到了
            map.put(node.data, stringBuilder.toString());
        }

        return map;
    }

    public static List<Node> getNodes(byte[] bytes) {
        // 统计每一个byte出现的次数：

        HashMap<Byte, Integer> map = new HashMap<>();
        for (byte aByte : bytes) {
            Integer count = map.get(aByte);
            if (count == null) {
                map.put(aByte, 1);
            } else {
                map.put(aByte, count + 1);
            }

        }
        List<Node> collect = map.entrySet().stream()
                .map((entry) -> new Node(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        //System.out.println(collect);
        return collect;

    }

    public static void previousOrder(Node node) {
        if (node == null) {
            System.out.println("不行");
            return;
        }
        System.out.print(node + "\t");
        if (node.left != null) {
            previousOrder(node.left);
        }
        if (node.right != null) {
            previousOrder(node.right);
        }
    }

    static class Node {
        private byte data;
        private int weight;
        private Node left;
        private Node right;

        public Node(byte data, int weight, Node left, Node right) {
            this.data = data;
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        public Node(byte data, int weight) {
            this.data = data;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", weight=" + weight +
                    '}';
        }

    }
}
