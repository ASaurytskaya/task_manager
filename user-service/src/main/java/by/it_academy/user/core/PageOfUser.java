package by.it_academy.user.core;

import by.it_academy.user.dao.entity.UserEntity;

public class PageOfUser {

    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int numberOfElements;
    private boolean last;
    private UserEntity content;

    public PageOfUser() {
    }

    public PageOfUser(int number, int size, int totalPages, long totalElements, boolean first, int numberOfElements, boolean last, UserEntity content) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.numberOfElements = numberOfElements;
        this.last = last;
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

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public UserEntity getContent() {
        return content;
    }

    public void setContent(UserEntity content) {
        this.content = content;
    }
}
