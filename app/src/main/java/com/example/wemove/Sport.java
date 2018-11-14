package com.example.wemove;

public class Sport {
    String Name;
    String Niveau;
    String Envie;
    Integer Intérêt;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNiveau() {
        return Niveau;
    }

    public Sport(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "Nom ='" + Name + '\'' +
                ", Niveau='" + Niveau + '\'' +
                ", Envie ='" + Envie + '\'' +
                ", Intérêt =" + Intérêt +
                '}';
    }

    public void setNiveau(String niveau) {
        Niveau = niveau;
    }

    public String getEnvie() {
        return Envie;
    }

    public void setEnvie(String envie) {
        Envie = envie;
    }

    public Integer getIntérêt() {
        return Intérêt;
    }

    public void setIntérêt(Integer intérêt) {
        Intérêt = intérêt;
    }

    public Sport(String name, String niveau, String envie, Integer intérêt) {

        Name = name;
        Niveau = niveau;
        Envie = envie;
        Intérêt = intérêt;
    }
}
