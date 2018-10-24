package temp.analizadanych;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Plik {
    String sciezka;
    String dataPulsometr;
    String dataRekawice;
    String dataKinect;
    String sciezkaRaport;
    List<String> liniePulsometr;
    List<String> linieRekawice;
    List<String[]> liniePodzieloneRekawice;
    List<String> linieKinect;
    String[] liniePodzieloneKinect;
    public double[] danePulsometr;
    public double[] danePulsometrInterpolowane;
    public double[][] daneRekawicaLewa;
    public double[][] daneRekawicaPrawa;
    String[] opisDanychRekawica;
    public double[][] daneKinect;
    public double[][] daneKincetInterpolowane;
    public double[][] daneNormyKinect;
    public double[][] daneNormyRoznicaKinect;
    public String[] opisDanychKinect;
    public String[] opisDanychKinectNormy;
    public boolean pulsometr;
    public boolean kinect;
    public boolean rekawica;
    int liczbaSekund;
    int liczbaKolumn;

    public void setSciezkaRaport(String sciezkaRaport) {
        this.sciezkaRaport = sciezkaRaport;
    }


    public String pobierzSciezke() {
        javax.swing.JFileChooser jFileChooser = new javax.swing.JFileChooser();
        jFileChooser.setCurrentDirectory(new java.io.File("C:"));
        int result = jFileChooser.showOpenDialog(new javax.swing.JFrame());


        if (result == 0) {
            java.io.File selectedFile = jFileChooser.getSelectedFile();
            String filename = selectedFile.getName();
            if (filename.toLowerCase().contains("PULSOMETER".toLowerCase())) {
                this.dataPulsometr = filename.substring(11, 21);
                this.dataPulsometr = (this.dataPulsometr.substring(3, 5) + "-" + this.dataPulsometr.substring(0,
                                                                                                              2) + "-" + this.dataPulsometr
                        .substring(6));
            }
            if (filename.toLowerCase().contains("GLOVE".toLowerCase())) {
                this.dataRekawice = filename.substring(6, 16);
                this.dataRekawice = (this.dataRekawice.substring(3, 5) + "-" + this.dataRekawice.substring(0,
                                                                                                           2) + "-" + this.dataRekawice
                        .substring(6));
            }
            if (filename.toLowerCase().contains("KINECT".toLowerCase())) {
                this.dataKinect = filename.substring(7, 17);
                this.dataKinect = (this.dataKinect.substring(3, 5) + "-" + this.dataKinect.substring(0,
                                                                                                     2) + "-" + this.dataKinect
                        .substring(6));
            }
            return selectedFile.getAbsolutePath();
        }

        return null;
    }

    public Plik() {
        this.pulsometr = false;
        this.kinect = false;
        this.rekawica = false;
    }

    public void pobierzDanePulsometr() {
        try {
            this.sciezka = pobierzSciezke();

            if (this.sciezka != null) {
                java.nio.file.Path p = java.nio.file.Paths.get(this.sciezka, new String[0]);
                Charset cs = Charset.forName("ISO-8859-1");


                this.liniePulsometr = java.nio.file.Files.readAllLines(p, cs);

                int liczbaSekund = this.liniePulsometr.size() - 1;

                this.danePulsometr = new double[liczbaSekund];


                for (String s : this.liniePulsometr) {
                    s = s.replace(",", ".");
                }


                for (int i = 1; i < this.liniePulsometr.size(); i++) {
                    this.danePulsometr[(i - 1)] = Double.parseDouble(
                            ((String) this.liniePulsometr.get(i)).split("\\|")[0]);
                }


                this.pulsometr = true;
            }
        } catch (IOException ex) {
            this.pulsometr = false;
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void zapisDoPlikuPulsometr(String sciezka) {
        try {
            PrintWriter zapis = new PrintWriter(sciezka);

            for (int j = 0; j < this.danePulsometr.length; j++) {
                zapis.print(String.valueOf(this.danePulsometr[j]).replace('.', ',') + ';');
            }
            zapis.println();

            zapis.close();
        } catch (java.io.FileNotFoundException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pobierzDaneRekawice() {
        try {
            this.sciezka = pobierzSciezke();
            if (this.sciezka != null) {
                java.nio.file.Path p = java.nio.file.Paths.get(this.sciezka, new String[0]);
                Charset cs = Charset.forName("ISO-8859-1");
                this.linieRekawice = java.nio.file.Files.readAllLines(p, cs);


                this.daneRekawicaLewa = new double[5][this.linieRekawice.size() / 36];
                this.daneRekawicaPrawa = new double[5][this.linieRekawice.size() / 36];


                this.opisDanychRekawica = new String[5];

                this.opisDanychRekawica[0] = "kciuk";
                this.opisDanychRekawica[1] = "wskazujący";
                this.opisDanychRekawica[2] = "środkowy";
                this.opisDanychRekawica[3] = "serdeczny";
                this.opisDanychRekawica[4] = "mały";
                for (String s : this.linieRekawice) {
                    s = s.replace(",", ".");
                }


                for (int i = 1; i < 14; i += 3) {
                    int k = 0;
                    for (int j = i; j < this.linieRekawice.size(); j += 36) {
                        if (j / 36 < this.daneRekawicaLewa[0].length) {
                            this.daneRekawicaLewa[(i / 3)][(j / 36)] = Double.parseDouble(
                                    ((String) this.linieRekawice.get(j)).split("\\|")[3]);
                        }
                    }
                }


                for (int i = 19; i < 32; i += 3) {
                    int k = 0;
                    for (int j = i; j < this.linieRekawice.size(); j += 36) {
                        if (j / 36 < this.daneRekawicaPrawa[0].length) {
                            this.daneRekawicaPrawa[((i - 18) / 3)][(j / 36)] = Double.parseDouble(
                                    ((String) this.linieRekawice.get(j)).split("\\|")[3]);
                        }
                    }
                }

                this.rekawica = true;
            }
        } catch (IOException ex) {
            this.rekawica = false;
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void zapisDoPlikuRekawice(String sciezka, int liczbaWierszy, int liczbaKolumn, boolean czyPrawa) {
        try {
            PrintWriter zapis = new PrintWriter(sciezka);
            for (int k = 0; k < this.opisDanychRekawica.length; k++) {
                zapis.print(String.valueOf(this.opisDanychRekawica[k]) + ";");
            }
            zapis.println();

            if (czyPrawa) {
                for (int i = 0; i < liczbaKolumn; i++) {
                    for (int j = 0; j < liczbaWierszy; j++) {
                        zapis.print(String.valueOf(this.daneRekawicaPrawa[j][i]).replace('.', ',') + ';');
                    }
                    zapis.println();
                }

            } else {
                for (int i = 0; i < liczbaKolumn; i++) {
                    for (int j = 0; j < liczbaWierszy; j++) {
                        zapis.print(String.valueOf(this.daneRekawicaLewa[j][i]).replace('.', ',') + ';');
                    }
                    zapis.println();
                }
            }
            zapis.close();
        } catch (java.io.FileNotFoundException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pobierzDaneKinect() {
        try {
            this.sciezka = pobierzSciezke();
            if (this.sciezka != null) {
                java.nio.file.Path p = java.nio.file.Paths.get(this.sciezka, new String[0]);
                Charset cs = Charset.forName("ISO-8859-1");
                this.linieKinect = java.nio.file.Files.readAllLines(p, cs);

                this.liczbaSekund = (this.linieKinect.size() - 1);
                this.liniePodzieloneKinect = ((String) this.linieKinect.get(0)).split("\\|");

                this.liczbaKolumn = this.liniePodzieloneKinect.length;

                this.liczbaKolumn -= 1;
                this.daneKinect = new double[this.liczbaSekund][this.liczbaKolumn];
                this.opisDanychKinect = ((String) this.linieKinect.get(0)).split("\\|");

                for (int i = 1; i < this.linieKinect.size(); i++) {
                    this.liniePodzieloneKinect = ((String) this.linieKinect.get(i)).split("\\|");
                    int dl = this.liniePodzieloneKinect.length;
                    dl -= 1;
                    if (dl != this.liczbaKolumn) {
                        dl += 1;
                    }
                    for (int j = 0; j < dl; j++) {
                        this.daneKinect[(i - 1)][j] = Double.parseDouble(
                                this.liniePodzieloneKinect[j].replace(',', '.'));
                    }
                    if (dl < this.liczbaKolumn) {
                        int r = this.liczbaKolumn - (this.liczbaKolumn - dl);
                        for (int k = r; k < this.liczbaKolumn; k++) {
                            this.daneKinect[(i - 1)][k] = 0.0D;
                        }
                    }
                }

                this.kinect = true;
            }
        } catch (IOException ex) {
            this.kinect = false;
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void zapisDoPlikuKinect(String sciezka, int liczbaSekund, int liczbaKolumn) {
        try {
            PrintWriter zapis = new PrintWriter(sciezka);
            for (int k = 0; k < this.opisDanychKinect.length - 1; k++) {
                zapis.print(String.valueOf(this.opisDanychKinect[k]) + ";");
            }
            zapis.println();

            for (int i = 0; i < liczbaSekund; i++) {
                for (int j = 0; j < liczbaKolumn; j++) {
                    zapis.print(String.valueOf(this.daneKinect[i][j]).replace('.', ',') + ';');
                }
                zapis.println();
            }
            zapis.close();
        } catch (java.io.FileNotFoundException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void przygotowanieOpisuKolumnNormy() {
        String laczenieNazw = "";
        for (int i = 0; i < this.opisDanychKinect.length - 1; i++) {
            if (i % 3 == 0) {
                int pom = this.opisDanychKinect[i].lastIndexOf("_");
                laczenieNazw = laczenieNazw + this.opisDanychKinect[i].substring(0, pom) + "|";
            }
        }
        laczenieNazw = laczenieNazw.substring(0, laczenieNazw.length());
        this.opisDanychKinectNormy = laczenieNazw.split("\\|");
    }


    public void przygotowanieNormRozniceKinect() {
        przygotowanieOpisuKolumnNormy();
        int x = this.daneKinect.length / 30 * 30;

        int y = this.daneKinect[0].length;
        int k = 0;
        int t = 0;
        this.daneNormyRoznicaKinect = new double[x - 1][y / 3];

        for (int j = 1; j < x; j += 1) {
            for (int i = 2; i < y; i += 3) {

                double suma = Math.pow(this.daneKinect[j][(i - 2)] - this.daneKinect[(j - 1)][(i - 2)],
                                       2.0D) + Math.pow(this.daneKinect[j][(i - 1)] - this.daneKinect[(j - 1)][(i - 1)],
                                                        2.0D) + Math.pow(
                        this.daneKinect[j][i] - this.daneKinect[(j - 1)][i], 2.0D);
                this.daneNormyRoznicaKinect[t][k] = Math.sqrt(suma);
                k++;
            }
            t += 1;
            k = 0;
        }


        zapisDoPlikuKinectNormyRoznice(this.sciezkaRaport + "\\kinectNormyRoznice.txt", x - 1, y / 3);
    }


    public void zapisDoPlikuKinectNormyRoznice(String sciezka, int liczbaSekund, int liczbaKolumn) {
        try {
            PrintWriter zapis = new PrintWriter(sciezka);
            for (int k = 0; k < this.opisDanychKinectNormy.length; k++) {
                zapis.print(String.valueOf(this.opisDanychKinectNormy[k]) + ";");
            }
            zapis.println();

            for (int i = 0; i < liczbaSekund; i++) {
                for (int j = 0; j < liczbaKolumn; j++) {
                    zapis.print(String.valueOf(this.daneNormyRoznicaKinect[i][j]).replace('.', ',') + ';');
                }
                zapis.println();
            }
            zapis.close();
        } catch (java.io.FileNotFoundException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void generujRaport(List<String> lines, List<String> linesKor) {
        if (this.pulsometr) {
            zapisDoPlikuPulsometr(this.sciezkaRaport + "\\pulsometr.txt");
        }
        if (this.rekawica) {
            zapisDoPlikuRekawice(this.sciezkaRaport + "\\rekawicaPrawa.txt", 5, this.linieRekawice.size() / 36, true);
            zapisDoPlikuRekawice(this.sciezkaRaport + "\\rekawicaLewa.txt", 5, this.linieRekawice.size() / 36, false);
        }
        if (this.kinect) {
            zapisDoPlikuKinect(this.sciezkaRaport + "\\kinect.txt", this.liczbaSekund, this.liczbaKolumn);
        }
        java.nio.file.Path file = java.nio.file.Paths.get(this.sciezkaRaport + "\\raport.txt", new String[0]);
        try {
            java.nio.file.Files.write(file, lines, Charset.forName("UTF-8"), new java.nio.file.OpenOption[0]);
            if (!linesKor.isEmpty()) {
                java.nio.file.Path file1 = java.nio.file.Paths.get(this.sciezkaRaport + "\\raportKorelacje.txt",
                                                                   new String[0]);
                java.nio.file.Files.write(file1, linesKor, Charset.forName("UTF-8"), new java.nio.file.OpenOption[0]);
            }
        } catch (IOException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void generujWnioskowanie(List<String> lines) throws IOException {
        java.nio.file.Path file = java.nio.file.Paths.get(this.sciezkaRaport + "\\raportWnioskowanie.txt",
                                                          new String[0]);
        java.nio.file.Files.write(file, lines, Charset.forName("UTF-8"), new java.nio.file.OpenOption[0]);
    }
}