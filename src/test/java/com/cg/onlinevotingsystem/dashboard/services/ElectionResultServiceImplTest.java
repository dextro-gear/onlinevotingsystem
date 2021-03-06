package com.cg.onlinevotingsystem.dashboard.services;

import com.cg.onlinevotingsystem.dashboard.dao.IElectionResultRepository;
import com.cg.onlinevotingsystem.dashboard.entities.ElectionResult;
import com.cg.onlinevotingsystem.dashboard.exceptions.InvalidResultException;
import com.cg.onlinevotingsystem.dashboard.exceptions.ResultNotFoundException;
import com.cg.onlinevotingsystem.nominatedcandidatems.entities.NominatedCandidates;
import com.cg.onlinevotingsystem.nominatedcandidatems.services.INominatedCandidatesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class ElectionResultServiceImplTest {

    @Mock
    IElectionResultRepository electionResultRepository;

    @Mock
    INominatedCandidatesService candidatesService;

    @Spy
    @InjectMocks
    ElectionResultServiceImpl electionResultService;

    /**
     * Scenario: ElectionResult object is saved in the DB successfully.
     * Input: ElectionResult object
     * Expectations : Verifying  electionResultRepository.save() is called; Saved instance is returned
     */
    @Test
    void addElectionResult_1() {
        ElectionResult resultArg = mock(ElectionResult.class);
        ElectionResult saved = mock(ElectionResult.class);

        // Stubbing save, validate methods
        when(electionResultRepository.save(resultArg)).thenReturn(saved);
        doNothing().when(electionResultService).validateResult(resultArg);

        // Verify the saved instance is returned; save() is being called
        ElectionResult result = electionResultService.addElectionResult(resultArg);
        Assertions.assertSame(saved, result);
        verify(electionResultRepository).save(resultArg);
    }


    /**
     * Scenario: Validation for object fails
     * Input: ElectionResult object
     * Expectations : Verifying  electionResultRepository.save() is never called;
     *                Verifying proper exception is thrown
     */
    @Test
    void testAddElectionResult_2() {
        ElectionResult resultArg = mock(ElectionResult.class);

        // Verify the correct exception is thrown; save() method is NEVER called
        Executable executable = () -> electionResultService.addElectionResult(resultArg);
        Assertions.assertThrows(InvalidResultException.class, executable);
        verify(electionResultRepository, never()).save(resultArg);

    }



    /**
     * Scenario: Retrieve all ElectionResult Objects
     * Expectations : Verifying electionResultRepository.findAll() is called; List is returned
     */
    @Test
    void viewElectionResultList() {
        List<ElectionResult> list = mock(List.class);

        // Stubbing the findAll() method
        when(electionResultRepository.findAll()).thenReturn(list);

        // Verify a list is returned; findAll() is called
        List<ElectionResult> result = electionResultService.viewElectionResultList();
        Assertions.assertSame(list, result);
        verify(electionResultRepository).findAll();
    }



    /**
     * Scenario: Retrieval of an ElectionResult object by candidateID is successful.
     * Input: candidateID
     * Expectations : Verifying electionResultRepository.indElectionResultByCandidate is called;
     *                Fetched instance is returned
     */
    @Test
    public void viewCandidatewiseResult_1(){
        int candidateId=6367;
        NominatedCandidates candidate=mock(NominatedCandidates.class);
        ElectionResult fetchedResult=mock(ElectionResult.class);

        // Stubbing searchByCandidateID(), findElectionResultByCandidate()
        when(candidatesService.searchByCandidateID(candidateId)).thenReturn(candidate);
        when(electionResultRepository.findElectionResultByCandidate(candidate)).thenReturn(fetchedResult);

        // Verify the saved instance is returned; findElectionResultByCandidate() is called
        ElectionResult result=electionResultService.viewCandidatewiseResult(candidateId);
        Assertions.assertSame(fetchedResult,result);
        verify(electionResultRepository).findElectionResultByCandidate(candidate);
    }



    /**
     * Scenario: ElectionResult record is NOT found in the DB
     * Input: candidateID
     * Expectations : Verifying electionResultRepository.findElectionResultByCandidate() is called;
     *                Verifying ResultNotFoundException is thrown
     */
    @Test
    public void viewCandidatewiseResult_2(){
        int candidateId=6367;
        NominatedCandidates candidate=mock(NominatedCandidates.class);

        // Stubbing searchByCandidateID(), findElectionResultByCandidate()
        when(candidatesService.searchByCandidateID(candidateId)).thenReturn(candidate);
        when(electionResultRepository.findElectionResultByCandidate(candidate)).thenReturn(null);

        // Verify the proper exception is thrown; findElectionResultByCandidate() is called
        Executable executable=()->electionResultService.viewCandidatewiseResult(candidateId);
        Assertions.assertThrows(ResultNotFoundException.class,executable);
        verify(electionResultRepository).findElectionResultByCandidate(candidate);
    }


}