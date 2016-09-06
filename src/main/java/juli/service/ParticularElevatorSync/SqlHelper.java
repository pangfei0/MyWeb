package juli.service.ParticularElevatorSync;

import java.sql.*;


public class SqlHelper {
    //public static final String url = "jdbc:mysql://127.0.0.1:3306/MySQL56?user=root&password=123456&useUnicode=true&characterEncoding=8859_1";
    public static  String url = "jdbc:mysql://localhost:3306/";
    public static final String driver = "com.mysql.jdbc.Driver";
    static String username = "root" ;
    static String password = "123456" ;
    static String mSqlDatabase="juli";
    public static  String mUrl;
    private SqlHelper() {
    }

    public static Connection getConnection() {

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            StringBuffer temp = new StringBuffer(url);

            temp.append(mSqlDatabase);
            mUrl=temp.toString();
            return DriverManager.getConnection(mUrl,username,password);
            //return DriverManager.getConnection(url);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public static Statement getStatement() {
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }
        try {
            return conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
            close(conn);
        }
        return null;
    }


    public static PreparedStatement getPreparedStatement(String cmdText, Object... cmdParams) {
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(cmdText);
            int i = 1;
            for (Object item : cmdParams) {
                pstmt.setObject(i, item);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            close(conn);
        }
        return pstmt;
    }


    public static int ExecuteNonQuery(String cmdText) {
        Statement stmt = getStatement();
        if (stmt == null) {
            return -2;
        }
        int i = -1;
        try {
            i = stmt.executeUpdate(cmdText);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection(stmt);
        return i;
    }


    public static int ExecuteNonQuery(String cmdText, Object... cmdParams) {
        PreparedStatement pstmt = getPreparedStatement(cmdText, cmdParams);
        if (pstmt == null) {
            return -2;
        }
        int i = -1;
        try {
            i = pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            closeConnection(pstmt);
        }
        return i;
    }


    public static ResultSet ExecuteQuery(String cmdText) {
        Statement stmt = getStatement();
        if (stmt == null) {
            return null;
        }
        try {
            return stmt.executeQuery(cmdText);
        } catch (SQLException ex) {
            ex.printStackTrace();
            closeConnection(stmt);
        }
        return null;
    }


    public static ResultSet ExecuteQuery(String cmdText, Object... cmdParams) {
        PreparedStatement pstmt = getPreparedStatement(cmdText, cmdParams);
        if (pstmt == null) {
            return null;
        }
        try {
            return pstmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            closeConnection(pstmt);
        }
        return null;
    }


    public static Object ExecScalar(String cmdText) {
        ResultSet rs = ExecuteQuery(cmdText);
        Object obj = buildScalar(rs);
        closeConnection(rs);
        return obj;
    }

    public static Object ExecScalar(String cmdText, Object... cmdParams) {
        ResultSet rs = ExecuteQuery(cmdText, cmdParams);
        Object obj = buildScalar(rs);
        closeConnection(rs);
        return obj;
    }

    public static Object buildScalar(ResultSet rs) {
        if (rs == null) {
            return null;
        }
        Object obj = null;
        try {
            if (rs.next()) {
                obj = rs.getObject(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    private static void close(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            if (obj instanceof Statement) {
                ((Statement) obj).close();
            } else if (obj instanceof PreparedStatement) {
                ((PreparedStatement) obj).close();
            } else if (obj instanceof ResultSet) {
                ((ResultSet) obj).close();
            } else if (obj instanceof Connection) {
                ((Connection) obj).close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void closeConnection(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            if (obj instanceof Statement) {
                ((Statement) obj).getConnection().close();
            } else if (obj instanceof PreparedStatement) {
                ((PreparedStatement) obj).getConnection().close();
            } else if (obj instanceof ResultSet) {
                ((ResultSet) obj).getStatement().getConnection().close();
            } else if (obj instanceof Connection) {
                ((Connection) obj).close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
