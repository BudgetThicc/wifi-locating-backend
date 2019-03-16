package cloud.model.section;

import cloud.controller.BaseController;
import cloud.controller.Result;
import cloud.model.classroom.ClassroomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class SectionController extends BaseController {

    ;
    @Resource
    private SectionService sectionService;

    @Resource
    private ClassroomService classroomService;

    @GetMapping(value = { "/section/all" })
    public Result findAll() {
        return new Result("SUCCESS", "find all sections", sectionService.findAll());
    }

    @PostMapping(value = { "/section/create" })
    public Result create(@ModelAttribute Section section) {

        if (isEmpty(section.getRoomName())) {
            return new Result("FAIL", "roomName cannot be empty");
        }

        if (!classroomService.existByRoomName(section.getRoomName())) {
            return new Result("FAIL", "Classroom not exist");
        }

        if (isEmpty(section.getCourseName())) {
            return new Result("FAIL", "courseName cannot be empty");
        }

        if (section.getPeriod() < 1 || section.getPeriod() > 11) {
            return new Result("FAIL", "period not in range (1~11)");
        }

        if (section.getDay() < 1 || section.getDay() > 5) {
            return new Result("FAIL", "day not in range (1~7), 1: Monday");
        }

        if (sectionService.existByDayAndPeriod(section.getDay(), section.getPeriod())) {
            return new Result("FAIL", "This period has already been used");
        }

        sectionService.save(section);
        return new Result("SUCCESS", "Create section", section);
    }

    @GetMapping(value = { "/section/deleteAllByRoomName/{roomName}" })
    public Result deleteByRoomName(@PathVariable("roomName") String roomName) {

        if (isEmpty(roomName)) {
            return new Result("FAIL", "roomName cannot be empty");
        }

        sectionService.deleteAllByRoomName(roomName);
        return new Result("SUCCESS", "Delete all sections by roomName");
    }

    @GetMapping(value = { "/section/deleteAllByCourseName/{courseName}" })
    public Result deleteByCourseName(@PathVariable("courseNameName") String courseName) {

        if (isEmpty(courseName)) {
            return new Result("FAIL", "courseName cannot be empty");
        }

        sectionService.deleteAllByCourseName(courseName);
        return new Result("SUCCESS", "Delete all sections by courseName");
    }

}
