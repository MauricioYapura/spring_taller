package spring.proyect.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import spring.proyect.model.CAlumnos;
import spring.proyect.model.CDatos;
import spring.proyect.model.CDocentes;
import spring.proyect.model.CPersonas;

@Service
public class PersonasManager {

	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate=new JdbcTemplate(dataSource2);
	}
	
	public List<CPersonas> listaPersonas(int xest1, int xest2){
		String xsql=" select codp, nombre,case when ap is null then ' ' else ap end as ap, case when am is null then ' ' else am end as am, estado,fnac,genero,direc,celular, foto , ecivil,\r\n   "
					+"					(select login from cdatos where cpersonas.codp=cdatos.codp) as login \r \n "
					+ "	from cpersonas   "
					+"	where estado between ? and ?  "
					+"  order by  ap, am, nombre   ";
		List est = this.jdbcTemplate.query(
		    xsql,
		    new RowMapper() {
		        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	CPersonas per= new CPersonas();
		        	per.setCodp(rs.getInt("codp"));
		        	per.setNombre(rs.getString("nombre"));
		        	per.setAp(rs.getString("ap"));
		        	per.setAm(rs.getString("am"));
		        	per.setEstado(rs.getInt("estado"));
		        	per.setFnac(rs.getDate("fnac"));
		        	per.setEcivil(rs.getString("ecivil").charAt(0));
		        	per.setGenero(rs.getString("genero").charAt(0));
		        	per.setDirec(rs.getString("direc"));
		        	per.setCelular(rs.getString("celular"));
		        	per.setFoto(rs.getString("foto"));
		        	per.setLogin(rs.getString("login"));
		        	
		        	return per;
		        }
		    },new Object[] {xest1, xest2});
	return est;		
}
	//solo una persona
	public CPersonas getDatosPersona(int codp) {
	
		BeanPropertyRowMapper bprm=new BeanPropertyRowMapper(CPersonas.class);
				String xsql="select p.codp, p.nombre, "
						+ "case when p.ap is null then ' ' else p.ap end as ap, "
						+ "case when p.am is null then ' ' else p.am end as am, p.estado, p.fnac, p.genero, p.direc, p.celular, p.foto, p.ecivil "
						+ " from cpersonas p "
						+ "where p.codp= ? ";
		return (CPersonas) jdbcTemplate.queryForObject(xsql, 
				new Object[]{codp}, bprm);
	}
	
	public CPersonas getDatosPersonaSerial(String nom, String ap, String am) {
		System.out.println(nom);
		BeanPropertyRowMapper bprm=new BeanPropertyRowMapper(CPersonas.class);
				String xsql="select p.codp, p.nombre, "
						+ "case when p.ap is null then ' ' else p.ap end as ap, "
						+ "case when p.am is null then ' ' else p.am end as am, p.estado, p.fnac, p.genero, p.direc, p.celular, p.foto, p.ecivil "
						+ " from cpersonas p "
						+ "where p.nombre=? or p.ap=? or p.am=?";
				System.out.println(xsql);
		return (CPersonas) jdbcTemplate.queryForObject(xsql, 
				new Object[]{nom,ap,am}, bprm);
	}

	

	public List< CPersonas> listaPersona(){		
			String xsql=" select codp, nombre,case when ap is null then ' ' else ap end as ap, case when am is null then ' ' else am end as am, estado,fnac,genero,direc,celular, foto , ecivil,\r\n" + 
					"					(select login from cdatos where cpersonas.codp=cdatos.codp) as login \r\n" + 
					"					 from cpersonas " ;
			List est=this.jdbcTemplate.query(
					xsql, 
					new RowMapper() {
						public Object mapRow(ResultSet rs, int rowNum)throws SQLException{
							CPersonas per=new CPersonas();
							per.setCodp(rs.getInt("codp"));
							per.setNombre(rs.getString("nombre"));
							per.setAp(rs.getString("ap"));
							per.setAm(rs.getString("am"));
							per.setEstado(rs.getInt("estado"));
							per.setFnac(rs.getDate("fnac"));
							per.setGenero(rs.getString("genero").charAt(0));
							per.setDirec(rs.getString("direc"));
							per.setCelular(rs.getString("celular"));
							per.setFoto(rs.getString("foto"));
							per.setEcivil(rs.getString("ecivil").charAt(0));
							per.setLogin(rs.getString("login"));
							
							return per;
							
						}
					},new Object[] {});
			
			return est;
		}
	
	public int setAddPersona(  String xnombre, String xap, String xam, int xestado, Date fnac,  char xgenero, String xdirec, String xcelular, String xfoto, char xecivil) {
		String xsql = "insert into cpersonas( nombre, ap, am, estado, fnac, genero, direc, celular, foto, ecivil)"
				+ "  values(?,?,?,?,?,?,?,?,?,?)";
			this.jdbcTemplate.update( xsql, xnombre, xap, xam, xestado, fnac, xgenero, xdirec,xcelular, xfoto, xecivil );
			CPersonas per = new CPersonas();
			per=getDatosPersonaSerial(xnombre,xap,xam);
			int codp=(int) per.getCodp();
			System.out.println("codifgo+¡"+codp);
	return codp;
	}
	
	
	public int setModPersona(long xcodp, String xnombre, String xap, String xam, int xestado, Date fnac,  char xgenero, String xdirec, String xcelular, String xfoto, char xecivil) {
		String  xsql="update  cpersonas "+
					"set nombre=?, ap=?, am=?, estado=?, fnac=?, genero=?, direc=?, celular=?, foto=?, ecivil=?"
					+" where codp=?";
		return this.jdbcTemplate.update(xsql,  xnombre, xap, xam, xestado, fnac, xgenero, xdirec,xcelular, xfoto, xecivil, xcodp);
	}
	
	public int setElimLogica( int xcodp ) {
		String xsql = "update cpersonas "
					+ "set estado=0 "
					+ "where codp=?";
		return this.jdbcTemplate.update( xsql, xcodp );
	}
	public int setHabilitarPersona( int xcodp ) {
		String xsql = "update cpersonas "
					+ "set estado=1 "
					+ "where codp=?";
		return this.jdbcTemplate.update( xsql, xcodp );
	}
	public int setfoto( long codp,String foto ) {
		String xsql = "update cpersonas "
					+ "set foto=?"
					+ "where codp=?";
		return this.jdbcTemplate.update( xsql,foto, codp );
	}
	
	public int setAlumno(int codp, int ru){
		String xsql ="insert into calumnos(codp, ru) values(?,?)";
		return this.jdbcTemplate.update(xsql,codp, ru);
	}
	public int setDocente(int codp, String cedula){
		String xsql ="insert into cdocentes(codp, cedula) values(?,?)";
		return this.jdbcTemplate.update(xsql,codp,cedula);
	}
	
	public int setDelPersona( int xcodp ) {
		String xsql = "delete from personas where codp=?";
		return this.jdbcTemplate.update( xsql , xcodp );
	}
	public CAlumnos getAlumno(int codp) {
		
		BeanPropertyRowMapper bprm=new BeanPropertyRowMapper(CAlumnos.class);
				String xsql="select codp, ru from calumnos where codp=?";
				System.out.println("alumno "+xsql);
		return (CAlumnos) jdbcTemplate.queryForObject(xsql, 
				new Object[]{codp}, bprm);
	}
	public CDocentes getDocente(int codp) {
		
		BeanPropertyRowMapper bprm=new BeanPropertyRowMapper(CDocentes.class);
				String xsql="select codp, cedula from cdocentes where codp=?";
		return (CDocentes) jdbcTemplate.queryForObject(xsql, 
				new Object[]{codp}, bprm);
	}
	
	public CDatos getCdatos(String login) {
		
		BeanPropertyRowMapper bprm=new BeanPropertyRowMapper(CDatos.class);
				String xsql="select login from cdatos where login=?";
		return (CDatos) jdbcTemplate.queryForObject(xsql, 
				new Object[]{login}, bprm);
	}
	
	public int setModCdatos(String password, String login) {
		String xsql = "update cdatos "
				+ "set password=?"
				+ "where login=?";
	return this.jdbcTemplate.update( xsql,password, login );
		
	}
	
	public int setAddCdatos(String login, String password, int codp) {
		String xsql ="insert into cdatos(login, codp, estado, password) values(?,?,1,?)";
		return this.jdbcTemplate.update(xsql,login,codp, password);
	}

	public boolean isExists(String nombre, String ap, String am) {
		// TODO Auto-generated method stub
		System.out.println("nombre: "+nombre+" ape pater: "+ ap+ "ap materno: "+ am);
		if (ap ==null) {
			ap=" ";
		}
		if (am==null) {
			am=" ";
		}
		String sql = "SELECT count(*) FROM cpersonas WHERE nombre = ? and ap= ? and am = ?";
		
		boolean result = false;
		int count = jdbcTemplate.queryForObject(sql, new Object[] { nombre,ap,am }, Integer.class);
		if (count > 0) {
		result = true;
		}
		return result;
	}

	public boolean isExistsLogin(String login) {
		// TODO Auto-generated method stub
		String sql = "SELECT count(*) FROM cdatos WHERE login = ?";
		boolean result = false;
		int count = jdbcTemplate.queryForObject(sql, new Object[] { login }, Integer.class);
		if (count > 0) {
		result = true;
		}
		return result;
	}
	
	
}
