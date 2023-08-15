package by.it_academy.user.util;

import java.util.List;

public class TPage<T> {
    private int number;
    private int size;
    private int total_pages;
    private int total_element;
    private boolean first;
    private boolean last;
    private int number_of_elements;

    private List<T> content;

    public TPage() {
    }

    public TPage(int number, int size, int total_pages, int total_element, boolean first, boolean last, int number_of_elements, List<T> content) {
        this.number = number;
        this.size = size;
        this.total_pages = total_pages;
        this.total_element = total_element;
        this.first = first;
        this.last = last;
        this.number_of_elements = number_of_elements;
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_element() {
        return total_element;
    }

    public void setTotal_element(int total_element) {
        this.total_element = total_element;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumber_of_elements() {
        return number_of_elements;
    }

    public void setNumber_of_elements(int number_of_elements) {
        this.number_of_elements = number_of_elements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void addElement(T t) {
        this.content.add(t);
    }
}
