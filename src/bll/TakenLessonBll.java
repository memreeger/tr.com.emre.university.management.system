package bll;

import abst.ITakenLesson;
import dao.TakenLessonDao;
import dto.takenLessonDto.TakenLessonResponseDto;
import model.TakenLesson;

import java.util.ArrayList;
import java.util.List;

public class TakenLessonBll implements ITakenLesson<TakenLessonResponseDto> {
    private static TakenLessonBll instance;

    private TakenLessonBll() {
    }

    public static TakenLessonBll getInstance() {
        if (instance == null) {
            instance = new TakenLessonBll();
        }
        return instance;
    }

    ITakenLesson<TakenLesson> dao = TakenLessonDao.getInstance();

    @Override
    public List<TakenLessonResponseDto> getLessonsByStudentId(int studentId) {
        List<TakenLesson> takenLessons = dao.getLessonsByStudentId(studentId);
        List<TakenLessonResponseDto> responseDtoList = new ArrayList<>();

        for (TakenLesson takenLesson : takenLessons) {
            responseDtoList.add(toResponseDto(takenLesson));
        }
        return responseDtoList;
    }

    @Override
    public void assignLessonToStudent(int studentId, short lessonId) {
        dao.assignLessonToStudent(studentId, lessonId);

    }

    @Override
    public void updateMidterm(int studentId, short lessonId, double midterm) {
        dao.updateMidterm(studentId, lessonId, midterm);
    }

    @Override
    public void updateFinal(int studentId, short lessonId, double finalExam) {
        dao.updateFinal(studentId, lessonId, finalExam);
    }


    @Override
    public void updateGrade(int studentId, short lessonId) {
        dao.updateGrade(studentId, lessonId);
    }

    @Override
    public void removeLessonFromStudent(int studentId, short lessonId) {
        dao.removeLessonFromStudent(studentId, lessonId);
    }

    // HELPER
    private TakenLesson toEntity(TakenLessonResponseDto dto) {
        TakenLesson takenLesson = new TakenLesson();
        double grade = (dto.getMidtermExam() * 0.4) + (dto.getFinalExam() * 0.6);

        takenLesson.setStudentId(dto.getStudentId());
        takenLesson.setLessonId(dto.getLessonId());
        takenLesson.setMidterm(dto.getMidtermExam());
        takenLesson.setFinalExam(dto.getFinalExam());
        takenLesson.setGrade(grade);

        return takenLesson;
    }

    private TakenLessonResponseDto toResponseDto(TakenLesson takenLesson) {
        TakenLessonResponseDto dto = new TakenLessonResponseDto();

        double grade = (takenLesson.getMidterm() * 0.4)
                + (takenLesson.getFinalExam() * 0.6);


        dto.setStudentId(takenLesson.getStudentId());
        dto.setLessonId(takenLesson.getLessonId());
        dto.setMidtermExam(takenLesson.getMidterm());
        dto.setFinalExam(takenLesson.getFinalExam());
        dto.setGrade(grade);

        return dto;

    }
}
