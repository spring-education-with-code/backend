package spring_education.backend.problem_common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_education.backend.mybatis.mapper.ProblemSubmitMapper;
import spring_education.backend.mybatis.model.ProblemSubmit;
import spring_education.backend.problem_common.dto.SubmitResponseDTO;
import spring_education.backend.problem_common.dto.SubmitsResponseDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonProblemService {

    @Autowired
    ProblemSubmitMapper problemSubmitMapper;

    public SubmitsResponseDTO getSubmitProblem(Long userId, Long problemId){
        List<ProblemSubmit> problemSubmitList = problemSubmitMapper.getProblemSubmit(userId, problemId);
        List<SubmitResponseDTO> problemSubmitDTOList = new ArrayList<>();

        for(ProblemSubmit problemSubmit : problemSubmitList){
            long daysDiff = ChronoUnit.DAYS.between(problemSubmit.getCreated_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
            SubmitResponseDTO problemSubmitDTO= SubmitResponseDTO.builder()
                    .submitId(problemSubmit.getProblem_submit_id())
                    .problemId(problemSubmit.getProblem_id())
                    .evaluation_progress(problemSubmit.getEvaluation_progress())
                    .submit_date(
                            ChronoUnit.DAYS.between(
                                    problemSubmit.getCreated_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                    LocalDate.now())
                                    +
                                    "일 전")
                    .build();


            problemSubmitDTOList.add(problemSubmitDTO);
        }

        SubmitsResponseDTO problemSubmitsDTO = SubmitsResponseDTO.builder()
                .totalCount(problemSubmitDTOList.size())
                .problemSubmitDTOList(problemSubmitDTOList)
                .build();

        return problemSubmitsDTO;
    }
}
