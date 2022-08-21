package com.epra.wiki.req;

public class PageReq {
    private int page;

    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        final StringBuffer stringBuffer = new StringBuffer("PageReq{");
        stringBuffer.append("page=").append(page);
        stringBuffer.append(",size=").append(size);
        stringBuffer.append('}');

        return stringBuffer.toString();
    }
}