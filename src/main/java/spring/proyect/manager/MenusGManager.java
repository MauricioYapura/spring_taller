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
public class MenusGManager {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate = new JdbcTemplate(dataSource2);
	}

	public List<CMenus> listaMenu(int x1, int x2) {

		String xsql = "select m.codm, m.nombre, m.estado from cmenus m " + "where m.estado between ? and ?";
		List menus = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CMenus men = new CMenus();
				men.setCodm(rs.getInt("codm"));
				men.setNombre(rs.getString("nombre"));
				men.setEstado(rs.getInt("estado"));

				return men;
			}
		}, new Object[] { x1, x2 });
		return menus;
	}
public List<CProcesos> listarolmepro(int pro){
		
		String xsql=" select p.codp, p.nombre ,p.enlace,p.ayuda,p.estado  from cprocesos as p,mepro as mp "
				+ "where mp.codm=? and mp.codp=p.codp";
		List est = this.jdbcTemplate.query(
		    xsql,
		    new RowMapper() {
		        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	CProcesos pro= new CProcesos();
		        	pro.setCodp(rs.getInt("codp"));
		        	pro.setNombre(rs.getString("nombre"));
		        	pro.setEnlace(rs.getString("enlace"));
		        	pro.setAyuda(rs.getString("ayuda"));
		        	pro.setEstado(rs.getInt("estado"));
		        return pro;
		        }
		    },new Object[] {pro});
		return est;		
	}

/// asignado
public List<CProcesos> listarolmeproAsignado(int codm, int asignado){
	String xsql = null;
	if(asignado==0) {
		//no asignado
		xsql=" select p.* from cprocesos p where not exists (select * from mepro mp where ?=mp.codm and p.codp=mp.codp )";
		List est = this.jdbcTemplate.query(
			    xsql,
			    new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	CProcesos pro= new CProcesos();
			        	pro.setCodp(rs.getInt("codp"));
			        	pro.setNombre(rs.getString("nombre"));
			        	pro.setEnlace(rs.getString("enlace"));
			        	pro.setAyuda(rs.getString("ayuda"));
			        	pro.setEstado(rs.getInt("estado"));
			        return pro;
			        }
			    },new Object[] {codm});
			return est;	
	}
	if(asignado==1) {
		// asignado
		 xsql=" select p.codp, p.nombre ,p.enlace,p.ayuda,p.estado  from cprocesos as p,mepro as mp "
				+ "where mp.codm=? and mp.codp=p.codp";
		 List est = this.jdbcTemplate.query(
				    xsql,
				    new RowMapper() {
				        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				        	CProcesos pro= new CProcesos();
				        	pro.setCodp(rs.getInt("codp"));
				        	pro.setNombre(rs.getString("nombre"));
				        	pro.setEnlace(rs.getString("enlace"));
				        	pro.setAyuda(rs.getString("ayuda"));
				        	pro.setEstado(rs.getInt("estado"));
				        return pro;
				        }
				    },new Object[] {codm});
				return est;	
	}
	if(asignado==2) {
		// todos
		String xsql1=" select p.codp, p.nombre ,p.enlace,p.ayuda,p.estado  from cprocesos as p";
		 List est1 = this.jdbcTemplate.query(
				 xsql1,
				    new RowMapper() {
				        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				        	CProcesos pro= new CProcesos();
				        	pro.setCodp(rs.getInt("codp"));
				        	pro.setNombre(rs.getString("nombre"));
				        	pro.setEnlace(rs.getString("enlace"));
				        	pro.setAyuda(rs.getString("ayuda"));
				        	pro.setEstado(rs.getInt("estado"));
				        return pro;
				        }
				    },new Object[] {});
				return est1;
	}
	
		return null;
}
//adicionar

	public int setAddMenu(int codm, String nombre, int estado) {
		String xsql = "insert into cmenus( nombre, estado )" + "  values(?,?)";
		return this.jdbcTemplate.update(xsql, nombre, estado);
	}

//modificar
	public int setModMenu(int codm, String nombre, int estado) {
		String xsql = "update  cmenus " + "set nombre=?, estado=? " + " where codm=?";
		return this.jdbcTemplate.update(xsql, nombre, estado, codm);
	}

//elimnacion logica
	public int setElimLogica(int xcodm) {
		String xsql = "update cmenus " + "set estado=0 " + "where codm=?";
		return this.jdbcTemplate.update(xsql, xcodm);
	}

//habilitacion
	public int setHabilitar(int xcodm) {
		String xsql = "update cmenus " + "set estado=1 " + "where codm=?";
		return this.jdbcTemplate.update(xsql, xcodm);
	}
	//adicionar

		public int setAddMepro(int codm, int codp) {
			String xsql = "insert into mepro( codm, codp )" + "  values(?,?)";
			return this.jdbcTemplate.update(xsql, codm, codp);
		}
		//modificar
		public int setDelMepro(int codm, int codp) {
			String xsql = "DELETE FROM mepro " + 
					" WHERE codm=? and codp=?;";
			return this.jdbcTemplate.update(xsql, codm, codp);
		}

		public boolean isExists(String nombre) {
			// TODO Auto-generated method stub
			String sql = "SELECT count(*) FROM cmenus WHERE nombre = ?";
			boolean result = false;
			int count = jdbcTemplate.queryForObject(sql, new Object[] { nombre }, Integer.class);
			if (count > 0) {
			result = true;
			}
			return result;
		}

		public boolean isExists(int xcodm, int xcodp) {
			// TODO Auto-generated method stub
			String sql = "SELECT count(*) FROM mepro WHERE codm=? and codp=?";
			boolean result = false;
			int count = jdbcTemplate.queryForObject(sql, new Object[] { xcodm,xcodp }, Integer.class);
			if (count > 0) {
			result = true;
			}
			return result;
		}
	
}
