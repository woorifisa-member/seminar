package com.woorifisa.seminar.controller;

import com.woorifisa.seminar.dto.MemberInfo;
import com.woorifisa.seminar.dto.common.CommonResponse;
import com.woorifisa.seminar.dto.result.ResultInfoResponse;
import com.woorifisa.seminar.dto.result.ResultScoreResponse;
import com.woorifisa.seminar.service.ResultService;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/estimation/result")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping(path = "/classes/{classId}/seminars/{seminarTypeId}")
    public ResponseEntity<CommonResponse<List<ResultInfoResponse>>> getResultInfo(@PathVariable Long classId,
                                                                                  @PathVariable Long seminarTypeId) {
        List<ResultInfoResponse> allResultInfo = resultService.getAllResultInfo(classId, seminarTypeId);
        return CommonResponse.create(HttpStatus.OK, allResultInfo);
    }

    //	@GetMapping(path="/classes/{classId}/{seminarTypeId}/top3")
//	public ResponseEntity<T> getResultTop3Info(@PathVariable Long classId, @PathVariable Long seminarTypeId){
//		
//	}
//	
    @GetMapping(path = "/score/{classId}/{seminarTypeId}/seminars/{subjectId}")
    public ResponseEntity<CommonResponse<ResultScoreResponse>> getResultScore(HttpSession session,
                                                                              @PathVariable Long classId,
                                                                              @PathVariable Long seminarTypeId,
                                                                              @PathVariable Long subjectId) {
        MemberInfo member = (MemberInfo) session.getAttribute(MemberInfo.KEY);
        return CommonResponse.create(HttpStatus.OK, resultService.getScoreByUser(member.getId(), subjectId));
    }
}
