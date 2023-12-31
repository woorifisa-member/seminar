package com.woorifisa.seminar.controller;

import static org.springframework.http.HttpStatus.*;

import com.woorifisa.seminar.dto.MemberInfo;
import com.woorifisa.seminar.dto.common.CommonResponse;
import com.woorifisa.seminar.dto.estimation.EstimationRequest;
import com.woorifisa.seminar.dto.estimation.EstimationResponse;
import com.woorifisa.seminar.dto.estimation.otherEstimation.request.EstimationRequestList;
import com.woorifisa.seminar.service.OtherEstimationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estimation/other")
@RequiredArgsConstructor
public class OtherEstimationController {

    private final OtherEstimationService otherEstimationService;

    @ApiOperation(value="타팀 평가 항목 리스트", notes="타팀 평가 항목 리스트")
    @GetMapping
    public ResponseEntity<CommonResponse<List<EstimationResponse>>> retrieveOtherEstimationItems(HttpSession session) {

        MemberInfo member = (MemberInfo) session.getAttribute(MemberInfo.KEY);
        List<EstimationResponse> estimationItems = otherEstimationService.findEstimationItems(member.getRole());

        return CommonResponse.create(OK, estimationItems);
    }

    @ApiOperation(value="타팀 평가", notes="타팀 평가")
    @PostMapping("/subjects/{subjectNo}")
    public ResponseEntity<CommonResponse<List<EstimationResponse>>> estimateOtherTeam(
        HttpSession session, @PathVariable Long subjectNo,
        @ApiParam(value = "평가 항목 아이디와 점수", required = true)
        @Validated @RequestBody List<EstimationRequest> estimations) {

        MemberInfo member = (MemberInfo) session.getAttribute(MemberInfo.KEY);
        List<EstimationResponse> estimationResponses =
            otherEstimationService.estimateOtherTeam(subjectNo, member, estimations);


        return CommonResponse.create(CREATED, estimationResponses);
    }

    @PostMapping("/subjects/{subjectNo}/teacher")
    public ResponseEntity<CommonResponse<List<EstimationResponse>>> estimateByTeacher(
        HttpSession session, @PathVariable Long subjectNo,
        @Validated @RequestBody List<EstimationRequest> estimations) {

        MemberInfo member = (MemberInfo) session.getAttribute("TEACHER");
        List<EstimationResponse> estimationResponses =
            otherEstimationService.estimateOtherTeam(subjectNo, member, estimations);

        return CommonResponse.create(CREATED, estimationResponses);
    }

    @PostMapping("/subjects/{subjectNo}/mentor")
    public ResponseEntity<CommonResponse<List<EstimationResponse>>> estimateByMentor(
        HttpSession session, @PathVariable Long subjectNo,
        @Validated @RequestBody List<EstimationRequest> estimations) {

        MemberInfo member = (MemberInfo) session.getAttribute("MENTOR");
        List<EstimationResponse> estimationResponses =
            otherEstimationService.estimateOtherTeam(subjectNo, member, estimations);

        return CommonResponse.create(CREATED, estimationResponses);
    }

}
