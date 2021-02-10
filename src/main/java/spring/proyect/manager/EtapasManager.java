package spring.proyect.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import spring.proyect.model.CAreas;
import spring.proyect.model.CEtapas;


@Service
public class EtapasManager {
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate=new JdbcTemplate(dataSource2);
	}
	
public List< CEtapas> listaEtapas(){

		
		String xsql=" select code, nombre,estado " + 
				" from cetapas " ;
		List est=this.jdbcTemplate.query(
				xsql, 
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum)throws SQLException{
						CEtapas etapas= new CEtapas();
						etapas.setCode(rs.getInt("code"));
						etapas.setNombre(rs.getString("nombre"));
						etapas.setEstado(rs.getInt("estado"));
						return etapas;
					}
				},new Object[] {});
		
		return est;
	}

public List<CEtapas>listaEtapas(int xest1, int xest2) {
	String xsql="select code, nombre,estado "
			+ " from cetapas "
			+"	where estado between ? and ?  ";
	List est=this.jdbcTemplate.query(
			xsql, 
			new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum)throws SQLException{
					CEtapas etapas= new CEtapas();
					etapas.setCode(rs.getInt("code"));
					etapas.setNombre(rs.getString("nombre"));
					etapas.setEstado(rs.getInt("estado"));
					return etapas;
				}
			},new Object[] {xest1,xest2});
	
	return est;
			
}

//adicionar

public int setAddEtapa(int code, String nombre, int estado) {
	String xsql = "insert into cetapas( nombre, estado )"
			+ "  values(?,?)";
	return	this.jdbcTemplate.update( xsql, nombre, estado);
}
//modificar
public int setModEtapa(int code ,String nombre, int estado) {
	String  xsql="update  cetapas "+
				"set nombre=?, estado=? "
				+" where code=?";
	return this.jdbcTemplate.update(xsql,  nombre, estado ,code);
}

//elimnacion logica
public int setElimLogica( int xcode ) {
	String xsql = "update cetapas "
				+ "set estado=0 "
				+ "where code=?";
	return this.jdbcTemplate.update( xsql, xcode );
}
//habilitacion
public int setHabilitarEtapa( int xcode ) {
	String xsql = "update cetapas "
				+ "set estado=1 "
				+ "where code=?";
	return this.jdbcTemplate.update( xsql, xcode );
}

public boolean isExists(String nombre) {
	// TODO Auto-generated method stub
	String sql = "SELECT count(*) FROM cetapas WHERE nombre = ?";
	boolean result = false;
	int count = jdbcTemplate.queryForObject(sql, new Object[] { nombre }, Integer.class);
	if (count > 0) {
	result = true;
	}
	return result;
}

}


