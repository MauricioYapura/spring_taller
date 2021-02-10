package spring.proyect.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import spring.proyect.model.CGrupos;

@Service

public class GruposManager {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate = new JdbcTemplate(dataSource2);
	}

	public List<CGrupos> listaGrupos() {

		String xsql = " select codg, nombre,estado " +

				" from cgrupos ";
		List est = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CGrupos grup = new CGrupos();
				grup.setCodg(rs.getInt("codg"));
				grup.setNombre(rs.getString("nombre"));
				grup.setEstado(rs.getInt("estado"));
				return grup;
			}
		}, new Object[] {});

		return est;
	}
	public List<CGrupos>listaGrupos(int xest1, int xest2) {
		String xsql="select codg, nombre,estado "
				+ " from cgrupos "
				+"	where estado between ? and ?  ";
		List est=this.jdbcTemplate.query(
				xsql, 
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum)throws SQLException{
						CGrupos grup = new CGrupos();
						grup.setCodg(rs.getInt("codg"));
						grup.setNombre(rs.getString("nombre"));
						grup.setEstado(rs.getInt("estado"));
						return grup;
					}
				},new Object[] {xest1,xest2});
		
		return est;
				
	}
	
//	adicionar
	
	public int setAddGrupo(int codg, String nombre, int estado) {
		String xsql = "insert into cgrupos( nombre, estado )"
				+ "  values(?,?)";
		return	this.jdbcTemplate.update( xsql, nombre, estado);
	}
//	modificar
	public int setModGrupos(int codg ,String nombre, int estado) {
		String  xsql="update  cgrupos "+
					"set nombre=?, estado=? "
					+" where codg=?";
		return this.jdbcTemplate.update(xsql,  nombre, estado ,codg);
	}
	
// elimnacion logica
	public int setElimLogica( int xcodg ) {
		String xsql = "update cgrupos "
					+ "set estado=0 "
					+ "where codg=?";
		return this.jdbcTemplate.update( xsql, xcodg );
	}
//	habilitacion
	public int setHabilitarGrupos( int xcodg) {
		String xsql = "update cgrupos "
					+ "set estado=1 "
					+ "where codg=?";
		return this.jdbcTemplate.update( xsql, xcodg );
	}

	public boolean isExists(String nombre) {
		// TODO Auto-generated method stub
		String sql = "SELECT count(*) FROM cgrupos WHERE nombre = ?";
		boolean result = false;
		int count = jdbcTemplate.queryForObject(sql, new Object[] { nombre }, Integer.class);
		if (count > 0) {
		result = true;
		}
		return result;
	}
}
