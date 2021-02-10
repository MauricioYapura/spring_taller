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

import spring.proyect.model.CPersonas;
import spring.proyect.model.CProgra;
import spring.proyect.model.CProyectos;

@Service
public class ProyectosManager {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate = new JdbcTemplate(dataSource2);
	}
	
	public List<CProyectos> listaProgra() {

		String xsql = "select cp.codpro, cp.titulo, cp.resumen, cp.file, cp.notadefensa,  cp.estado, cp.fecha, cp.estadoproy, cp.codtutor, \r\n" + 
				"(select nombre  from cpersonas , cdocentes where cdocentes.codp=cpersonas.codp and  cdocentes.codp=cp.codtutor) as nombredoc,\r\n" + 
				" cp.codarea, car.nombre as nombrearea, cpro.codp, (cpr.nombre || ' ' ||case when cpr.ap is null then ' ' else cpr.ap end  || ' ' ||case when cpr.am is null then ' ' else cpr.am end) as nomautor, cpro.codg, cg.nombre as nombregrupo ,cpro.gestion\r\n" + 
				"from cproyectos cp,cdocentes cd, calumnos ca, cpersonas cpr, careas car ,cprogra cpro, cgrupos cg\r\n" + 
				"where cp.codtutor=cd.codp and ca.codp=cpr.codp and ca.codp=cp.codp\r\n" + 
				" and cp.codarea=car.coda and car.coda=cp.codarea and cpro.codp=cp.codp and cpro.codg=cp.codg and cpro.gestion= cp.gestion and cg.codg=cpro.codg and cpro.codg=cp.codg \r\n" + 
				"";
		
		List est = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CProyectos pro = new CProyectos();
				pro.setCodpro(rs.getInt("codpro"));
				pro.setTitulo(rs.getString("titulo"));
				pro.setResumen(rs.getString("resumen"));
				pro.setFile(rs.getString("file"));
				pro.setNotadefensa(rs.getFloat("notadefensa"));
				pro.setEstado(rs.getInt("estado"));
				pro.setFecha(rs.getDate("fecha"));
				pro.setEstadoproy(rs.getInt("estadoproy"));
				pro.setCodTutor(rs.getInt("codTutor"));
				pro.setNombredoc(rs.getString("nombredoc"));
				pro.setCodarea(rs.getInt("codarea"));
				pro.setNombrearea(rs.getString("nombrearea"));
				pro.setCodp(rs.getInt("codp"));
				pro.setNombreautor(rs.getString("nomautor"));
				
				pro.setCodg(rs.getInt("codg"));	
				pro.setNombregrupo(rs.getString("nombregrupo"));
				pro.setGestion(rs.getInt("gestion"));
				
				return pro;
			}
		}, new Object[] {});

		return est;
	}
	public List<CProyectos> listaPrograEstadp(int xest1,int xest2) {

		String xsql = "select cp.codpro, cp.titulo, cp.resumen, cp.file, cp.notadefensa,  cp.estado, cp.fecha, cp.estadoproy, cp.codtutor, \r\n" + 
				"(select nombre  from cpersonas , cdocentes where cdocentes.codp=cpersonas.codp and  cdocentes.codp=cp.codtutor) as nombredoc,\r\n" + 
				" cp.codarea, car.nombre as nombrearea, cpro.codp, (cpr.nombre || ' ' ||cpr.ap || ' ' ||cpr.am) as nomautor, cpro.codg, cg.nombre as nombregrupo ,cpro.gestion\r\n" + 
				"from cproyectos cp,cdocentes cd, calumnos ca, cpersonas cpr, careas car ,cprogra cpro, cgrupos cg\r\n" + 
				"where cp.codtutor=cd.codp and ca.codp=cpr.codp and ca.codp=cp.codp\r\n" + 
				" and cp.codarea=car.coda and car.coda=cp.codarea and cpro.codp=cp.codp and cpro.codg=cp.codg and cpro.gestion= cp.gestion and cg.codg=cpro.codg and cpro.codg=cp.codg\r\n" + 
				" and cp.estado between ? and ?";
		
		List est = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CProyectos pro = new CProyectos();
				pro.setCodpro(rs.getInt("codpro"));
				pro.setTitulo(rs.getString("titulo"));
				pro.setResumen(rs.getString("resumen"));
				pro.setFile(rs.getString("file"));
				pro.setNotadefensa(rs.getFloat("notadefensa"));
				pro.setEstado(rs.getInt("estado"));
				pro.setFecha(rs.getDate("fecha"));
				pro.setEstadoproy(rs.getInt("estadoproy"));
				pro.setCodTutor(rs.getInt("codTutor"));
				pro.setNombredoc(rs.getString("nombredoc"));
				pro.setCodarea(rs.getInt("codarea"));
				pro.setNombrearea(rs.getString("nombrearea"));
				pro.setCodp(rs.getInt("codp"));
				pro.setNombreautor(rs.getString("nomautor"));
				
				pro.setCodg(rs.getInt("codg"));	
				pro.setNombregrupo(rs.getString("nombregrupo"));
				pro.setGestion(rs.getInt("gestion"));
				
				return pro;
			}
		}, new Object[] {xest1,xest2});

		return est;
	}
	//solo una proy codpro
		public CProyectos getDatosPro(int codpro) {
			BeanPropertyRowMapper bprm=new BeanPropertyRowMapper(CProyectos.class);
					String xsql="select cp.codpro, cp.titulo, cp.resumen, cp.file, cp.notadefensa,  cp.estado, cp.fecha, cp.estadoproy, cp.codtutor, \r\n" + 
							"(select nombre  from cpersonas , cdocentes where cdocentes.codp=cpersonas.codp and  cdocentes.codp=cp.codtutor) as nombredoc,\r\n" + 
							" cp.codarea, car.nombre as nombrearea, cpro.codp, (cpr.nombre || ' ' ||cpr.ap || ' ' ||cpr.am) as nomautor, cpro.codg, cg.nombre as nombregrupo ,cpro.gestion\r\n" + 
							"from cproyectos cp,cdocentes cd, calumnos ca, cpersonas cpr, careas car ,cprogra cpro, cgrupos cg\r\n" + 
							"where cp.codtutor=cd.codp and ca.codp=cpr.codp and ca.codp=cp.codp\r\n" + 
							" and cp.codarea=car.coda and car.coda=cp.codarea and cpro.codp=cp.codp and cpro.codg=cp.codg and cpro.gestion= cp.gestion and cg.codg=cpro.codg and cpro.codg=cp.codg "
							+ "and cp.codpro=? ";
					CProyectos pro= (CProyectos) jdbcTemplate.queryForObject(xsql, 
							new Object[]{codpro}, bprm);
					System.out.println(pro);
			
			return pro;
		}

		//solo una proy titulo
				public CProyectos getDatosProtitulo(String titulo) {
					
					boolean saber=true;
					BeanPropertyRowMapper bprm=new BeanPropertyRowMapper(CProyectos.class);
							String xsql="select cp.codpro, cp.titulo, cp.resumen, cp.file, cp.notadefensa,  cp.estado, cp.fecha, cp.estadoproy, cp.codtutor, \r\n" + 
									"(select nombre  from cpersonas , cdocentes where cdocentes.codp=cpersonas.codp and  cdocentes.codp=cp.codtutor) as nombredoc,\r\n" + 
									" cp.codarea, car.nombre as nombrearea, cpro.codp, (cpr.nombre || ' ' ||cpr.ap || ' ' ||cpr.am) as nomautor, cpro.codg, cg.nombre as nombregrupo ,cpro.gestion\r\n" + 
									"from cproyectos cp,cdocentes cd, calumnos ca, cpersonas cpr, careas car ,cprogra cpro, cgrupos cg\r\n" + 
									"where cp.codtutor=cd.codp and ca.codp=cpr.codp and ca.codp=cp.codp\r\n" + 
									" and cp.codarea=car.coda and car.coda=cp.codarea and cpro.codp=cp.codp and cpro.codg=cp.codg and cpro.gestion= cp.gestion and cg.codg=cpro.codg and cpro.codg=cp.codg "
									+ "and cp.titulo=?";
							CProyectos pro= (CProyectos) jdbcTemplate.queryForObject(xsql, 
									new Object[]{titulo}, bprm);
							System.out.println(pro);
					return pro;
				}

	//add
		public int setAddProyectos(String titulo, String resumen, String file, float notadefensa,int estado, Date fecha, int estadoproy, int codtutor,int codarea, int codp, int codg, int gestion) {
			String xsql = "insert into cproyectos(  titulo, resumen, file , notadefensa , estado, fecha, estadoproy, codtutor, codarea, codp, codg, gestion)"
					+ "  values(?,?,?,?,?,?,?,?,?,?,?,?)";
			this.jdbcTemplate.update( xsql,titulo,resumen,file ,notadefensa,estado, fecha, estadoproy,codtutor,codarea,codp,codg,gestion);
			CProyectos proNew=new CProyectos();
			proNew=getDatosProtitulo(titulo);
			int codpro1= (int)proNew.getCodpro();
			return codpro1;
		}
//		modificar
		public int setModProyectos(int codpro, String titulo, String resumen, String file, float notadefensa,int estado, Date fecha, int estadoproy, int codtutor,int codarea, int codp, int codg, int gestion) {
			String  xsql="update  cproyectos "+
						"set  titulo=?, resumen=?, file=? , notadefensa=? , estado=?, fecha=?, estadoproy=?, codtutor=?, codarea=?, codp=?, codg=?, gestion=? "
						+" where codpro=?";
			return this.jdbcTemplate.update(xsql,titulo,resumen,file ,notadefensa,estado, fecha, estadoproy,codtutor,codarea,codp,codg,gestion, codpro);
		}
		
		// elimnacion logica
		public int setElimLogica( int xcodpro ) {
			String xsql = "update cproyectos "
						+ "set estado=0 "
						+ "where codpro=?";
			return this.jdbcTemplate.update( xsql, xcodpro );
		}
//		habilitacion
		public int setHabilitarproyectos( int xcodpro) {
			String xsql = "update cproyectos "
						+ "set estado=1 "
						+ "where codpro=?";
			return this.jdbcTemplate.update( xsql, xcodpro );
		}
		public int setfoto( int codpro,String file ) {
			String xsql = "update cproyectos "
						+ "set file=?"
						+ "where codpro=?";
			return this.jdbcTemplate.update( xsql,file, codpro );
		}

		
		
		/// verificar si existe
		public boolean isExists(String nom) {
			String sql = "SELECT count(*) FROM cproyectos WHERE titulo = ?";
			boolean result = false;
			int count = jdbcTemplate.queryForObject(sql, new Object[] { nom }, Integer.class);
			if (count > 0) {
			result = true;
			}
			return result;
			}
		
}
