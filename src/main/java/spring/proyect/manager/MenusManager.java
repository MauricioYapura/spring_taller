package spring.proyect.manager;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import spring.proyect.model.CMenus;
import spring.proyect.model.CPersonas;
import spring.proyect.model.CProcesos;
import spring.proyect.model.CRoles;

@Service
public class MenusManager {

	
	
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate=new JdbcTemplate(dataSource2);
	}
	
	public CPersonas getDatosPersonas(String xuser,String xclave) {
		//System.out.println("entra");
		BeanPropertyRowMapper bprm=new BeanPropertyRowMapper(CPersonas.class);
				String xsql="select p.codp, p.nombre, "
						+ "case when p.ap is null then ' ' else p.ap end as ap, "
						+ "case when p.am is null then ' ' else p.am end as am, p.estado,  p.foto, p.ecivil ,p.genero , d.login "
						+ " from cpersonas p, cdatos d "
						+ "where p.codp=d.codp and p.estado=1 and "
						+ "d.login=? and d.password=?  ";
				//System.out.println(xsql);
		return (CPersonas) jdbcTemplate.queryForObject(xsql, 
				new Object[]{xuser,xclave}, bprm);
	}
	
	public CPersonas getDatosPlogin(String xuser) {
		BeanPropertyRowMapper bprm=new BeanPropertyRowMapper(CPersonas.class);
				String xsql="select p.codp, p.nombre, "
						+ "case when p.ap is null then ' ' else p.ap end as ap, "
						+ "case when p.am is null then ' ' else p.am end as am, p.estado,  p.foto, p.ecivil ,p.genero  "
						+ " from cpersonas p, cdatos d "
						+ "where p.codp=d.codp and p.estado=1 and "
						+ "d.login=?   ";
		return (CPersonas) jdbcTemplate.queryForObject(xsql, 
				new Object[]{xuser,}, bprm);
	}
	
	
	
	
	public  List<CRoles> listaRoles(String login){
		
		String xsql="select r.codr , r.nombre, r.estado from croles r, usurol ur, cdatos d "
				+ "where   r.codr=ur.codr and ur.login=d.login and d.login= '"+login+"'" ;
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
				},new Object[] {});
		return roles ;
	}
	
	
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
	


}
