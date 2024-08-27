import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.alibaba.fastjson.JSON;
import com.situ.ws.LeaseSpringApplication;
import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.LogDto;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.entries.SystemLogs;
import com.situ.ws.bean.enums.OrdersStatus;
import com.situ.ws.config.LeaseWebConfigurer;
import com.situ.ws.controller.FileController;
import com.situ.ws.mapper.OrdersMapper;
import com.situ.ws.service.ISystemLogsService;
import com.situ.ws.util.ExcelUtil;
import com.situ.ws.util.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest(classes = LeaseSpringApplication.class)
public class TestSpring {

    @Autowired
    private OrdersMapper mapper;



    @Test
    public void get(){
        List<Orders> list = mapper.selectPage(null,null);

        OrdersStatus status = list.get(0).getStatus();

        System.out.println(status.getName());
        System.out.println(status.getStatus());
        System.out.println("==============");
        System.out.println(list);
    }

    @Test
    public void a(){
        String a = "api/img/426a72c1-2a1f-487c-b395-52394f15fe2f.jpg";

        System.out.println(a.substring(3));
    }

    @Test
    public void b(){
        LocalDate now = LocalDate.now();
        String string = now.toString();
        System.out.println(string);

        // 获取月日
        String substring = string.substring(5);
        System.out.println(substring);
    }

    @Test
    public void c(){

//        System.out.println(LocalDateTime.now());

        SystemLogs logs = new SystemLogs();
        logs.setCreatedAt(LocalDateTime.now());

        String s = JSON.toJSONString(logs);
        System.out.println(s);
    }

    @Test
    public void d(){
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println(format);

    }


    @Autowired
    private ISystemLogsService service;

    @Test
    public void e(){
        Page page = new Page();
        page.setPage(0);
        page.setSize(5);
        LogDto logDto = new LogDto();
        List<LogDto> dtos = service.PageQuery(logDto, page);

        ExcelUtil.exportData(LeaseWebConfigurer.FILE+"test.xls", LogDto.class,
                "所有",dtos);
    }

    @Test
    public void f(){
        long defaultTimeOffset = Snowflake.DEFAULT_TIME_OFFSET;
        System.out.println(defaultTimeOffset);
    }
}
