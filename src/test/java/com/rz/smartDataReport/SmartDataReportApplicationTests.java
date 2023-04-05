package com.rz.smartDataReport;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.rz.smartDataReport.pojo.vo.MonitoringPlantPageDataVo;
import com.rz.smartDataReport.service.IMonitoringPlantformStatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SmartDataReportApplicationTests {

    @Resource
    private IMonitoringPlantformStatisticService iMonitoringPlantformStatisticService;
    @Test
    void contextLoads() {
    }

    @Test
    void  getPageDataByCategoryIdTest()
    {
        MonitoringPlantPageDataVo pageDataByCategoryId = iMonitoringPlantformStatisticService.getPageDataByCategoryId(1);
        String info = "";
    }


    @Test
    void demoMethod()
    {
        String info ="abcba";
        char[] inArray = info.toCharArray();
        boolean[][] dp = new boolean[info.length()][info.length()];
        int[] best = new int[2];
        int maxlength = 1;
        for(int i= info.length();i>=0;i--)
        {
            for(int j=i;j<info.length();j++)
            {
               if(j-i<=1)
               {
                   dp[i][j]= info.charAt(i)==info.charAt(j);
               }else
               {
                   dp[i][j]= dp[i+1][j-1] && info.charAt(i)==info.charAt(j);
               }
                if(j-i+1>maxlength)
                {
                    maxlength = j-i+1;
                    best[0]=i;
                    best[1]=j;

                }
            }

        }
        String res = info.substring(best[0],best[1]);


    }

    private enum SingleClass{
  INSTANCE;
public void doSomeThing()
{

}

    }
}
