package org.aba2.calendar.common.domain.record.service;

import org.aba2.calendar.common.domain.record.dto.RecordFormRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class RecordServiceTest {
    @Autowired
    private RecordService recordService;

    private RecordFormRequest record;

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
//        for(int i=0; i<100; i++){
//            LocalDate date = LocalDate.now().plusDays(i);
//            RecordFormRequest recordFormRequest = RecordFormRequest.builder()
//                    .createAt(date)
//                    .title("Title"+i)
//                    .content("Content"+i)
//                    .weather("Sunny")
//                    .createAt(date)
//                    .build();
//            recordService.handleRecordSaveOrUpdate(recordFormRequest, "sjhoon1212!");
//        }
        for(int i=0; i<100; i++){
            LocalDate date = LocalDate.now().plusDays(i);
            RecordFormRequest recordFormRequest = RecordFormRequest.builder()
                    .createAt(date)
                    .title("Title"+i)
                    .content("Content"+i)
                    .weather("Sun")
                    .createAt(date)
                    .build();
            recordService.handleRecordSaveOrUpdate(recordFormRequest, "testUser99");
        }
    }

    @Test
    void updateRecord() {
        LocalDate date = LocalDate.now().plusDays(1);
        RecordFormRequest recordFormRequest = RecordFormRequest.builder()
                .createAt(date)
                .title("고칠까"+2)
                .content("말까"+2)
                .weather("Sunny")
                .createAt(date)
                .build();
        recordService.handleRecordSaveOrUpdate(recordFormRequest, "kkkkkk999!");
    }

    @Test
    void deleteDiary() {
        LocalDate date = LocalDate.now().plusDays(1);
        recordService.deleteRecord("sjhoon1212!", date);
    }
}