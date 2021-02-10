package spring.proyect.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import spring.proyect.model.CGrupos;
import spring.proyect.model.CPersonas;
import spring.proyect.model.CProgra;

@Service
public class PrograManager {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate = new JdbcTemplate(dataSource2);
	}
	public List<CProgra> listaProgra() {

		String xsql = "select p.codp, (p.nombre || ' ' ||p.ap || ' ' ||p.am) as nombre,g.codg, g.nombre as grupo, cp.gestion, cp.estado, notafinal   "
					+ "from cprogra cp, cpersonas p, calumnos cd, cgrupos g where cp.codp=p.codp  and p.codp=cd.codp and g.codg=cp.codg";
		System.out.println(xsql);
		List est = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CProgra pro = new CProgra();
				pro.setCodp(rs.getInt("codp"));
				pro.setNombre(rs.getString("nombre"));
				pro.setCodg(rs.getInt("codg"));
				pro.setGrupo(rs.getString("grupo"));
				pro.setGestion(rs.getInt("gestion"));
				pro.setEstado(rs.getInt("estado"));
				pro.setNotaFinal(rs.getFloat("notafinal"));
				return pro;
			}
		}, new Object[] {});

		return est;
	}
	//add
	public int setAddProgra(int codp, int codg, int gestion, int estado, float notafinal) {
		String xsql = "insert into cprogra( codp, codg, gestion, estado, notafinal )"
				+ "  values(?,?,?,?,?)";
		return	this.jdbcTemplate.update( xsql, codp, codg,gestion, estado,notafinal);
	}
//	modificar
	public int setModProgra(int codp, int codg, int gestion, int estado,float notafinal, int codp2, int codg2, int gestion2) {
		String  xsql="update  cprogra "+
					"set codp=?, codg=?, gestion=?, estado=?, notafinal=? "
					+" where codp=? and codg=? and gestion=?";
		return this.jdbcTemplate.update(xsql, codp,codg,gestion, estado,notafinal ,codp2,codg2,gestion2);
	}
	
// elimnacion 
	public int setDelProgra( int codp,int codg, int gestion ) {
		String xsql = "delete from cprogra where codp=? and codg=? and gestion=?";
		return this.jdbcTemplate.update(xsql, codp, codg,gestion );
	}
	
	///
		public List< CPersonas> listaPersona(){		
			String xsql=" select p.codp, p.nombre, "
					+ "case when p.ap is null then ' ' else p.ap end as ap, "
					+ "case when p.am is null then ' ' else p.am end as am, "
					+ " p.estado, p.fnac, p.genero, p.direc, p.celular, p.foto, p.ecivil, "
					+ " (select login from cdatos  where p.codp=cdatos.codp) as login "
					+ "from calumnos d, cpersonas p where d.codp=p.codp" ;
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
		
		///
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
		public List<CProgra> busquedaGes(int gestion) {
			String xsql = "select p.codp, (p.nombre || ' ' ||p.ap || ' ' ||p.am) as nombre ,g.codg, g.nombre as grupo, cp.gestion, cp.estado, notafinal   "
					+ "from cprogra cp, cpersonas p, calumnos cd, cgrupos g where cp.codp=p.codp  and p.codp=cd.codp and g.codg=cp.codg and cp.gestion=?";
		System.out.println(xsql);
		List est = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CProgra pro = new CProgra();
				pro.setCodp(rs.getInt("codp"));
				pro.setNombre(rs.getString("nombre"));
				pro.setCodg(rs.getInt("codg"));
				pro.setGrupo(rs.getString("grupo"));
				pro.setGestion(rs.getInt("gestion"));
				pro.setEstado(rs.getInt("estado"));
				pro.setNotaFinal(rs.getFloat("notafinal"));
				return pro;
			}
		}, new Object[] {gestion});
			return est;
		}
		public List<CProgra> busquedaGgrupo(int grupo) {
			String xsql = "select p.codp, (p.nombre || ' ' ||p.ap || ' ' ||p.am) as nombre,g.codg, g.nombre as grupo, cp.gestion, cp.estado, notafinal   "
					+ "from cprogra cp, cpersonas p, calumnos cd, cgrupos g where cp.codp=p.codp  and p.codp=cd.codp and g.codg=cp.codg and cp.codg=?";
		System.out.println(xsql);
		List est = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CProgra pro = new CProgra();
				pro.setCodp(rs.getInt("codp"));
				pro.setNombre(rs.getString("nombre"));
				pro.setCodg(rs.getInt("codg"));
				pro.setGrupo(rs.getString("grupo"));
				pro.setGestion(rs.getInt("gestion"));
				pro.setEstado(rs.getInt("estado"));
				pro.setNotaFinal(rs.getFloat("notafinal"));
				return pro;
			}
		}, new Object[] {grupo});
			return est;
		}
		public boolean isExists(int codp, int codg, int gestion) {
			// TODO Auto-generated method stub
			String sql = "SELECT count(*) FROM cprogra WHERE codp = ? and codg = ? and gestion = ?";
			boolean result = false;
			int count = jdbcTemplate.queryForObject(sql, new Object[] { codp, codg, gestion}, Integer.class);
			if (count > 0) {
			result = true;
			}
			return result;
		}

}
