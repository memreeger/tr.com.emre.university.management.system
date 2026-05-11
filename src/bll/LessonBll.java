package bll;

import abst.LessonReadable;
import abst.Writeable;
import dao.LessonDao;
import dto.lessonDto.LessonRequestDto;
import dto.lessonDto.LessonResponseDto;
import model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class LessonBll implements LessonReadable<LessonResponseDto, Short>,
        Writeable<LessonRequestDto, Short> {

    private static LessonBll instance;

    private LessonBll() {
    }

    public static LessonBll getInstance() {
        if (instance == null) {
            instance = new LessonBll();
        }
        return instance;
    }

    private final LessonDao dao = LessonDao.getInstance();

    @Override
    public LessonResponseDto getById(Short id) {
        Lesson lesson = dao.getById(id);
        return toResponseDto(lesson);

    }

    @Override
    public List<LessonResponseDto> getAll() {
        List<Lesson> lessons = dao.getAll();
        List<LessonResponseDto> responseList = new ArrayList<>();

        for (Lesson lesson : lessons) {
            responseList.add(toResponseDto(lesson));
        }
        return responseList;
    }

    @Override
    public boolean existById(Short id) {
        return dao.existById(id);
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public void add(LessonRequestDto obj) {
        Lesson lesson = toEntity(obj);
        dao.add(lesson);
    }

    @Override
    public void update(LessonRequestDto obj, Short id) {
        Lesson lesson = toEntity(obj);
        dao.update(lesson, id);

    }

    @Override
    public void delete(Short id) {
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }


    //HELPER
    private LessonResponseDto toResponseDto(Lesson lesson) {
        LessonResponseDto dto = new LessonResponseDto();

        dto.setId(lesson.getId());
        dto.setName(lesson.getName());
        dto.setCode(lesson.getCode());
        dto.setLessonType(lesson.getLessonType());
        dto.setCredit(lesson.getCredit());
        dto.setAvailable(lesson.isAvailable());

        return dto;
    }

    private Lesson toEntity(LessonRequestDto dto) {
        Lesson lesson = new Lesson();

        lesson.setName(dto.getName());
        lesson.setCode(dto.getCode());
        lesson.setLessonType(dto.getLessonType());
        lesson.setCredit(dto.getCredit());
        lesson.setAvailable(dto.isAvailable());

        return lesson;
    }
}
