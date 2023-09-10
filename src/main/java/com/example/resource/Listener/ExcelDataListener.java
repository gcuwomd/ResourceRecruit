package com.example.resource.Listener;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.resource.mapper.ListenerMapper;
import com.example.resource.pojo.ReadExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ExcelDataListener extends AnalysisEventListener<ReadExcel> {

    ListenerMapper listenerMapper; // 替换成你的数据库仓库

    public ExcelDataListener(ListenerMapper listenerMapper) {
        this.listenerMapper = listenerMapper;
    }

    @Override
    public void invoke(ReadExcel data, AnalysisContext context) {
        // 在这里处理 Excel 中的数据，将其保存到数据库
        listenerMapper.insertRecruitUser(data);
        listenerMapper.insertVolunteer1(data);
        listenerMapper.insertVolunteer2(data);
        listenerMapper.insertStatus(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 所有数据解析完毕后的操作
    }
}

