package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.swing.JOptionPane;

import view.Cargos;
import view.Filiais;
import view.Listas;

@Entity
@Table(name="funcionarios")
public class Funcionario {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String cpf;
	private String nome;
	private String senha;
	private String cargo;
	private String cargaHoraria;
	private int fk_filiais_id;
	private boolean seg;
	private boolean ter;
	private boolean qua;
	private boolean qui;
	private boolean sex;
	private boolean sab;
	private boolean dom;
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public int getFk_filiais_id() {
		return fk_filiais_id;
	}

	public void setFk_filiais_id(int fk_filiais_id) {
		this.fk_filiais_id = fk_filiais_id;
	}
	
	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
	public boolean isSeg() {
		return seg;
	}
	
	public void setSeg(boolean seg) {
		this.seg = seg;
	}
	
	public boolean isTer() {
		return ter;
	}
	
	public void setTer(boolean ter) {
		this.ter = ter;
	}
	
	public boolean isQua() {
		return qua;
	}
	
	public void setQua(boolean qua) {
		this.qua = qua;
	}
	
	public boolean isQui() {
		return qui;
	}
	
	public void setQui(boolean qui) {
		this.qui = qui;
	}
	
	public boolean isSex() {
		return sex;
	}
	
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	public boolean isSab() {
		return sab;
	}
	
	public void setSab(boolean sab) {
		this.sab = sab;
	}
	
	public boolean isDom() {
		return dom;
	}
	
	public void setDom(boolean dom) {
		this.dom = dom;
	}
	
	public void cadastrarFuncionario(String nome, String cpf, String senha, String cargo, int filial,
			String carga_horaria, boolean seg, boolean ter, boolean qua, boolean qui, boolean sex, boolean sab, boolean dom) {

		EntityManager con = new ConnectionFactory().getConnection();
		
		this.setNome(nome);
		this.setCpf(cpf);
		this.setSenha(senha);
		this.setCargo(cargo);
		this.setFk_filiais_id(filial);
		this.setSeg(seg);
		this.setCargaHoraria(carga_horaria);
		this.setTer(ter);
		this.setQua(qua);
		this.setQui(qui);
		this.setSex(sex);
		this.setSab(sab);
		this.setDom(dom);
		
		try {
			con.getTransaction().begin();
			con.persist(this);
			con.getTransaction().commit();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um problema ao cadastrar o funcionário. Tente novamente.\nErro: "+ e, "Erro", JOptionPane.ERROR_MESSAGE);
			con.getTransaction().rollback();
		}
		finally {
			con.close();
		}
		
	}
	
	public Filial cadastrarFilial(String nome, String cidade, String uf) {
		EntityManager con = new ConnectionFactory().getConnection();
		Filial filial = new Filial();
		
		filial.setNome(nome);
		filial.setCidade(cidade);
		filial.setEstado(uf);	
		
		try {
			con.getTransaction().begin();
			con.persist(filial);
			con.getTransaction().commit();
			
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: "+ e, "Erro", JOptionPane.ERROR_MESSAGE);
		}
		finally {
			con.close();
		}
		
		return filial;
		
	}
	
	public void alterarDadosFuncionario(String novoNome, String cpfFuncionario, String novaSenha, String novoCargo, int novaFilial, String carga, boolean seg, boolean ter, boolean qua, boolean qui, boolean sex, boolean sab, boolean dom) {
		EntityManager con = new ConnectionFactory().getConnection();
		
		this.nome = novoNome;
		this.cpf = cpfFuncionario;
		this.senha = novaSenha;
		this.cargo = novoCargo;
		this.fk_filiais_id = novaFilial;
		this.cargaHoraria = carga;
		this.seg = seg;
		this.ter = ter;
		this.qua = qua;
		this.qui = qui;
		this.sex = sex;
		this.sab = sab;
		this.dom = dom;
		
		try {
			con.getTransaction().begin();
			con.merge(this);
			con.getTransaction().commit();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: "+ e, "Erro", JOptionPane.ERROR_MESSAGE);
			con.getTransaction().rollback();
		}
		finally {
			con.close();
		}
		
	}
	
	public List<Funcionario> consultarTodosFuncionarios(){
		EntityManager con = new ConnectionFactory().getConnection();
		List<Funcionario> funcionarios = null;

		try {
			funcionarios = con.createQuery("from Funcionario f").getResultList();
		}
		catch (Exception e) {
			System.err.println(e);
		}
		finally {
			con.close();
		}

		return funcionarios;
		
	}

	public List<Listas> listarFuncionarios(){
		List<Listas> lista = new ArrayList<>();
		for (Funcionario f: this.consultarTodosFuncionarios()) {
			Listas nome = new Listas(f.cpf, f.nome);
			lista.add(nome);
		}
		return lista;
	}
	
	public List<Cargos> listarCargos(){
		
		List<Cargos> cargos = new ArrayList<>();
		
		Cargos cargo1 = new Cargos(1, "Motorista");
		cargos.add(cargo1);
		Cargos cargo2 = new Cargos(2, "Supervisor");
		cargos.add(cargo2);
		Cargos cargo3 = new Cargos(3, "Administrador");
		cargos.add(cargo3);
		
		return cargos;
	}
	
	public List<Filial> consultarTodasFiliais(){
		EntityManager con = new ConnectionFactory().getConnection();
		List<Filial> filiais = null;
		try {
			filiais = con.createQuery("from Filial f").getResultList();
		}
		catch (Exception e) {
			System.err.println(e);
		}
		finally {
			con.close();
		}

		return filiais;
	}
	
	public List<Filiais> listarFiliais(){	
		List<Filiais> comboBox = new ArrayList<>();
		for (Filial f: this.consultarTodasFiliais()) {
			Filiais filiais = new Filiais(f.getId(), f.getNome());
			comboBox.add(filiais);
		}
		
		return comboBox;
		
	}
	
	public Funcionario removerFuncionario(String cpf){
		EntityManager con = new ConnectionFactory().getConnection();

		Funcionario funcionario  = null;

		try {
			funcionario = con.find(model.Funcionario.class, cpf);		

			con.getTransaction().begin();
			con.remove(funcionario);
			con.getTransaction().commit();
		}
		catch (Exception e) {
			System.err.println(e);
			con.getTransaction().rollback();
		}
		finally {
			con.close();
		}

		return funcionario;
	}
	
	public Funcionario encontrarFuncionario(String cpf){
		EntityManager con = new ConnectionFactory().getConnection();

		Funcionario funcionario  = null;

		try {
			funcionario = con.find(model.Funcionario.class, cpf);		
		}
		catch (Exception e) {
			System.err.println(e);
			con.getTransaction().rollback();
		}
		finally {
			con.close();
		}

		return funcionario;
	}
}