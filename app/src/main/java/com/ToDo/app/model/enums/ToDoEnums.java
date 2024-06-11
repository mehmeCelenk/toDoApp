package com.ToDo.app.model.enums;

public enum ToDoEnums {
    PENDING("pending"),
    COMPLETED("completed");



    private String value;

    ToDoEnums(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
