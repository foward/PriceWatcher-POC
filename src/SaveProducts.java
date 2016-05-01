import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.pricewatcher.model.Product;

public class SaveProducts {

	public static void saveProduct(Product product) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst = null;

		String url = "jdbc:postgresql://localhost:5432/thedatabase";
		String user = "theuser";
		String password = "thepassword";

		try {
			con = DriverManager.getConnection(url, user, password);

			String stm = "INSERT INTO authors(id, name) VALUES(?, ?)";
			pst = con.prepareStatement(stm);
		//	pst.setInt(1, id);
		//	pst.setString(2, author);
			pst.executeUpdate();

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(SaveProducts.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(SaveProducts.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

}
