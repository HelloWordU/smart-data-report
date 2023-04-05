package com.rz.smartDataReport.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentNegativeConfigVo {
    private Integer id;

    private Integer normalCount;

    private Integer negetiveCount;

    private Integer noCommentCount;

    private Integer last2HourNormalCount;

    private Integer last2HourNegetiveCount;

    private Integer last2HourNoCommentCount;

    private List<CommentNegativeInfoVo> last2HourData;

    private Integer last5HourNormalCount;

    private Integer last5HourNegetiveCount;

    private Integer last5HourNoCommentCount;

    private List<CommentNegativeInfoVo> last5HourData;

    private Integer last12HourNormalCount;

    private Integer last12HourNegetiveCount;

    private Integer last12HourNoCommentCount;

    private List<CommentNegativeInfoVo> last12HourData;

    private Integer last24HourNormalCount;

    private Integer last24HourNegetiveCount;

    private Integer last24HourNoCommentCount;

    private List<CommentNegativeInfoVo> last24HourData;

    private Integer categoryId;
}
