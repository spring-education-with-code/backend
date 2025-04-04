package spring_education.backend.db_security_utility.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import spring_education.backend.problem_spring.dto.SpringSubmitRequestDTO;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class RedisUtility {

    private final Sha256 sha256;
    private final StringRedisTemplate stringRedisTemplate;

    //1 : 기존 제출이 있음. 정답임
    //0 : 기존 제출이 있음. 오답임
    //-1 : 최초 제출임. 낸 이력이 없음
    public int isThereSameRedisSubmit(SpringSubmitRequestDTO springSubmitRequestDTO){
        Long userId = springSubmitRequestDTO.getUser_id();
        Long problemId = springSubmitRequestDTO.getProblem_id();
        String controller = springSubmitRequestDTO.getController();
        String service = springSubmitRequestDTO.getService();
        String codeHash = sha256.encrypt(controller + service);

        String key = "submit_cache:" + userId + ":" + problemId;

        Object tempCachedResult = stringRedisTemplate.opsForHash().get(key,codeHash);

        if(tempCachedResult != null){
            String cachedResult = tempCachedResult.toString();
            if(cachedResult.equals("1")){
                return 1;
            }else{
                return 0;
            }
        }
        return -1;
    }

    public void insertRedis(Map<String, String> dtoMap, int isCorrect){
        String userId = dtoMap.get("user_id");
        String problemId = dtoMap.get("problem_id");
        String controller = dtoMap.get("controller");
        String service = dtoMap.get("service");
        String codeHash = sha256.encrypt(controller + service);

        String key = "submit_cache:" + userId + ":" + problemId;
        stringRedisTemplate.opsForHash().put(key, codeHash, String.valueOf(isCorrect));
    }
}
