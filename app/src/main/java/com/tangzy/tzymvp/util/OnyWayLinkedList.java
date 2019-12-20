package com.tangzy.tzymvp.util;

/**
 * OnyWayLinkedList class
 *
 * @author Administrator
 * @date 2019/12/20
 */

public class OnyWayLinkedList<E> {
    private int size;
    private Node<E> first;
    private Node<E> last;
    /**
     * 添加
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        // 默认添加到链表尾部，这就是为啥链接添加快的原因。
        return addLast(e);
    }
    /**
     * 向链表尾部添加一个元素
     *
     * @param e
     * @return
     */
    public boolean addLast(E e) {
        Node<E> newNode = new Node<E>(e, null);
        Node<E> l = last;
        // size=0时，last和first都指向newNode。
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        return true;
    }
    /**
     * 向链表开头添加元素
     *
     * @param e
     * @return
     */
    public boolean addFirst(E e) {
        Node<E> newNode = new Node<E>(e, null);
        Node<E> currentNode = first;
        first = newNode;
        if (currentNode == null) {
            last = newNode;
        } else {
            newNode.next = currentNode;
        }
        size++;
        return true;
    }
    /**
     * 向链表指定位置添加元素
     */
    public boolean add(int index, E e) {
        checkIndex(index);
        Node<E> newNode = new Node<E>(e, null);
        if (index == size) {
            return addLast(e);
        } else if (index == 0) {
            addFirst(e);
        } else {
            Node<E> currentNode = first;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.next;
            }
            newNode.next = currentNode.next;
            //
            currentNode.next = newNode;
            // first = newNode;
            size++;
        }
        return true;
    }
    /**
     * 查看指定位置元素
     *
     * @return
     */
    public E get(int index) {
        checkIndex(index);
        E e = null;
        Node<E> currentNode = first;
        if (index > (size - 1))
            return null;
        else {
            // 每次查询元素时，都有遍历链表 index 次，时间复杂度为 o(index),这就是为啥链表查询慢的原因。
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        e = currentNode.item;
        return e;
    }
    /**
     * 删除链表中的一个元素
     *
     * @return
     */
    public E remove() {
        if (size == 0)
            return null;
        // 默认删除尾部，这就是为啥链表删除快的原因
        return removeLast();
    }
    /**
     * 从链表尾部删除一个元素
     *
     * @return
     */
    public E removeLast() {
        if (size==0){
            return null;
        }
        Node<E> l = last;
        --size;
        if (l == first) {
            first = null;
            last = null;
        } else {
            Node<E> currentNode = first;
            for (int i = 0; i < size - 1; i++) {
                currentNode = currentNode.next;
            }
            currentNode.next = null;
            last = currentNode;
        }
        E e = l.item;
        l.item = null;
        l.next = null;
        l = null;
        return e;
    }
    /**
     * 从链表头部删除一个元素
     *
     * @return
     */
    public E removeFirst() {
        if (first == null)
            return null;
        size--;
        Node<E> f = first;
        first = f.next;
        E e = f.item;
        f.item = null;
        f.next = null;
        f = null;
        return e;
    }
    /**
     * 删除指定元素
     *
     * @param e
     * @return
     */
    public E remove(E e) {
        if (size <= 0)
            return null;
        Node<E> currentNode = first;
        if (e.equals(currentNode.item)) {
            removeFirst();
        }
        if(e.equals(last.item)){
            removeLast();
        }
        for (int i = 0; i < size; i++) {
            if (e.equals(currentNode.item)) {
                // 可以看出来，单链表中如果删除的元素不是头或尾，删除的时间复杂度还是非常高的。o(2n-1)
                remove(i);
                break;
            }
            currentNode = currentNode.next;
        }
        return e;
    }
    /**
     * 删除指定位置元素
     *
     * @param index
     * @return
     */
    private void remove(int index) {
        checkIndex(index);
        Node<E> currentNode = first;
        // last=null
        for (int j = 0; j < index - 1; j++) {
            currentNode = currentNode.next;
        }
        Node<E> removeTarget = currentNode.next;
        currentNode.next = currentNode.next.next;
        removeTarget.item = null;
        removeTarget.next = null;
        removeTarget = null;
        size--;
    }
    /**
     * 获取链表长度
     *
     * @return
     */
    public int size() {
        return size;
    }
    private void checkIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            System.out.println("Exception");
            throw new IndexOutOfBoundsException("Index out of size: " + index + ";");
        }
    }
    /**
     * 节点类（内部类）
     *
     * @author mercy_yang
     *
     * @param <E>
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node(E e, Node<E> next) {
            this.item = e;
            this.next = next;
        }
    }
    /**
     * 重写toString()方法
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.size(); i++) {
            sb.append(this.get(i)).append((i + 1) == size ? "" : ", ");
        }
        sb.append("]");
        return sb.toString();
    }


    /**
     * 单链表反转
     */
    public void reverse(){

        if (size==0 || size ==1){
            return ;
        }

        Node<E> transferNode = this.first;
        Node<E> thirdNode = transferNode.next.next;
        first =  transferNode.next;
        first.next = transferNode;
        transferNode.next = thirdNode;

        while (transferNode.next!=null){
            Node<E> target = transferNode.next;
            Node<E> firstNode = this.first;
            Node<E> next = target.next;
            this.first = target;
            target.next = firstNode;
            transferNode.next = next;
        }
    }

    public static void main(String[] args) {
        OnyWayLinkedList<String> strList = new OnyWayLinkedList<>();
//        strList.removeLast();
        strList.add(0,"3");
        strList.add("4");
        strList.addFirst("2");
//        strList.addLast("5");
//        strList.addFirst("1");
//        strList.remove(3);
        strList.reverse();
        System.out.println(strList);
    }
}
