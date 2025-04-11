package spring_education.backend.problem_common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring_education.backend.db_security_utility.annotation.warmup.WarmUp;
import spring_education.backend.db_security_utility.mybatis.mapper.ProblemMapper;
import spring_education.backend.db_security_utility.mybatis.mapper.ProblemSubmitMapper;
import spring_education.backend.db_security_utility.mybatis.model.*;
import spring_education.backend.problem_common.dto.problem.ProblemConstraintDTO;
import spring_education.backend.problem_common.dto.problem.ProblemContentDTO;
import spring_education.backend.problem_common.dto.problem.ProblemExampleDTO;
import spring_education.backend.problem_common.dto.problem.ProblemResponseDTO;
import spring_education.backend.problem_common.dto.submit.SubmitResponseDTO;
import spring_education.backend.problem_common.dto.submit.SubmitsResponseDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonProblemService {

    private final ProblemSubmitMapper problemSubmitMapper;
    private final ProblemMapper problemMapper;

    @WarmUp(args = {"1","1","1"})
    public SubmitsResponseDTO getSubmitProblem(Long userId, Long problemId, int pageNum){

        long startTime = System.currentTimeMillis(); // 코드 시작 시간

        PageHelper.startPage(pageNum, 20);
        List<ProblemSubmit> problemSubmitList = problemSubmitMapper.getProblemSubmit(userId, problemId);
        PageInfo<ProblemSubmit> pageInfo = new PageInfo<>(problemSubmitList);

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
                .totalCount((int) pageInfo.getTotal())
                .totalPages((int) (pageInfo.getTotal() / 20) + 1)
                .submits(problemSubmitDTOList)
                .build();

        long endTime = System.currentTimeMillis(); // 코드 끝난 시간

        long durationTimeSec = endTime - startTime;
        log.info(durationTimeSec + "ms 소요 - /submit/ api"); // 밀리세컨드 출력

        return problemSubmitsDTO;
    }

    @WarmUp(args = {"1"})
    public ProblemResponseDTO getProblem(Long problemId){
        Problem problem = problemMapper.getProblem(problemId);
        List<ProblemContent> problemContents = problemMapper.getProblemContent(problemId);
        List<ProblemConstraint> problemConstraints = problemMapper.getProblemConstraint(problemId);
        List<ProblemExample> problemExamples = problemMapper.getProblemExample(problemId);

        List<ProblemContentDTO> problemContentDTOs = new ArrayList<>();
        List<ProblemConstraintDTO> problemConstraintDTOs = new ArrayList<>();
        List<ProblemExampleDTO> problemExampleDTOs = new ArrayList<>();

        for(int i=0; i<problemContents.size(); i++){
            problemContentDTOs.add(new ProblemContentDTO(problemContents.get(i).getContent_type(), problemContents.get(i).getContent()));
        }

        for(int i=0; i<problemConstraints.size(); i++){
            problemConstraintDTOs.add(new ProblemConstraintDTO(problemConstraints.get(i).getConstraint_text()));
        }

        for(int i=0; i<problemExamples.size(); i++){
            problemExampleDTOs.add(new ProblemExampleDTO(problemExamples.get(i).getInput_example(),
                    problemExamples.get(i).getOutput_example(), problemExamples.get(i).getDescription()));
        }

        return ProblemResponseDTO.builder()
                .title(problem.getTitle())
                .level(problem.getLevel())
                .content_length(problemContentDTOs.size())
                .content(problemContentDTOs)
                .constraint_length(problemConstraintDTOs.size())
                .constraint(problemConstraintDTOs)
                .example_length(problemExampleDTOs.size())
                .example(problemExampleDTOs)
                .build();
    }
}
