package bases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author oracle
 */
public class BaseRelacional {

    private Connection conexion;

    public static void main(String[] args) {

        BaseRelacional br = new BaseRelacional().conectar();
        br.ejecutar("insert into productos values('p1','parafusos',3)");
        br.ejecutar("insert into productos values('p2','cravos',4)");
        br.ejecutar("insert into productos values('p3','tachas',5)");
        
        System.out.println("Ejecucion correcta!");

        ResultSet resultados = br.consultar("SELECT * FROM productos");
        if (resultados != null) {
            try {
                System.out.println("CODIGO            DESCRIPCION");
                System.out.println("--------------------------------");
                while (resultados.next()) {
                    
                    System.out.println("" + resultados.getNString("CODIGO") + "    "+
                                            resultados.getString("DESCRICION")+"     "+
                                            resultados.getInt("PREZO"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public BaseRelacional conectar() {
        try {
            String BaseDeDatos = "jdbc:oracle:thin:@localhost:1521:orcl";
            conexion = DriverManager.getConnection(BaseDeDatos, "hr", "hr");
            if (conexion != null) {
                System.out.println("Conexion exitosa!");
            } else {
                System.out.println("Conexion fallida!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean ejecutar(String sql) {
        try {
            Statement sentencia;
            sentencia = getConexion().createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            sentencia.executeUpdate(sql);
            //getConexion().commit();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ResultSet consultar(String sql) {
        ResultSet resultado = null;
        try {
            Statement sentencia;
            sentencia = getConexion().createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
            //getConexion().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultado;
    }
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}
