package cn.edu.datasource.hash;

/**
 * @Author: DDG
 * @Date: 2020/5/4 13:53
 * @Description:
 */
public class MyHash {

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    static final int TREEIFY_THRESHOLD = 8;

    static final int UNTREEIFY_THRESHOLD = 6;

    private EmployeeLinkedList[] table;
    private int size;

    public MyHash() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public MyHash(int capacity) {
        table = new EmployeeLinkedList[capacity];
    }
    //
    public void add(Employee employee){
        // 首先就是使用 hash 算法求出所在的下标
        int hash = hash(employee);
        size ++;
       if (table[hash] == null){
           EmployeeLinkedList employeeLinkedList = new EmployeeLinkedList();
           table[hash] = employeeLinkedList;
       }
       table[hash].add(employee);
    }
    public void list(){
        for (EmployeeLinkedList employeeLinkedList : table) {
            if (employeeLinkedList != null){
                employeeLinkedList.list();
                System.out.println("-------------");
            }
        }
    }

    public int size(){
        return size;
    }

    public boolean contain(Employee employee){
        int hash = hash(employee);
        System.out.println(hash);
        return  table[hash].contain(employee);

    }



    private int hash(Employee employee){
        if (employee == null){
            throw new RuntimeException("对象为空");
        }
        int hashcode = employee.hashCode();
        //System.out.println(hashcode);
        return  (hashcode ^ hashcode >>> 16) & (table.length - 1);
    }
}
