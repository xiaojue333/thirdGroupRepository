package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.OrderService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetmealService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<<<<<<< HEAD
import java.io.IOException;
import java.math.BigDecimal;
=======
import javax.xml.crypto.Data;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2020/11/1
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;

    /**
     * 会员数量拆线图
<<<<<<< HEAD
     * @return
     */
    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
=======
     *
     * @return
     */
    @GetMapping("/getMemberReport")
    public Result getMemberReport() {
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
        // 产生12个月的数据, 2020-01
        List<String> months = new ArrayList<String>();
        // 使用日历
        Calendar car = Calendar.getInstance();
        // 过去一年, 年-1
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        car.add(Calendar.YEAR, -1);
        // 遍历12次，依次加1个月
        for (int i = 0; i < 12; i++) {
            // +1个月
<<<<<<< HEAD
            car.add(Calendar.MONTH,1);
=======
            car.add(Calendar.MONTH, 1);
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
            months.add(sdf.format(car.getTime()));
        }

        // 调用服务去查询12个月的数据
        List<Integer> memberCount = memberService.getMemberReport(months);
        // 构建返回的数据
        /**
         * {
         *     flag
         *     message:
         *     data:{
         *         months:
         *         memberCount:
         *     }
         * }
         */
<<<<<<< HEAD
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("months",months);
        resultMap.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,resultMap);
=======
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("months", months);
        resultMap.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, resultMap);
    }

    /**
     * 会员数量拆线图-根据用户输入数据查询
     *
     * @return
     */
    @GetMapping("/getMemberReportByDate")
    public Result getMemberReportByDate(String dateFirst, String dateLate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar car = Calendar.getInstance();
        List<String> months = new ArrayList<String>();
        //根据用户输入的数据 查询成员的数量
        //假如两个都为空--->从今天往前推12个月
        if (dateFirst.equals("null") && dateLate.equals("null")) {
            return getMemberReport();
        }
        //填了两个--->这个时间段内的所有月份
        if (!dateFirst.equals("null") && !dateLate.equals("null")) {
            Date dateFirst1 = null;
            Date dateLate1 = null;
            try {
                //格式化日期
                dateFirst1 = sdf.parse(dateFirst);
                dateLate1 = sdf.parse(dateLate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int result = dateFirst1.compareTo(dateLate1);
            //先判断哪个日期小
            if (result < 0) {
                //dateFirst1 比较小
                //取小的日期放入日历类
                String[] split = dateFirst.split("-");
                Integer year = Integer.valueOf(split[0]);
                Integer month = Integer.valueOf(split[1]);
                car.set(Calendar.YEAR, year);
                car.set(Calendar.MONTH, month-1);

                while (true) {
                    //判断现在的日期是否大于等于较大的日期
                    int compareTo = car.getTime().compareTo(dateLate1);
                    if (compareTo > 0) {
                        break;
                    }
                    // +1个月
                    car.add(Calendar.MONTH, 1);
                    months.add(sdf.format(car.getTime()));
                }

            } else if (result > 0) {
                //dateFirst1 比较小
                //取小的日期放入日历类
                String[] split = dateLate.split("-");
                Integer year = Integer.valueOf(split[0]);
                Integer month = Integer.valueOf(split[1]);
                car.set(Calendar.YEAR, year);
                car.set(Calendar.MONTH, month-1);


                while (true) {
                    //判断现在的日期是否大于等于较大的日期
                    int compareTo = car.getTime().compareTo(dateFirst1);
                    if (compareTo > 0) {
                        break;
                    }
                    // +1个月
                    car.add(Calendar.MONTH, 1);
                    months.add(sdf.format(car.getTime()));
                }

            } else {
                //两个日期相同
                months.add(dateFirst.substring(0,7));
            }
            List<Integer> memberCount = memberService.getMemberReport(months);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("months", months);
            resultMap.put("memberCount", memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, resultMap);
        }

        //假如填了一个--->从这个日期往前推12个月的数据
        if (!dateFirst.equals("null")) {
            String[] split = dateFirst.split("-");
            Integer year = Integer.valueOf(split[0]);
            Integer month = Integer.valueOf(split[1]);
            car.set(Calendar.YEAR, year);
            car.set(Calendar.MONTH, month-1);
            // 过去一年, 年-1
            car.add(Calendar.YEAR, -1);
            // 遍历12次，依次加1个月
            for (int i = 0; i < 12; i++) {
                // +1个月
                car.add(Calendar.MONTH, 1);
                months.add(sdf.format(car.getTime()));
            }
            // 调用服务去查询12个月的数据
            List<Integer> memberCount = memberService.getMemberReport(months);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("months", months);
            resultMap.put("memberCount", memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, resultMap);

        } else {
            String[] split = dateLate.split("-");
            Integer year = Integer.valueOf(split[0]);
            Integer month = Integer.valueOf(split[1]);
            car.set(Calendar.YEAR, year);
            car.set(Calendar.MONTH, month-1);
            // 过去一年, 年-1
            car.add(Calendar.YEAR, -1);
            // 遍历12次，依次加1个月
            for (int i = 0; i < 12; i++) {
                // +1个月
                car.add(Calendar.MONTH, 1);
                months.add(sdf.format(car.getTime()));
            }
            List<Integer> memberCount = memberService.getMemberReport(months);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("months", months);
            resultMap.put("memberCount", memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, resultMap);
        }
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
    }

    /**
     * 套餐预约占比
     */
    @GetMapping("/getSetmealReport")
<<<<<<< HEAD
    public Result getSetmealReport(){
        // 调用服务查询套餐预约占比, map {value, name} name=套餐名称
        List<Map<String,Object>> list = setmealService.getSetmealReport();
        List<String> setmealNames = list.stream().map(m -> (String)m.get("name")).collect(Collectors.toList());
=======
    public Result getSetmealReport() {
        // 调用服务查询套餐预约占比, map {value, name} name=套餐名称
        List<Map<String, Object>> list = setmealService.getSetmealReport();
        List<String> setmealNames = list.stream().map(m -> (String) m.get("name")).collect(Collectors.toList());
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
        //List<String> setmealNames = new ArrayList<String>();
        //for (Map<String, Object> map : list) {
        //    // {value, name: 套餐名称}
        //    String setmealName = (String) map.get("name");
        //    setmealNames.add(setmealName);
        //}
        /**
         * {
         *     flag
         *     message
         *     data:{
         *         setmealNames: ['名称'...],
         *         setmealCount: [{value,name}] list
         *     }
         * }
         */
        // 构建前端需要的数据格式
<<<<<<< HEAD
        Map<String,Object> resultMap = new HashMap<String,Object>(2);
        resultMap.put("setmealNames", setmealNames);
        resultMap.put("setmealCount", list);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,resultMap);
=======
        Map<String, Object> resultMap = new HashMap<String, Object>(2);
        resultMap.put("setmealNames", setmealNames);
        resultMap.put("setmealCount", list);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, resultMap);
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
    }

    /**
     * 获取运营数据统计
<<<<<<< HEAD
     * @return
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        // 调用服务查询
        Map<String,Object> reportData = reportService.getBusinessReportData();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,reportData);
=======
     *
     * @return
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        // 调用服务查询
        Map<String, Object> reportData = reportService.getBusinessReportData();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, reportData);
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
    }

    /**
     * 导出excel
     */
    @GetMapping("/exportBusinessReport")
<<<<<<< HEAD
    public Result exportBusinessReport(HttpServletRequest req, HttpServletResponse res){
=======
    public Result exportBusinessReport(HttpServletRequest req, HttpServletResponse res) {
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
        //- 获取模板所在
        String templatePath = req.getSession().getServletContext().getRealPath("/template/report_template.xlsx");
        //- 获取报表数据
        Map<String, Object> reportData = reportService.getBusinessReportData();
        //- 创建Workbook传模板所在路径
<<<<<<< HEAD
        try(Workbook wk = new XSSFWorkbook(templatePath)) {
=======
        try (Workbook wk = new XSSFWorkbook(templatePath)) {
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
            //- 获取工作表
            Sheet sht = wk.getSheetAt(0);
            //- 获取行，单元格，设置相应的数据
            sht.getRow(2).getCell(5).setCellValue((String) reportData.get("reportDate"));
            //  ================= 会员数量 ================
            sht.getRow(4).getCell(5).setCellValue((Integer) reportData.get("todayNewMember"));
            sht.getRow(4).getCell(7).setCellValue((Integer) reportData.get("totalMember"));
            sht.getRow(5).getCell(5).setCellValue((Integer) reportData.get("thisWeekNewMember"));
            sht.getRow(5).getCell(7).setCellValue((Integer) reportData.get("thisMonthNewMember"));

            // =================== 预约到诊数量 =====================
            sht.getRow(7).getCell(5).setCellValue((Integer) reportData.get("todayOrderNumber"));
            sht.getRow(7).getCell(7).setCellValue((Integer) reportData.get("todayVisitsNumber"));
            sht.getRow(8).getCell(5).setCellValue((Integer) reportData.get("thisWeekOrderNumber"));
            sht.getRow(8).getCell(7).setCellValue((Integer) reportData.get("thisWeekVisitsNumber"));
            sht.getRow(9).getCell(5).setCellValue((Integer) reportData.get("thisMonthOrderNumber"));
            sht.getRow(9).getCell(7).setCellValue((Integer) reportData.get("thisMonthVisitsNumber"));

            // ================== 热门套餐，遍历输出填值 ================
<<<<<<< HEAD
            List<Map<String,Object>> hotSetmealList = (List<Map<String,Object>>)reportData.get("hotSetmeal");
=======
            List<Map<String, Object>> hotSetmealList = (List<Map<String, Object>>) reportData.get("hotSetmeal");
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
            int rowIndex = 12;
            for (Map<String, Object> setmeal : hotSetmealList) {
                sht.getRow(rowIndex).getCell(4).setCellValue(((String) setmeal.get("name")));
                //  - 数量的类型为Long
<<<<<<< HEAD
                sht.getRow(rowIndex).getCell(5).setCellValue((Long)setmeal.get("setmeal_count"));
                //  - 占比的值的类型为bigdecimal，转成dubbo
                BigDecimal proportion = (BigDecimal) setmeal.get("proportion");
                sht.getRow(rowIndex).getCell(6).setCellValue(proportion.doubleValue());
                sht.getRow(rowIndex).getCell(7).setCellValue((String)setmeal.get("remark"));
=======
                sht.getRow(rowIndex).getCell(5).setCellValue((Long) setmeal.get("setmeal_count"));
                //  - 占比的值的类型为bigdecimal，转成dubbo
                BigDecimal proportion = (BigDecimal) setmeal.get("proportion");
                sht.getRow(rowIndex).getCell(6).setCellValue(proportion.doubleValue());
                sht.getRow(rowIndex).getCell(7).setCellValue((String) setmeal.get("remark"));
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
                rowIndex++;
            }
            //
            //- 设置响应体内容的格式application/vnd.ms-excel
            res.setContentType("application/vnd.ms-excel");
            String filename = "运营数据统计.xlsx";
            byte[] bytes = filename.getBytes();
            filename = new String(bytes, "ISO-8859-1");
            //- 设置响应头信息，告诉浏览器下载的文件名叫什么 Content-Disposition, attachment;filename=文件名
<<<<<<< HEAD
            res.setHeader("Content-Disposition","attachment;filename=" + filename);
=======
            res.setHeader("Content-Disposition", "attachment;filename=" + filename);
>>>>>>> 52e5152ed6763a8dac362a4bade870cd3aa60995
            //- Workbook.write响应输出流
            wk.write(res.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
