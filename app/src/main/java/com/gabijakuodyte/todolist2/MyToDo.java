package com.gabijakuodyte.todolist2;

public class MyToDo {
    String titleToDo;
    String dateToDo;
    String descToDo;
    String catToDo;
    String keyToDo;

    public MyToDo() {
    }

    public MyToDo(String titleToDo, String dateToDo, String descToDo, String catToDo, String keyToDo) {
        this.titleToDo = titleToDo;
        this.dateToDo = dateToDo;
        this.descToDo = descToDo;
        this.catToDo = catToDo;
        this.keyToDo = keyToDo;
    }

    public String getCatToDo() {
        return catToDo;
    }

    public void setCatToDo(String catToDo) {
        this.catToDo = catToDo;
    }

    public String getKeyToDo() {
        return keyToDo;
    }

    public void setKeyToDo(String keyToDo) {
        this.keyToDo = keyToDo;
    }

    public String getTitleToDo() {
        return titleToDo;
    }

    public void setTitleToDo(String titleToDo) {
        this.titleToDo = titleToDo;
    }

    public String getDateToDo() {
        return dateToDo;
    }

    public void setDateToDo(String dateToDo) {
        this.dateToDo = dateToDo;
    }

    public String getDescToDo() {
        return descToDo;
    }

    public void setDescToDo(String descToDo) {
        this.descToDo = descToDo;
    }
}
