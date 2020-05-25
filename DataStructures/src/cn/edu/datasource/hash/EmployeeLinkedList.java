package cn.edu.datasource.hash;

/**
 * @Author: DDG
 * @Date: 2020/5/4 14:08
 * @Description:
 */
public class EmployeeLinkedList {

    private EmployeeNode dummyHead;

    private int size;

    public EmployeeLinkedList() {
    }

    public EmployeeLinkedList(EmployeeNode dummyHead) {
        this.dummyHead = dummyHead;
    }
    public void add(Employee employee){
        EmployeeNode tempNode = dummyHead;
        size ++;
        if (tempNode == null){
            dummyHead = new EmployeeNode(employee, null);
            return;
        }
        while (tempNode.next != null){
           tempNode =  tempNode.next;
        }
        tempNode.next = new EmployeeNode(employee, null);

    }
    public int size(){
        return size;
    }
    public boolean contain(Employee employee){
        if(employee == null){
            throw new RuntimeException("对象为空");
        }
        if (isEmpty()){
            System.out.println("链表为空");
        }
        EmployeeNode tempNode = dummyHead;
        while (tempNode != null){
            if (tempNode.employee.equals(employee)){
                return true;
            }
            tempNode = tempNode.next;
        }
        return false;
    }
    public void delete(Employee employee){
        if(employee == null){
            throw new RuntimeException("对象为空");
        }
        if (isEmpty()){
            System.out.println("链表为空");
        }
        EmployeeNode tempNode = dummyHead;
        while (tempNode.next != null){
            if (tempNode.next.employee.equals(employee)){
                tempNode.next = tempNode.next.next;
                size --;
                break;
            }
            tempNode = tempNode.next;
        }
        System.out.println("不存在");
    }

   public void list(){
        if (isEmpty()){
            System.out.println("链表为空 先添加数据");
        }
        EmployeeNode tempNode = dummyHead;
        while (tempNode != null){
            System.out.println(tempNode.employee);
            tempNode = tempNode.next;
        }
   }
   public boolean isEmpty(){
        return dummyHead == null;
   }

    private class EmployeeNode {

        private Employee employee;

        private EmployeeNode next;

        public EmployeeNode(Employee employee, EmployeeNode next) {
            this.employee = employee;
            this.next = next;
        }

        public Employee getEmployee() {
            return employee;
        }

        public void setEmployee(Employee employee) {
            this.employee = employee;
        }

        public EmployeeNode getNext() {
            return next;
        }

        public void setNext(EmployeeNode next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "EmployeeNode{" +
                    "employee=" + employee +
                    ", next=" + next +
                    '}';
        }
    }
}
