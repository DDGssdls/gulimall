package com.itheima.test;

import java.util.Calendar;

public class TestFactory {

    public static void main(String[] args) {

    }
}

class SimpleFactory{
    public Baoma produceBaoma(){
        return new Baoma();
    }

    public Benchi produceBenchi(){
        return new Benchi();
    }
}
interface Car{
    void produce();
}

class Baoma implements Car{
    public Baoma () {
        this.produce();
    }
    @Override
    public void produce() {
        // TODO Auto-generated method stub
        System.out.println("生产的是宝马汽车");
    }
}
class Benchi implements Car{
    public Benchi () {
        this.produce();
    }
    @Override
    public void produce() {
        // TODO Auto-generated method stub
        System.out.println("生产的是奔驰汽车");
    }
}
interface ProduceFactory{
    Car produceCar();
}
class ProduceBaoma implements ProduceFactory{

    @Override
    public Car produceCar() {
        return new Baoma();
    }
}
class ProduceBenchi implements ProduceFactory{

    @Override
    public Benchi produceCar() {
        return new Benchi();
    }
}