package exemplo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exemplo.modelo.Carro;

public class CarroDao implements IDao<Carro> {
	
	public CarroDao() {
		try {
			createTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Cria a tabela se não existir
	private void createTable() throws SQLException {
		final String sqlCreate = "IF NOT EXISTS (" 
				+ "SELECT * FROM sys.tables t JOIN sys.schemas s ON " 
				+ "(t.schema_id = s.schema_id) WHERE s.name = 'dbo'" 
				+ "AND t.name = 'Carro')"
				+ "CREATE TABLE Carro"
				+ " (id	int	IDENTITY,"
				+ "  modelo VARCHAR(255),"
				+ "	 marca VARCHAR(255)"
				+ "  PRIMARY KEY (id))";
		
		Connection conn = DatabaseAccess.getConnection();
		
		Statement stmt = conn.createStatement();
		stmt.execute(sqlCreate);
	}

	public List<Carro> getAll() {
		Connection conn = DatabaseAccess.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Carro> carros = new ArrayList<Carro>();
		
		try {
			stmt = conn.createStatement();
			
			String SQL = "SELECT * FROM Carro"; // consulta de SELECT
	        rs = stmt.executeQuery(SQL); // executa o SELECT
	        
	        while (rs.next()) {
	        	Carro d = getCarroFromRs(rs);
	        	
	        	carros.add(d);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getAllCarros] Erro ao selecionar todos os carros", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return carros;		
	}
	
	public Carro getById(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Carro carro = null;
		
		try {
			String SQL = "SELECT * FROM Carro WHERE id = ?"; // consulta de SELECT
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        rs = stmt.executeQuery(); // executa o SELECT
	        
	        while (rs.next()) {
	        	carro = getCarroFromRs(rs);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getCarroById] Erro ao selecionar o carro por id", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return carro;		
	}
	
	public void insert(Carro carro) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "INSERT INTO Carro (marca, modelo) VALUES (?, ?)";
			stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
	    	stmt.setString(1, carro.getMarca());
	    	stmt.setString(2, carro.getModelo());

	        stmt.executeUpdate(); // executa o SELECT
	        
	        rs = stmt.getGeneratedKeys();
	        
	        if (rs.next()) {
	        	carro.setId(rs.getInt(1));
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[insereCarro] Erro ao inserir o carro", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public void delete(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
			
		try {
			String SQL = "DELETE Carro WHERE id=?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        stmt.executeUpdate(); 			
		} catch (SQLException e) {
			throw new RuntimeException("[deleteCarro] Erro ao remover carro por id", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void update(Carro carro) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "UPDATE Carro SET modelo = ? WHERE id=?";
			stmt = conn.prepareStatement(SQL);
	    	stmt.setString(1, carro.getModelo()); 
	    	stmt.setInt(2, carro.getId());
	    	
	        stmt.executeUpdate(); // executa o UPDATE			
		} catch (SQLException e) {
			throw new RuntimeException("[updateCarro] Erro ao atualizar o carro", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	private Carro getCarroFromRs(ResultSet rs) throws SQLException {
		Carro d = new Carro(); // cria um objeto de Carro
		d.setId(rs.getInt("id")); // insere id recuperado do banco na pessoa
		d.setMarca(rs.getString("marca")); // insere marca do carro no banco de dados
		d.setModelo(rs.getString("modelo"));
		return d;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) { rs.close(); }
			if (stmt != null) { stmt.close(); }
			if (conn != null) { conn.close(); }
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar recursos.", e);
		}
	}
}
