package temp.analizadanych;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Wnioskowanie {
    public List<String> lines;
    public StatystycznaAnalizaDanych sd;
    DecimalFormat df;
    public String korelacjaP;
    public String korelacjaL;
    public String skosnoscP;
    public String skosnoscL;
    public String kurtozaP;
    public String kurtozaL;
    public String analizaWariancjiP;
    public String analizaWariancjiL;
    public String skosnoscPulsometr;
    public String kurtozaPulsometr;
    public String korelacjaKinect;
    public String skosnoscKinect;
    public String kurtozaKinect;
    public String analizaWariancjiKinect;
    public String analizaNormKinect;

    public Wnioskowanie(StatystycznaAnalizaDanych sd1) {
        this.lines = new ArrayList();
        this.sd = sd1;
        this.df = new DecimalFormat();
        this.df.setMaximumFractionDigits(2);
        this.df.setMinimumFractionDigits(2);
    }

    public void wnioskowanieRekawica() {
        analizaKorelacjiRekawica();

        analizaKurtozySkosnosciRekawice();

        analizaWariancjiRekawice(50000.0D);
    }


    public void analizaKorelacjiRekawica() {
        this.korelacjaP = "";
        this.korelacjaL = "";


        double minL = this.sd.korelacjaLewa[1][0];
        double maxL = this.sd.korelacjaLewa[1][0];
        int inwMinL = 1;
        int inkMinL = 0;
        int inwMaxL = 1;
        int inkMaxL = 0;
        for (int i = 0; i < this.sd.korelacjaLewa[0].length; i++) {
            for (int j = i + 1; j < this.sd.korelacjaLewa.length; j++) {
                if (Math.abs(this.sd.korelacjaLewa[j][i]) > Math.abs(maxL)) {
                    maxL = this.sd.korelacjaLewa[j][i];
                    inwMaxL = j;
                    inkMaxL = i;
                } else if (Math.abs(this.sd.korelacjaLewa[j][i]) < Math.abs(minL)) {
                    minL = this.sd.korelacjaLewa[j][i];
                    inwMinL = j;
                    inkMinL = i;
                }
            }
        }


        double minP = this.sd.korelacjaPrawa[1][0];
        double maxP = this.sd.korelacjaPrawa[1][0];
        int inwMinP = 1;
        int inkMinP = 0;
        int inwMaxP = 1;
        int inkMaxP = 0;
        for (int i = 0; i < this.sd.korelacjaPrawa[0].length; i++) {
            for (int j = i + 1; j < this.sd.korelacjaPrawa.length; j++) {
                if (Math.abs(this.sd.korelacjaPrawa[j][i]) > Math.abs(maxP)) {
                    maxP = this.sd.korelacjaPrawa[j][i];
                    inwMaxP = j;
                    inkMaxP = i;
                } else if (Math.abs(this.sd.korelacjaPrawa[j][i]) < Math.abs(minP)) {
                    minP = this.sd.korelacjaPrawa[j][i];
                    inwMinP = j;
                    inkMinP = i;
                }
            }
        }


        this.lines.add("*************REKAWICA********************");
        this.lines.add("-----------------KORELACJA-----------------");
        this.lines.add("Prawa reka: ");
        this.lines.add("Najmniejsza wartosc korelacji abs: " + String.valueOf(this.df.format(minP)));
        this.lines.add(okreslenieKorelacji(minP) + " pomiedzy palcami: " + okresleniePalca(
                inwMinP) + " oraz " + okresleniePalca(inkMinP));
        this.lines.add("Najwieksza wartosc korelacji abs: " + String.valueOf(this.df.format(maxP)));
        this.lines.add(okreslenieKorelacji(maxP) + " pomiedzy palcami: " + okresleniePalca(
                inwMaxP) + " oraz " + okresleniePalca(inkMaxP));
        this.lines.add("");

        this.lines.add("Lewa reka: ");
        this.lines.add("Najmniejsza wartosc korelacji abs: " + String.valueOf(this.df.format(minL)));
        this.lines.add(okreslenieKorelacji(minL) + " pomiedzy palcami: " + okresleniePalca(
                inwMinL) + " oraz " + okresleniePalca(inkMinL));
        this.lines.add("Najwieksza wartosc korelacji abs: " + String.valueOf(maxP));
        this.lines.add(okreslenieKorelacji(maxL) + " pomiedzy palcami: " + okresleniePalca(
                inwMaxL) + " oraz " + okresleniePalca(inkMaxL));
        this.lines.add("");

        this.korelacjaP = (String.valueOf(this.df.format(minP)) + " " + String.valueOf(inwMinP) + ";" + String.valueOf(
                inkMinP) + " " + String.valueOf(this.df.format(maxP)) + " " + String.valueOf(
                inwMaxP) + ";" + String.valueOf(inkMaxP));
        this.korelacjaL = (String.valueOf(this.df.format(minL)) + " " + String.valueOf(inwMinL) + ";" + String.valueOf(
                inkMinL) + " " + String.valueOf(this.df.format(maxL)) + " " + String.valueOf(
                inwMaxL) + ";" + String.valueOf(inkMaxL));
    }

    public String okreslenieKorelacji(double wartosc) {
        String wynik = "";
        if (Math.abs(wartosc) <= 0.2D) {
            wynik = "Brak korelacji";
        }

        if ((Math.abs(wartosc) > 0.2D) && (Math.abs(wartosc) <= 0.4D)) {
            wynik = "Korelacja slaba";
        }

        if ((Math.abs(wartosc) > 0.4D) && (Math.abs(wartosc) <= 0.7D)) {
            wynik = "Korelacja srednia";
        }

        if ((Math.abs(wartosc) > 0.7D) && (Math.abs(wartosc) <= 0.9D)) {
            wynik = "Korelacja silna";
        }

        if ((Math.abs(wartosc) > 0.9D) && (Math.abs(wartosc) <= 1.0D)) {
            wynik = "Korelacja bardzo silna";
        }

        return wynik;
    }

    public String okresleniePalca(int indeks) {
        String wynik = "";
        if (indeks == 0) {
            wynik = "kciuk";
        }

        if (indeks == 1) {
            wynik = "wskazujący";
        }
        if (indeks == 2) {
            wynik = "srodkowy";
        }

        if (indeks == 3) {
            wynik = "serdeczny";
        }
        if (indeks == 4) {
            wynik = "maly";
        }
        return wynik;
    }


    public void analizaKurtozySkosnosciRekawice() {
        this.skosnoscL = "";
        this.skosnoscP = "";

        this.lines.add("-----------------ANALIZA SKOSNOSCI-----------------");
        this.lines.add("Prawa reka:");
        for (int i = 0; i < 5; i++) {
            if (this.sd.wspolczynnikSkosnosciRekawicaPrawa[i] > 0.0D) {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " ma prawostronna asymetria rokładu");
                this.skosnoscP += "p ";
            } else if (this.sd.wspolczynnikSkosnosciRekawicaPrawa[i] < 0.0D) {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " ma lewostronna asymetrie rozkladu");
                this.skosnoscP += "l ";
            } else {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " nie ma asymetrii");
                this.skosnoscP += "b ";
            }
        }


        this.skosnoscP = this.skosnoscP.substring(0, this.skosnoscP.length() - 1);

        this.lines.add("");

        this.lines.add("Lewa reka:");
        for (int i = 0; i < 5; i++) {
            if (this.sd.wspolczynnikSkosnosciRekawicaLewa[i] > 0.0D) {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " ma prawostronna asymetrie rozkladu");
                this.skosnoscL += "p ";
            } else if (this.sd.wspolczynnikSkosnosciRekawicaLewa[i] < 0.0D) {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " ma lewostronna asymetrie rozkladu");
                this.skosnoscL += "l ";
            } else {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " nie ma asymetrii");
                this.skosnoscL += "b ";
            }
        }

        this.skosnoscL = this.skosnoscL.substring(0, this.skosnoscL.length() - 1);
        this.lines.add("");

        this.kurtozaL = "";
        this.kurtozaP = "";
        this.lines.add("-----------------ANALIZA KURTOZY-----------------");

        for (int i = 0; i < 5; i++) {
            if (this.sd.kurtozaRekawicaPrawa[i] > 0.0D) {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " ma rozklad leptokurtyczny");
                this.kurtozaP += "l ";
            } else if (this.sd.kurtozaRekawicaPrawa[i] < 0.0D) {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " ma rozklad platokurtyczny");
                this.kurtozaP += "p ";
            } else {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " ma rozklad normlany");
                this.kurtozaP += "n ";
            }
        }

        this.kurtozaP = this.kurtozaP.substring(0, this.kurtozaP.length() - 1);
        this.lines.add("");

        this.lines.add("Lewa reka:");
        for (int i = 0; i < 5; i++) {
            if (this.sd.kurtozaRekawicaLewa[i] > 0.0D) {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " ma rozklad leptokurtyczny");
                this.kurtozaL += "l ";
            } else if (this.sd.kurtozaRekawicaLewa[i] < 0.0D) {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " ma rozklad platokurtyczny");
                this.kurtozaL += "p ";
            } else {
                this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i] + " ma rozklad normalny");
                this.kurtozaL += "n ";
            }
        }

        this.kurtozaL = this.kurtozaL.substring(0, this.kurtozaL.length() - 1);
        this.lines.add("");
    }


    public void analizaWariancjiRekawice(double prog) {
        this.analizaWariancjiP = "";
        this.analizaWariancjiL = "";
        this.lines.add("-------------------- ANALIZA WARIANCJI--------------------");
        this.lines.add("Prawa reka");
        ArrayList sekundy = new ArrayList();

        for (int i = 0; i < this.sd.wariancjaRekawicaPrawaOdcinki1s.length; i++) {
            boolean pom = true;
            for (int j = 0; j < this.sd.wariancjaRekawicaPrawaOdcinki1s[0].length; j++) {
                if (this.sd.wariancjaRekawicaPrawaOdcinki1s[i][j] > prog) {
                    sekundy.add(Integer.valueOf(j + 1));
                    this.analizaWariancjiP = (this.analizaWariancjiP + String.valueOf(j + 1) + " ");
                    pom = false;
                }
            }

            if (pom == true) {
                this.analizaWariancjiP += "-1;";
            } else {
                this.analizaWariancjiP = this.analizaWariancjiP.substring(0, this.analizaWariancjiP.length() - 1);
                this.analizaWariancjiP += ";";
            }

            this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i]);
            this.lines.add(Arrays.toString(sekundy.toArray()));

            sekundy.clear();
        }
        this.analizaWariancjiP = this.analizaWariancjiP.substring(0, this.analizaWariancjiP.length() - 1);

        this.lines.add("");
        this.lines.add("Lewa reka");
        sekundy.clear();

        for (int i = 0; i < this.sd.wariancjaRekawicaPrawaOdcinki1s.length; i++) {
            boolean pom = true;
            for (int j = 0; j < this.sd.wariancjaRekawicaPrawaOdcinki1s[0].length; j++) {
                if (this.sd.wariancjaRekawicaLewaOdcinki1s[i][j] > prog) {
                    sekundy.add(Integer.valueOf(j + 1));
                    this.analizaWariancjiL = (this.analizaWariancjiL + String.valueOf(j + 1) + " ");
                    pom = false;
                }
            }


            if (pom == true) {
                this.analizaWariancjiL += "-1;";
            } else {
                this.analizaWariancjiL = this.analizaWariancjiL.substring(0, this.analizaWariancjiL.length() - 1);
                this.analizaWariancjiL += ";";
            }

            this.lines.add(this.sd.plikRekawice.opisDanychRekawica[i]);
            this.lines.add(Arrays.toString(sekundy.toArray()));

            sekundy.clear();
        }
        this.analizaWariancjiL = this.analizaWariancjiL.substring(0, this.analizaWariancjiL.length() - 1);
    }


    public void wnioskowanieKinect() {
        analizaKorelacjiKinect();

        analizaKurtozySkosnosciKinect();

        analizaOdleglosciKinect(0.15D);

        analizaWariancjiKinect(0.05D);
    }


    public void analizaKorelacjiKinect() {
        String czescMin = "";
        String czescMax = "";
        String co = "";
        this.korelacjaKinect = "";
        this.lines.add("*************KINECT********************");
        for (int i = 0; i < this.sd.korelacjaRozniceKinect.length - 2; i++) {
            int inwMin = znajdzIndeksMin(i);
            int inwMax = znajdzIndeksMax(i);
            double min = this.sd.korelacjaRozniceKinect[inwMin][i];
            double max = this.sd.korelacjaRozniceKinect[inwMax][i];
            czescMin = this.sd.plikKinect.opisDanychKinectNormy[inwMin];
            czescMax = this.sd.plikKinect.opisDanychKinectNormy[inwMax];
            co = this.sd.plikKinect.opisDanychKinectNormy[i];

            this.korelacjaKinect = (this.korelacjaKinect + String.valueOf(i) + " " + String.valueOf(
                    this.df.format(min)) + " " + String.valueOf(inwMin) + " " + String.valueOf(
                    this.df.format(max)) + " " + String.valueOf(inwMax) + ";");
            this.lines.add(co);
            this.lines.add("Najmniejsza wartosc korelacji abs: " + String.valueOf(this.df.format(min)));
            this.lines.add(okreslenieKorelacji(min) + " z " + czescMin);
            this.lines.add("Najwieksza wartosc korelacji abs: " + String.valueOf(this.df.format(max)));
            this.lines.add(okreslenieKorelacji(max) + " z " + czescMax);
            this.lines.add("");
        }


        co = this.sd.plikKinect.opisDanychKinectNormy[(this.sd.plikKinect.opisDanychKinectNormy.length - 2)];
        czescMin = this.sd.plikKinect.opisDanychKinectNormy[(this.sd.plikKinect.opisDanychKinectNormy.length - 1)];
        double min = this.sd.korelacjaRozniceKinect[(this.sd.korelacjaRozniceKinect.length - 1)][(this.sd.korelacjaRozniceKinect[0].length - 2)];
        this.lines.add(co);
        this.lines.add("Najmniejsza/najwieksza wartosc korelacji abs: " + String.valueOf(this.df.format(min)));
        this.lines.add(okreslenieKorelacji(min) + " z " + czescMin);

        this.lines.add("");

        this.korelacjaKinect = (this.korelacjaKinect + String.valueOf(
                this.sd.plikKinect.opisDanychKinectNormy.length - 2) + " " + String.valueOf(
                this.df.format(min)) + " " + String.valueOf(this.sd.plikKinect.opisDanychKinectNormy.length - 1));
    }


    public int znajdzIndeksMin(int index) {
        int in = index + 1;
        double min = this.sd.korelacjaRozniceKinect[(index + 1)][index];
        for (int i = in + 1; i < this.sd.korelacjaRozniceKinect[0].length; i++) {
            if (Math.abs(min) > Math.abs(this.sd.korelacjaRozniceKinect[i][index])) {
                min = this.sd.korelacjaRozniceKinect[i][index];
                in = i;
            }
        }
        return in;
    }

    public int znajdzIndeksMax(int index) {
        int in = index + 1;
        double max = this.sd.korelacjaRozniceKinect[(index + 1)][index];
        for (int i = in + 1; i < this.sd.korelacjaRozniceKinect[0].length; i++) {
            if (Math.abs(max) < Math.abs(this.sd.korelacjaRozniceKinect[i][index])) {
                max = this.sd.korelacjaRozniceKinect[i][index];
                in = i;
            }
        }
        return in;
    }


    public void analizaKurtozySkosnosciKinect() {
        this.skosnoscKinect = "";
        this.lines.add("-----------------ANALIZA SKOSNOSCI-----------------");

        for (int i = 0; i < this.sd.wspolczynikSkosnosciKinect.length; i++) {
            if (this.sd.wspolczynikSkosnosciKinect[i] > 0.0D) {
                this.lines.add(this.sd.plikKinect.opisDanychKinectNormy[i] + " ma prawostronna asymetria rokładu");
                this.skosnoscKinect += "p ";
            } else if (this.sd.wspolczynikSkosnosciKinect[i] < 0.0D) {
                this.lines.add(this.sd.plikKinect.opisDanychKinectNormy[i] + " ma lewostronna asymetrie rozkladu");
                this.skosnoscKinect += "l ";
            } else {
                this.lines.add(this.sd.plikKinect.opisDanychKinectNormy[i] + " nie ma asymetrii");
                this.skosnoscKinect += "b ";
            }
        }

        this.skosnoscKinect = this.skosnoscKinect.substring(0, this.skosnoscKinect.length() - 1);

        this.lines.add("");
        this.kurtozaKinect = "";
        this.lines.add("-----------------ANALIZA KURTOZY-----------------");

        for (int i = 0; i < this.sd.kurtozaKinect.length; i++) {
            if (this.sd.kurtozaKinect[i] > 0.0D) {
                this.lines.add(this.sd.plikKinect.opisDanychKinectNormy[i] + " ma rozklad leptokurtyczny");
                this.kurtozaKinect += "l ";
            } else if (this.sd.kurtozaKinect.length < 0) {
                this.lines.add(this.sd.plikKinect.opisDanychKinectNormy[i] + " ma rozklad platokurtyczny");
                this.kurtozaKinect += "p ";
            } else {
                this.lines.add(this.sd.plikKinect.opisDanychKinectNormy[i] + " ma rozklad normalny");
                this.kurtozaKinect += "n ";
            }
        }

        this.kurtozaKinect = this.kurtozaKinect.substring(0, this.kurtozaKinect.length() - 1);
        this.lines.add("");
    }


    public void analizaOdleglosciKinect(double prog) {
        this.analizaNormKinect = "";
        this.lines.add("------------------------ANALIZA NORM-------------------");
        ArrayList sekundy = new ArrayList();


        for (int i = 0; i < this.sd.plikKinect.daneNormyRoznicaKinect[0].length; i++) {
            boolean pom = true;
            for (int j = 0; j < this.sd.plikKinect.daneNormyRoznicaKinect.length; j++) {
                if (this.sd.plikKinect.daneNormyRoznicaKinect[j][i] > prog) {
                    if (j <= 28) {
                        if (!sekundy.contains(Integer.valueOf(1))) {
                            sekundy.add(Integer.valueOf(1));
                            this.analizaNormKinect += "1 ";
                            pom = false;
                        }
                    } else {
                        int rob1 = (j - 28) / 30;

                        if (!sekundy.contains(Integer.valueOf(rob1 + 2))) {
                            sekundy.add(Integer.valueOf(rob1 + 2));
                            this.analizaNormKinect = (this.analizaNormKinect + String.valueOf(rob1 + 2) + " ");
                            pom = false;
                        }
                    }
                }
            }


            if (pom == true) {
                this.analizaNormKinect += "-1;";
            } else {
                this.analizaNormKinect = this.analizaNormKinect.substring(0, this.analizaNormKinect.length() - 1);
                this.analizaNormKinect += ";";
            }

            this.lines.add(this.sd.plikKinect.opisDanychKinectNormy[i]);
            this.lines.add(Arrays.toString(sekundy.toArray()));

            sekundy.clear();
        }

        this.analizaNormKinect = this.analizaNormKinect.substring(0, this.analizaNormKinect.length() - 1);
        this.lines.add("");
    }

    public void analizaWariancjiKinect(double prog) {
        this.analizaWariancjiKinect = "";
        this.lines.add("-------------------ANALIZA WARIANCJI--------------------");
        ArrayList sekundy = new ArrayList();


        for (int i = 0; i < this.sd.wariancjaKinekctOdcinki1s[0].length; i++) {
            boolean pom = true;
            for (int j = 0; j < this.sd.wariancjaKinekctOdcinki1s.length; j++) {
                if (this.sd.wariancjaKinekctOdcinki1s[j][i] > prog) {
                    sekundy.add(Integer.valueOf(j + 1));
                    this.analizaWariancjiKinect = (this.analizaWariancjiKinect + String.valueOf(j + 1) + " ");
                    pom = false;
                }
            }


            if (pom == true) {
                this.analizaWariancjiKinect += "-1;";
            } else {
                this.analizaWariancjiKinect = this.analizaWariancjiKinect.substring(0,
                                                                                    this.analizaWariancjiKinect.length() - 1);
                this.analizaWariancjiKinect += ";";
            }

            this.lines.add(this.sd.plikKinect.opisDanychKinectNormy[i]);
            this.lines.add(Arrays.toString(sekundy.toArray()));

            sekundy.clear();
        }

        this.analizaWariancjiKinect = this.analizaWariancjiKinect.substring(0,
                                                                            this.analizaWariancjiKinect.length() - 1);

        this.lines.add("");
    }


    public void kurtozaPulsometr() {
        this.kurtozaPulsometr = "";
        this.lines.add("-------------------KURTOZA--------------------");
        if (this.sd.kurtozaPulsometr > 0.0D) {
            this.lines.add("rozklad leptokurtyczny");
            this.kurtozaPulsometr = "l";
        } else if (this.sd.kurtozaPulsometr < 0.0D) {
            this.lines.add("rozklad platokurtyczny");
            this.kurtozaPulsometr = "p";
        } else {
            this.lines.add("rozklad normalny");
            this.kurtozaPulsometr = "n";
        }
    }


    public void skosnoscPulsometr() {
        this.skosnoscPulsometr = "";
        this.lines.add("-------------------WSPOLCZYNNIK SKOSNOSCI--------------------");
        if (this.sd.wspolczynnikSkosnosciPulsometr > 0.0D) {
            this.lines.add("prawostronna asymetria rokładu");
            this.skosnoscPulsometr = "p";
        } else if (this.sd.wspolczynnikSkosnosciPulsometr < 0.0D) {
            this.lines.add("lewostronna asymetrie rozkladu");
            this.skosnoscPulsometr = "l";
        } else {
            this.lines.add(" ma asymetrii");
            this.skosnoscPulsometr = "b";
        }
    }

    public void wnioskowaniePulsometr() {
        this.lines.add("***********************PULSOMETR*****************************");

        kurtozaPulsometr();

        skosnoscPulsometr();
    }

    public void tworzenieWnioskowania() {
        if (this.sd.plikRekawice != null) {
            wnioskowanieRekawica();
        }

        if (this.sd.plikKinect != null) {
            wnioskowanieKinect();
        }
        if (this.sd.plikPulsometr != null) {
            wnioskowaniePulsometr();
        }
    }
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\analizadanych\Wnioskowanie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */