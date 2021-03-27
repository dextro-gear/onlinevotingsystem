package com.cg.onlinevotingsystem.cooperativesocietyms.service;

import com.cg.onlinevotingsystem.cooperativesocietyms.dao.ICooperativeSocietyDaoRepository;
import com.cg.onlinevotingsystem.cooperativesocietyms.entities.CooperativeSociety;
import com.cg.onlinevotingsystem.cooperativesocietyms.exceptions.CooperativeSocietyNotFoundException;
import com.cg.onlinevotingsystem.cooperativesocietyms.exceptions.CooperativeSocietyCannotBeNullException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CooperativeSocietyServiceImpl implements ICooperativeSocietyService{

    @Autowired
    ICooperativeSocietyDaoRepository cooperativeSocietyRepository;


    @Override
    public CooperativeSociety addSocietyDetails(String societyName, String headOfSociety, String village, String mandal, String district, String pincode) {
        CooperativeSociety s1 = new CooperativeSociety(societyName, headOfSociety, village, mandal, district, pincode);
        return cooperativeSocietyRepository.save(s1) ;
    }

    @Override
    public CooperativeSociety addSocietyDetails(CooperativeSociety society){
        try{
            return cooperativeSocietyRepository.save(society);
        }catch (NullPointerException e){
            throw new CooperativeSocietyCannotBeNullException("Society cannot be null. Null entities cannot be saved.");
        }
    }

    @Override
    public CooperativeSociety updateSocietyDetails(int societyId,String societyName, String headOfSociety, String village, String mandal, String district, String pincode) {
         Optional<CooperativeSociety> s1 = cooperativeSocietyRepository.findById(societyId);
         CooperativeSociety s2 = s1.get();

         if(s1.isPresent()) {
             s2.setHeadOfSociety(headOfSociety);
             s2.setDistrict(district);
             s2.setMandal(mandal);
             s2.setVillage(village);
             s2.setPincode(pincode);
             s2.setSocietyName(societyName);
         } else
             throw new CooperativeSocietyNotFoundException("CooperativeSociety with id:" + societyId + " was not found in the DB");

        return cooperativeSocietyRepository.save(s2);
    }

    @Override
    public CooperativeSociety deleteSociety(int societyId) {
        Optional<CooperativeSociety> cooperativeSocietyOptional = cooperativeSocietyRepository.findById(societyId);
        if(cooperativeSocietyOptional.isPresent())
            cooperativeSocietyRepository.delete(cooperativeSocietyOptional.get());
        else
            throw new CooperativeSocietyNotFoundException("CooperativeSociety with id:" + societyId + " was not found in the DB");

        return cooperativeSocietyOptional.get();
    }

    @Override
    public List<CooperativeSociety> viewSocietyList() {
        return cooperativeSocietyRepository.findAll();
    }

    @Override
    public CooperativeSociety viewSocietyById(int societyId) {
        Optional<CooperativeSociety> cooperativeSocietyOptional = cooperativeSocietyRepository.findById(societyId);
        if (cooperativeSocietyOptional.isPresent())
            return cooperativeSocietyOptional.get() ;
        else
            throw new CooperativeSocietyNotFoundException("CooperativeSociety with id:" + societyId + " was not found in the DB");
    }

}
