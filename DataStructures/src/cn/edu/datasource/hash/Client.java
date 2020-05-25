package cn.edu.datasource.hash;

/**
 * @Author: DDG
 * @Date: 2020/5/4 14:18
 * @Description:
 */
public class Client {
    public static void main(String[] args) {
        EmployeeLinkedList employeeLinkedList = new EmployeeLinkedList();
        for (int i = 0; i < 20; i++) {
            employeeLinkedList.add(new Employee(i, String.valueOf(i), Integer.toString(i)));
        }
        employeeLinkedList.delete(new Employee(1, "10", "10"));
        employeeLinkedList.list();

        System.out.println(employeeLinkedList.size());

        System.out.println("---------------------------");

        MyHash myHashMap = new MyHash();
        for (int i = 0; i < 100; i++) {
            myHashMap.add(new Employee(i, "1", "1"));
        }

        myHashMap.list();
        System.out.println(myHashMap.size());
        System.out.println(myHashMap.contain(new Employee(-1, "1", "1")));
    }
}
