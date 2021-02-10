package spring.proyect.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


import spring.proyect.model.CDicta;
import spring.proyect.model.CGrupos;
import spring.proyect.model.CPersonas;

@Service
public class DictaManger {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate = new JdbcTemplate(dataSource2);
	}

	public List<CDicta> listaDicta() {

		String xsql = "  select   p.codp,(p.nombre || ' ' || case when p.ap is null then ' ' else p.ap end  || ' ' ||case when p.am is null then ' ' else p.am end) as nombre,g.codg, g.nombre as grupo, d.gestion, d.estado " + 
				" from dicta d, cpersonas p, cdocentes cd, cgrupos g where d.codp=p.codp  and p.codp=cd.codp and g.codg=d.codg  ";
		List est = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CDicta dic = new CDicta();
				dic.setCodp(rs.getInt("codp"));
				dic.setNombre(rs.getString("nombre"));
				dic.setCodg(rs.getInt("codg"));
				dic.setGrupo(rs.getString("grupo"));
				dic.setGestion(rs.getInt("gestion"));
				dic.setEstado(rs.getInt("estado"));
				return dic;
			}
		}, new Object[] {});

		return est;
	}

	public List<CDicta> listaDicta(int xest1, int xest2) {
		String xsql = "select codp,codg, gestion,estado "
					+ " from dicta "
					+ "	where estado between ? and ?  ";
		List est = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CDicta dic = new CDicta();
				dic.setCodp(rs.getInt("codp"));
				dic.setCodg(rs.getInt("codg"));
				dic.setGestion(rs.getInt("gestion"));
				dic.setEstado(rs.getInt("estado"));
				return dic;
			}
		}, new Object[] { xest1, xest2 });

		return est;

	}
	
//	adicionar
	
	public int setAddDicta(int codp, int codg, int gestion, int estado) {
		String xsql = "insert into dicta( codp, codg, gestion, estado )"
				+ "  values(?,?,?,?)";
		return	this.jdbcTemplate.update( xsql, codp, codg,gestion, estado);
	}
//	modificar solo
	public int setModDicta(int codp, int codg, int gestion, int estado, int codp2, int codg2, int gestion2) {
		String  xsql="update  dicta "+
					"set codp=?, codg=?, gestion=?, estado=? "
					+" where codp=? and codg=? and gestion=?";
		return this.jdbcTemplate.update(xsql, codp,codg,gestion, estado ,codp2,codg2,gestion2);
	}
//	modificar solo
	public int setModDictaProy(int codp, int codg, int gestion, int estado, int codp2, int codg2, int gestion2) {
		String  xsql="update  dicta "+
					"set codp=?, codg=?, gestion=?, estado=? "
					+" where codp=? and codg=? and gestion=?;";
		return this.jdbcTemplate.update(xsql, codp,codg,gestion, estado ,codp2,codg2,gestion2);
	}
	
// elimnacion
	public int setDelDicta( int codp,int codg, int gestion ) {
		String xsql = "delete from dicta where codp=? and codg=? and gestion=? ;"
				+ " ";
		return this.jdbcTemplate.update(xsql, codp, codg,gestion );
	}
///
	public List< CPersonas> listaPersona(){		
		String xsql=" select p.codp, p.nombre, "
				+ "case when p.ap is null then ' ' else p.ap end as ap, "
				+ "case when p.am is null then ' ' else p.am end as am, "
				+ " p.estado, p.fnac, p.genero, p.direc, p.celular, p.foto, p.ecivil, "
				+ " (select login from cdatos  where p.codp=cdatos.codp) as login "
				+ "from cdocentes d, cpersonas p where d.codp=p.codp" ;
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

	public List<CDicta> busquedaGesDcita(int gestion) {
		String xsql = " select p.codp, (p.nombre || ' ' ||p.ap || ' ' ||p.am) as nombre,g.codg, g.nombre as grupo, d.gestion, d.estado " 
				+ " from dicta d, cpersonas p, cdocentes cd, cgrupos g where d.codp=p.codp  and p.codp=cd.codp and g.codg=d.codg and d.gestion=? ";
	List est = this.jdbcTemplate.query(xsql, new RowMapper() {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CDicta dic = new CDicta();
			dic.setCodp(rs.getInt("codp"));
			dic.setNombre(rs.getString("nombre"));
			dic.setCodg(rs.getInt("codg"));
			dic.setGrupo(rs.getString("grupo"));
			dic.setGestion(rs.getInt("gestion"));
			dic.setEstado(rs.getInt("estado"));
			return dic;
		}
	}, new Object[] {gestion});

	return est;
	}

	public boolean isExists(int codp, int codg, int gestion) {
		// TODO Auto-generated method stub
		String sql = "SELECT count(*) FROM dicta WHERE codp = ? and codg = ? and gestion = ?";
		boolean result = false;
		int count = jdbcTemplate.queryForObject(sql, new Object[] { codp, codg, gestion }, Integer.class);
		if (count > 0) {
		result = true;
		}
		return result;
	}

}
