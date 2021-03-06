package com.cg.onlinevotingsystem.cooperativesocietyms.service;

import java.util.List;

import com.cg.onlinevotingsystem.cooperativesocietyms.entities.CooperativeSociety;

public interface ICooperativeSocietyService {

	CooperativeSociety addSocietyDetails(CooperativeSociety society);
	CooperativeSociety updateSociety(CooperativeSociety society);
		CooperativeSociety deleteSociety(int societyId);
	List<CooperativeSociety>viewSocietyList();
	CooperativeSociety viewSocietyById(int societyId);
	
}
