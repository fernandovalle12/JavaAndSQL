package exemplo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exemplo.modelo.Casa;

public class CasaDao implements IDao<Casa> {
	
	public CasaDao() {
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
				+ "AND t.name = 'Casa')"
				+ "CREATE TABLE Casa"
				+ " (id	int	IDENTITY,"
				+ "  endereco VARCHAR(255),"
				+ "	 proprietario int"
				+ "  PRIMARY KEY (id))";
		
		Connection conn = DatabaseAccess.getConnection();
		
		Statement stmt = conn.createStatement();
		stmt.execute(sqlCreate);
	}

	public List<Casa> getAll() {
		Connection conn = DatabaseAccess.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Casa> casas = new ArrayList<Casa>();
		
		try {
			stmt = conn.createStatement();
			
			String SQL = "SELECT * FROM Casa"; // consulta de SELECT
	        rs = stmt.executeQuery(SQL); // executa o SELECT
	        
	        while (rs.next()) {
	        	Casa d = getCasaFromRs(rs);
	        	
	        	casas.add(d);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getAllCasas] Erro ao selecionar todas as casas", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return casas;		
	}
	
	public Casa getById(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Casa casa = null;
		
		try {
			String SQL = "SELECT * FROM Casa WHERE id = ?"; // consulta de SELECT
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        rs = stmt.executeQuery(); // executa o SELECT
	        
	        while (rs.next()) {
	        	casa = getCasaFromRs(rs);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getCasaById] Erro ao selecionar a casa por id", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return casa;		
	}
	
	public void insert(Casa casa) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "INSERT INTO Casa (endereco, proprietario) VALUES (?, ?)";
			stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
	    	stmt.setString(1, casa.getEndereco());
	    	stmt.setInt(2, casa.getProprietario().getId());
			
	        stmt.executeUpdate(); // executa o SELECT
	        
	        rs = stmt.getGeneratedKeys();
	        
	        if (rs.next()) {
	        	casa.setId(rs.getInt(1));
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[insereCasa] Erro ao inserir a casa", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public void delete(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
			
		try {
			String SQL = "DELETE Casa WHERE id=?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        stmt.executeUpdate(); 			
		} catch (SQLException e) {
			throw new RuntimeException("[deleteCasa] Erro ao remover casa por id", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void update(Casa casa) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "UPDATE Casa SET endereco = ? WHERE id=?";
			stmt = conn.prepareStatement(SQL);
	    	stmt.setString(1, casa.getEndereco()); 
	    	stmt.setInt(2, casa.getId());
	    	
	        stmt.executeUpdate(); // executa o UPDATE			
		} catch (SQLException e) {
			throw new RuntimeException("[updateCasa] Erro ao atualizar a casa", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	private Casa getCasaFromRs(ResultSet rs) throws SQLException {
		Casa d = new Casa(); // cria um objeto de pessoa
		d.setId(rs.getInt("id")); // insere id recuperado do banco na pessoa
		d.setEndereco(rs.getString("endereco")); // insere endereco da casa no banco de dados
		
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
