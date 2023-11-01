package com.example.mappe2_s364536;

public class Avtaler {
    private String avtalerId;
    private String navn;
    private String dato;
    private String klokkeslett;
    private String treffsted;
    private String vennTlf;

    public Avtaler() {
    }

    public Avtaler(long avtalerId, String navn, String dato, String klokkeslett, String treffsted, String vennTlf) {
        this.avtalerId = String.valueOf(avtalerId);
        this.navn = navn;
        this.dato = dato;
        this.klokkeslett = klokkeslett;
        this.treffsted = treffsted;
        this.vennTlf = vennTlf;
    }

    public Avtaler(String navn, String dato, String klokkeslett, String treffsted, String vennTlf) {
        this.navn = navn;
        this.dato = dato;
        this.klokkeslett = klokkeslett;
        this.treffsted = treffsted;
        this.vennTlf = vennTlf;
    }

    public String getAvtalerId() {
        return avtalerId;
    }

    public void setAvtalerId(long avtalerId) {
        this.avtalerId = String.valueOf(avtalerId);
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getKlokkeslett() {
        return klokkeslett;
    }

    public void setKlokkeslett(String klokkeslett) {
        this.klokkeslett = klokkeslett;
    }

    public String getTreffsted() {
        return treffsted;
    }

    public void setTreffsted(String treffsted) {
        this.treffsted = treffsted;
    }

    public String getVennTlf() {
        return vennTlf;
    }

    public void setVennTlf(String vennTlf) {
        this.vennTlf = vennTlf;
    }

    @Override
    public String toString() {
        return  "Navn: " + navn + " | Dato: " + dato + " | Klokkeslett: " + klokkeslett +
                " | Treffsted: " + treffsted + " | Venntlf: " + vennTlf;
    }
}