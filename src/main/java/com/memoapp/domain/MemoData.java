package com.memoapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MemoData{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String content;

    public MemoData() {
        super();
    }

    public MemoData(String title, String content) {
        super();
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
