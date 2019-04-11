package com.fate;

import java.util.List;

/**
 * @Author oyg
 * @Date 2018/8/5/21:12
 */
public class TestResponse<T, K, M> {

    private List<T> tList;

    private K k;

    private M m;

    public List<T> gettList() {
        return tList;
    }

    public void settList(List<T> tList) {
        this.tList = tList;
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public M getM() {
        return m;
    }

    public void setM(M m) {
        this.m = m;
    }
}
