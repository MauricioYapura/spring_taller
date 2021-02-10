package spring.proyect.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import spring.proyect.model.CAreas;
import spring.proyect.model.CPersonas;

@Service
public class AreasManager {
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate=new JdbcTemplate(dataSource2);
	}
	
	public List< CAreas> listaAreas(){

		
		String xsql=" select coda, nombre,estado " + 
				
				" from careas " ;
		List est=this.jdbcTemplate.query(
				xsql, 
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum)throws SQLException{
						CAreas areas= new CAreas();
						areas.setCoda(rs.getInt("coda"));
						areas.setNombre(rs.getString("nombre"));
						areas.setEstado(rs.getInt("estado"));
						return areas;
					}
				},new Object[] {});
		
		return est;
	}
	
public List<CAreas>listaAreas(int xest1, int xest2) {
	String xsql="select coda, nombre,estado "
			+ " from careas "
			+"	where estado between ? and ?  ";
	List est=this.jdbcTemplate.query(
			xsql, 
			new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum)throws SQLException{
					CAreas areas= new CAreas();
					areas.setCoda(rs.getInt("coda"));
					areas.setNombre(rs.getString("nombre"));
					areas.setEstado(rs.getInt("estado"));
					return areas;
				}
			},new Object[] {xest1,xest2});
	
	return est;
			
}
	
	
//	adicionar
	
	public int setAddArea(int coda, String nombre, int estado) {
		String xsql = "insert into careas( nombre, estado )"
				+ "  values(?,?)";
		return	this.jdbcTemplate.update( xsql, nombre, estado);
	}
//	modificar
	public int setModArea(int coda ,String nombre, int estado) {
		String  xsql="update  careas "+
					"set nombre=?, estado=? "
					+" where coda=?";
		return this.jdbcTemplate.update(xsql,  nombre, estado ,coda);
	}
	
// elimnacion logica
	public int setElimLogica( int xcoda ) {
		String xsql = "update careas "
					+ "set estado=0 "
					+ "where coda=?";
		return this.jdbcTemplate.update( xsql, xcoda );
	}
//	habilitacion
	public int setHabilitarPersona( int xcoda ) {
		String xsql = "update careas "
					+ "set estado=1 "
					+ "where coda=?";
		return this.jdbcTemplate.update( xsql, xcoda );
	}
	
	
/// verificar si existe
	public boolean isExists(String nom) {
		String sql = "SELECT count(*) FROM careas WHERE nombre = ?";
		boolean result = false;
		int count = jdbcTemplate.queryForObject(sql, new Object[] { nom }, Integer.class);
		if (count > 0) {
		result = true;
		}
		return result;
		}

}
