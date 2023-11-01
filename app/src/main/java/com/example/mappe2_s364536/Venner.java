package com.example.mappe2_s364536;

public class Venner {
    private long vennerId;
    private String navn;
    private String Tlf;

    public Venner(long vennerId, String navn, String tlf) {
        this.vennerId = vennerId;
        this.navn = navn;
        Tlf = tlf;
    }

    public Venner(String navn, String tlf) {
        this.navn = navn;
        Tlf = tlf;
    }

    public Venner() {
    }

    public long getVennerId() {
        return vennerId;
    }

    public void setVennerId(long vennerId) {
        this.vennerId = vennerId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getTlf() {
        return Tlf;
    }

    public void setTlf(String tlf) {
        Tlf = tlf;
    }

    @Override
    public String toString() {
        return "Navn: " + navn + " | Tlf: " + Tlf;
    }
}
