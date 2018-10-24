package temp.analizadanych;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BazaDanych {
    private Connection c;
    private Plik plikPulsometr;
    private Plik plikRekawice;
    private Plik plikKinect;
    private Wnioskowanie wn;

    public BazaDanych(Plik wczytany, Wnioskowanie wn) throws ClassNotFoundException, SQLException {
        this.plikRekawice = null;
        this.plikKinect = null;
        this.plikPulsometr = null;
        this.wn = wn;


        if (wczytany.pulsometr == true) {
            this.plikPulsometr = wczytany;
        }

        if (wczytany.kinect == true) {
            this.plikKinect = wczytany;
        }

        if (wczytany.rekawica == true) {
            this.plikRekawice = wczytany;
        }


        Class.forName("org.sqlite.JDBC");
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite::resource:jar:" + getClass().getResource("/db/DaneZPomiarow.db").getPath();
            this.c = DriverManager.getConnection(dbURL);
            if (this.c != null) {
                this.c.setAutoCommit(false);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public BazaDanych() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite::resource:jar:" + getClass().getResource("/db/DaneZPomiarow.db").getPath();
            this.c = DriverManager.getConnection(dbURL);
            if (this.c != null) {
                this.c.setAutoCommit(false);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void dodajBadanie(String id, String scenariusz) throws SQLException {
        int idPulsometr = -1;
        int idRekawice = -1;
        int idKinect = -1;


        if (this.plikPulsometr != null) {
            PreparedStatement statement = this.c.prepareStatement("INSERT INTO BadaniaPulsometr VALUES(null,?,?)");
            statement.setString(1, this.wn.skosnoscPulsometr);
            statement.setString(2, this.wn.skosnoscPulsometr);
            statement.executeUpdate();
            this.c.commit();
            statement = this.c.prepareStatement("SELECT max(idBadaniaPulsometr) from BadaniaPulsometr");
            ResultSet rs = statement.executeQuery();
            rs.next();
            idPulsometr = rs.getInt(1);
        }


        if (this.plikRekawice != null) {
            PreparedStatement statement = this.c.prepareStatement(
                    "INSERT INTO BadaniaRekawice VALUES(null,?,?,?,?,?,?,?,?)");
            statement.setString(1, this.wn.korelacjaP);
            statement.setString(2, this.wn.korelacjaL);
            statement.setString(3, this.wn.skosnoscP);
            statement.setString(4, this.wn.skosnoscL);
            statement.setString(5, this.wn.kurtozaP);
            statement.setString(6, this.wn.kurtozaL);
            statement.setString(7, this.wn.analizaWariancjiP);
            statement.setString(8, this.wn.analizaWariancjiL);
            statement.executeUpdate();
            this.c.commit();
            statement = this.c.prepareStatement("SELECT max(idBadaniaRekawice) from BadaniaRekawice");
            ResultSet rs = statement.executeQuery();
            rs.next();
            idRekawice = rs.getInt(1);
        }


        if (this.plikKinect != null) {
            PreparedStatement statement = this.c.prepareStatement("INSERT INTO BadaniaKinect VALUES(null,?,?,?,?,?)");
            statement.setString(1, this.wn.korelacjaKinect);
            statement.setString(2, this.wn.skosnoscKinect);
            statement.setString(3, this.wn.kurtozaKinect);
            statement.setString(4, this.wn.analizaWariancjiKinect);
            statement.setString(5, this.wn.analizaNormKinect);
            statement.executeUpdate();
            this.c.commit();
            statement = this.c.prepareStatement("SELECT max(idBadaniaKinect) from BadaniaKinect");
            ResultSet rs = statement.executeQuery();
            rs.next();
            idKinect = rs.getInt(1);
        }


        PreparedStatement statement = this.c.prepareStatement("INSERT INTO Badania VALUES(null,?,?,?,?,?,?)");
        statement.setString(1, id);
        statement.setString(3, scenariusz);
        if (this.plikPulsometr != null) {
            statement.setString(2, this.plikPulsometr.dataPulsometr);
            statement.setInt(5, idPulsometr);
        }
        if (this.plikRekawice != null) {
            statement.setString(2, this.plikRekawice.dataRekawice);
            statement.setInt(6, idRekawice);
        }
        if (this.plikKinect != null) {
            statement.setString(2, this.plikKinect.dataKinect);
            statement.setInt(4, idKinect);
        }
        statement.executeUpdate();
        this.c.commit();
        statement.close();
    }

    public List pobierzWynikiDlaJednejOsoby(String id) throws SQLException {
        List<String[]> lista = new ArrayList();
        PreparedStatement statement = this.c.prepareStatement(
                "select Badania.idBadanego as idBadanego, Badania.idBadania as idBadania, Badania.scenariusz as scenariusz, Badania.timestamp as timestamp, BadaniaKinect.analizaNormKinect as analizaNormKinect, BadaniaKinect.analizaWariancjiKinect as analizaWariancjiKinect, BadaniaKinect.korelacjaKinect as korelacjaKinect, BadaniaKinect.kurtozaKinect as kurtozaKinect, BadaniaKinect.skosnoscKinect as skosnoscKinect, BadaniaRekawice.analizaWariancjiL as analizaWariancjiL, BadaniaRekawice.analizaWariancjiP as analizaWariancjiP, BadaniaRekawice.korelacjaL as korelacjaL, BadaniaRekawice.korelacjaP as korelacjaP, BadaniaRekawice.kurtozaL as kurtozaL, BadaniaRekawice.kurtozaP as kurtozaP, BadaniaRekawice.skosnoscL as skosnoscL, BadaniaRekawice.skosnoscP as skosnoscP, BadaniaPulsometr.kurtozaPulsometr as kurtozaPulsometr, BadaniaPulsometr.skosnoscPulsometr as skosnoscPulsometr from Badania  left join BadaniaKinect on Badania.idBadaniaKinect=BadaniaKinect.idBadaniaKinect left join BadaniaPulsometr on Badania.idBadaniaPulsometr=BadaniaPulsometr.idBadaniaPulsometr left join BadaniaRekawice on Badania.idBadaniaRekawice=BadaniaRekawice.idBadaniaRekawice where idBadanego = ?;");


        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();

        if (rs.isBeforeFirst()) {
            int n = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String[] rekord = new String[n];
                for (int i = 0; i < n; i++) {
                    rekord[i] = rs.getString(i + 1);
                }

                lista.add(rekord);
            }
            rs.close();
            statement.close();

            return lista;
        }

        return null;
    }

    public void rozlacz() throws SQLException {
        this.c.commit();
        this.c.close();
    }
}