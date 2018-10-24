package temp.analizadanych;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabSyntaxException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;


public class StatystycznaAnalizaDanych {
    Plik plikPulsometr;
    Plik plikKinect;
    Plik plikRekawice;
    String sciezka;
    double sredniaPulsometr;
    double[] sredniaPulsometrOdcinki1s;
    double wariancjaPulsometr;
    double odchyleniePulsometr;
    double wspolczynnikSkosnosciPulsometr;
    double kurtozaPulsometr;
    double[] sredniaRekawicaLewa;
    double[][] sredniaRekawicaLewaOdcinki1s;
    double[] sredniaRekawicaPrawa;
    double[][] sredniaRekawicaPrawaOdcinki1s;
    double[] wariancjaRekawicaLewa;
    double[][] wariancjaRekawicaLewaOdcinki1s;
    double[] wariancjaRekawicaPrawa;
    double[][] wariancjaRekawicaPrawaOdcinki1s;
    double[] odchylenieRekawicaLewa;
    double[][] odchylenieRekawicaLewaOdcinki1s;
    double[] odchylenieRekawicaPrawa;
    double[][] odchylenieRekawicaPrawaOdcinki1s;
    double[] wspolczynnikSkosnosciRekawicaLewa;
    double[][] wspolczynnikSkosnosciRekawicaLewaOdcinki1s;
    double[] wspolczynnikSkosnosciRekawicaPrawa;
    double[][] wspolczynnikSkosnosciRekawicaPrawaOdcinki1s;
    double[] kurtozaRekawicaPrawa;
    double[] kurtozaRekawicaLewa;
    double[][] kurtozaRekawicaPrawaOdcinki1s;
    double[][] kurtozaRekawicaLewaOdcinki1s;
    double[] sredniaKinect;
    double[][] sredniaKinekctOdcinki1s;
    double[] wariancjaKinect;
    double[][] wariancjaKinekctOdcinki1s;
    double[] odchylenieKinect;
    double[][] odchylenieKinekctOdcinki1s;
    double[] wspolczynikSkosnosciKinect;
    double[][] wspolczynnikSkosnosciKinekctOdcinki1s;
    double[] kurtozaKinect;
    double[][] kurtozaKinectOdcinki1s;
    MatlabEngine eng;
    double[][] korelacjaLewa;
    double[][] korelacjaPrawa;
    double[][] korelacjaRozniceKinect;
    DecimalFormat df;

    public StatystycznaAnalizaDanych(Plik wczytany, String sciezka) throws EngineException, InterruptedException {
        this.eng = MatlabEngine.startMatlab();
        this.plikRekawice = null;
        this.plikKinect = null;
        this.plikPulsometr = null;

        if (wczytany.pulsometr == true) {
            this.plikPulsometr = wczytany;
        }

        if (wczytany.kinect == true) {
            this.plikKinect = wczytany;
        }

        if (wczytany.rekawica == true) {
            this.plikRekawice = wczytany;
        }

        this.sciezka = sciezka;
        this.df = new DecimalFormat();
        this.df.setMaximumFractionDigits(3);
        this.df.setMinimumFractionDigits(3);
    }

    public void sredniaArytmetycznaPulsometr() {
        this.sredniaPulsometr = 0.0D;
        for (double wartosc : this.plikPulsometr.danePulsometr) {
            this.sredniaPulsometr += wartosc;
        }
        this.sredniaPulsometr /= this.plikPulsometr.danePulsometr.length;


        this.sredniaPulsometrOdcinki1s = this.plikPulsometr.danePulsometr;
    }


    public void wariancjaArytmetycznaPulsometr() {
        for (int i = 0; i < this.plikPulsometr.danePulsometr.length; i++) {
            this.wariancjaPulsometr += Math.pow(this.plikPulsometr.danePulsometr[i] - this.sredniaPulsometr, 2.0D);
        }
        this.wariancjaPulsometr /= this.plikPulsometr.danePulsometr.length;
    }


    public void odchylenieStandardowePulsometr() {
        this.odchyleniePulsometr = Math.sqrt(this.wariancjaPulsometr);
    }


    private void wspolczynnikSkosnosciPulsometr() {
        double n = this.plikPulsometr.danePulsometr.length;


        for (int i = 0; i < this.plikPulsometr.danePulsometr.length; i++) {
            this.wspolczynnikSkosnosciPulsometr += Math.pow(this.plikPulsometr.danePulsometr[i] - this.sredniaPulsometr,
                                                            3.0D);
        }
        this.wspolczynnikSkosnosciPulsometr *= n / ((n - 1.0D) * (n - 2.0D) * Math.pow(this.odchyleniePulsometr, 3.0D));
    }


    public void kurtozaPulsometr() {
        double n = this.plikPulsometr.danePulsometr.length;
        double suma = 0.0D;

        for (int i = 0; i < this.plikPulsometr.danePulsometr.length; i++) {
            suma += Math.pow((this.plikPulsometr.danePulsometr[i] - this.sredniaPulsometr) / this.odchyleniePulsometr,
                             4.0D);
        }
        this.kurtozaPulsometr = (n * (n + 1.0D) / ((n - 1.0D) * (n - 2.0D) * (n - 3.0D)) * suma - 3.0D * (n - 1.0D) * (n - 1.0D) / ((n - 2.0D) * (n - 3.0D)));
    }

    public List<String> przygotowanieRaportPulsometr() {
        sredniaArytmetycznaPulsometr();
        wariancjaArytmetycznaPulsometr();
        odchylenieStandardowePulsometr();
        wspolczynnikSkosnosciPulsometr();
        kurtozaPulsometr();

        List<String> lines = new ArrayList();
        lines.add("--------------------Statystyki dla pulsometru--------------------");
        lines.add("Średnia arytmetyczna = " + this.df.format(this.sredniaPulsometr));
        lines.add("Wariancja = " + this.df.format(this.wariancjaPulsometr));
        lines.add("Odchylenie standardowe = " + this.df.format(this.odchyleniePulsometr));
        lines.add("Współczynnik skośności = " + this.df.format(this.wspolczynnikSkosnosciPulsometr));
        lines.add("Kurtoza= " + this.df.format(this.kurtozaPulsometr));
        lines.add("");
        return lines;
    }

    public void sredniaArytmetycznaRekawica() {
        this.sredniaRekawicaLewa = new double[5];
        this.sredniaRekawicaPrawa = new double[5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < this.plikRekawice.daneRekawicaLewa[0].length / 4 * 4; j++) {
                this.sredniaRekawicaLewa[i] += this.plikRekawice.daneRekawicaLewa[i][j];
            }
            this.sredniaRekawicaLewa[i] /= this.plikRekawice.daneRekawicaLewa[0].length;

            for (int j = 0; j < this.plikRekawice.daneRekawicaPrawa[0].length / 4 * 4; j++) {
                this.sredniaRekawicaPrawa[i] += this.plikRekawice.daneRekawicaPrawa[i][j];
            }
            this.sredniaRekawicaPrawa[i] /= this.plikRekawice.daneRekawicaPrawa[0].length / 4 * 4;
        }


        int pom = this.plikRekawice.daneRekawicaPrawa[0].length;

        this.sredniaRekawicaPrawaOdcinki1s = new double[5][pom / 4];
        double suma = 0.0D;
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; (j < pom) && (k < pom / 4); j++) {
                if (j % 4 == 3) {
                    suma += this.plikRekawice.daneRekawicaPrawa[i][j];
                    this.sredniaRekawicaPrawaOdcinki1s[i][k] = (suma / 4.0D);
                    k++;
                    suma = 0.0D;
                } else {
                    suma += this.plikRekawice.daneRekawicaPrawa[i][j];
                }
            }

            suma = 0.0D;
            k = 0;
        }

        pom = this.plikRekawice.daneRekawicaLewa[0].length;

        this.sredniaRekawicaLewaOdcinki1s = new double[5][pom / 4];
        suma = 0.0D;
        k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; (j < pom) && (k < pom / 4); j++) {
                if (j % 4 == 3) {
                    suma += this.plikRekawice.daneRekawicaLewa[i][j];
                    this.sredniaRekawicaLewaOdcinki1s[i][k] = (suma / 4.0D);
                    k++;
                    suma = 0.0D;
                } else {
                    suma += this.plikRekawice.daneRekawicaLewa[i][j];
                }
            }

            suma = 0.0D;
            k = 0;
        }
    }

    public void wariancjaRekawica() {
        this.wariancjaRekawicaLewa = new double[5];
        this.wariancjaRekawicaPrawa = new double[5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < this.plikRekawice.daneRekawicaLewa[0].length; j++) {
                this.wariancjaRekawicaLewa[i] += Math.pow(
                        this.plikRekawice.daneRekawicaLewa[i][j] - this.sredniaRekawicaLewa[i], 2.0D);
            }
            this.wariancjaRekawicaLewa[i] /= (this.plikRekawice.daneRekawicaLewa[0].length - 1);

            for (int j = 0; j < this.plikRekawice.daneRekawicaPrawa[0].length; j++) {
                this.wariancjaRekawicaPrawa[i] += Math.pow(
                        this.plikRekawice.daneRekawicaPrawa[i][j] - this.sredniaRekawicaPrawa[i], 2.0D);
            }
            this.wariancjaRekawicaPrawa[i] /= (this.plikRekawice.daneRekawicaPrawa[0].length - 1);
        }


        int pom = this.plikRekawice.daneRekawicaPrawa[0].length;
        this.wariancjaRekawicaPrawaOdcinki1s = new double[5][pom / 4];
        double suma = 0.0D;
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; (j < pom) && (k < pom / 4); j++) {
                if (j % 4 == 3) {
                    suma += Math.pow(
                            this.plikRekawice.daneRekawicaPrawa[i][j] - this.sredniaRekawicaPrawaOdcinki1s[i][k], 2.0D);
                    this.wariancjaRekawicaPrawaOdcinki1s[i][k] = (suma / 4.0D);
                    k++;
                    suma = 0.0D;
                } else {
                    suma += Math.pow(
                            this.plikRekawice.daneRekawicaPrawa[i][j] - this.sredniaRekawicaPrawaOdcinki1s[i][k], 2.0D);
                }
            }

            suma = 0.0D;
            k = 0;
        }

        pom = this.plikRekawice.daneRekawicaLewa[0].length;
        this.wariancjaRekawicaLewaOdcinki1s = new double[5][pom / 4];
        suma = 0.0D;
        k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; (j < pom) && (k < pom / 4); j++) {
                if (j % 4 == 3) {
                    suma += Math.pow(this.plikRekawice.daneRekawicaLewa[i][j] - this.sredniaRekawicaLewaOdcinki1s[i][k],
                                     2.0D);
                    this.wariancjaRekawicaLewaOdcinki1s[i][k] = (suma / 4.0D);
                    k++;
                    suma = 0.0D;
                } else {
                    suma += Math.pow(this.plikRekawice.daneRekawicaLewa[i][j] - this.sredniaRekawicaLewaOdcinki1s[i][k],
                                     2.0D);
                }
            }

            suma = 0.0D;
            k = 0;
        }
    }


    public void odchylenieStandardoweRekawica() {
        this.odchylenieRekawicaLewa = new double[5];
        this.odchylenieRekawicaPrawa = new double[5];

        for (int i = 0; i < 5; i++) {
            this.odchylenieRekawicaLewa[i] = Math.sqrt(this.wariancjaRekawicaLewa[i]);
            this.odchylenieRekawicaPrawa[i] = Math.sqrt(this.wariancjaRekawicaPrawa[i]);
        }


        int pom = this.sredniaRekawicaPrawaOdcinki1s[0].length;
        this.odchylenieRekawicaPrawaOdcinki1s = new double[5][pom];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < pom; j++) {
                this.odchylenieRekawicaPrawaOdcinki1s[i][j] = Math.sqrt(this.wariancjaRekawicaPrawaOdcinki1s[i][j]);
            }
        }

        pom = this.sredniaRekawicaLewaOdcinki1s[0].length;
        this.odchylenieRekawicaLewaOdcinki1s = new double[5][pom];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < pom; j++) {
                this.odchylenieRekawicaLewaOdcinki1s[i][j] = Math.sqrt(this.wariancjaRekawicaLewaOdcinki1s[i][j]);
            }
        }
    }


    private void wspolczynnikSkosnosciRekawica() {
        this.wspolczynnikSkosnosciRekawicaLewa = new double[5];
        this.wspolczynnikSkosnosciRekawicaPrawa = new double[5];

        double n = this.plikRekawice.daneRekawicaLewa[0].length;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < this.plikRekawice.daneRekawicaLewa[0].length; j++) {
                this.wspolczynnikSkosnosciRekawicaLewa[i] += Math.pow(
                        this.plikRekawice.daneRekawicaLewa[i][j] - this.sredniaRekawicaLewa[i], 3.0D);
            }
            this.wspolczynnikSkosnosciRekawicaLewa[i] *= n / ((n - 1.0D) * (n - 2.0D) * Math.pow(
                    this.odchylenieRekawicaLewa[i], 3.0D));

            for (int j = 0; j < this.plikRekawice.daneRekawicaPrawa[0].length; j++) {
                this.wspolczynnikSkosnosciRekawicaPrawa[i] += Math.pow(
                        this.plikRekawice.daneRekawicaPrawa[i][j] - this.sredniaRekawicaPrawa[i], 3.0D);
            }
            this.wspolczynnikSkosnosciRekawicaPrawa[i] *= n / ((n - 1.0D) * (n - 2.0D) * Math.pow(
                    this.odchylenieRekawicaPrawa[i], 3.0D));
        }


        int pom = this.plikRekawice.daneRekawicaPrawa[0].length;
        this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s = new double[5][pom / 4];
        double suma = 0.0D;
        double pom1 = 4.0D;
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; (j < pom) && (k < pom / 4); j++) {
                if (j % 4 == 3) {
                    suma += Math.pow(
                            this.plikRekawice.daneRekawicaPrawa[i][j] - this.sredniaRekawicaPrawaOdcinki1s[i][k], 3.0D);
                    this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[i][k] = (suma * (pom1 / ((pom1 - 1.0D) * (pom1 - 2.0D) * Math
                            .pow(this.odchylenieRekawicaPrawaOdcinki1s[i][k], 3.0D))));

                    k++;
                    suma = 0.0D;
                } else {
                    suma += Math.pow(
                            this.plikRekawice.daneRekawicaPrawa[i][j] - this.sredniaRekawicaPrawaOdcinki1s[i][k], 3.0D);
                }
            }

            suma = 0.0D;
            k = 0;
        }

        pom = this.plikRekawice.daneRekawicaLewa[0].length;
        this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s = new double[5][pom / 4];
        suma = 0.0D;
        k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; (j < pom) && (k < pom / 4); j++) {
                if (j % 4 == 3) {
                    suma += Math.pow(this.plikRekawice.daneRekawicaLewa[i][j] - this.sredniaRekawicaLewaOdcinki1s[i][k],
                                     3.0D);
                    this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[i][k] = (suma * (pom1 / ((pom1 - 1.0D) * (pom1 - 2.0D) * Math
                            .pow(this.odchylenieRekawicaLewaOdcinki1s[i][k], 3.0D))));

                    k++;
                    suma = 0.0D;
                } else {
                    suma += Math.pow(this.plikRekawice.daneRekawicaLewa[i][j] - this.sredniaRekawicaLewaOdcinki1s[i][k],
                                     3.0D);
                }
            }

            suma = 0.0D;
            k = 0;
        }
    }


    public void kurtozaRekawice() {
        this.kurtozaRekawicaPrawa = new double[5];
        this.kurtozaRekawicaLewa = new double[5];

        double n = this.plikRekawice.daneRekawicaLewa[0].length;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < this.plikRekawice.daneRekawicaLewa[0].length; j++) {
                this.kurtozaRekawicaLewa[i] += Math.pow(
                        (this.plikRekawice.daneRekawicaLewa[i][j] - this.sredniaRekawicaLewa[i]) / this.odchylenieRekawicaLewa[i],
                        4.0D);
            }
            this.kurtozaRekawicaLewa[i] = (n * (n + 1.0D) / ((n - 1.0D) * (n - 2.0D) * (n - 3.0D)) * this.kurtozaRekawicaLewa[i] - 3.0D * (n - 1.0D) * (n - 1.0D) / ((n - 2.0D) * (n - 3.0D)));
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < this.plikRekawice.daneRekawicaPrawa[0].length; j++) {
                this.kurtozaRekawicaPrawa[i] += Math.pow(
                        (this.plikRekawice.daneRekawicaPrawa[i][j] - this.sredniaRekawicaPrawa[i]) / this.odchylenieRekawicaPrawa[i],
                        4.0D);
            }
            this.kurtozaRekawicaPrawa[i] = (n * (n + 1.0D) / ((n - 1.0D) * (n - 2.0D) * (n - 3.0D)) * this.kurtozaRekawicaPrawa[i] - 3.0D * (n - 1.0D) * (n - 1.0D) / ((n - 2.0D) * (n - 3.0D)));
        }


        int pom = this.plikRekawice.daneRekawicaLewa[0].length;
        this.kurtozaRekawicaLewaOdcinki1s = new double[5][pom / 4];
        double suma = 0.0D;
        n = 4.0D;
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; (j < pom) && (k < pom / 4); j++) {
                if (j % 4 == 3) {
                    suma += Math.pow(
                            (this.plikRekawice.daneRekawicaLewa[i][j] - this.sredniaRekawicaLewaOdcinki1s[i][k]) / this.odchylenieRekawicaLewaOdcinki1s[i][k],
                            4.0D);
                    this.kurtozaRekawicaLewaOdcinki1s[i][k] = (n * (n + 1.0D) / ((n - 1.0D) * (n - 2.0D) * (n - 3.0D)) * suma - 3.0D * (n - 1.0D) * (n - 1.0D) / ((n - 2.0D) * (n - 3.0D)));
                    k++;
                    suma = 0.0D;
                } else {
                    suma += Math.pow(
                            (this.plikRekawice.daneRekawicaLewa[i][j] - this.sredniaRekawicaLewaOdcinki1s[i][k]) / this.odchylenieRekawicaLewaOdcinki1s[i][k],
                            4.0D);
                }
            }

            suma = 0.0D;
            k = 0;
        }

        this.kurtozaRekawicaPrawaOdcinki1s = new double[5][pom / 4];
        suma = 0.0D;
        n = 4.0D;
        k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; (j < pom) && (k < pom / 4); j++) {
                if (j % 4 == 3) {
                    suma += Math.pow(
                            (this.plikRekawice.daneRekawicaPrawa[i][j] - this.sredniaRekawicaPrawaOdcinki1s[i][k]) / this.odchylenieRekawicaPrawaOdcinki1s[i][k],
                            4.0D);
                    this.kurtozaRekawicaPrawaOdcinki1s[i][k] = (n * (n + 1.0D) / ((n - 1.0D) * (n - 2.0D) * (n - 3.0D)) * suma - 3.0D * (n - 1.0D) * (n - 1.0D) / ((n - 2.0D) * (n - 3.0D)));
                    k++;
                    suma = 0.0D;
                } else {
                    suma += Math.pow(
                            (this.plikRekawice.daneRekawicaPrawa[i][j] - this.sredniaRekawicaPrawaOdcinki1s[i][k]) / this.odchylenieRekawicaPrawaOdcinki1s[i][k],
                            4.0D);
                }
            }

            suma = 0.0D;
            k = 0;
        }
    }


    public void sredniaArytmetycznaKinect() {
        this.sredniaKinect = new double[this.plikKinect.daneNormyRoznicaKinect[0].length];
        for (int k = 0; k < this.sredniaKinect.length; k++) {
            this.sredniaKinect[k] = 0.0D;
        }

        for (int i = 0; i < this.plikKinect.daneNormyRoznicaKinect.length; i++) {
            for (int j = 0; j < this.plikKinect.daneNormyRoznicaKinect[0].length; j++) {
                this.sredniaKinect[j] += this.plikKinect.daneNormyRoznicaKinect[i][j];
            }
        }

        for (int k = 0; k < this.sredniaKinect.length; k++) {
            this.sredniaKinect[k] /= this.plikKinect.daneNormyRoznicaKinect.length;
        }


        int t = 0;
        this.sredniaKinekctOdcinki1s = new double[(this.plikKinect.daneNormyRoznicaKinect.length - 29) / 30 + 1][this.plikKinect.daneNormyRoznicaKinect[0].length];
        for (int i = 0; i < this.sredniaKinekctOdcinki1s.length; i++) {
            for (int j = 0; j < this.sredniaKinekctOdcinki1s[0].length; j++) {
                this.sredniaKinekctOdcinki1s[i][j] = 0.0D;
            }
        }


        for (int i = 0; i < this.plikKinect.daneNormyRoznicaKinect.length; i++) {
            for (int j = 0; j < this.sredniaKinekctOdcinki1s[0].length; j++) {
                this.sredniaKinekctOdcinki1s[t][j] += this.plikKinect.daneNormyRoznicaKinect[i][j];
            }

            if ((i == 28) || ((i != 28) && (i % 30 == 28))) {
                t++;
            }
        }


        for (int k = 0; k < this.sredniaKinekctOdcinki1s.length; k++) {
            for (int j = 0; j < this.sredniaKinekctOdcinki1s[0].length; j++) {
                if (k == 0) {
                    this.sredniaKinekctOdcinki1s[k][j] /= 29.0D;
                } else {
                    this.sredniaKinekctOdcinki1s[k][j] /= 30.0D;
                }
            }
        }
    }


    public void wariancjaKinect() {
        this.wariancjaKinect = new double[this.plikKinect.daneNormyRoznicaKinect[0].length];
        for (int k = 0; k < this.wariancjaKinect.length; k++) {
            this.wariancjaKinect[k] = 0.0D;
        }

        for (int i = 0; i < this.plikKinect.daneNormyRoznicaKinect.length; i++) {
            for (int j = 0; j < this.plikKinect.daneNormyRoznicaKinect[0].length; j++) {
                this.wariancjaKinect[j] += Math.pow(
                        this.plikKinect.daneNormyRoznicaKinect[i][j] - this.sredniaKinect[j], 2.0D);
            }
        }
        for (int k = 0; k < this.wariancjaKinect.length; k++) {
            this.wariancjaKinect[k] /= (this.plikKinect.daneNormyRoznicaKinect.length - 1);
        }


        int t = 0;
        this.wariancjaKinekctOdcinki1s = new double[(this.plikKinect.daneNormyRoznicaKinect.length - 29) / 30 + 1][this.plikKinect.daneNormyRoznicaKinect[0].length];
        for (int i = 0; i < this.wariancjaKinekctOdcinki1s.length; i++) {
            for (int j = 0; j < this.wariancjaKinekctOdcinki1s[0].length; j++) {
                this.wariancjaKinekctOdcinki1s[i][j] = 0.0D;
            }
        }


        for (int i = 0; i < this.plikKinect.daneNormyRoznicaKinect.length; i++) {
            for (int j = 0; j < this.wariancjaKinekctOdcinki1s[0].length; j++) {
                this.wariancjaKinekctOdcinki1s[t][j] += Math.pow(
                        this.plikKinect.daneNormyRoznicaKinect[i][j] - this.sredniaKinekctOdcinki1s[t][j], 2.0D);
            }

            if ((i == 28) || ((i != 28) && (i % 30 == 28))) {
                t++;
            }
        }
        for (int i = 0; i < this.wariancjaKinekctOdcinki1s.length; i++) {
            for (int j = 0; j < this.wariancjaKinekctOdcinki1s[0].length; j++) {
                if (i == 0) {
                    this.wariancjaKinekctOdcinki1s[i][j] /= 29.0D;
                } else {
                    this.wariancjaKinekctOdcinki1s[i][j] /= 30.0D;
                }
            }
        }
    }


    public void odchylenieStandardoweKinect() {
        this.odchylenieKinect = new double[this.plikKinect.daneNormyRoznicaKinect[0].length];
        for (int i = 0; i < this.odchylenieKinect.length; i++) {
            this.odchylenieKinect[i] = Math.sqrt(this.wariancjaKinect[i]);
        }


        int t = 0;
        this.odchylenieKinekctOdcinki1s = new double[(this.plikKinect.daneNormyRoznicaKinect.length - 29) / 30 + 1][this.plikKinect.daneNormyRoznicaKinect[0].length];
        for (int i = 0; i < this.odchylenieKinekctOdcinki1s.length; i++) {
            for (int j = 0; j < this.odchylenieKinekctOdcinki1s[0].length; j++) {
                this.odchylenieKinekctOdcinki1s[i][j] = 0.0D;
            }
        }


        for (int i = 0; i < this.odchylenieKinekctOdcinki1s.length; i++) {
            for (int j = 0; j < this.odchylenieKinekctOdcinki1s[0].length; j++) {
                this.odchylenieKinekctOdcinki1s[i][j] = Math.sqrt(this.wariancjaKinekctOdcinki1s[i][j]);
            }
        }
    }


    public void wspolczynnikSkosnosciKinect() {
        double n = this.plikKinect.daneNormyRoznicaKinect.length;
        this.wspolczynikSkosnosciKinect = new double[this.plikKinect.daneNormyRoznicaKinect[0].length];
        for (int k = 0; k < this.wspolczynikSkosnosciKinect.length; k++) {
            this.wspolczynikSkosnosciKinect[k] = 0.0D;
        }

        for (int i = 0; i < this.plikKinect.daneNormyRoznicaKinect[0].length; i++) {
            for (int j = 0; j < this.plikKinect.daneNormyRoznicaKinect.length; j++) {
                this.wspolczynikSkosnosciKinect[i] += Math.pow(
                        this.plikKinect.daneNormyRoznicaKinect[j][i] - this.sredniaKinect[i], 3.0D);
            }
            this.wspolczynikSkosnosciKinect[i] *= n / ((n - 1.0D) * (n - 2.0D) * Math.pow(this.odchylenieKinect[i],
                                                                                          3.0D));
        }


        int t = 0;
        this.wspolczynnikSkosnosciKinekctOdcinki1s = new double[(this.plikKinect.daneNormyRoznicaKinect.length - 29) / 30 + 1][this.plikKinect.daneNormyRoznicaKinect[0].length];
        for (int i = 0; i < this.wspolczynnikSkosnosciKinekctOdcinki1s.length; i++) {
            for (int j = 0; j < this.wspolczynnikSkosnosciKinekctOdcinki1s[0].length; j++) {
                this.wspolczynnikSkosnosciKinekctOdcinki1s[i][j] = 0.0D;
            }
        }


        for (int i = 0; i < this.plikKinect.daneNormyRoznicaKinect.length; i++) {
            for (int j = 0; j < this.wspolczynnikSkosnosciKinekctOdcinki1s[0].length; j++) {
                this.wspolczynnikSkosnosciKinekctOdcinki1s[t][j] += Math.pow(
                        this.plikKinect.daneNormyRoznicaKinect[i][j] - this.sredniaKinekctOdcinki1s[t][j], 3.0D);
            }

            if ((i == 28) || ((i != 28) && (i % 30 == 28))) {
                t++;
            }
        }

        for (int i = 0; i < this.wspolczynnikSkosnosciKinekctOdcinki1s.length; i++) {
            for (int j = 0; j < this.wspolczynnikSkosnosciKinekctOdcinki1s[0].length; j++) {
                if (i == 0) {
                    this.wspolczynnikSkosnosciKinekctOdcinki1s[i][j] *= 29.0D / (756.0D * Math.pow(
                            this.odchylenieKinekctOdcinki1s[i][j], 3.0D));
                } else {
                    this.wspolczynnikSkosnosciKinekctOdcinki1s[i][j] *= 30.0D / (812.0D * Math.pow(
                            this.odchylenieKinekctOdcinki1s[i][j], 3.0D));
                }
            }
        }
    }


    public void kurtozaKinect() {
        double n = this.plikKinect.daneNormyRoznicaKinect.length;
        this.kurtozaKinect = new double[this.plikKinect.daneNormyRoznicaKinect[0].length];
        for (int k = 0; k < this.kurtozaKinect.length; k++) {
            this.kurtozaKinect[k] = 0.0D;
        }

        for (int i = 0; i < this.plikKinect.daneNormyRoznicaKinect[0].length; i++) {
            for (int j = 0; j < this.plikKinect.daneNormyRoznicaKinect.length; j++) {
                this.kurtozaKinect[i] += Math.pow(
                        (this.plikKinect.daneNormyRoznicaKinect[j][i] - this.sredniaKinect[i]) / this.odchylenieKinect[i],
                        4.0D);
            }
            this.kurtozaKinect[i] = (n * (n + 1.0D) / ((n - 1.0D) * (n - 2.0D) * (n - 3.0D)) * this.kurtozaKinect[i] - 3.0D * (n - 1.0D) * (n - 1.0D) / ((n - 2.0D) * (n - 3.0D)));
        }


        int t = 0;
        this.kurtozaKinectOdcinki1s = new double[(this.plikKinect.daneNormyRoznicaKinect.length - 29) / 30 + 1][this.plikKinect.daneNormyRoznicaKinect[0].length];
        for (int i = 0; i < this.kurtozaKinectOdcinki1s.length; i++) {
            for (int j = 0; j < this.kurtozaKinectOdcinki1s[0].length; j++) {
                this.kurtozaKinectOdcinki1s[i][j] = 0.0D;
            }
        }


        for (int i = 0; i < this.plikKinect.daneNormyRoznicaKinect.length; i++) {
            for (int j = 0; j < this.kurtozaKinectOdcinki1s[0].length; j++) {
                this.kurtozaKinectOdcinki1s[t][j] += Math.pow(
                        (this.plikKinect.daneNormyRoznicaKinect[i][j] - this.sredniaKinekctOdcinki1s[t][j]) / this.odchylenieKinekctOdcinki1s[t][j],
                        4.0D);
            }

            if ((i == 28) || ((i != 28) && (i % 30 == 28))) {
                t++;
            }
        }

        for (int i = 0; i < this.kurtozaKinectOdcinki1s.length; i++) {
            for (int j = 0; j < this.kurtozaKinectOdcinki1s[0].length; j++) {
                if (i == 0) {
                    n = 29.0D;
                    this.kurtozaKinectOdcinki1s[i][j] = (n * (n + 1.0D) / ((n - 1.0D) * (n - 2.0D) * (n - 3.0D)) * this.kurtozaKinectOdcinki1s[i][j] - 3.0D * (n - 1.0D) * (n - 1.0D) / ((n - 2.0D) * (n - 3.0D)));
                } else {
                    n = 30.0D;
                    this.kurtozaKinectOdcinki1s[i][j] = (n * (n + 1.0D) / ((n - 1.0D) * (n - 2.0D) * (n - 3.0D)) * this.kurtozaKinectOdcinki1s[i][j] - 3.0D * (n - 1.0D) * (n - 1.0D) / ((n - 2.0D) * (n - 3.0D)));
                }
            }
        }
    }


    public void korelacjaRekawice() throws RejectedExecutionException, InterruptedException, ExecutionException {
        int liczbaKolumn = this.plikRekawice.daneRekawicaPrawa[0].length;
        int liczbaWierszy = this.plikRekawice.daneRekawicaPrawa.length;
        double[][] transpozycjaPrawa = new double[this.plikRekawice.daneRekawicaPrawa[0].length][this.plikRekawice.daneRekawicaPrawa.length];

        for (int i = 0; i < liczbaKolumn; i++) {
            for (int j = 0; j < liczbaWierszy; j++) {
                transpozycjaPrawa[i][j] = this.plikRekawice.daneRekawicaPrawa[j][i];
            }
        }

        this.korelacjaPrawa = ((double[][]) this.eng.feval("corr", new Object[]{transpozycjaPrawa}));


        liczbaKolumn = this.plikRekawice.daneRekawicaLewa[0].length;
        liczbaWierszy = this.plikRekawice.daneRekawicaLewa.length;
        double[][] transpozycjaLewa = new double[this.plikRekawice.daneRekawicaLewa[0].length][this.plikRekawice.daneRekawicaLewa.length];

        for (int i = 0; i < liczbaKolumn; i++) {
            for (int j = 0; j < liczbaWierszy; j++) {
                transpozycjaLewa[i][j] = this.plikRekawice.daneRekawicaLewa[j][i];
            }
        }

        this.korelacjaLewa = ((double[][]) this.eng.feval("corr", new Object[]{transpozycjaLewa}));
    }

    public List<String> przygotowanieKorelacjeRekawica()
            throws RejectedExecutionException, InterruptedException, ExecutionException {
        korelacjaRekawice();
        List<String> lines = new ArrayList();
        String pom = "";
        lines.add("");
        lines.add("Tabela korelacji dla palców prawej ręki(odpowiednio kciuk, wskazujący, środkowy, serdeczny, mały)");
        lines.add("");
        for (int i = 0; i < this.korelacjaPrawa.length; i++) {
            for (int j = 0; j < this.korelacjaPrawa[0].length; j++) {
                pom = pom + String.valueOf(this.df.format(this.korelacjaPrawa[i][j])) + "\t";
            }
            lines.add(pom);
            pom = "";
        }

        lines.add("");
        lines.add("Tabela korelacji dla palców lewej ręki(odpowiednio kciuk, wskazujący, środkowy, serdeczny, mały)");
        lines.add("");
        pom = "";
        for (int i = 0; i < this.korelacjaLewa.length; i++) {
            for (int j = 0; j < this.korelacjaLewa[0].length; j++) {
                pom = pom + String.valueOf(this.df.format(this.korelacjaLewa[i][j])) + "\t";
            }
            lines.add(pom);
            pom = "";
        }

        return lines;
    }


    public void korelacjeKinect() throws RejectedExecutionException, InterruptedException, ExecutionException {
        this.korelacjaRozniceKinect = ((double[][]) this.eng.feval("corr",
                                                                   new Object[]{this.plikKinect.daneNormyRoznicaKinect}));
    }


    public List<String> przygotowanieKorelacjeKinect()
            throws RejectedExecutionException, InterruptedException, ExecutionException {
        korelacjeKinect();
        List<String> lines = new ArrayList();
        String pom = "";
        lines.add("");
        lines.add("");
        lines.add("Tabela korelacji dla kinecta ");
        lines.add(Arrays.deepToString(this.plikKinect.opisDanychKinectNormy));
        lines.add("");

        for (int i = 0; i < this.korelacjaRozniceKinect.length; i++) {
            for (int j = 0; j < this.korelacjaRozniceKinect[0].length; j++) {
                pom = pom + String.valueOf(this.df.format(this.korelacjaRozniceKinect[i][j])) + "\t";
            }
            lines.add(pom);
            pom = "";
        }
        return lines;
    }

    public List<String> korelacje() throws RejectedExecutionException, InterruptedException, ExecutionException {
        List<String> lines = new ArrayList();
        if (this.plikRekawice != null) {
            lines.addAll(przygotowanieKorelacjeRekawica());
        }


        if (this.plikKinect != null) {
            lines.addAll(przygotowanieKorelacjeKinect());
        }

        return lines;
    }

    public List<String> przygotowanieRaportRekawice()
            throws RejectedExecutionException, InterruptedException, ExecutionException {
        sredniaArytmetycznaRekawica();
        wariancjaRekawica();
        odchylenieStandardoweRekawica();
        wspolczynnikSkosnosciRekawica();
        kurtozaRekawice();

        List<String> lines = new ArrayList();
        lines.add(
                "----------Statystyki dla rękawic 5DT(odpowiednio kciuk, wskazujący, środkowy, serdeczny, mały---------------");
        lines.add("LEWA RĘKA");
        lines.add(String.format(Locale.US, "%30s\t%.6f\t%.6f\t%.6f\t%.6f\t%.6f",
                                new Object[]{"Średnia arytmetyczna dla całego pliku: ", Double.valueOf(
                                        this.sredniaRekawicaLewa[0]), Double.valueOf(
                                        this.sredniaRekawicaLewa[1]), Double.valueOf(
                                        this.sredniaRekawicaLewa[2]), Double.valueOf(
                                        this.sredniaRekawicaLewa[3]), Double.valueOf(this.sredniaRekawicaLewa[4])}));
        lines.add(String.format(Locale.US, "%30s\t%.6f\t%.6f\t%.6f\t%.6f\t%.6f",
                                new Object[]{"Wariancja dla całego pliku: ", Double.valueOf(
                                        this.wariancjaRekawicaLewa[0]), Double.valueOf(
                                        this.wariancjaRekawicaLewa[1]), Double.valueOf(
                                        this.wariancjaRekawicaLewa[2]), Double.valueOf(
                                        this.wariancjaRekawicaLewa[3]), Double.valueOf(
                                        this.wariancjaRekawicaLewa[4])}));
        lines.add(String.format(Locale.US, "%30s\t%.6f\t%.6f\t%.6f\t%.6f\t%.6f",
                                new Object[]{"Odchylenie standardowe dla całego pliku: ", Double.valueOf(
                                        this.odchylenieRekawicaLewa[0]), Double.valueOf(
                                        this.odchylenieRekawicaLewa[1]), Double.valueOf(
                                        this.odchylenieRekawicaLewa[2]), Double.valueOf(
                                        this.odchylenieRekawicaLewa[3]), Double.valueOf(
                                        this.odchylenieRekawicaLewa[4])}));
        lines.add(String.format(Locale.US, "%30s\t%.6f\t%.6f\t%.6f\t%.6f\t%.6f",
                                new Object[]{"Współczynnik skośności dla całego pliku: ", Double.valueOf(
                                        this.wspolczynnikSkosnosciRekawicaLewa[0]), Double.valueOf(
                                        this.wspolczynnikSkosnosciRekawicaLewa[1]), Double.valueOf(
                                        this.wspolczynnikSkosnosciRekawicaLewa[2]), Double.valueOf(
                                        this.wspolczynnikSkosnosciRekawicaLewa[3]), Double.valueOf(
                                        this.wspolczynnikSkosnosciRekawicaLewa[4])}));

        lines.add(String.format(Locale.US, "%30s\t%.6f\t%.6f\t%.6f\t%.6f\t%.6f",
                                new Object[]{"kurtoza dla całego pliku: ", Double.valueOf(
                                        this.kurtozaRekawicaLewa[0]), Double.valueOf(
                                        this.kurtozaRekawicaLewa[1]), Double.valueOf(
                                        this.kurtozaRekawicaLewa[2]), Double.valueOf(
                                        this.kurtozaRekawicaLewa[3]), Double.valueOf(this.kurtozaRekawicaLewa[4])}));

        lines.add("");
        lines.add("PRAWA RĘKA");
        lines.add(String.format(Locale.US, "%30s\t%.6f\t%.6f\t%.6f\t%.6f\t%.6f",
                                new Object[]{"Średnia arytmetyczna dla całego pliku:", Double.valueOf(
                                        this.sredniaRekawicaPrawa[0]), Double.valueOf(
                                        this.sredniaRekawicaPrawa[1]), Double.valueOf(
                                        this.sredniaRekawicaPrawa[2]), Double.valueOf(
                                        this.sredniaRekawicaPrawa[3]), Double.valueOf(this.sredniaRekawicaPrawa[4])}));
        lines.add(String.format(Locale.US, "%30s\t%.6f\t%.6f\t%.6f\t%.6f\t%.6f",
                                new Object[]{"Wariancja dla całego pliku: ", Double.valueOf(
                                        this.wariancjaRekawicaPrawa[0]), Double.valueOf(
                                        this.wariancjaRekawicaPrawa[1]), Double.valueOf(
                                        this.wariancjaRekawicaPrawa[2]), Double.valueOf(
                                        this.wariancjaRekawicaPrawa[3]), Double.valueOf(
                                        this.wariancjaRekawicaPrawa[4])}));
        lines.add(String.format(Locale.US, "%30s\t%.6f\t%.6f\t%.6f\t%.6f\t%.6f",
                                new Object[]{"Odchylenie standardowe dla całego pliku: ", Double.valueOf(
                                        this.odchylenieRekawicaPrawa[0]), Double.valueOf(
                                        this.odchylenieRekawicaPrawa[1]), Double.valueOf(
                                        this.odchylenieRekawicaPrawa[2]), Double.valueOf(
                                        this.odchylenieRekawicaPrawa[3]), Double.valueOf(
                                        this.odchylenieRekawicaPrawa[4])}));
        lines.add(String.format(Locale.US, "%30s\t%.6f\t%.6f\t%.6f\t%.6f\t%.6f",
                                new Object[]{"Współczynnik skośności dla całego pliku: ", Double.valueOf(
                                        this.wspolczynnikSkosnosciRekawicaPrawa[0]), Double.valueOf(
                                        this.wspolczynnikSkosnosciRekawicaPrawa[1]), Double.valueOf(
                                        this.wspolczynnikSkosnosciRekawicaPrawa[2]), Double.valueOf(
                                        this.wspolczynnikSkosnosciRekawicaPrawa[3]), Double.valueOf(
                                        this.wspolczynnikSkosnosciRekawicaPrawa[4])}));
        lines.add(String.format(Locale.US, "%30s\t%.6f\t%.6f\t%.6f\t%.6f\t%.6f",
                                new Object[]{"kurtoza dla całego pliku: ", Double.valueOf(
                                        this.kurtozaRekawicaPrawa[0]), Double.valueOf(
                                        this.kurtozaRekawicaPrawa[1]), Double.valueOf(
                                        this.kurtozaRekawicaPrawa[2]), Double.valueOf(
                                        this.kurtozaRekawicaPrawa[3]), Double.valueOf(this.kurtozaRekawicaPrawa[4])}));
        return lines;
    }

    public List<String> przygotowanieRaportKinect() {
        List<String> lines = new ArrayList();
        sredniaArytmetycznaKinect();
        wariancjaKinect();
        odchylenieStandardoweKinect();
        wspolczynnikSkosnosciKinect();
        kurtozaKinect();

        lines.add("--------------------Statystyki dla kinekctu - normy--------------------");
        lines.add(Arrays.deepToString(this.plikKinect.opisDanychKinectNormy));
        lines.add("Średnia arytmetyczna: ");
        lines.add(Arrays.toString(this.sredniaKinect));
        lines.add("Wariancja: ");
        lines.add(Arrays.toString(this.wariancjaKinect));
        lines.add("Odchylenie standardowe: ");
        lines.add(Arrays.toString(this.odchylenieKinect));
        lines.add("Współczynnik skośności: ");
        lines.add(Arrays.toString(this.wspolczynikSkosnosciKinect));
        lines.add("Kurtoza: ");
        lines.add(Arrays.toString(this.kurtozaKinect));
        lines.add("");

        return lines;
    }

    public List<String> tworzenieWspolnegoRaportu()
            throws RejectedExecutionException, InterruptedException, ExecutionException {
        List<String> lines = new ArrayList();
        if (this.plikPulsometr != null) {
            lines.addAll(przygotowanieRaportPulsometr());
        }

        if (this.plikRekawice != null) {
            lines.addAll(przygotowanieRaportRekawice());
        }


        if (this.plikKinect != null) {
            lines.addAll(przygotowanieRaportKinect());
        }
        return lines;
    }


    public void generujWykresy()
            throws EngineException, InterruptedException, MatlabSyntaxException, ExecutionException {
        if (this.plikPulsometr != null) {
            double[] sekundy = new double[this.plikPulsometr.danePulsometr.length];
            for (int i = 1; i <= sekundy.length; i++) {
                sekundy[(i - 1)] = i;
            }


            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot", new Object[]{sekundy, this.plikPulsometr.danePulsometr});

            this.eng.eval("xlabel('Czas [s]');", null, null);

            this.eng.eval("ylabel('Tętno');", null, null);

            this.eng.eval("grid", null, null);

            this.eng.eval("title('Dynamika pulsu');", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\pulsometr_dane.png');", null, null);


            this.eng.eval("close(gcf);", null, null);
        }


        if (this.plikRekawice != null) {

            double[] probki = new double[this.plikRekawice.daneRekawicaLewa[0].length];
            double[] sekundy = new double[this.sredniaRekawicaLewaOdcinki1s[0].length];
            double[] s = new double[probki.length];
            for (int i = 0; i < s.length; i++) {
                s[i] = (i / 4.0D);
            }
            for (int i = 1; i <= probki.length; i++) {
                probki[(i - 1)] = i;
            }
            for (int i = 1; i <= sekundy.length; i++) {
                sekundy[(i - 1)] = i;
            }

            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{s, this.plikRekawice.daneRekawicaPrawa[0], s, this.plikRekawice.daneRekawicaPrawa[1], s, this.plikRekawice.daneRekawicaPrawa[2], s, this.plikRekawice.daneRekawicaPrawa[3], s, this.plikRekawice.daneRekawicaPrawa[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("ylabel('Wartości pobrane z rękawicy');", null, null);
            this.eng.eval("grid", null, null);

            this.eng.eval("legend('Kciuk','Wskazujący', 'Średni', 'Serdeczny', 'Mały');", null, null);
            this.eng.eval("title('Wartości pobrane z poszczególnych palców prawej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_prawa_dane.png');", null, null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{s, this.plikRekawice.daneRekawicaLewa[0], s, this.plikRekawice.daneRekawicaLewa[1], s, this.plikRekawice.daneRekawicaLewa[2], s, this.plikRekawice.daneRekawicaLewa[3], s, this.plikRekawice.daneRekawicaLewa[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("ylabel('Wartości pobrane z rękawicy');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("legend('Kciuk','Wskazujący', 'Średni', 'Serdeczny', 'Mały');", null, null);
            this.eng.eval("title('Wartości pobrane z poszczególnych palców lewej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_lewa_dane.png');", null, null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, this.sredniaRekawicaPrawaOdcinki1s[0], sekundy, this.sredniaRekawicaPrawaOdcinki1s[1], sekundy, this.sredniaRekawicaPrawaOdcinki1s[2], sekundy, this.sredniaRekawicaPrawaOdcinki1s[3], sekundy, this.sredniaRekawicaPrawaOdcinki1s[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("ylabel('Średnia arytmetyczna');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("title('Średnia arytmetyczna dla poszczególnych palców prawej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_prawa_wszystkie_srednia.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, this.sredniaRekawicaLewaOdcinki1s[0], sekundy, this.sredniaRekawicaLewaOdcinki1s[1], sekundy, this.sredniaRekawicaLewaOdcinki1s[2], sekundy, this.sredniaRekawicaLewaOdcinki1s[3], sekundy, this.sredniaRekawicaLewaOdcinki1s[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("ylabel('Średnia arytmetyczna');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("title('Średnia arytmetyczna dla poszczególnych palców lewej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_lewa_wszystkie_srednia.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, this.wariancjaRekawicaPrawaOdcinki1s[0], sekundy, this.wariancjaRekawicaPrawaOdcinki1s[1], sekundy, this.wariancjaRekawicaPrawaOdcinki1s[2], sekundy, this.wariancjaRekawicaPrawaOdcinki1s[3], sekundy, this.wariancjaRekawicaPrawaOdcinki1s[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("title('Wariancja dla poszczególnych palców prawej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_prawa_wszystkie_wariancja.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,2,1);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wariancjaRekawicaPrawaOdcinki1s[0]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("title('P. kciuk')", null, null);

            this.eng.eval("subplot(2,2,2);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wariancjaRekawicaPrawaOdcinki1s[1]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("title('P. wskazujący')", null, null);

            this.eng.eval("subplot(2,2,3);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wariancjaRekawicaPrawaOdcinki1s[2]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("title('P. średni')", null, null);

            this.eng.eval("subplot(2,2,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, this.wariancjaRekawicaPrawaOdcinki1s[3], sekundy, this.wariancjaRekawicaPrawaOdcinki1s[4]});

            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("title('P. serdeczny,mały')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_prawa_wszystkie_wariancja1.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, this.wariancjaRekawicaLewaOdcinki1s[0], sekundy, this.wariancjaRekawicaLewaOdcinki1s[1], sekundy, this.wariancjaRekawicaLewaOdcinki1s[2], sekundy, this.wariancjaRekawicaLewaOdcinki1s[3], sekundy, this.wariancjaRekawicaLewaOdcinki1s[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("title('Wariancja dla poszczególnych palców lewej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_lewa_wszystkie_wariancja.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,2,1);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wariancjaRekawicaLewaOdcinki1s[0]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("title('L. kciuk')", null, null);

            this.eng.eval("subplot(2,2,2);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wariancjaRekawicaLewaOdcinki1s[1]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("title('L. wskazujący')", null, null);

            this.eng.eval("subplot(2,2,3);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wariancjaRekawicaLewaOdcinki1s[2]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("title('L. średni')", null, null);

            this.eng.eval("subplot(2,2,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, this.wariancjaRekawicaLewaOdcinki1s[3], sekundy, this.wariancjaRekawicaLewaOdcinki1s[4]});

            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("title('L. serdeczny, mały')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_lewa_wszystkie_wariancja1.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, this.odchylenieRekawicaPrawaOdcinki1s[0], sekundy, this.odchylenieRekawicaPrawaOdcinki1s[1], sekundy, this.odchylenieRekawicaPrawaOdcinki1s[2], sekundy, this.odchylenieRekawicaPrawaOdcinki1s[3], sekundy, this.odchylenieRekawicaPrawaOdcinki1s[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("ylabel('Odchylenie');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("title('Odchylenie standardowe dla poszczególnych palców prawej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_prawa_wszystkie_odchylenie.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, this.odchylenieRekawicaLewaOdcinki1s[0], sekundy, this.odchylenieRekawicaLewaOdcinki1s[1], sekundy, this.odchylenieRekawicaLewaOdcinki1s[2], sekundy, this.odchylenieRekawicaLewaOdcinki1s[3], sekundy, this.odchylenieRekawicaLewaOdcinki1s[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("ylabel('Odchylenie');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("title('Odchylenie standardowe dla poszczególnych palców lewej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_lewa_wszystkie_odchylenie.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[0], sekundy, this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[1], sekundy, this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[2], sekundy, this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[3], sekundy, this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("ylabel('Współczynnik skosnośći');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("title('Współczynnik skosnośći dla poszczególnych palców prawej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_prawa_wszystkie_skosnosc.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,2,1);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[0]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Współczynnik skośności');", null, null);
            this.eng.eval("title('P. kciuk')", null, null);

            this.eng.eval("subplot(2,2,2);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[1]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Współczynnik skośności');", null, null);
            this.eng.eval("title('P. wskazujący')", null, null);

            this.eng.eval("subplot(2,2,3);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[2]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Współczynnik skośności');", null, null);
            this.eng.eval("title('P. średni')", null, null);

            this.eng.eval("subplot(2,2,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[3], sekundy, this.wspolczynnikSkosnosciRekawicaPrawaOdcinki1s[4]});

            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Współczynnik skośności');", null, null);
            this.eng.eval("title('P. serdeczny,mały')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_prawa_wszystkie_skosnosc1.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[0], sekundy, this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[1], sekundy, this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[2], sekundy, this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[3], sekundy, this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Współczynnik skosnośći');", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("title('Współczynnik skosnośći dla poszczególnych palców lewej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_lewa_wszystkie_skosnosc.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,2,1);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[0]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Współczynnik skośności');", null, null);
            this.eng.eval("title('L. kciuk')", null, null);

            this.eng.eval("subplot(2,2,2);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[1]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Współczynnik skośności');", null, null);
            this.eng.eval("title('L. wskazujący')", null, null);

            this.eng.eval("subplot(2,2,3);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[2]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Współczynnik skośności');", null, null);
            this.eng.eval("title('L. średni')", null, null);

            this.eng.eval("subplot(2,2,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[3], sekundy, this.wspolczynnikSkosnosciRekawicaLewaOdcinki1s[4]});

            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Współczynnik skośności');", null, null);
            this.eng.eval("title('L. serdeczny,mały')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_lewa_wszystkie_skosnosc1.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, this.kurtozaRekawicaPrawaOdcinki1s[0], sekundy, this.kurtozaRekawicaPrawaOdcinki1s[1], sekundy, this.kurtozaRekawicaPrawaOdcinki1s[2], sekundy, this.kurtozaRekawicaPrawaOdcinki1s[3], sekundy, this.kurtozaRekawicaPrawaOdcinki1s[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("title('Kurtoza dla poszczególnych palców prawej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_prawa_wszystkie_kurtoza.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,2,1);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.kurtozaRekawicaPrawaOdcinki1s[0]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("title('P. kciuk')", null, null);

            this.eng.eval("subplot(2,2,2);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.kurtozaRekawicaPrawaOdcinki1s[1]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("title('P. wskazujący')", null, null);

            this.eng.eval("subplot(2,2,3);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.kurtozaRekawicaPrawaOdcinki1s[2]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("title('P. średni')", null, null);

            this.eng.eval("subplot(2,2,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, this.kurtozaRekawicaPrawaOdcinki1s[3], sekundy, this.kurtozaRekawicaPrawaOdcinki1s[4]});

            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("title('P. serdeczny,mały')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_prawa_wszystkie_kurtoza1.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, this.kurtozaRekawicaLewaOdcinki1s[0], sekundy, this.kurtozaRekawicaLewaOdcinki1s[1], sekundy, this.kurtozaRekawicaLewaOdcinki1s[2], sekundy, this.kurtozaRekawicaLewaOdcinki1s[3], sekundy, this.kurtozaRekawicaLewaOdcinki1s[4]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("title('Kurtoza dla poszczególnych palców lewej ręki');", null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_lewa_wszystkie_kurtoza.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,2,1);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.kurtozaRekawicaLewaOdcinki1s[0]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("title('L. kciuk')", null, null);

            this.eng.eval("subplot(2,2,2);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.kurtozaRekawicaLewaOdcinki1s[1]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("title('L. wskazujący')", null, null);

            this.eng.eval("subplot(2,2,3);", null, null);
            this.eng.feval("plot", new Object[]{sekundy, this.kurtozaRekawicaLewaOdcinki1s[2]});
            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("title('L. średni')", null, null);

            this.eng.eval("subplot(2,2,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, this.kurtozaRekawicaLewaOdcinki1s[3], sekundy, this.kurtozaRekawicaLewaOdcinki1s[4]});

            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("title('L. serdeczny,mały')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_lewa_wszystkie_kurtoza1.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("scatter",
                           new Object[]{this.sredniaRekawicaPrawaOdcinki1s[0], this.wariancjaRekawicaPrawaOdcinki1s[0], "*"});

            this.eng.eval("hold on;");
            this.eng.feval("scatter",
                           new Object[]{this.sredniaRekawicaPrawaOdcinki1s[1], this.wariancjaRekawicaPrawaOdcinki1s[1], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter",
                           new Object[]{this.sredniaRekawicaPrawaOdcinki1s[2], this.wariancjaRekawicaPrawaOdcinki1s[2], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter",
                           new Object[]{this.sredniaRekawicaPrawaOdcinki1s[3], this.wariancjaRekawicaPrawaOdcinki1s[3], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter",
                           new Object[]{this.sredniaRekawicaPrawaOdcinki1s[4], this.wariancjaRekawicaPrawaOdcinki1s[4], "*"});
            this.eng.eval("xlabel('Średnia arytmetyczna');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("title('Wykres rozrzutu dla prawej ręki')", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_prawa_rozrut_srednia_wariancja.png');",
                          null, null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("scatter",
                           new Object[]{this.sredniaRekawicaPrawaOdcinki1s[0], this.wariancjaRekawicaLewaOdcinki1s[0], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter",
                           new Object[]{this.sredniaRekawicaPrawaOdcinki1s[1], this.wariancjaRekawicaLewaOdcinki1s[1], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter",
                           new Object[]{this.sredniaRekawicaPrawaOdcinki1s[2], this.wariancjaRekawicaLewaOdcinki1s[2], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter",
                           new Object[]{this.sredniaRekawicaPrawaOdcinki1s[3], this.wariancjaRekawicaLewaOdcinki1s[3], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter",
                           new Object[]{this.sredniaRekawicaPrawaOdcinki1s[4], this.wariancjaRekawicaLewaOdcinki1s[4], "*"});
            this.eng.eval("xlabel('Średnia arytmetyczna');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("title('Wykres rozrzutu dla lewej ręki')", null, null);
            this.eng.eval(
                    "legend({'Kciuk','Wskaz.', 'Średni', 'Serd.', 'Mały'},'Location','north','Orientation','Horizontal')",
                    null, null);
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\rekawice_lewa_rozrut_srednia_wariancja.png');",
                          null, null);

            this.eng.eval("close(gcf);", null, null);
        }


        if (this.plikKinect != null) {
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);

            double[] probki = new double[this.plikKinect.daneNormyRoznicaKinect.length];
            double[] sekundy = new double[this.sredniaKinekctOdcinki1s.length];
            double[] s = new double[probki.length];
            double[][] daneNormyRoznicaKinect = new double[this.plikKinect.daneNormyRoznicaKinect[0].length][this.plikKinect.daneNormyRoznicaKinect.length];
            double[][] srednia = new double[this.sredniaKinekctOdcinki1s[0].length][this.sredniaKinekctOdcinki1s.length];
            double[][] wariancja = new double[this.sredniaKinekctOdcinki1s[0].length][this.sredniaKinekctOdcinki1s.length];
            double[][] odchylenie = new double[this.sredniaKinekctOdcinki1s[0].length][this.sredniaKinekctOdcinki1s.length];
            double[][] skosnosc = new double[this.sredniaKinekctOdcinki1s[0].length][this.sredniaKinekctOdcinki1s.length];
            double[][] kurtoza = new double[this.sredniaKinekctOdcinki1s[0].length][this.sredniaKinekctOdcinki1s.length];

            for (int i = 0; i < this.plikKinect.daneNormyRoznicaKinect[0].length; i++) {
                for (int j = 0; j < this.plikKinect.daneNormyRoznicaKinect.length; j++) {
                    daneNormyRoznicaKinect[i][j] = this.plikKinect.daneNormyRoznicaKinect[j][i];
                }
            }

            for (int i = 0; i < this.sredniaKinekctOdcinki1s[0].length; i++) {
                for (int j = 0; j < this.sredniaKinekctOdcinki1s.length; j++) {
                    srednia[i][j] = this.sredniaKinekctOdcinki1s[j][i];
                    wariancja[i][j] = this.wariancjaKinekctOdcinki1s[j][i];
                    odchylenie[i][j] = this.odchylenieKinekctOdcinki1s[j][i];
                    skosnosc[i][j] = this.wspolczynnikSkosnosciKinekctOdcinki1s[j][i];
                    kurtoza[i][j] = this.kurtozaKinectOdcinki1s[j][i];
                }
            }

            for (int i = 1; i <= probki.length; i++) {
                probki[(i - 1)] = i;
            }
            for (int i = 1; i <= sekundy.length; i++) {
                sekundy[(i - 1)] = i;
            }
            for (int i = 0; i < probki.length; i++) {
                s[i] = (i / 30.0D);
            }

            this.eng.feval("plot",
                           new Object[]{s, daneNormyRoznicaKinect[0], s, daneNormyRoznicaKinect[1], s, daneNormyRoznicaKinect[2], s, daneNormyRoznicaKinect[3], s, daneNormyRoznicaKinect[4], s, daneNormyRoznicaKinect[5], s, daneNormyRoznicaKinect[6], s, daneNormyRoznicaKinect[7], s, daneNormyRoznicaKinect[8], s, daneNormyRoznicaKinect[9], s, daneNormyRoznicaKinect[10], s, daneNormyRoznicaKinect[11], s, daneNormyRoznicaKinect[12], s, daneNormyRoznicaKinect[13], s, daneNormyRoznicaKinect[14], s, daneNormyRoznicaKinect[15], s, daneNormyRoznicaKinect[16], s, daneNormyRoznicaKinect[17], s, daneNormyRoznicaKinect[18], s, daneNormyRoznicaKinect[19], s, daneNormyRoznicaKinect[20], s, daneNormyRoznicaKinect[21], s, daneNormyRoznicaKinect[22], s, daneNormyRoznicaKinect[23], s, daneNormyRoznicaKinect[24]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wartość róznicy norm');", null, null);
            this.eng.eval("title('Różnice norm dla poszczególnych węzłów Kinecta');", null, null);
            this.eng.eval(
                    "legend ('SpineBase', 'SpineMid', 'Neck', 'Head', 'ShoulderL', 'ElbowL', 'WristL', 'HandL', 'ShoulderR', 'ElbowR', 'WristR', 'HandR', 'HipL', 'KneeL', 'AnkleL', 'FootL', 'HipR', 'KneeR', 'AnkleR', 'FootR', 'SpineShoulder', 'HandTipL', 'ThumbL', 'HandTipR', 'ThumbR');");
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\kinect_roznice_norm.png');", null, null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,3,1);", null, null);
            this.eng.feval("plot",
                           new Object[]{s, daneNormyRoznicaKinect[0], s, daneNormyRoznicaKinect[1], s, daneNormyRoznicaKinect[2], s, daneNormyRoznicaKinect[3]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("ylabel('Wartość róznicy norm');", null, null);

            this.eng.eval("title('SpineBase,SpineMid,Neck,Head')", null, null);

            this.eng.eval("subplot(2,3,2);", null, null);
            this.eng.feval("plot",
                           new Object[]{s, daneNormyRoznicaKinect[4], s, daneNormyRoznicaKinect[5], s, daneNormyRoznicaKinect[6], s, daneNormyRoznicaKinect[7]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wartość róznicy norm');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderL,ElbowL,WristL,HandL')", null, null);

            this.eng.eval("subplot(2,3,3);", null, null);
            this.eng.feval("plot",
                           new Object[]{s, daneNormyRoznicaKinect[8], s, daneNormyRoznicaKinect[9], s, daneNormyRoznicaKinect[10], s, daneNormyRoznicaKinect[11]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wartość róznicy norm');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderR,ElbowR,WristR,HandR')", null, null);

            this.eng.eval("subplot(2,3,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{s, daneNormyRoznicaKinect[12], s, daneNormyRoznicaKinect[13], s, daneNormyRoznicaKinect[14], s, daneNormyRoznicaKinect[15]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wartość róznicy norm');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipL,KneeL,AnkleL,FootL')", null, null);

            this.eng.eval("subplot(2,3,5);", null, null);
            this.eng.feval("plot",
                           new Object[]{s, daneNormyRoznicaKinect[16], s, daneNormyRoznicaKinect[17], s, daneNormyRoznicaKinect[18], s, daneNormyRoznicaKinect[19]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wartość róznicy norm');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipR,KneeR,AnkleR,FootR')", null, null);

            this.eng.eval("subplot(2,3,6);", null, null);
            this.eng.feval("plot",
                           new Object[]{s, daneNormyRoznicaKinect[20], s, daneNormyRoznicaKinect[21], s, daneNormyRoznicaKinect[22], s, daneNormyRoznicaKinect[23], s, daneNormyRoznicaKinect[24]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wartość róznicy norm');", null, null);
            this.eng.eval("legend('1','2','3','4','5')", null, null);
            this.eng.eval("title('SpineShoulder,HandTipL,ThumbL,HandTipR,ThumbR')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\kinect_roznice_norm1.png');", null, null);
            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, srednia[0], sekundy, srednia[1], sekundy, srednia[2], sekundy, srednia[3], sekundy, srednia[4], sekundy, srednia[5], sekundy, srednia[6], sekundy, srednia[7], sekundy, srednia[8], sekundy, srednia[9], sekundy, srednia[10], sekundy, srednia[11], sekundy, srednia[12], sekundy, srednia[13], sekundy, srednia[14], sekundy, srednia[15], sekundy, srednia[16], sekundy, srednia[17], sekundy, srednia[18], sekundy, srednia[19], sekundy, srednia[20], sekundy, srednia[21], sekundy, srednia[22], sekundy, srednia[23], sekundy, srednia[24]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Średnia');", null, null);
            this.eng.eval("title('Średnia arytmetyczna dla poszczególnych węzłów Kinecta');", null, null);
            this.eng.eval(
                    "legend ('SpineBase', 'SpineMid', 'Neck', 'Head', 'ShoulderL', 'ElbowL', 'WristL', 'HandL', 'ShoulderR', 'ElbowR', 'WristR', 'HandR', 'HipL', 'KneeL', 'AnkleL', 'FootL', 'HipR', 'KneeR', 'AnkleR', 'FootR', 'SpineShoulder', 'HandTipL', 'ThumbL', 'HandTipR', 'ThumbR');");
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\kinect_roznice_norm_srednia.png');", null, null);

            this.eng.eval("close(gcf);", null, null);

            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,3,1);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, srednia[0], sekundy, srednia[1], sekundy, srednia[2], sekundy, srednia[3]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("ylabel('Średnia');", null, null);

            this.eng.eval("title('SpineBase,SpineMid,Neck,Head')", null, null);

            this.eng.eval("subplot(2,3,2);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, srednia[4], sekundy, srednia[5], sekundy, srednia[6], sekundy, srednia[7]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Średnia');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderL,ElbowL,WristL,HandL')", null, null);

            this.eng.eval("subplot(2,3,3);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, srednia[8], sekundy, srednia[9], sekundy, srednia[10], sekundy, srednia[11]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Średnia');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderR,ElbowR,WristR,HandR')", null, null);

            this.eng.eval("subplot(2,3,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, srednia[12], sekundy, srednia[13], sekundy, srednia[14], sekundy, srednia[15]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Średnia');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipL,KneeL,AnkleL,FootL')", null, null);

            this.eng.eval("subplot(2,3,5);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, srednia[16], sekundy, srednia[17], sekundy, srednia[18], sekundy, srednia[19]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Średnia');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipR,KneeR,AnkleR,FootR')", null, null);

            this.eng.eval("subplot(2,3,6);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, srednia[20], sekundy, srednia[21], sekundy, srednia[22], sekundy, srednia[23], sekundy, srednia[24]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Średnia');", null, null);
            this.eng.eval("legend('1','2','3','4','5')", null, null);
            this.eng.eval("title('SpineShoulder,HandTipL,ThumbL,HandTipR,ThumbR')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\kinect_roznice_norm_srednia1.png');", null, null);
            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.feval("plot",
                           new Object[]{sekundy, wariancja[0], sekundy, wariancja[1], sekundy, wariancja[2], sekundy, wariancja[3], sekundy, wariancja[4], sekundy, wariancja[5], sekundy, wariancja[6], sekundy, wariancja[7], sekundy, wariancja[8], sekundy, wariancja[9], sekundy, wariancja[10], sekundy, wariancja[11], sekundy, wariancja[12], sekundy, wariancja[13], sekundy, wariancja[14], sekundy, wariancja[15], sekundy, wariancja[16], sekundy, wariancja[17], sekundy, wariancja[18], sekundy, wariancja[19], sekundy, wariancja[20], sekundy, wariancja[21], sekundy, wariancja[22], sekundy, wariancja[23], sekundy, wariancja[24]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wartość wariancji');", null, null);
            this.eng.eval("title('Wariancja dla poszczególnych węzłów Kinecta');", null, null);
            this.eng.eval(
                    "legend ('SpineBase', 'SpineMid', 'Neck', 'Head', 'ShoulderL', 'ElbowL', 'WristL', 'HandL', 'ShoulderR', 'ElbowR', 'WristR', 'HandR', 'HipL', 'KneeL', 'AnkleL', 'FootL', 'HipR', 'KneeR', 'AnkleR', 'FootR', 'SpineShoulder', 'HandTipL', 'ThumbL', 'HandTipR', 'ThumbR');");
            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\kinect_roznice_norm_wariancja.png');", null,
                          null);

            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,3,1);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, wariancja[0], sekundy, wariancja[1], sekundy, wariancja[2], sekundy, wariancja[3]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('SpineBase,SpineMid,Neck,Head')", null, null);

            this.eng.eval("subplot(2,3,2);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, wariancja[4], sekundy, wariancja[5], sekundy, wariancja[6], sekundy, wariancja[7]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderL,ElbowL,WristL,HandL')", null, null);

            this.eng.eval("subplot(2,3,3);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, wariancja[8], sekundy, wariancja[9], sekundy, wariancja[10], sekundy, wariancja[11]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderR,ElbowR,WristR,HandR')", null, null);

            this.eng.eval("subplot(2,3,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, wariancja[12], sekundy, wariancja[13], sekundy, wariancja[14], sekundy, wariancja[15]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipL,KneeL,AnkleL,FootL')", null, null);

            this.eng.eval("subplot(2,3,5);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, wariancja[16], sekundy, wariancja[17], sekundy, wariancja[18], sekundy, wariancja[19]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipR,KneeR,AnkleR,FootR')", null, null);

            this.eng.eval("subplot(2,3,6);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, wariancja[20], sekundy, wariancja[21], sekundy, wariancja[22], sekundy, wariancja[23], sekundy, wariancja[24]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4','5')", null, null);
            this.eng.eval("title('SpineShoulder,HandTipL,ThumbL,HandTipR,ThumbR')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\kinect_roznice_norm_wariancja1.png');", null,
                          null);
            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,3,1);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, odchylenie[0], sekundy, odchylenie[1], sekundy, odchylenie[2], sekundy, odchylenie[3]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Odchylenie');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('SpineBase,SpineMid,Neck,Head')", null, null);

            this.eng.eval("subplot(2,3,2);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, odchylenie[4], sekundy, odchylenie[5], sekundy, odchylenie[6], sekundy, odchylenie[7]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Odchylenie');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderL,ElbowL,WristL,HandL')", null, null);

            this.eng.eval("subplot(2,3,3);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, odchylenie[8], sekundy, odchylenie[9], sekundy, odchylenie[10], sekundy, odchylenie[11]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Odchylenie');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderR,ElbowR,WristR,HandR')", null, null);

            this.eng.eval("subplot(2,3,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, odchylenie[12], sekundy, odchylenie[13], sekundy, odchylenie[14], sekundy, odchylenie[15]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Odchylenie');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipL,KneeL,AnkleL,FootL')", null, null);

            this.eng.eval("subplot(2,3,5);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, odchylenie[16], sekundy, odchylenie[17], sekundy, odchylenie[18], sekundy, odchylenie[19]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Odchylenie');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipR,KneeR,AnkleR,FootR')", null, null);

            this.eng.eval("subplot(2,3,6);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, odchylenie[20], sekundy, odchylenie[21], sekundy, odchylenie[22], sekundy, odchylenie[23], sekundy, odchylenie[24]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Odchylenie');", null, null);
            this.eng.eval("legend('1','2','3','4','5')", null, null);
            this.eng.eval("title('SpineShoulder,HandTipL,ThumbL,HandTipR,ThumbR')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\kinect_roznice_norm_odchylenie.png');", null,
                          null);
            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,3,1);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, skosnosc[0], sekundy, skosnosc[1], sekundy, skosnosc[2], sekundy, skosnosc[3]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Skośność');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('SpineBase,SpineMid,Neck,Head')", null, null);

            this.eng.eval("subplot(2,3,2);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, skosnosc[4], sekundy, skosnosc[5], sekundy, skosnosc[6], sekundy, skosnosc[7]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Skośność');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderL,ElbowL,WristL,HandL')", null, null);

            this.eng.eval("subplot(2,3,3);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, skosnosc[8], sekundy, skosnosc[9], sekundy, skosnosc[10], sekundy, skosnosc[11]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Skośność');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderR,ElbowR,WristR,HandR')", null, null);

            this.eng.eval("subplot(2,3,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, skosnosc[12], sekundy, skosnosc[13], sekundy, skosnosc[14], sekundy, skosnosc[15]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Skośność');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipL,KneeL,AnkleL,FootL')", null, null);

            this.eng.eval("subplot(2,3,5);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, skosnosc[16], sekundy, skosnosc[17], sekundy, skosnosc[18], sekundy, skosnosc[19]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Skośność');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipR,KneeR,AnkleR,FootR')", null, null);

            this.eng.eval("subplot(2,3,6);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, skosnosc[20], sekundy, skosnosc[21], sekundy, skosnosc[22], sekundy, skosnosc[23], sekundy, skosnosc[24]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Skośność');", null, null);
            this.eng.eval("legend('1','2','3','4','5')", null, null);
            this.eng.eval("title('SpineShoulder,HandTipL,ThumbL,HandTipR,ThumbR')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\kinect_roznice_norm_skosnosc.png');", null, null);
            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,3,1);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, kurtoza[0], sekundy, kurtoza[1], sekundy, kurtoza[2], sekundy, kurtoza[3]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('SpineBase,SpineMid,Neck,Head')", null, null);

            this.eng.eval("subplot(2,3,2);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, kurtoza[4], sekundy, kurtoza[5], sekundy, kurtoza[6], sekundy, kurtoza[7]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderL,ElbowL,WristL,HandL')", null, null);

            this.eng.eval("subplot(2,3,3);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, kurtoza[8], sekundy, kurtoza[9], sekundy, kurtoza[10], sekundy, kurtoza[11]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('ShoulderR,ElbowR,WristR,HandR')", null, null);

            this.eng.eval("subplot(2,3,4);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, kurtoza[12], sekundy, kurtoza[13], sekundy, kurtoza[14], sekundy, kurtoza[15]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipL,KneeL,AnkleL,FootL')", null, null);

            this.eng.eval("subplot(2,3,5);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, kurtoza[16], sekundy, kurtoza[17], sekundy, kurtoza[18], sekundy, kurtoza[19]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("title('HipR,KneeR,AnkleR,FootR')", null, null);

            this.eng.eval("subplot(2,3,6);", null, null);
            this.eng.feval("plot",
                           new Object[]{sekundy, kurtoza[20], sekundy, kurtoza[21], sekundy, kurtoza[22], sekundy, kurtoza[23], sekundy, kurtoza[24]});


            this.eng.eval("xlabel('Czas [s]');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Kurtoza');", null, null);
            this.eng.eval("legend('1','2','3','4','5')", null, null);
            this.eng.eval("title('SpineShoulder,HandTipL,ThumbL,HandTipR,ThumbR')", null, null);

            this.eng.eval("saveas(gcf,'" + this.sciezka + "\\wykresy\\kinect_roznice_norm_kurtoza.png');", null, null);
            this.eng.eval("close(gcf);", null, null);
            this.eng.eval("figure('units','normalized','outerposition',[0 0 1 1]);set(gcf, 'Visible', 'off');", null,
                          null);


            this.eng.eval("subplot(2,3,1);", null, null);
            this.eng.feval("scatter", new Object[]{srednia[0], wariancja[0], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[1], wariancja[1], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[2], wariancja[2], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[3], wariancja[3], "*"});
            this.eng.eval("xlabel('Średnia');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("set(gca,'yscale','log')");
            this.eng.eval("title('SpineBase,SpineMid,Neck,Head')", null, null);

            this.eng.eval("subplot(2,3,2);", null, null);
            this.eng.feval("scatter", new Object[]{srednia[4], wariancja[4], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[5], wariancja[5], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[6], wariancja[6], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[7], wariancja[7], "*"});
            this.eng.eval("xlabel('Średnia');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("set(gca,'yscale','log')");
            this.eng.eval("title('ShoulderL,ElbowL,WristL,HandL')", null, null);

            this.eng.eval("subplot(2,3,3);", null, null);
            this.eng.feval("scatter", new Object[]{srednia[8], wariancja[8], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[9], wariancja[9], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[10], wariancja[10], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[11], wariancja[11], "*"});
            this.eng.eval("xlabel('Średnia');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("set(gca,'yscale','log')");
            this.eng.eval("title('ShoulderR,ElbowR,WristR,HandR')", null, null);

            this.eng.eval("subplot(2,3,4);", null, null);
            this.eng.feval("scatter", new Object[]{srednia[12], wariancja[12], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[13], wariancja[13], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[14], wariancja[14], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[15], wariancja[15], "*"});
            this.eng.eval("xlabel('Średnia');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("set(gca,'yscale','log')");
            this.eng.eval("title('HipL,KneeL,AnkleL,FootL')", null, null);

            this.eng.eval("subplot(2,3,5);", null, null);
            this.eng.feval("scatter", new Object[]{srednia[16], wariancja[16], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[17], wariancja[17], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[18], wariancja[18], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[19], wariancja[19], "*"});
            this.eng.eval("xlabel('Średnia');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4')", null, null);
            this.eng.eval("set(gca,'yscale','log')");
            this.eng.eval("title('HipR,KneeR,AnkleR,FootR')", null, null);

            this.eng.eval("subplot(2,3,6);", null, null);
            this.eng.feval("scatter", new Object[]{srednia[20], wariancja[20], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[21], wariancja[21], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[22], wariancja[22], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[23], wariancja[23], "*"});
            this.eng.eval("hold on;");
            this.eng.feval("scatter", new Object[]{srednia[24], wariancja[24], "*"});
            this.eng.eval("xlabel('Średnia');", null, null);
            this.eng.eval("grid", null, null);
            this.eng.eval("ylabel('Wariancja');", null, null);
            this.eng.eval("legend('1','2','3','4','5')", null, null);
            this.eng.eval("set(gca,'yscale','log')");
            this.eng.eval("title('SpineShoulder,HandTipL,ThumbL,HandTipR,ThumbR')", null, null);

            this.eng.eval(
                    "saveas(gcf,'" + this.sciezka + "\\wykresy\\kinect_roznice_norm_rozrzut_srednia_wariancja.png');",
                    null, null);
            this.eng.eval("close(gcf);", null, null);
        }
    }


    public void zamnikj() throws EngineException {
        this.eng.disconnect();
    }
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\analizadanych\StatystycznaAnalizaDanych.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */