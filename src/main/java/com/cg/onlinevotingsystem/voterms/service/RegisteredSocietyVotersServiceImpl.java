package com.cg.onlinevotingsystem.voterms.service;

import java.util.List;
import com.cg.onlinevotingsystem.voterms.entities.RegisteredSocietyVoters;

public class RegisteredSocietyVotersServiceImpl implements IRegisteredSocietyVotersService {

    @Override
    public int voterRegistration(RegisteredSocietyVoters voter) {
        return 0;
    }

    @Override
    public int updateRegisteredVoterDetails(RegisteredSocietyVoters voter) {
        return 0;
    }

    @Override
    public int deleteRegisteredVoter(int voterId) {
        return 0;
    }

    @Override
    public List<RegisteredSocietyVoters> viewRegisteredVoterList() {
        return null;
    }

    @Override
    public RegisteredSocietyVoters searchByVoterID(int voterId) {
        return null;
    }

    @Override
    public RegisteredSocietyVoters loginValidate(String userid, String password) {
        return null;
    }


}