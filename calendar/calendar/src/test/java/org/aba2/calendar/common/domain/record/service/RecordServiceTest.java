package org.aba2.calendar.common.domain.record.service;

import org.aba2.calendar.common.domain.record.dto.RecordFormRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class RecordServiceTest {
    @Autowired
    private RecordService recordService;

    private RecordFormRequest record;

    @BeforeEach
    void setUp() {
        record = new RecordFormRequest();
        record.setWeather("Sunny");
    }

//    pagenation으로 바꿔서 이건 못쓴다.
//    @Test
//    void getDiaryList() {
//        //when
//        List<DiaryEntity> list = diaryService.getDiaryList("Test1");
//
//        //then
//        assertNotNull(list);
//        assertEquals(100, list.size());
//        //딱 100개. Test1아이디로 만들어진 일기의 갯수가 100개다.
//    }

    @Test
    void getDiaryDetail() {
        //given

        //when

        //then
    }

    //오늘부터 100일 이후까지 만들기
    @Test
    void createRecord() {
        //given
        for(int i=0; i<100; i++){
            LocalDate date = LocalDate.now().plusDays(i);
            record.setTitle("test"+i);
            record.setContent("test"+i);
            record.setCreateAt(date);
            recordService.handleRecordSaveOrUpdate(record, "sjhoon1212!");
        }
    }

    @Test
    void updateRecord() {
    }

    @Test
    void deleteDiary() {
    }
}