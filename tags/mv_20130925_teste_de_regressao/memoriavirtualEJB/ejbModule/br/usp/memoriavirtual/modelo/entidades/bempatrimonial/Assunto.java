/**
 * 
 */
package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author mac
 * 
 */
@Entity
public class Assunto implements Comparable<Assunto>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4539453445771459458L;

	/**
	 * 
	 */
	public Assunto() {
	}

	@Id
	private String assunto;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "assuntos", cascade = CascadeType.MERGE)
	@XmlTransient
	private Set<BemPatrimonial> bens;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	@XmlTransient
	public Set<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(Set<BemPatrimonial> bens) {
		this.bens = bens;
	}

	@Override
	public int compareTo(Assunto outroAssunto) {

		return this.assunto.compareTo(outroAssunto.assunto);
	}

}
