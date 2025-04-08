package spring_education.backend.problem_common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring_education.backend.db_security_utility.annotation.warmup.WarmUp;
import spring_education.backend.db_security_utility.mybatis.mapper.ProblemSubmitMapper;
import spring_education.backend.db_security_utility.mybatis.model.ProblemSubmit;
import spring_education.backend.problem_common.dto.SubmitResponseDTO;
import spring_education.backend.problem_common.dto.SubmitsResponseDTO;

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
}
