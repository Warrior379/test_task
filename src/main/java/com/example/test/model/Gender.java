package com.example.test.model;

import java.util.ArrayList;
import java.util.List;

public enum Gender {
    MALE("Male",0),FEMALE("Female",1);

    private final String displayName;
    private final int integerName;

    Gender(String displayName, int integerName) {
        this.displayName = displayName;
        this.integerName = integerName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getIntegerName() {
        return integerName;
    }
}
