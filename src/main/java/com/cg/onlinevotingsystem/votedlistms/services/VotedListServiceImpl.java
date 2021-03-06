package com.cg.onlinevotingsystem.votedlistms.services;

import com.cg.onlinevotingsystem.cooperativesocietyms.entities.CooperativeSociety;
import com.cg.onlinevotingsystem.nominatedcandidatems.entities.NominatedCandidates;
import com.cg.onlinevotingsystem.votedlistms.dao.IVotedListRepository;
import com.cg.onlinevotingsystem.votedlistms.entities.VotedList;
import com.cg.onlinevotingsystem.votedlistms.exceptions.InvalidVoteException;
import com.cg.onlinevotingsystem.votedlistms.exceptions.VoteNotFoundException;
import com.cg.onlinevotingsystem.voterms.entities.RegisteredSocietyVoters;
import com.cg.onlinevotingsystem.voterms.service.IRegisteredSocietyVotersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VotedListServiceImpl implements IVotedListService {

    @Autowired
    private IVotedListRepository votedListRepository;

    @Autowired
    private IRegisteredSocietyVotersService votersService;

    @Autowired
    private IRegisteredSocietyVotersService voterService;

    private static final Logger LOG = LoggerFactory.getLogger(VotedListServiceImpl.class);

    /**
     * This method saves a VotedList record to the database. Saving a record
     * to the database is considered as "casting a vote". The appropriate flags
     * are set in the voter object and is updated.
     *
     * Note: The voter and the candidate must already be registered before
     * a vote can be cast
     *
     * @param voter     The RegisteredSocietyVoter object pertaining to the voter who is casting a vote
     * @param candidate The NominatedCandidate object pertaining to the candidate for whom the vote is being cast
     * @param society   The CooperativeSociety object pertaining to society to which the voter belongs to.
     * @return The saved instance of the VotedList object
     */
    @Override
    public VotedList castVotedList(RegisteredSocietyVoters voter, NominatedCandidates candidate, CooperativeSociety society) {
        validateVote(voter, candidate, society);
        VotedList vote = newVote(voter, candidate, society);
        vote.setPollingDateTime(currentDate());
        voter.setCastedVote(true);
        votersService.updateRegisteredVoterDetails(voter);
        LOG.info("Casted a vote for candidate {}" , candidate.getCandidateID());
        return votedListRepository.save(vote);
    }


    /**
     * This method returns a new instance of VotedList object
     *
     * @param voter     The RegisteredSocietyVoter object pertaining to the voter who is casting a vote
     * @param candidate The NominatedCandidate object pertaining to the candidate for whom the vote is being cast
     * @param society   The CooperativeSociety object pertaining to society to which the voter belongs to.
     * @return The saved instance of the VotedList object
     */
    VotedList newVote(RegisteredSocietyVoters voter, NominatedCandidates candidate, CooperativeSociety society) {
        return new VotedList(voter, candidate, society);
    }


    /**
     * This method returns the current date-time from the system clock in the default time-zone.
     *
     * @return An instance of LocalDateTime object with the current date-time
     */
    LocalDateTime currentDate() {
        return LocalDateTime.now();
    }


    /**
     * This method updates the existing record of a VotedList object in the DB,
     * and throws an exception if the record doesn't exist.
     *
     * @param votedListID The ID of the candidate record which must be updated
     * @param voter       The updated voter object
     * @param candidate   The updated candidate object
     * @param society     The updated society object
     * @return The instance of the updated VotedList object
     * @throws VoteNotFoundException
     */
    @Override
    public VotedList updateVotedListDetails(int votedListID, RegisteredSocietyVoters voter, NominatedCandidates candidate, CooperativeSociety society) {
        validateVote(voter, candidate, society);
        Optional<VotedList> votedListOptional = votedListRepository.findById(votedListID);
        if (!votedListOptional.isPresent()) {
            throw new VoteNotFoundException("VotedList with id:" + votedListID + " was not found in the DB");
        }
        VotedList vote = votedListOptional.get();
        vote.setVoter(voter);
        vote.setCandidate(candidate);
        vote.setSociety(society);
        return votedListRepository.save(vote);

    }


    /**
     * This method deletes an existing record of VotedList from the database,
     * and throws an exception if the record doesn't exist.
     *
     * @param votedListID The ID of the record to be deleted
     * @return The instance of the object which has been deleted
     * @throws VoteNotFoundException
     */
    @Override
    public VotedList deletedVotedListDetails(int votedListID) {
        Optional<VotedList> votedListOptional = votedListRepository.findById(votedListID);
        if (votedListOptional.isPresent()) {
            votedListRepository.deleteById(votedListID);
            LOG.info("Deleted vote with ID: {}" , votedListOptional.get().getId());
            return votedListOptional.get();
        }

        throw new VoteNotFoundException("VotedList with id:" + votedListID + " was not found in the DB");
    }


    /**
     * This method retrieves all the records of VotedList in the database
     *
     * @return List of VotedList objects
     */
    @Override
    public List<VotedList> viewVotedList() {
        return votedListRepository.findAll();
    }


    /**
     * This methods retrieves the record of a particular VotedList by voterID,
     * and throws an exception if the record doesn't exist.
     *
     * @param voterId The ID of the record to be retrieved
     * @return The instance of the retrieved VotedList object
     * @throws VoteNotFoundException
     */
    @Override
    public VotedList searchByVoterId(int voterId) {
        VotedList votedList = votedListRepository.findByVoter_Id(voterId);
        if (votedList != null) {
            return votedList;
        }
        throw new VoteNotFoundException("VotedList record with voter:" + voterId + " was not found in the DB");
    }

    @Override
    public VotedList searchByVoterCardId(String cardId) {
        RegisteredSocietyVoters voter = voterService.findByVoterCardId(cardId);
        boolean voteExists = votedListRepository.existsByVoter(voter);
        if (!voteExists) {
            throw new VoteNotFoundException("vote not found for voter card=" + cardId);
        }
        VotedList vote = votedListRepository.findVotedListByVoter(voter);
        return vote;
    }


    /**
     * This methods retrieves the record of a particular VotedList by candidateID,
     * and throws an exception if the record doesn't exist.
     *
     * @param candidateId The ID of the record to be retrieved
     * @return The instance of the retrieved VotedList object
     * @throws VoteNotFoundException
     */
    @Override
    public List<VotedList> searchByNominatedCandidateId(int candidateId) {
        List<VotedList> votes = votedListRepository.findByCandidate_CandidateID(candidateId);
        if (votes.size() > 0) {
            return votes;
        }

        throw new VoteNotFoundException("VotedList records for Candidate:" + candidateId + " were not found in the DB");

    }

    /**
     * This method validates the parameters passed to it. Throws an
     * exception if any of the parameters are invalid.
     *
     * @param voter     The voter object to be validated
     * @param candidate The candidate object to be validated
     * @param society   The society object to be validated
     * @throws InvalidVoteException
     */
    void validateVote(RegisteredSocietyVoters voter, NominatedCandidates candidate, CooperativeSociety society) {
        if (voter == null) {
            throw new InvalidVoteException("voter can't be null");
        }
        if (candidate == null) {
            throw new InvalidVoteException("candidate can't be null");
        }
        if (society == null) {
            throw new InvalidVoteException("voter can't be null");
        }

    }

}
