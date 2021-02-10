package spring.proyect.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import spring.proyect.model.CMenus;
import spring.proyect.model.CProcesos;
import spring.proyect.model.CRoles;

@Service
public class RolesManager {
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate=new JdbcTemplate(dataSource2);
	}
public  List<CRoles> listaRoles(int x1,int x2){
		
		String xsql="select r.codr , r.nombre, r.estado from croles r "
				+ "where r.estado between ? and ?" ;
		List roles=this.jdbcTemplate.query(
				xsql, 
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum)throws SQLException{
						CRoles rol=new CRoles();
						rol.setCodr(rs.getInt("codr"));
						rol.setNombre(rs.getString("nombre"));
						rol.setEstado(rs.getInt("estado"));
						
						return rol;
					}
				},new Object[] {x1,x2});
		return roles ;
	}

//adicionar

public int setAddRole(int codr, String nombre, int estado) {
	String xsql = "insert into croles( nombre, estado )"
			+ "  values(?,?)";
	return	this.jdbcTemplate.update( xsql, nombre, estado);
}
//modificar
public int setModRole(int codr ,String nombre, int estado) {
	String  xsql="update  croles "+
				"set nombre=?, estado=? "
				+" where codr=?";
	return this.jdbcTemplate.update(xsql,  nombre, estado ,codr);
}

//elimnacion logica
public int setElimLogica( int xcodr ) {
	String xsql = "update croles "
				+ "set estado=0 "
				+ "where codr=?";
	return this.jdbcTemplate.update( xsql, xcodr );
}
//habilitacion
public int setHabilitarRole( int xcodr ) {
	String xsql = "update croles "
				+ "set estado=1 "
				+ "where codr=?";
	return this.jdbcTemplate.update( xsql, xcodr );
}
/////rolme

public List<CMenus> listarolme(int codr) {
	String xsql=  "select m.codm, m.nombre, m.estado "
			+" from cmenus as m, croles as  r,rolme as rm where m.codm=rm.codm and r.codr="+codr+" and rm.codr="+codr;
	List menu=this.jdbcTemplate.query(
			xsql, 
			new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum)throws SQLException{
					CMenus menu=new CMenus();
					menu.setCodm(rs.getInt("codm"));
					menu.setNombre(rs.getString("nombre"));
					menu.setEstado(rs.getInt("estado"));
					
					return menu;
				}
			},new Object[] {});
	return menu ;
}

////rolme
public List<CMenus> listarolmeSolo(int codr){
	
	String xsql=" select m.*  from cmenus as m,rolme as rm "
			+ "where rm.codr=? and rm.codm=m.codm";
	List est = this.jdbcTemplate.query(
	    xsql,
	    new RowMapper() {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	CMenus menu= new CMenus();
	        	menu.setCodm(rs.getInt("codm"));
	        	menu.setNombre(rs.getString("nombre"));			        	
	        	menu.setEstado(rs.getInt("estado"));
	        return menu;
	        }
	    },new Object[] {codr});
	return est;		
}
/// asignado
public List<CMenus> listarRolmeAsignado(int codr, int asignado){
	String xsql = null;
	if(asignado==0) {
		//no asignado
		xsql=" select m.* from cmenus m " + 
				"where not exists (select * from rolme rm where ?=rm.codr and m.codm=rm.codm)";
		List est = this.jdbcTemplate.query(
			    xsql,
			    new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	CMenus menu= new CMenus();
			        	menu.setCodm(rs.getInt("codm"));
			        	menu.setNombre(rs.getString("nombre"));			        	
			        	menu.setEstado(rs.getInt("estado"));
			        return menu;
			        }
			    },new Object[] {codr});
			return est;	
	}
	if(asignado==1) {
		// asignado
		
		 xsql=" select m.*  from cmenus as m, rolme as rm  where rm.codr=? and m.codm=rm.codm";
		 List est = this.jdbcTemplate.query(
				    xsql,
				    new RowMapper() {
				        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				        	CMenus menu= new CMenus();
				        	menu.setCodm(rs.getInt("codm"));
				        	menu.setNombre(rs.getString("nombre"));			        	
				        	menu.setEstado(rs.getInt("estado"));
				        return menu;
				        }
				    },new Object[] {codr});
				return est;	
	}
	if(asignado==2) {
		// todos
		String xsql1=" select m.*  from cmenus as m";
		 List est1 = this.jdbcTemplate.query(
				 xsql1,
				    new RowMapper() {
				        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				        	CMenus menu= new CMenus();
				        	menu.setCodm(rs.getInt("codm"));
				        	menu.setNombre(rs.getString("nombre"));			        	
				        	menu.setEstado(rs.getInt("estado"));
				        return menu;
				        }
				    },new Object[] {});
				return est1;
	}
	
		return null;
}


//adicionar

		public int setAddRolme(int codr, int codm) {
			String xsql = "insert into rolme( codr, codm )" + "  values(?,?)";
			return this.jdbcTemplate.update(xsql, codr, codm);
		}
		//modificar
		public int setDelRolme(int codr, int codm) {
			String xsql = "DELETE FROM rolme " + 
					" WHERE codr=? and codm=?;";
			return this.jdbcTemplate.update(xsql, codr, codm);
		}
		public boolean isExistslogin(String nombre) {
			// TODO Auto-generated method stub
			String sql = "SELECT count(*) FROM croles WHERE nombre = ?";
			boolean result = false;
			int count = jdbcTemplate.queryForObject(sql, new Object[] { nombre }, Integer.class);
			if (count > 0) {
			result = true;
			}
			return result;
		}
		public boolean isExists(int xcodr, int xcodm) {
			// TODO Auto-generated method stub
			String sql = "SELECT count(*) FROM rolme WHERE codr=? and codm=?";
			boolean result = false;
			int count = jdbcTemplate.queryForObject(sql, new Object[] { xcodr,xcodm }, Integer.class);
			if (count > 0) {
			result = true;
			}
			return result;
		}
		

}
