package com.example.application.intellijassistant.Heap;

/**
 * User: malek
 * Date: 4/10/2018
 * Time: 9:04 PM
 */


public class Heap<T extends Comparable<T>> {

    public enum Type {
        GREATER, LESS
    }

    private T[] heap;
    private int capacity;
    private int size;
    private boolean greater;

    public Heap() {

    }

    public Heap(Type type) {

        if(type == Type.GREATER) {
            greater = true;
        }
    }

    public T add(T comparable) {
        if(capacity == 0) {
            capacity = 2;
            heap = (T[]) new Comparable[capacity];
        }

        heap[size++] = comparable;

        if(size >= capacity) {
            resize(2 * capacity);
        }

        return heap[upHeap(size -1)];
    }

    public T remove(int index) {
        if(capacity == 0) {
            throw new ArrayIndexOutOfBoundsException();

        } else if(index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }

        swap(index, size-1);
        size--;
        downHeap(index);

        if(size < capacity/2) {
            resize(capacity/2);
        }

        return heap[size];
    }

    public void printHeap() {

        for(int i=0; i<size; i++) {
            System.out.println(heap[i]);
        }
        System.out.println();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    public T changeKey(int index, T object) {

        if(index < 0 || index >= size || object == null) {
            throw new IllegalArgumentException();
        }

        T existing = heap[index];
        heap[index] = object;

        if((greater && greater(existing, object)) || (!greater && greater(object, existing))) {
            return heap[downHeap(index)];

        } else return heap[upHeap(index)];
    }

    public T get(int index) {
        return heap[index];
    }

    private int upHeap(int index) {

        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if(index != 0) {
            if((greater && greater(index, parent(index))) || (!greater && !greater(index, parent(index)))) {
                swap(index, parent(index));
                return upHeap(parent(index));
            }
        }

        return index;
    }

    private int downHeap(int index) {

        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        int child = leftChild(index);

        if(child == -1) {
            return index;

        } else {

            if(rightChild(index) != -1) {
                if ((greater && greater(rightChild(index), child)) ||
                        (!greater && greater(child, rightChild(index)))) {

                    child = rightChild(index);
                }
            }

            if((greater && greater(child, index)) || (!greater && greater(index, child))) {
                swap(index, child);
                return downHeap(child);
            }

            return index;
        }
    }

    private boolean greater(int i, int j) {
        return greater(heap[i], heap[j]);
    }

    private boolean greater(T first, T second) {
        if(first.compareTo(second) > 0) {
            return true;
        }
        else return false;
    }

    private void swap(int i, int j) {
        T t = heap[i];
        heap[i] = heap[j];
        heap[j] = t;
    }

    private int getChild(int index, int side) {

        int child;
        if((child = 2*index + side) >= size) {
            return -1;
        }
        return child;
    }

    private int leftChild(int index) {
        return getChild(index, 1);
    }

    private int rightChild(int index) {
        return getChild(index, 2);
    }

    private int parent(int index) {
        if(index == 0) {
            return index;
        }
        return (index-1)/2;
    }

    private void resize(int newSize) {

        if(newSize != capacity) {

            T[] newHeap = (T[]) new Comparable[newSize];

            if(newSize < capacity) {

                if(size > newSize) {
                    size = newSize;
                    newSize *= 2;
                }

            }

            for(int i = 0; i< size; i++) {
                newHeap[i] = heap[i];
            }

            heap = newHeap;
        }

        capacity = newSize;
    }
}