package app.dtos;

import app.entities.Instructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class InstructorDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private int yearsOfExperience;
    private Set<SkiLessonDTO> lessons = new HashSet<>();

    public InstructorDTO(Instructor instructor) {
        this.id = instructor.getId();
        this.firstname = instructor.getFirstname();
        this.lastname = instructor.getLastname();
        this.email = instructor.getEmail();
        this.phone = instructor.getPhone();
        this.yearsOfExperience = instructor.getYearsOfExperience();
        if (instructor.getSkiLessons() != null)
            instructor.getSkiLessons().forEach(lesson -> lessons.add(new SkiLessonDTO(lesson)));
    }

    public InstructorDTO(String firstname, String lastname, String email, String phone, int yearsOfExperience) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    public static List<InstructorDTO> toDTOList(List<Instructor> instructors) {
        return instructors.stream().map(InstructorDTO::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstructorDTO dto)) return false;
        return getId().equals(dto.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}