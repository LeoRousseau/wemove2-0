package com.example.wemove;

public class Sport {
    private String Name;
    private float interest;
    private String level;
    private String type;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public Sport(String name) {
        Name = name;
    }

    public Sport() {
    }

    @Override
    public String toString() {
        return "Sport{" +
                "Nom ='" + Name + '\'' +
                ", Niveau='" + level + '\'' +
                ", Envie ='" + type + '\'' +
                ", Intérêt =" + interest +
                '}';
    }

    public float getInterest() {
        return interest;
    }

    public String getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    public Sport(String name, String level, String type, float interest) {

        this.Name = name;
        this.level = level;
        this.type = type;
        this.interest = interest;
    }
}
