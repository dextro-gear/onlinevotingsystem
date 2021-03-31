package com.cg.onlinevotingsystem.cooperativesocietyms.entities;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class CooperativeSociety {

	@GeneratedValue
	@Id
	private int societyId;

	private String societyName;
	private String headOfSociety;
	private String village;
	private String mandal;
	private String district;
	private String pincode;

	public CooperativeSociety() {
		
	}
	

	public CooperativeSociety( String societyName, String headOfSociety, String village, String mandal,
			String district, String pincode) {
		
		super();

		this.societyName = societyName;
		this.headOfSociety = headOfSociety;
		this.village = village;
		this.mandal = mandal;
		this.district = district;
		this.pincode = pincode;
	}



	public int getSocietyId() {
		return societyId;
	}


	public String getSocietyName() {
		return societyName;
	}

	public void setSocietyName(String societyName) {
		this.societyName = societyName;
	}

	public String getHeadOfSociety() {
		return headOfSociety;
	}

	public void setHeadOfSociety(String headOfSociety) {
		this.headOfSociety = headOfSociety;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getMandal() {
		return mandal;
	}

	public void setMandal(String mandal) {
		this.mandal = mandal;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "CooperativeSociety{" +
				"societyId=" + societyId +
				", societyName='" + societyName + '\'' +
				", headOfSociety='" + headOfSociety + '\'' +
				", village='" + village + '\'' +
				", mandal='" + mandal + '\'' +
				", district='" + district + '\'' +
				", pincode='" + pincode + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CooperativeSociety)) return false;
		CooperativeSociety society = (CooperativeSociety) o;
		return societyId == society.societyId && societyName.equals(society.societyName) && headOfSociety.equals(society.headOfSociety) && village.equals(society.village) && mandal.equals(society.mandal) && district.equals(society.district) && pincode.equals(society.pincode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(societyId, societyName, headOfSociety, village, mandal, district, pincode);
	}
}
