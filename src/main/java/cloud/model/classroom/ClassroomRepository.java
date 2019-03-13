package cloud.model.classroom;

import org.springframework.data.repository.CrudRepository;

public interface ClassroomRepository extends CrudRepository<Classroom, Long> {

    public Classroom findByRoomName(String roomName);

    public Classroom findByMacAddress(String macAddress);

    public void deleteByRoomName(String roomName);
}
