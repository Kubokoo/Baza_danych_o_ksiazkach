package com.JGSS.Projekt.Classes;

import java.util.Date;

public class Book {
    private String ISBN;
    private String title;
    private Date release_Date;
    private String author;
    private String publishing_House;
    private Integer owner;

    public Book(String ISBN, String title, Date release_Date, String author, String publishing_House, Integer owner) {
        this.ISBN = ISBN;
        this.title = title;
        this.release_Date = release_Date;
        this.author = author;
        this.publishing_House = publishing_House;
        this.owner = owner;
    }

    public Book(){
        super();
    }

    public String getPublishing_House() {
        return publishing_House;
    }

    public void setPublishing_House(String publishing_House) {
        this.publishing_House = publishing_House;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRelease_Date() {
        return release_Date;
    }

    public void setRelease_Date(Date release_Date) {
        this.release_Date = release_Date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisihng_House() {
        return publishing_House;
    }

    public void setPublisihng_House(String publisihng_House) {
        this.publishing_House = publisihng_House;
    }
}