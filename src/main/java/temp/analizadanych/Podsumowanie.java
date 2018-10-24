package temp.analizadanych;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Podsumowanie {
    List<String[]> listaRekawice;
    List<String[]> listaKinect;
    List<String[]> listaPulsometr;
    String[] opisDanychKinect;
    String scieska;

    public Podsumowanie(List<String[]> lista1, String sciezka) {
        this.scieska = sciezka;

        this.opisDanychKinect = new String[25];
        this.opisDanychKinect[0] = "SpineBase";
        this.opisDanychKinect[1] = "SpineMid";
        this.opisDanychKinect[2] = "Neck";
        this.opisDanychKinect[3] = "Head";
        this.opisDanychKinect[4] = "ShoulderLeft";
        this.opisDanychKinect[5] = "ElbowLeft";
        this.opisDanychKinect[6] = "WristLeft";
        this.opisDanychKinect[7] = "HandLeft";
        this.opisDanychKinect[8] = "ShoulderRight";
        this.opisDanychKinect[9] = "ElbowRight";
        this.opisDanychKinect[10] = "WristRight";
        this.opisDanychKinect[11] = "HandRight";
        this.opisDanychKinect[12] = "HipLeft";
        this.opisDanychKinect[13] = "KneeLeft";
        this.opisDanychKinect[14] = "AnkleLeft";
        this.opisDanychKinect[15] = "FootLeft";
        this.opisDanychKinect[16] = "HipRight";
        this.opisDanychKinect[17] = "KneeRight";
        this.opisDanychKinect[18] = "AnkleRight";
        this.opisDanychKinect[19] = "FootRight";
        this.opisDanychKinect[20] = "SpineShoulder";
        this.opisDanychKinect[21] = "HandTipLeft";
        this.opisDanychKinect[22] = "ThumbLeft";
        this.opisDanychKinect[23] = "HandTipRight";
        this.opisDanychKinect[24] = "ThumbRight";

        this.listaRekawice = new ArrayList();
        this.listaKinect = new ArrayList();
        this.listaPulsometr = new ArrayList();

        for (int i = 0; i < lista1.size(); i++) {
            if (((String[]) lista1.get(i))[6] != null) {
                String[] pomK = new String[7];
                pomK[0] = ((String[]) lista1.get(i))[3];
                pomK[1] = ((String[]) lista1.get(i))[2];
                for (int j = 4; j <= 8; j++) {
                    pomK[(j - 2)] = ((String[]) lista1.get(i))[j];
                }

                this.listaKinect.add(pomK);
            }


            if (((String[]) lista1.get(i))[11] != null) {
                String[] pomR = new String[10];
                pomR[0] = ((String[]) lista1.get(i))[3];
                pomR[1] = ((String[]) lista1.get(i))[2];
                for (int j = 9; j <= 16; j++) {
                    pomR[(j - 7)] = ((String[]) lista1.get(i))[j];
                }
                this.listaRekawice.add(pomR);
            }

            if (((String[]) lista1.get(i))[17] != null) {
                String[] pomP = new String[4];
                pomP[0] = ((String[]) lista1.get(i))[3];
                pomP[1] = ((String[]) lista1.get(i))[2];

                pomP[2] = ((String[]) lista1.get(i))[17];
                pomP[3] = ((String[]) lista1.get(i))[18];

                this.listaPulsometr.add(pomP);
                for (int y = 0; y < this.listaPulsometr.size(); y++) {
                }
            }
        }
    }


    public String[] tworzPodsumowanieRekawica() {
        int liczbaBadan = this.listaRekawice.size();


        if (liczbaBadan != 0) {
            String[] podsumowanieRekawica = new String[24];
            podsumowanieRekawica[0] = "data_badania;";
            podsumowanieRekawica[1] = "scenariusz;";

            podsumowanieRekawica[2] = "analiza_wariancji_Kciuk_L;";
            podsumowanieRekawica[3] = "analiza_wariancji_Wskazujacy_L;";
            podsumowanieRekawica[4] = "analiza_wariancji_Srodkowy_L;";
            podsumowanieRekawica[5] = "analiza_wariancji_Serdeczny_L;";
            podsumowanieRekawica[6] = "analiza_wariancji_Maly_L;";
            podsumowanieRekawica[7] = "analiza_wariancji_Kciuk_P;";
            podsumowanieRekawica[8] = "analiza_wariancji_Wskazujacy_P;";
            podsumowanieRekawica[9] = "analiza_wariancji_Srodkowy_P;";
            podsumowanieRekawica[10] = "analiza_wariancji_Serdeczny_P;";
            podsumowanieRekawica[11] = "analiza_wariancji_Maly_P;";

            podsumowanieRekawica[12] = "korelacja_min_L;";
            podsumowanieRekawica[13] = "korelacja_coZczym_L;";
            podsumowanieRekawica[14] = "korelacja_max_L;";
            podsumowanieRekawica[15] = "korelacja_coZczym_L;";

            podsumowanieRekawica[16] = "korelacja_min_P;";
            podsumowanieRekawica[17] = "korelacja_coZczym_P;";
            podsumowanieRekawica[18] = "korelacja_max_P;";
            podsumowanieRekawica[19] = "korelacja_coZczym_P;";

            podsumowanieRekawica[20] = "kurtoza_L;";
            podsumowanieRekawica[21] = "kurtoza_P;";
            podsumowanieRekawica[22] = "wsp. skosnosci_L;";
            podsumowanieRekawica[23] = "wsp. skosnosci_P;";
            for (int j = 0; j < this.listaRekawice.size(); j++) {
                podsumowanieRekawica[0] = (podsumowanieRekawica[0] + ((String[]) this.listaRekawice.get(j))[0] + ";");
                podsumowanieRekawica[1] = (podsumowanieRekawica[1] + ((String[]) this.listaRekawice.get(j))[1] + ";");
                String[] pomL = ((String[]) this.listaRekawice.get(j))[2].split(";");
                int t = 0;
                for (int k = 2; k <= 6; k++) {
                    podsumowanieRekawica[k] = (podsumowanieRekawica[k] + pomL[t] + ";");
                    t++;
                }
                t = 0;
                String[] pomP = ((String[]) this.listaRekawice.get(j))[3].split(";");
                for (int k = 7; k <= 11; k++) {
                    podsumowanieRekawica[k] = (podsumowanieRekawica[k] + pomP[t] + ";");
                    t++;
                }

                pomL = ((String[]) this.listaRekawice.get(j))[4].split(" ");
                podsumowanieRekawica[12] = (podsumowanieRekawica[12] + pomL[0] + ";");
                podsumowanieRekawica[13] = (podsumowanieRekawica[13] + pomL[1].replace(';', ',') + ";");
                podsumowanieRekawica[14] = (podsumowanieRekawica[14] + pomL[2] + ";");
                podsumowanieRekawica[15] = (podsumowanieRekawica[15] + pomL[3].replace(';', ',') + ";");

                pomP = ((String[]) this.listaRekawice.get(j))[5].split(" ");
                podsumowanieRekawica[16] = (podsumowanieRekawica[16] + pomP[0] + ";");
                podsumowanieRekawica[17] = (podsumowanieRekawica[17] + pomP[1].replace(';', ',') + ";");
                podsumowanieRekawica[18] = (podsumowanieRekawica[18] + pomP[2] + ";");
                podsumowanieRekawica[19] = (podsumowanieRekawica[19] + pomP[3].replace(';', ',') + ";");

                t = 6;
                for (int k = 20; k <= 23; k++) {
                    podsumowanieRekawica[k] = (podsumowanieRekawica[k] + ((String[]) this.listaRekawice.get(
                            j))[t] + ";");
                    t++;
                }
            }

            return podsumowanieRekawica;
        }
        return null;
    }

    public String[] tworzPodsumowaniesKinect() {
        int liczbaBadan = this.listaKinect.size();
        String[] podsumowanieKinect = null;


        if (liczbaBadan != 0) {
            podsumowanieKinect = new String[''];
            podsumowanieKinect[0] = "data_badania;";
            podsumowanieKinect[1] = "scenariusz;";

            for (int k = 2; k <= 26; k++) {
                podsumowanieKinect[k] = ("analizaNorm " + this.opisDanychKinect[(k - 2)] + ";");
            }

            for (int k = 27; k <= 51; k++) {
                podsumowanieKinect[k] = ("analizaWariancji " + this.opisDanychKinect[(k - 27)] + ";");
            }

            int t = 0;
            for (int k = 52; k <= 140; k += 4) {
                podsumowanieKinect[k] = ("korelacja_min_" + this.opisDanychKinect[t] + ";");
                podsumowanieKinect[(k + 1)] = ("korelacja_coZczym_" + this.opisDanychKinect[t] + ";");
                podsumowanieKinect[(k + 2)] = ("korelacja_max_" + this.opisDanychKinect[t] + ";");
                podsumowanieKinect[(k + 3)] = ("korelacja_coZczym_" + this.opisDanychKinect[t] + ";");
                t++;
            }
            podsumowanieKinect[''] = ("korelacja_" + this.opisDanychKinect[23] + ";");
            podsumowanieKinect[''] = ("korelacja_coZczym_" + this.opisDanychKinect[23] + ";");

            podsumowanieKinect[''] = "kurtozaKinect;";
            podsumowanieKinect[''] = "skosnoscKinect;";

            for (int j = 0; j < this.listaKinect.size(); j++) {
                String[] pom = ((String[]) this.listaKinect.get(j))[2].split(";");

                podsumowanieKinect[0] = (podsumowanieKinect[0] + ((String[]) this.listaKinect.get(j))[0] + ";");
                podsumowanieKinect[1] = (podsumowanieKinect[1] + ((String[]) this.listaKinect.get(j))[1] + ";");
                for (int k = 2; k <= 26; k++) {
                    podsumowanieKinect[k] = (podsumowanieKinect[k] + pom[(k - 2)] + ";");
                }

                pom = ((String[]) this.listaKinect.get(j))[3].split(";");
                for (int k = 27; k <= 51; k++) {
                    podsumowanieKinect[k] = (podsumowanieKinect[k] + pom[(k - 27)] + ";");
                }

                pom = ((String[]) this.listaKinect.get(j))[4].split(";");
                t = 0;
                for (int k = 52; k <= 140; k += 4) {
                    String[] pom1 = pom[t].split(" ");
                    podsumowanieKinect[k] = (podsumowanieKinect[k] + pom1[1] + ";");
                    podsumowanieKinect[(k + 1)] = (podsumowanieKinect[(k + 1)] + this.opisDanychKinect[Integer.valueOf(
                            pom1[2]).intValue()] + ";");
                    podsumowanieKinect[(k + 2)] = (podsumowanieKinect[(k + 2)] + pom1[3] + ";");
                    podsumowanieKinect[(k + 3)] = (podsumowanieKinect[(k + 3)] + this.opisDanychKinect[Integer.valueOf(
                            pom1[4]).intValue()] + ";");
                    t++;
                }


                String[] pom1 = pom[23].split(" ");
                podsumowanieKinect[''] = (podsumowanieKinect[''] + pom1[1] + ";");
                podsumowanieKinect[''] = (podsumowanieKinect[''] + this.opisDanychKinect[Integer.valueOf(pom1[2])
                                                                                                  .intValue()] + ";");

                podsumowanieKinect[''] = (podsumowanieKinect[''] + ((String[]) this.listaKinect.get(j))[5] + ";");
                podsumowanieKinect[''] = (podsumowanieKinect[''] + ((String[]) this.listaKinect.get(j))[6] + ";");
            }


            return podsumowanieKinect;
        }
        return null;
    }


    public String[] tworzPodsumowaniePulsometr() {
        int liczbaBadan = this.listaPulsometr.size();


        if (liczbaBadan != 0) {
            String[] podsumowaniePulsometr = new String[4];
            podsumowaniePulsometr[0] = "data_badania;";
            podsumowaniePulsometr[1] = "scenariusz;";
            podsumowaniePulsometr[2] = "kurtozaPulsomter;";
            podsumowaniePulsometr[3] = "skosnoscPulsomter;";

            for (int j = 0; j < this.listaPulsometr.size(); j++) {
                podsumowaniePulsometr[0] = (podsumowaniePulsometr[0] + ((String[]) this.listaPulsometr.get(
                        j))[0] + ";");
                podsumowaniePulsometr[1] = (podsumowaniePulsometr[1] + ((String[]) this.listaPulsometr.get(
                        j))[1] + ";");
                podsumowaniePulsometr[2] = (podsumowaniePulsometr[2] + ((String[]) this.listaPulsometr.get(
                        j))[2] + ";");
                podsumowaniePulsometr[3] = (podsumowaniePulsometr[3] + ((String[]) this.listaPulsometr.get(
                        j))[3] + ";");
            }

            return podsumowaniePulsometr;
        }
        return null;
    }

    public void tworzRaport() throws FileNotFoundException {
        String[] rekawice = tworzPodsumowanieRekawica();
        String[] kinect = tworzPodsumowaniesKinect();
        String[] pulsometr = tworzPodsumowaniePulsometr();

        String sciezka = "";

        if (rekawice != null) {
            PrintWriter zapis = new PrintWriter(this.scieska + "\\rekawice.csv");
            for (int i = 0; i < rekawice.length; i++) {
                zapis.println(rekawice[i]);
            }
            zapis.close();
        }
        if (kinect != null) {
            PrintWriter zapis = new PrintWriter(this.scieska + "\\kinect.csv");
            for (int i = 0; i < kinect.length; i++) {
                zapis.println(kinect[i]);
            }
            zapis.close();
        }
        if (pulsometr != null) {
            PrintWriter zapis = new PrintWriter(this.scieska + "\\pulsometr.csv");
            for (int i = 0; i < pulsometr.length; i++) {
                zapis.println(pulsometr[i]);
            }
            zapis.close();
        }
    }
}
